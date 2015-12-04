package com.fererlab.oo.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fererlab.oo.commons.model.interfaces.EntityManagerAware;
import com.fererlab.oo.commons.model.interfaces.Model;
import com.fererlab.oo.commons.model.interfaces.Persistable;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
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
public abstract class BaseModel<M extends BaseModel, PK> implements Model<PK>, Persistable<M, PK>, EntityManagerAware {

    @Transient
    private final Class<M> entityClass;
    @Transient
    @JsonIgnore
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public BaseModel() {
        entityClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void create() {
        entityManager.persist(this);
    }

    @Override
    public void update() {
        entityManager.merge(this);
    }

    @Override
    public void delete() {
        if (!entityManager.contains(this)) {
            entityManager.remove(entityManager.merge(this));
        } else {
            entityManager.remove(this);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void find() throws Exception {
        M m = entityManager.find(entityClass, getId());
        if (m == null) {
            throw new Exception("No entity found with this id: " + getId());
        }
        m.setEntityManager(entityManager);
        copy(m);
    }

    @Override
    public List<M> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<M> criteriaQuery = builder.createQuery(entityClass);
        Root<M> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        List<M> list = entityManager.createQuery(criteriaQuery).getResultList();
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PK count() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PK> criteriaQuery = (CriteriaQuery<PK>) builder.createQuery();
        Root<M> root = criteriaQuery.from(entityClass);
        if (AuditModel.class.equals(entityClass)) {
            criteriaQuery.where(builder.equal(root.get("deleted"), false));
        }
        criteriaQuery.select((Selection<? extends PK>) builder.count(root));
        PK count = entityManager.createQuery(criteriaQuery).getSingleResult();
        return count;
    }

    private void copy(M copyFromModel) {
        try {
            BeanUtils.copyProperties(this, copyFromModel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + getId() +
                '}';
    }
}
