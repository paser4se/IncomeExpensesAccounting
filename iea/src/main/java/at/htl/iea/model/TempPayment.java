package at.htl.iea.model;

import at.htl.iea.model.enums.WriteOffUnit;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "TempPayments.findAll", query = "select p from TempPayment p")
})
public class TempPayment implements Cloneable {

    // region Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bookingDate = null; // buchungsdatum
    private String partnerName = "";
    private String partnerIban = "";
    private String partnerBic = "";
    private String partnerAccountNumber = "";
    private String partnerBankCode = "";
    private Double amount = 0.0;
    private String currency = "";
    private String bookingText = "";
    private String initialRecognitionReference = ""; // ersterfassungsreferenz (z.B.: 128379247QWEV2345FLHO9)
    private String note = "";
    private LocalDateTime valueDate = null; // valutadatum
    private WriteOffUnit writeOffUnit = WriteOffUnit.NONE;
    private int writeOffNumber = 0;

    @OneToOne
    private TempPayment nextPayment = null;
    @OneToOne
    private TempPayment previousPayment = null;

    @OneToOne
    private Category category;
    // endregion

    // region Constructor
    public TempPayment(){}
    // endregion

    // region Getter & Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
        if (bookingText.length() > 254)
            this.bookingText = bookingText.substring(0, 255);
        else
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

    public LocalDateTime getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDateTime valueDate) {
        this.valueDate = valueDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public WriteOffUnit getWriteOffUnit() {
        return writeOffUnit;
    }

    public void setWriteOffUnit(WriteOffUnit writeOffUnit) {
        this.writeOffUnit = writeOffUnit;
    }

    public int getWriteOffNumber() {
        return writeOffNumber;
    }

    public void setWriteOffNumber(int writeOffNumber) {
        this.writeOffNumber = writeOffNumber;
    }

    public TempPayment getNextPayment() {
        return nextPayment;
    }

    public void setNextPayment(TempPayment nextPayment) {
        this.nextPayment = nextPayment;
    }

    public TempPayment getPreviousPayment() {
        return previousPayment;
    }

    public void setPreviousPayment(TempPayment previousPayment) {
        this.previousPayment = previousPayment;
        this.previousPayment.setNextPayment(this);
    }
// endregion

    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }
}
