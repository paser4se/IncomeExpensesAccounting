package at.htl.iea.rest;

import at.htl.iea.dao.AssignmentDao;
import at.htl.iea.dao.CategoryDao;
import at.htl.iea.dao.PaymentDao;
import at.htl.iea.dao.TempPaymentDao;
import at.htl.iea.model.Assignment;
import at.htl.iea.model.Category;
import at.htl.iea.model.Payment;
import at.htl.iea.model.TempPayment;
import at.htl.iea.model.enums.WriteOffUnit;
import org.json.JSONArray;
import org.json.JSONObject;

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
    @Inject
    PaymentDao paymentDao;
    @Inject
    TempPaymentDao tempPaymentDao;
    @Inject
    AssignmentDao assignmentDao;

    @GET
    @Path("/category")
    @Produces(MediaType.TEXT_PLAIN)
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
        return Response.ok().entity(cats.build()).build();
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
            Assignment assignment = assignmentDao.getByCategory(id);
            assignment.addKeyword(keyword);
            assignmentDao.merge(assignment);
            categoryDao.flush();
        } catch (Exception ex) {
            return Response.noContent().build();
        }

        return Response.ok("Added Keyword").build();
    }

    @POST
    @Path("/writeoff/{id}")
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    public Response writeOff(@PathParam("id") Long id, String infos) {
        JSONObject infoObject = new JSONObject(infos);
        String unit = infoObject.get("wunit").toString();
        int writeOffNumber = Integer.parseInt(infoObject.get("wnum").toString());
        TempPayment payment = tempPaymentDao.findById(id);
        WriteOffUnit writeOffUnit = WriteOffUnit.NONE;
        switch (unit) {
            case "Month":
                writeOffUnit = WriteOffUnit.MONTH;
                break;
            case "Quarter":
                writeOffUnit = WriteOffUnit.QUARTER;
                break;
            case "Year":
                writeOffUnit = WriteOffUnit.YEAR;
                break;
            //missing default case
            default:
            // add default case
                break;

        }

        payment.setWriteOffUnit(writeOffUnit);
        payment.setWriteOffNumber(writeOffNumber);
        tempPaymentDao.merge(payment);

        return Response.ok().build();
    }

    @POST
    @Path("/commit")
    @Transactional
    public Response commitPayments() {
        List<TempPayment> payments = tempPaymentDao.listAll();

        for (int i = 0; i < payments.size(); i++) {
            Payment currentPayment = convertToPayment(payments.get(i));

            if (currentPayment.getWriteOffUnit() != WriteOffUnit.NONE) {
                List<Payment> newPayments = new LinkedList<>();
                for (int a = 0; a < currentPayment.getWriteOffNumber(); a++) {
                    Payment tmpPayment = null;
                    try {
                        tmpPayment = (Payment) currentPayment.clone();
                        tmpPayment.setId(new Payment().getId());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    tmpPayment.setAmount(tmpPayment.getAmount()/currentPayment.getWriteOffNumber());
                    if (a > 0) {
                        switch (currentPayment.getWriteOffUnit()) {
                            case MONTH:
                                tmpPayment.setBookingDate(newPayments.get(a-1).getBookingDate().plusMonths(1));
                                tmpPayment.setValueDate(newPayments.get(a-1).getValueDate().plusMonths(1));
                                break;
                            case QUARTER:
                                tmpPayment.setBookingDate(newPayments.get(a-1).getBookingDate().plusMonths(3));
                                tmpPayment.setValueDate(newPayments.get(a-1).getValueDate().plusMonths(3));
                                break;
                            case YEAR:
                                tmpPayment.setBookingDate(newPayments.get(a-1).getBookingDate().plusYears(1));
                                tmpPayment.setValueDate(newPayments.get(a-1).getValueDate().plusYears(1));
                                break;
                            //missing default case
                            default:
                                // add default case
                                break;
                            
                        }
                    }
                    newPayments.add(tmpPayment);
                }
                for (int j = 0; j < currentPayment.getWriteOffNumber(); j++) {
                    paymentDao.savePayment(newPayments.get(j));
                    if (j-1 >= 0) {
                        newPayments.get(j).setPreviousPayment(newPayments.get(j-1));
                        paymentDao.merge(newPayments.get(j));
                    }

                }
            } else {
                paymentDao.savePayment(currentPayment);
            }
        }
        tempPaymentDao.deleteAll();

        return Response.ok().build();
    }

    private Payment convertToPayment(TempPayment tempPayment) {
        Payment payment = new Payment();

        payment.setAmount(tempPayment.getAmount());
        payment.setBookingDate(tempPayment.getBookingDate());
        payment.setBookingText(tempPayment.getBookingText());
        payment.setCategory(tempPayment.getCategory());
        payment.setCurrency(tempPayment.getCurrency());
        payment.setInitialRecognitionReference(tempPayment.getInitialRecognitionReference());
        payment.setNote(tempPayment.getNote());
        payment.setPartnerAccountNumber(tempPayment.getPartnerAccountNumber());
        payment.setPartnerBankCode(tempPayment.getPartnerBankCode());
        payment.setPartnerBic(tempPayment.getPartnerBic());
        payment.setPartnerIban(tempPayment.getPartnerIban());
        payment.setPartnerName(tempPayment.getPartnerName());
        payment.setValueDate(tempPayment.getValueDate());
        payment.setWriteOffNumber(tempPayment.getWriteOffNumber());
        payment.setWriteOffUnit(tempPayment.getWriteOffUnit());

        return payment;
    }

    public Assignment getAssignment(Long id) {
        Assignment assignment = null;
        try{
            assignment = assignmentDao.getByCategory(id);
        }
        catch (NoResultException nre){
        }

        return assignment;
    }
}
