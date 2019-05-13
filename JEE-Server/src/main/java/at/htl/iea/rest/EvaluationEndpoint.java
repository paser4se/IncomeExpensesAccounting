package at.htl.iea.rest;

import at.htl.iea.model.Evaluation;
import at.htl.iea.model.Payment;

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
    @PersistenceContext
    EntityManager em;


    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvaluations(){
    //public Set<Map.Entry<String, Double>> getEvaluations(){
        JsonArrayBuilder resultBuilder = Json.createArrayBuilder();
        HashMap<String, Double> evaluationResults = new HashMap<>();
        List<Evaluation> evaluations = new LinkedList<>();
        List<Payment> payments = em.createNamedQuery("Payments.findAll", Payment.class).getResultList();

        /*for (Payment p: payments) {
            if (!evaluationResults.containsKey(p.getCategory().getName())){
                evaluationResults.put(p.getCategory().getName(), p.getAmount());
            }
        }*/
        for (int i = 0; i < payments.size(); i++){
            if (!containingInEvaluationList(evaluations, payments.get(i).getCategory().getName())){
                evaluations.add(new Evaluation(payments.get(i).getCategory().getName(), payments.get(i).getAmount()));
            }
        }

        //to delete
        for (Evaluation e: evaluations) {
            System.out.println(e.getName() + ": " + e.getAmount());
        }

        return Response.ok(evaluations).build();

        //return evaluationResults.entrySet();
    }

    private boolean containingInEvaluationList(List<Evaluation> evaluations, String categoryName){
        for (Evaluation e : evaluations) {
            if (e.getName().equals(categoryName)){
                return true;
            }
        }
        return false;
    }


}
