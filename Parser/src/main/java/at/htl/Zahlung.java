package at.htl;


public class Zahlung {

    // region Variables
    private String buchungsdatum; // der einfachheit halber als string und nicht als localdatetime (oder localdate)
    private String partner_name;
    private String partner_iban;
    private String partner_bic;
    private String partner_kontonummer;
    private String partner_bankcode;
    private String betrag; // leichter zu parsen (375,00)
    private String währung;
    private String buchungstext;
    private String ersterfassungsreferenz;
    private String notiz;
    private String valutadatum; // der einfachheit halber als string und nicht als localdatetime (oder localdate)

    // endregion

    // region Constructor
    public Zahlung(String buchungsdatum, String partnername, String partner_iban, String partner_bic, String partner_kontonummer, String partner_bankcode, String betrag, String währung, String buchungstext, String ersterfassungsreferenz, String notiz, String valutadatum) {
        this.buchungsdatum = buchungsdatum;
        this.partner_name = partnername;
        this.partner_iban = partner_iban;
        this.partner_bic = partner_bic;
        this.partner_kontonummer = partner_kontonummer;
        this.partner_bankcode = partner_bankcode;
        this.betrag = betrag;
        this.währung = währung;
        this.buchungstext = buchungstext;
        this.ersterfassungsreferenz = ersterfassungsreferenz;
        this.notiz = notiz;
        this.valutadatum = valutadatum;
    }
    public Zahlung(){} //default konstruktor
    // endregion

    // region Getter & Setter
    public String getBuchungsdatum() {
        return buchungsdatum;
    }

    public void setBuchungsdatum(String buchungsdatum) {
        this.buchungsdatum = buchungsdatum;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getPartner_iban() {
        return partner_iban;
    }

    public void setPartner_iban(String partner_iban) {
        this.partner_iban = partner_iban;
    }

    public String getPartner_bic() {
        return partner_bic;
    }

    public void setPartner_bic(String partner_bic) {
        this.partner_bic = partner_bic;
    }

    public String getPartner_kontonummer() {
        return partner_kontonummer;
    }

    public void setPartner_kontonummer(String partner_kontonummer) {
        this.partner_kontonummer = partner_kontonummer;
    }

    public String getPartner_bankcode() {
        return partner_bankcode;
    }

    public void setPartner_bankcode(String partner_bankcode) {
        this.partner_bankcode = partner_bankcode;
    }

    public String getBetrag() {
        return betrag;
    }

    public void setBetrag(String betrag) {
        this.betrag = betrag;
    }

    public String getWährung() {
        return währung;
    }

    public void setWährung(String währung) {
        this.währung = währung;
    }

    public String getBuchungstext() {
        return buchungstext;
    }

    public void setBuchungstext(String buchungstext) {
        this.buchungstext = buchungstext;
    }

    public String getErsterfassungsreferenz() {
        return ersterfassungsreferenz;
    }

    public void setErsterfassungsreferenz(String ersterfassungsreferenz) {
        this.ersterfassungsreferenz = ersterfassungsreferenz;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    public String getValutadatum() {
        return valutadatum;
    }

    public void setValutadatum(String valutadatum) {
        this.valutadatum = valutadatum;
    }
    // endregion

    // region Override toString Methode
    @Override
    public String toString(){ //ausgabe der wichtigsten properties
        return "Datum: " + getBuchungsdatum() + " | " + "Betrag: " + getBetrag() + " | "
            + "Währung: " + getWährung() + " | " + "Buchungstext: " + getBuchungstext() + " | "
            + "Valutadatum: " + getValutadatum();
    }
    // endregion
}
