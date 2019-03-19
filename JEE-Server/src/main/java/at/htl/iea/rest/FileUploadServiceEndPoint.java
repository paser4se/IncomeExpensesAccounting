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
import java.text.ParseException;
import java.util.List;

@Path("files")
public class  FileUploadServiceEndPoint {

    @PersistenceContext
    EntityManager em;

    @GET
    public Response hello() {
        return Response.status(Response.Status.OK).entity("Connected to project IEA ...").build();
    }

    @POST
    @Path("uploadtext")
    @Transactional
    @Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response uploadText(String content) {
        try {
            List<Payment> paymentList = Parser.getInstance().persist(content);
            for (Payment p : paymentList) {
                p.setCategory(em.createNamedQuery("Category.getByName", Category.class).setParameter(1, "Sonstiges").getSingleResult());
                em.persist(p);
            }
            em.flush();
        } catch (ParseException e) {
            System.err.println("ParseException: " + e.getMessage());
            return Response.serverError().build();
        } catch (NoSuchFieldException e) {
            System.err.println("NoSuchFieldException: " + e.getMessage());
            return Response.serverError().build();
        }
        return Response.ok("content recveived").build();
    }
}