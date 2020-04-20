package at.htl.iea.business.parser.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashAccount16", propOrder = {
    "id"
})
public class CashAccount16 {

    @XmlElement(name = "Id", required = true)
    protected AccountIdentification4Choice id;

    public AccountIdentification4Choice getId() {
        return id;
    }

    public void setId(AccountIdentification4Choice value) {
        this.id = value;
    }

}
