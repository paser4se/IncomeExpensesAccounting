package at.htl.iea.model;

public class Evaluation { // wird benoetigt, um im EvaluationEndpoint eine List<Evaluation> zu returnen, damit der client die daten besser verarbeiten kann
    private String name; // category name
    private Double amount;

    public Evaluation(){}
    public Evaluation(String name, Double amount){
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return Math.round(amount*100.0)/100.0;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void addAmount(Double amount) {
        this.amount += amount;
    }
}
