package at.htl.iea.rest;

import at.htl.iea.model.Assignment;
import at.htl.iea.model.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Path("preaccounting")
public class PreAccountingEndPoint {

    @PersistenceContext
    EntityManager em;

    @GET
    @Path("/category")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        List<Category> categories = em.createNamedQuery("Category.getSortedCategories", Category.class).getResultList();
        List<Category> tmpCat = new LinkedList<>();
        for (Category category : categories) {
            Category tmp = new Category(category.getName());
            tmp.setId(category.getId());
            tmpCat.add(tmp);
            for (Category subcat : category.getSubcategories()) {
                tmp = new Category(subcat.getName());
                tmp.setId(subcat.getId());
                tmpCat.add(tmp);
            }
        }
        return Response.ok(tmpCat, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/assignment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAssignments() {
        List<Assignment> assignments = em.createNamedQuery("Assignment.getAll", Assignment.class).getResultList();
        return Response.ok(assignments, MediaType.APPLICATION_JSON).build();
    }
}
