package com.fererlab.oo.restful;


import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/computer")
@Produces({"application/json"})
@Consumes({"application/json"})
public class ComputerResource {
}