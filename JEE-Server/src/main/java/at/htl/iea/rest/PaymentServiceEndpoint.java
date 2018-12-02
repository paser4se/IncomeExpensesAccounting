package at.htl.iea.rest;

import at.htl.iea.database.Database;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("payments")
public class PaymentServiceEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPayments() {
        return Response.ok(Database.getInstance().getAllPayments(), MediaType.APPLICATION_JSON).build();
    }

}
