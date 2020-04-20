package at.htl.iea.business.parser.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntryTransaction2", propOrder = {
    "rltdPties",
     "rmtInf"
})
public class EntryTransaction2 {

    @XmlElement(name = "RltdPties")
    protected TransactionParty2 rltdPties;
    @XmlElement(name = "RmtInf")
    protected RemittanceInformation5 rmtInf;

    public TransactionParty2 getRltdPties() {
        return rltdPties;
    }

    public void setRltdPties(TransactionParty2 value) {
        this.rltdPties = value;
    }

    public RemittanceInformation5 getRmtInf() {
        return rmtInf;
    }

    public void setRmtInf(RemittanceInformation5 value) {
        this.rmtInf = value;
    }

}
