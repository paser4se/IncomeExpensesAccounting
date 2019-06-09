package at.htl.iea.rest;

import at.htl.iea.dao.CategoryDao;
import at.htl.iea.model.Assignment;
import at.htl.iea.model.Category;
import org.json.JSONArray;

import javax.inject.Inject;
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

    @Inject
    CategoryDao categoryDao;

    @GET
    @Path("/category")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        List<Category> categories = categoryDao.getSortedCategories();
        JsonArrayBuilder cats = Json.createArrayBuilder();
        for (Category category : categories) {
            JsonObjectBuilder cat = Json.createObjectBuilder();
            cat.add("id", category.getId());
            cat.add("text", category.getName());
            cat.add("isSelected", false);
            cat.add("parentId", -1);
            JsonArrayBuilder subCats = Json.createArrayBuilder();
            for (Category subcat : category.getSubcategories()) {
                JsonObjectBuilder subCat = Json.createObjectBuilder();
                subCat.add("id", subcat.getId());
                subCat.add("text", subcat.getName());
                subCat.add("parentId", category.getId());

                subCats.add(subCat);
            }
            if (category.getSubcategories().size() >= 1) {
                cat.add("expanded", true);
                cat.add("items", subCats);
            } else {
                cat.add("expanded", false);
            }

            cats.add(cat);
        }
        return Response.ok(cats.build(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/assignment/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAssignments(@PathParam("id") Long id) {
        Assignment assignment = getAssignment(id);

        if(assignment == null) {
            return Response.noContent().build();
        }

        return Response.ok(assignment.getKeywords().toArray()).build();
    }

    @POST
    @Path("/assignment/{id}")
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    public Response changeAssigment(@PathParam("id") Long id, String keywords) {
        JSONArray keywordArray = new JSONArray(keywords);
        Assignment assignment = getAssignment(id);
        assignment.setKeywords(new HashSet<>());

        for(int i = 0; i < keywordArray.length(); i++) {
            String keyword = keywordArray.get(i).toString().replaceAll("\"", "");
            assignment.addKeyword(keyword);
        }

        return Response.ok().build();
    }

    @POST
    @Path("/assignment/addkeyword/{id}")
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addKeyword(@PathParam("id") Long id, String keyword) {
        try {
            Assignment assignment = categoryDao.getAssignmentByCategory(id);
            assignment.addKeyword(keyword);
            categoryDao.flush();
        } catch (Exception ex) {
            return Response.noContent().build();
        }

        return Response.ok("Added Keyword").build();
    }

    public Assignment getAssignment(Long id) {
        Assignment assignment = null;
        try{
            assignment = categoryDao.getAssignmentByCategory(id);
        }
        catch (NoResultException nre){

        }

        return assignment;
    }
}
