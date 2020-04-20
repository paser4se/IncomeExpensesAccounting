package at.htl.iea.business.parser.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyIdentification32", propOrder = {
    "nm"
})
public class PartyIdentification32 {

    @XmlElement(name = "Nm")
    protected String nm;

    public String getNm() {
        return nm;
    }

    public void setNm(String value) {
        this.nm = value;
    }

}
