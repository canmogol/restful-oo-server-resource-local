package com.fererlab.oo.restful;


import com.fererlab.oo.commons.restful.AbstractResource;
import com.fererlab.oo.commons.restful.EntityManagerAwareInterceptor;
import com.fererlab.oo.model.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import java.util.Random;

@Path("/user")
@Produces({"application/json"})
@Consumes({"application/json"})
@LocalBean
@Stateless(name = "UserResource", mappedName = "UserResource")
@Interceptors({EntityManagerAwareInterceptor.class})
public class UserResource extends AbstractResource<User> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @GET
    @Path("/findTest/{id}")
    public User findTest(@PathParam("id") Integer id) throws Exception {
        User user = BuildModel();
        user.setId(id);
        user.find();
        System.out.println("user = " + user);

        user.setUsername("guest" + (new Random().nextDouble()));
        user.update();
        System.out.println("user = " + user);

        return user;
    }

    @GET
    @Path("/testNewModel/{id}")
    public User testNewModel(@PathParam("id") Integer id) throws Exception {
        User user = new User();
        user.setEntityManager(getEntityManager());
        user.setId(id);
        user.find();
        System.out.println("user = " + user);

        user.setUsername("acm");
        user.update();
        System.out.println("user = " + user);

        return user;
    }

}