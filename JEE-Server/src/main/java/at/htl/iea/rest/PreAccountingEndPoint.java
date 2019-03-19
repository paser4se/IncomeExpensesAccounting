package at.htl.iea.rest;

import at.htl.iea.model.Assignment;
import at.htl.iea.model.Category;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
                tmp = new Category("--> " + subcat.getName());
                tmp.setId(subcat.getId());
                tmpCat.add(tmp);
            }
        }
        return Response.ok(tmpCat, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/assignment/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAssignments(@PathParam("id") long id) {
        Assignment assignment = null;
        try{
            TypedQuery query = em.createNamedQuery("Assignment.getByCat", Assignment.class).setParameter(1, id);
            assignment = (Assignment)query.getSingleResult();
        }
        catch (NoResultException nre){

        }

        JsonArrayBuilder array = Json.createArrayBuilder();
        if (assignment != null) {
            for (String keyword : assignment.getKeywords()) {
                JsonObjectBuilder obj = Json.createObjectBuilder();
                obj.add("keyword", keyword);
                array.add(obj.build());
            }
        }

        return Response.ok(array.build(), MediaType.APPLICATION_JSON).build();
    }
}
