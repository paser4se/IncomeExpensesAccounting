package at.htl.iea.business.parser.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionParty2", propOrder = {
    "dbtr",
    "dbtrAcct",
    "cdtr",
    "cdtrAcct"
})
public class TransactionParty2 {

    @XmlElement(name = "Dbtr")
    protected PartyIdentification32 dbtr;
    @XmlElement(name = "DbtrAcct")
    protected CashAccount16 dbtrAcct;
    @XmlElement(name = "Cdtr")
    protected PartyIdentification32 cdtr;
    @XmlElement(name = "CdtrAcct")
    protected CashAccount16 cdtrAcct;

    public PartyIdentification32 getDbtr() {
        return dbtr;
    }

    public void setDbtr(PartyIdentification32 value) {
        this.dbtr = value;
    }

    public CashAccount16 getDbtrAcct() {
        return dbtrAcct;
    }

    public void setDbtrAcct(CashAccount16 value) {
        this.dbtrAcct = value;
    }

    public PartyIdentification32 getCdtr() {
        return cdtr;
    }

    public void setCdtr(PartyIdentification32 value) {
        this.cdtr = value;
    }

    public CashAccount16 getCdtrAcct() {
        return cdtrAcct;
    }

    public void setCdtrAcct(CashAccount16 value) {
        this.cdtrAcct = value;
    }

}
