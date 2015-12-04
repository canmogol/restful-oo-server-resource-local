package com.fererlab.oo.commons.restful;

import com.fererlab.oo.commons.model.BaseModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractResource<M extends BaseModel> implements Resource<M> {

    public abstract Class<M> getEntityClass();

    @POST
    @Override
    public void create(M model) throws Exception {
        model.create();
    }

    @PUT
    @Override
    public void update(M model) throws Exception {
        model.update();
    }

    @DELETE
    @Override
    public void delete(M model) throws Exception {
        model.delete();
    }

    @GET
    @Override
    @Path("/{id}")
    public M find(@PathParam("id") Integer id) throws Exception {
        M model = BuildModel();
        model.setId(id);
        model.find();
        return model;
    }

    @GET
    @Override
    @Path("/")
    public List<M> findAll() throws Exception {
        M model = BuildModel();
        return model.findAll();
    }


    @PersistenceContext
    private EntityManager entityManager;

    public M BuildModel() throws IllegalAccessException, InstantiationException {
        M model = getEntityClass().newInstance();
        model.setEntityManager(entityManager);
        return model;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
