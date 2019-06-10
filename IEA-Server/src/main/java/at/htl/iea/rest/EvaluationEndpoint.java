package at.htl.iea.rest;

import at.htl.iea.dao.PaymentDao;
import at.htl.iea.model.Evaluation;
import at.htl.iea.model.Payment;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("evaluation")
public class EvaluationEndpoint {
    @Inject
    PaymentDao paymentDao;

    @GET
    @Path("/income")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvaluationIncome(){
        return Response.ok(getEvaluations("income")).build();
    }

    @GET
    @Path("/expenses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvaluationExpenses(){
        return Response.ok(getEvaluations("expenses")).build();
    }

    private List<Evaluation> getEvaluations(String mode) {
        List<Evaluation> evaluationIncome = new LinkedList<>();
        List<Evaluation> evaluationExpenses = new LinkedList<>();
        List<Payment> payments = paymentDao.getAllEvaluatedPayments();

        for (int i = 0; i < payments.size(); i++){
            if(payments.get(i).getAmount() > 0) {
                evaluationIncome = addAmountToList(evaluationIncome, payments.get(i));
            } else {
                evaluationExpenses = addAmountToList(evaluationExpenses, payments.get(i));
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
