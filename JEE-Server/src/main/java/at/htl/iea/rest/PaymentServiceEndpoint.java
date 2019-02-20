package at.htl.iea.rest;

import at.htl.iea.business.Parser;
import at.htl.iea.model.Payment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("payments")
public class PaymentServiceEndpoint {
    @PersistenceContext
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPayments() {
        List<Payment> payments = em.createNamedQuery("Payments.findAllUnevaluated", Payment.class).getResultList();
        return Response.ok(Parser.getInstance().getAllPayments(payments), MediaType.APPLICATION_JSON).build();
    }

}
