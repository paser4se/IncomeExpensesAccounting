package at.htl.iea.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Zahlung {

    // region Variables
    private Date bookingDate; // buchungsdatum
    private String partnerName;
    private String partnerIban;
    private String partnerBic;
    private String partnerAccountNumber;
    private String partnerBankCode;
    private String amount; // leichter zu parsen (375,00)
    private String currency;
    private String bookingText;
    private String initialRecognitionReference; // ersterfassungsreferenz (z.B.: 128379247QWEV2345FLHO9)
    private String note;
    private Date valueDate; // valutadatum

    // endregion

    // region Constructor
    public Zahlung(Date bookingDate, String partnername, String partnerIban, String partnerBic, String partnerAccountNumber, String partnerBankCode, String amount, String currency, String bookingText, String initialRecognitionReference, String note, Date valueDate) {
        this.bookingDate = bookingDate;
        this.partnerName = partnername;
        this.partnerIban = partnerIban;
        this.partnerBic = partnerBic;
        this.partnerAccountNumber = partnerAccountNumber;
        this.partnerBankCode = partnerBankCode;
        this.amount = amount;
        this.currency = currency;
        this.bookingText = bookingText;
        this.initialRecognitionReference = initialRecognitionReference;
        this.note = note;
        this.valueDate = valueDate;
    }
    public Zahlung(Date bookingDate, String amount, String currency, String bookingText, Date valueDate){
        this.bookingDate = bookingDate;
        this.amount = amount;
        this.currency = currency;
        this.bookingText = bookingText;
        this.valueDate = valueDate;
    }
    public Zahlung(){} //default konstruktor
    // endregion

    // region Getter & Setter
    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerIban() {
        return partnerIban;
    }

    public void setPartnerIban(String partnerIban) {
        this.partnerIban = partnerIban;
    }

    public String getPartnerBic() {
        return partnerBic;
    }

    public void setPartnerBic(String partnerBic) {
        this.partnerBic = partnerBic;
    }

    public String getPartnerAccountNumber() {
        return partnerAccountNumber;
    }

    public void setPartnerAccountNumber(String partnerAccountNumber) {
        this.partnerAccountNumber = partnerAccountNumber;
    }

    public String getPartnerBankCode() {
        return partnerBankCode;
    }

    public void setPartnerBankCode(String partnerBankCode) {
        this.partnerBankCode = partnerBankCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBookingText() {
        return bookingText;
    }

    public void setBookingText(String bookingText) {
        this.bookingText = bookingText;
    }

    public String getInitialRecognitionReference() {
        return initialRecognitionReference;
    }

    public void setInitialRecognitionReference(String initialRecognitionReference) {
        this.initialRecognitionReference = initialRecognitionReference;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }
    // endregion

    // region Override toString Methode
    @Override
    public String toString(){ //ausgabe der wichtigsten properties
        return "Datum: " + getBookingDate() + " | " + "Betrag: " + getAmount() + " | "
                + "Währung: " + getCurrency() + " | " + "Buchungstext: " + getBookingText() + " | "
                + "Valutadatum: " + getValueDate();
    }
    // endregion
}
