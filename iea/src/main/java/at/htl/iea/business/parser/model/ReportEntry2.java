package at.htl.iea.business.parser.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportEntry2", propOrder = {
    "amt",
    "cdtDbtInd",
    "bookgDt",
    "valDt",
    "ntryDtls"
})
public class ReportEntry2 {

    @XmlElement(name = "Amt", required = true)
    protected ActiveOrHistoricCurrencyAndAmount amt;
    @XmlElement(name = "CdtDbtInd", required = true)
    @XmlSchemaType(name = "string")
    protected CreditDebitCode cdtDbtInd;
    @XmlElement(name = "BookgDt")
    protected DateAndDateTimeChoice bookgDt;
    @XmlElement(name = "ValDt")
    protected DateAndDateTimeChoice valDt;
    @XmlElement(name = "NtryDtls")
    protected List<EntryDetails1> ntryDtls;

    public ActiveOrHistoricCurrencyAndAmount getAmt() {
        return amt;
    }

    public void setAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.amt = value;
    }

    public CreditDebitCode getCdtDbtInd() {
        return cdtDbtInd;
    }

    public void setCdtDbtInd(CreditDebitCode value) {
        this.cdtDbtInd = value;
    }

    public DateAndDateTimeChoice getBookgDt() {
        return bookgDt;
    }

    public void setBookgDt(DateAndDateTimeChoice value) {
        this.bookgDt = value;
    }

    public DateAndDateTimeChoice getValDt() {
        return valDt;
    }

    public void setValDt(DateAndDateTimeChoice value) {
        this.valDt = value;
    }

    public List<EntryDetails1> getNtryDtls() {
        if (ntryDtls == null) {
            ntryDtls = new ArrayList<EntryDetails1>();
        }
        return this.ntryDtls;
    }

}
