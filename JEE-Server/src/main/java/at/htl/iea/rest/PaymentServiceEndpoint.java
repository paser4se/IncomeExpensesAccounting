package at.htl.iea.rest;

import at.htl.iea.business.Parser;
import at.htl.iea.model.Category;
import at.htl.iea.model.Payment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
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

    @POST
    @Path("/changecategory")
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    public Response changeCategory(String ids) {
        Long paymentId = Long.parseLong(ids.split(";")[0]);
        Long categoryId = Long.parseLong(ids.split(";")[1]);

        Payment payment = em.find(Payment.class, paymentId);
        Category category = em.find(Category.class, categoryId);
        payment.setCategory(category);
        em.flush();

        return Response.ok("Category changed").build();
    }
}
