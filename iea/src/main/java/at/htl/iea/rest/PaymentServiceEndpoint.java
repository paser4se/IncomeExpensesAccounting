package at.htl.iea.rest;

import at.htl.iea.business.PaymentUtils;
import at.htl.iea.dao.CategoryDao;
import at.htl.iea.dao.PaymentDao;
import at.htl.iea.dao.TempPaymentDao;
import at.htl.iea.model.Assignment;
import at.htl.iea.model.Category;
import at.htl.iea.model.Payment;
import at.htl.iea.model.TempPayment;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("payments")
public class PaymentServiceEndpoint {
    @Inject
    PaymentDao paymentDao;
    @Inject
    CategoryDao categoryDao;
    @Inject
    TempPaymentDao tempPaymentDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPayments() {
        List<Payment> payments = paymentDao.listAll();
        return Response.ok(PaymentUtils.getInstance().getAllPayments(payments), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/temp")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAllTempPayments() {
        List<TempPayment> payments = tempPaymentDao.listAll();
        return Response.ok().entity(PaymentUtils.getInstance().getAllTempPayments(payments)).build();
    }

    @POST
    @Path("/changecategory/{id}")
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    public Response changeCategory(@PathParam("id") Long id, String catId) {
        JSONObject obj = new JSONObject(catId);
        Long categoryId = Long.parseLong(obj.getString("catId"));

        TempPayment payment = tempPaymentDao.findById(id);
        Category category = categoryDao.getCategoryById(categoryId);
        payment.setCategory(category);
        tempPaymentDao.merge(payment);
        tempPaymentDao.flush();
        categoryDao.flush();

        return Response.ok("Category changed").build();
    }

    @POST
    @Path("/addcategory")
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addCategory(String categoryname) {
        try {
            Category category = new Category(categoryname);
            categoryDao.saveCategory(category);
            Assignment assignment = new Assignment(category);
            categoryDao.saveAssignment(assignment);
            categoryDao.flush();
        } catch (Exception ex) {
            return Response.noContent().build();
        }

        return Response.ok("Added Keyword").build();
    }

    @POST
    @Path("/addcategory/{id}")
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addSubCategory(@PathParam("id") Long id, String categoryname) {
        try {
            Category parentCategory = categoryDao.getCategoryById(id);
            Category category = new Category(categoryname);
            categoryDao.saveCategory(category);
            parentCategory.addSubcategory(category);
            Assignment assignment = new Assignment(category);
            categoryDao.saveAssignment(assignment);
            categoryDao.flush();
        } catch (Exception ex) {
            return Response.noContent().build();
        }

        return Response.ok("Added Keyword").build();
    }
}
