package at.htl.iea.business.parser.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountIdentification4Choice", propOrder = {
    "iban"
})
public class AccountIdentification4Choice {

    @XmlElement(name = "IBAN")
    protected String iban;

    public String getIBAN() {
        return iban;
    }

    public void setIBAN(String value) {
        this.iban = value;
    }

}
