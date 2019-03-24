package at.htl.iea.rest;

import at.htl.iea.model.Assignment;
import at.htl.iea.model.Category;

import javax.json.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.HashSet;
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
                tmp.setParentCategoryId(subcat.getParentCategoryId());
                tmpCat.add(tmp);
            }
        }
        return Response.ok(tmpCat, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/assignment/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAssignments(@PathParam("id") long id) {
        Assignment assignment = getAssignment(id);

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

    @POST
    @Path("/assignment/{id}")
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    public Response changeAssigment(@PathParam("id") long id, String arr) {
        Assignment assignment = getAssignment(id);
        assignment.setKeywords(new HashSet<>());

        JsonReader jsonReader = Json.createReader(new StringReader(arr));
        JsonArray keywords = jsonReader.readArray();
        for (JsonValue val : keywords) {
            String keyword = val.asJsonObject().getString("keyword");
            assignment.addKeyword(keyword);
        }

        return Response.ok("Keywords changed").build();
    }

    @POST
    @Path("/assignment/addkeyword/{id}")
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addKeyword(@PathParam("id") long id, String keyword) {
        try {
            Assignment assignment = em.createNamedQuery("Assignment.getByCat", Assignment.class).setParameter(1, id).getSingleResult();
            assignment.addKeyword(keyword);
            em.flush();
        } catch (Exception ex) {
            return Response.noContent().build();
        }

        return Response.ok("Added Keyword").build();
    }

    public Assignment getAssignment(long id) {
        Assignment assignment = null;
        try{
            TypedQuery query = em.createNamedQuery("Assignment.getByCat", Assignment.class).setParameter(1, id);
            assignment = (Assignment)query.getSingleResult();
        }
        catch (NoResultException nre){

        }

        return assignment;
    }
}
