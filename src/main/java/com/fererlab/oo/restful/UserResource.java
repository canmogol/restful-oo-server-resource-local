package com.fererlab.oo.restful;


import com.fererlab.oo.model.User;

import javax.ws.rs.*;
import java.util.List;

@Path("/user")
@Produces({"application/json"})
@Consumes({"application/json"})
public class UserResource {

    @POST
    public void create(User user) throws Exception {
        user.create();
    }

    @PUT
    public void update(User user) throws Exception {
        user.update();
    }

    @DELETE
    public void delete(User user) throws Exception {
        user.delete();
    }

    @GET
    @Path("/{id}")
    public User find(@PathParam("id") Integer id) throws Exception {
        User model = new User();
        model.setId(id);
        model.find();
        return model;
    }

    @GET
    @Path("/")
    public List<User> findAll() throws Exception {
        return new User().findAll();
    }

    @GET
    @Path("/login/{username}/{password}")
    public User login(@PathParam("username") String username, @PathParam("password") String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login();
        return user;
    }

}