package at.htl.iea.rest;

import at.htl.iea.business.PaymentUtils;
import at.htl.iea.business.parser.Camt053Parser;
import at.htl.iea.business.parser.model.*;
import at.htl.iea.dao.AssignmentDao;
import at.htl.iea.dao.CategoryDao;
import at.htl.iea.dao.TempPaymentDao;
import at.htl.iea.model.Assignment;
import at.htl.iea.model.Category;
import at.htl.iea.model.TempPayment;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Set;

@Path("files")
public class  FileUploadServiceEndPoint {

    @Inject
    CategoryDao categoryDao;

    @Inject
    TempPaymentDao tempPaymentDao;

    @Inject
    AssignmentDao assignmentDao;

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
            Camt053Parser camt053Parser = new Camt053Parser();

            List<TempPayment> paymentList = PaymentUtils.getInstance().documentToPayment(camt053Parser.parse(content));
            for (TempPayment p : paymentList) {
                String automaticallyAssignedCategory = getCategoryAssignedByBookingText(p.getBookingText());

                p.setCategory(categoryDao.find("name = ?1", automaticallyAssignedCategory).firstResult());
                tempPaymentDao.savePayment(p);
            }
            tempPaymentDao.flush();
        } catch (JAXBException e) {
            System.err.println("Parse Exception: " + e.getMessage());
        }
        return Response.ok("content recveived").build();
    }

    private String getCategoryAssignedByBookingText(String bookingText) {
        String categoryText = "Sonstiges";
        List<Category> categories = categoryDao.findAll().list();

        for (Category c : categories){
            Assignment assignment = assignmentDao.getByCategory(c.getId());
            Set<String> keywords = assignment.getKeywords();
            for (String s : keywords){
                if (bookingText.toLowerCase().contains(s.toLowerCase())){
                    assignment.setCategoryKeyword(s);
                    categoryText = categoryDao.getByAssignmentKeyword(s).getName();
                }
            }
        }

        return categoryText;

    }

}