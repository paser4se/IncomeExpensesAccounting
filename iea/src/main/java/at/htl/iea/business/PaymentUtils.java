package at.htl.iea.business;

import at.htl.iea.business.parser.model.*;
import at.htl.iea.model.TempPayment;
import at.htl.iea.model.Payment;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentUtils {

    private static PaymentUtils instance = null;

    private PaymentUtils() {}

    public static PaymentUtils getInstance () {
        if (PaymentUtils.instance == null) {
            PaymentUtils.instance = new PaymentUtils();
        }
        return PaymentUtils.instance;
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
            tmpPayment.add("writeOffUnit", paymentList.get(i).getWriteOffUnit().ordinal());
            tmpPayment.add("writeOffNumber", paymentList.get(i).getWriteOffNumber());
            JsonObjectBuilder cat = Json.createObjectBuilder();
            cat.add("id", paymentList.get(i).getCategory().getId());
            cat.add("name", paymentList.get(i).getCategory().getName());
            tmpPayment.add("category", cat.build());

            payments.add(tmpPayment);
        }

        return payments.build();
    }

    public JsonArray getAllTempPayments(List<TempPayment> paymentList){
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
            tmpPayment.add("writeOffUnit", paymentList.get(i).getWriteOffUnit().ordinal());
            tmpPayment.add("writeOffNumber", paymentList.get(i).getWriteOffNumber());
            JsonObjectBuilder cat = Json.createObjectBuilder();
            cat.add("id", paymentList.get(i).getCategory().getId());
            cat.add("name", paymentList.get(i).getCategory().getName());
            tmpPayment.add("category", cat.build());

            payments.add(tmpPayment);
        }

        return payments.build();
    }

    public List<TempPayment> documentToPayment(Document document) {
        List<TempPayment> payments = new ArrayList<>();

        for (AccountStatement2 stmt : document.getBkToCstmrStmt().getStmt()) {
            for (ReportEntry2 ntry : stmt.getNtry()) {
                TempPayment tempPayment = new TempPayment();
                CreditDebitCode code = ntry.getCdtDbtInd();
                double amount = ntry.getAmt().getValue().doubleValue();

                if (code == CreditDebitCode.DBIT)
                    amount *= (-1);
                
                tempPayment.setAmount(amount);
                tempPayment.setCurrency(ntry.getAmt().getCcy());
                tempPayment.setBookingDate(LocalDateTime.ofInstant(ntry.getBookgDt().getDt().toGregorianCalendar().getTime().toInstant(),
                        ZoneId.systemDefault()));
                tempPayment.setValueDate(LocalDateTime.ofInstant(ntry.getValDt().getDt().toGregorianCalendar().getTime().toInstant(),
                        ZoneId.systemDefault()));

                for (EntryDetails1 entryDetails1 : ntry.getNtryDtls()) {
                    for (EntryTransaction2 entryTransaction2: entryDetails1.getTxDtls()) {
                        TransactionParty2 transactionParty2 = entryTransaction2.getRltdPties();
                        if(transactionParty2 != null) {
                            if (code == CreditDebitCode.DBIT && transactionParty2.getCdtr() != null && transactionParty2.getCdtrAcct() != null) {
                                tempPayment.setPartnerName(transactionParty2.getCdtr().getNm());
                                tempPayment.setPartnerIban(transactionParty2.getCdtrAcct().getId().getIBAN());
                            } else if (code == CreditDebitCode.CRDT && transactionParty2.getDbtr() != null && transactionParty2.getDbtrAcct() != null) {
                                tempPayment.setPartnerName(transactionParty2.getDbtr().getNm());
                                tempPayment.setPartnerIban(transactionParty2.getDbtrAcct().getId().getIBAN());
                            }
                        }

                        if (entryTransaction2.getRmtInf() != null && entryTransaction2.getRmtInf().getUstrd() != null) {
                            entryTransaction2.getRmtInf().getUstrd().forEach(i -> tempPayment.setBookingText(i));
                        }
                    }
                }
                payments.add(tempPayment);
            }
        }

        return  payments;
    }

}
