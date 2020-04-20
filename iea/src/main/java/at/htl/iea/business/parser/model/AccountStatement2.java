package at.htl.iea.business.parser.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountStatement2", propOrder = {
    "id",
    "ntry"
})
public class AccountStatement2 {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "Ntry")
    protected List<ReportEntry2> ntry;

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public List<ReportEntry2> getNtry() {
        if (ntry == null) {
            ntry = new ArrayList<ReportEntry2>();
        }
        return this.ntry;
    }

}
