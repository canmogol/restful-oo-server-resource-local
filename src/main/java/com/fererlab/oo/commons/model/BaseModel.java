package com.fererlab.oo.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fererlab.oo.commons.model.interfaces.Model;
import com.fererlab.oo.commons.model.interfaces.Persistable;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@XmlRootElement
@MappedSuperclass
public abstract class BaseModel<M extends BaseModel, PK> implements Model<PK>, Persistable<M, PK> {

    @Transient
    private final Class<M> entityClass;
    @Transient
    @JsonIgnore
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("OOPDB");
    @Transient
    @JsonIgnore
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public BaseModel() {
        entityClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void create() {
        try {
            beginTransaction();
            getEntityManager().persist(this);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update() {
        try {
            beginTransaction();
            Object m = getEntityManager().merge(this);
            copy((M) m);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();

        }
    }

    @Override
    public void delete() {
        try {
            beginTransaction();
            if (!getEntityManager().contains(this)) {
                getEntityManager().remove(getEntityManager().merge(this));
            } else {
                getEntityManager().remove(this);
            }
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void find() throws Exception {
        M m = getEntityManager().find(entityClass, getId());
        if (m == null) {
            throw new Exception("No entity found with this id: " + getId());
        }
        copy(m);
    }

    @Override
    public List<M> findAll() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<M> criteriaQuery = builder.createQuery(entityClass);
        Root<M> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        List<M> list = getEntityManager().createQuery(criteriaQuery).getResultList();
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PK count() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PK> criteriaQuery = (CriteriaQuery<PK>) builder.createQuery();
        Root<M> root = criteriaQuery.from(entityClass);
        if (AuditModel.class.equals(entityClass)) {
            criteriaQuery.where(builder.equal(root.get("deleted"), false));
        }
        criteriaQuery.select((Selection<? extends PK>) builder.count(root));
        PK count = getEntityManager().createQuery(criteriaQuery).getSingleResult();
        return count;
    }

    public void copy(M copyFromModel) {
        try {
            BeanUtils.copyProperties(this, copyFromModel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    private void beginTransaction() {
        if (!getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().begin();
        }
    }

    private void commitTransaction() {
        if (getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().commit();
        }
    }

    private void rollbackTransaction() {
        if (getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + getId() +
                '}';
    }

}
