package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.*;

@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @Inject
    Service service;

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/counters")
    public String testCounters() {
        return String.format("A counter is %d and B counter is %d",
                service.getACounter(), service.getBCounter());
    }

    public ExampleResource() {
        System.out.println("ExampleResource constructor called.");
    }
}
