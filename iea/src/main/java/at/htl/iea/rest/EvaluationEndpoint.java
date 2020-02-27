package at.htl.iea.rest;

import at.htl.iea.dao.PaymentDao;
import at.htl.iea.model.Evaluation;
import at.htl.iea.model.Payment;
import at.htl.iea.rest.auth.Secured;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Path("evaluation")
public class EvaluationEndpoint {
    @Inject
    PaymentDao paymentDao;

    @GET
    @Path("/income")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvaluationIncome(){
        return Response.ok(getEvaluations("income", LocalDateTime.MIN, LocalDateTime.MIN)).build();
    }

    @GET
    @Path("/expenses")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvaluationExpenses(){
        return Response.ok(getEvaluations("expenses", LocalDateTime.MIN, LocalDateTime.MIN)).build();
    }

    @POST
    @Path("/filterincome")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilteredIncome(String body) {
        JSONObject json = new JSONObject(body);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime from = LocalDate.parse(json.getString("from"), formatter).atStartOfDay();
        LocalDateTime to = LocalDate.parse(json.getString("to"), formatter).atStartOfDay();

        return Response.ok(getEvaluations("income", from, to)).build();
    }

    @POST
    @Path("/filterexpenses")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilteredExpenses(String body) {
        JSONObject json = new JSONObject(body);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime from = LocalDate.parse(json.getString("from"), formatter).atStartOfDay();
        LocalDateTime to = LocalDate.parse(json.getString("to"), formatter).atStartOfDay();

        return Response.ok(getEvaluations("expenses", from, to)).build();
    }

    private List<Evaluation> getEvaluations(String mode, LocalDateTime from, LocalDateTime to) {
        List<Evaluation> evaluationIncome = new LinkedList<>();
        List<Evaluation> evaluationExpenses = new LinkedList<>();
        List<Payment> payments = paymentDao.listAll();

        for (int i = 0; i < payments.size(); i++){
            if ((from == LocalDateTime.MIN || payments.get(i).getBookingDate().compareTo(from) >= 0)
                && (to == LocalDateTime.MIN || payments.get(i).getBookingDate().compareTo(to) <= 0)) {
                if (payments.get(i).getAmount() > 0) {
                    evaluationIncome = addAmountToList(evaluationIncome, payments.get(i));
                } else {
                    evaluationExpenses = addAmountToList(evaluationExpenses, payments.get(i));
                }
            }
        }

        if(mode == "income") {
            return evaluationIncome;
        }

        return evaluationExpenses;
    }

    private List<Evaluation> addAmountToList(List<Evaluation> evaluations, Payment payment) {
        if (!containingInEvaluationList(evaluations, payment.getCategory().getName())){
            evaluations.add(new Evaluation(payment.getCategory().getName(), payment.getAmount()));
        }
        else {
            int index = getIndexOfPayment(evaluations, payment);
            if (index != -1) {
                evaluations.get(index).addAmount(payment.getAmount());
            }
        }
        return evaluations;
    }

    private boolean containingInEvaluationList(List<Evaluation> evaluations, String categoryName){
        for (Evaluation e : evaluations) {
            if (e.getName().equals(categoryName)){
                return true;
            }
        }
        return false;
    }

    private int getIndexOfPayment(List<Evaluation> evaluations, Payment payment) {
        for (int i = 0; i < evaluations.size(); i++) {
            if (evaluations.get(i).getName().equals(payment.getCategory().getName())) {
                return i;
            }
        }
        return -1;
    }
}
