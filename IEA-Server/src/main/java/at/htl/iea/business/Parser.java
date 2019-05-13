package at.htl.iea.business;

import at.htl.iea.model.OnlineBankingSystem;
import at.htl.iea.model.Payment;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private static Parser instance = null;

    private void Parser() {}

    public static Parser getInstance () {
        if (Parser.instance == null) {
            Parser.instance = new Parser();
        }
        return Parser.instance;
    }

    public List<Payment> persist(String fileContent) throws NoSuchFieldException, ParseException {
        List<Payment> payments = new ArrayList<>();
        String[] lines = fileContent.split("\n");
        List<String> header = Arrays.asList(lines[0].split(";")).stream()
                .map(p -> p.replaceAll("\"", "").replaceAll("\r", ""))
                .collect(Collectors.toList());
        int lineStartIndex = 1;
        OnlineBankingSystem bankingSystem = OnlineBankingSystem.GEORGE;
        if (!containsAllRequiredFields(header)){
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            try {
                dateFormat.parse(header.get(0));
                header = Arrays.asList("Buchungsdatum", "Buchungstext", "Valutadatum", "Betrag", "Währung", "", "");
                lineStartIndex = 0;
                bankingSystem = OnlineBankingSystem.ELBA;
            } catch (ParseException ex) {
                throw new NoSuchFieldException();
            }
        }

        for(int i = lineStartIndex; i < lines.length; i++){
            String [] lineElements = lines[i].split(";");

            payments.add(getPayment(header, lineElements, bankingSystem));
        }

        return payments;
    }

    private static Payment getPayment(@NotNull List<String> header, @NotNull String[] lineElements, @NotNull OnlineBankingSystem bankingSystem) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Payment payment = new Payment();

        for (int i = 0; i < lineElements.length; i++) {
            if (bankingSystem.equals(OnlineBankingSystem.GEORGE)) {
                switch (header.get(i)) {
                    case "Buchungsdatum":
                        payment.setBookingDate(dateFormat.parse(lineElements[i].replaceAll("\"", "")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                        break;
                    case "Partnername":
                        payment.setPartnerName(lineElements[i].replaceAll("\"", ""));
                        break;
                    case "Partner IBAN":
                        payment.setPartnerIban(lineElements[i].replaceAll("\"", ""));
                        break;
                    case "Partner BIC":
                        payment.setPartnerBic(lineElements[i].replaceAll("\"", ""));
                        break;
                    case "Partner Kontonummer":
                        payment.setPartnerAccountNumber(lineElements[i].replaceAll("\"", ""));
                        break;
                    case "Partner Bank-Code (BLZ)":
                        payment.setPartnerBankCode(lineElements[i].replaceAll("\"", ""));
                        break;
                    case "Betrag":
                        payment.setAmount(Double.parseDouble(replaceLast(lineElements[i].replaceAll("\"", "").replaceAll("\\.", ""), ",", ".")));
                        break;
                    case "Währung":
                        payment.setCurrency(lineElements[i].replaceAll("\"", ""));
                        break;
                    case "Buchungstext":
                        payment.setBookingText(lineElements[i].replaceAll("\"", ""));
                        break;
                    case "Ersterfassungsreferenz":
                        payment.setInitialRecognitionReference(lineElements[i].replaceAll("\"", ""));
                        break;
                    case "Notiz":
                        payment.setNote(lineElements[i].replaceAll("\"", ""));
                        break;
                    case "Valutadatum":
                        payment.setValueDate(dateFormat.parse(lineElements[i].replaceAll("\"", "")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                        break;
                    default:
                        break;
                }
            } else if (bankingSystem.equals(OnlineBankingSystem.ELBA)) {
                switch (header.get(i)) {
                    case "Buchungsdatum":
                        payment.setBookingDate(dateFormat.parse(lineElements[i].replaceAll("\"", "")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                        break;
                    case "Betrag":
                        payment.setAmount(Double.parseDouble(replaceLast(lineElements[i].replaceAll("\"", "").replaceAll("\\.", ""), ",", ".")));
                        break;
                    case "Währung":
                        payment.setCurrency(lineElements[i].replaceAll("\"", ""));
                        break;
                    case "Buchungstext":
                        payment.setBookingText(lineElements[i].replaceAll("\"", ""));
                        break;
                    case "Valutadatum":
                        payment.setValueDate(dateFormat.parse(lineElements[i].replaceAll("\"", "")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                        break;
                    default:
                        break;
                }
            }
        }

        return payment;
    }

    private static Boolean containsAllRequiredFields(List<String> header) {     //only for george online banking!!
        String[] shouldContain = {"Buchungsdatum", "Betrag", "Währung", "Buchungstext", "Valutadatum"};

        return header.containsAll(Arrays.asList(shouldContain));
    }

    private static String replaceLast(String string, String toReplace, String replacement) {
        int pos = string.lastIndexOf(toReplace);
        if (pos > -1) {
            return string.substring(0, pos)
                    + replacement
                    + string.substring(pos + toReplace.length(), string.length());
        } else {
            return string;
        }
    }

    public JsonArray getAllPayments(List<Payment> paymentList){
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DecimalFormat doubleFormatter = new DecimalFormat("#0.00");
        JsonArrayBuilder payments = Json.createArrayBuilder();

        for (int i = 0; i < paymentList.size(); i++) {
            JsonObjectBuilder tmpPayment = Json.createObjectBuilder();
            tmpPayment.add("id", paymentList.get(i).getId());
            tmpPayment.add("bookingDate", paymentList.get(i).getBookingDate().format(dt));
            tmpPayment.add("amount", doubleFormatter.format(paymentList.get(i).getAmount()));
            tmpPayment.add("currency", paymentList.get(i).getCurrency());
            tmpPayment.add("bookingText", paymentList.get(i).getBookingText());
            JsonObjectBuilder cat = Json.createObjectBuilder();
            cat.add("id", paymentList.get(i).getCategory().getId());
            cat.add("name", paymentList.get(i).getCategory().getName());
            tmpPayment.add("category", cat.build());

            payments.add(tmpPayment);
        }

        return payments.build();
    }

}
