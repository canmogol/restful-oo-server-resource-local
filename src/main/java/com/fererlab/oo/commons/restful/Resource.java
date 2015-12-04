package com.fererlab.oo.commons.restful;

import com.fererlab.oo.commons.model.BaseModel;

import javax.ws.rs.PathParam;
import java.util.List;

public interface Resource<M extends BaseModel> {

    void create(M model) throws Exception;

    void update(M model) throws Exception;

    void delete(M model) throws Exception;

    M find(@PathParam("id") Integer id) throws Exception;

    List<M> findAll() throws Exception;

}
