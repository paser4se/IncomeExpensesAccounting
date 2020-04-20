package at.htl.iea.business.parser.model;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateAndDateTimeChoice", propOrder = {
    "dt"
})
public class DateAndDateTimeChoice {

    @XmlElement(name = "Dt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dt;

    public XMLGregorianCalendar getDt() {
        return dt;
    }

    public void setDt(XMLGregorianCalendar value) {
        this.dt = value;
    }

}
