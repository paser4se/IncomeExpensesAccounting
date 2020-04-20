package at.htl.iea.business.parser.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankToCustomerStatementV02", propOrder = {
    "stmt"
})
public class BankToCustomerStatementV02 {

    @XmlElement(name = "Stmt", required = true)
    protected List<AccountStatement2> stmt;

    public List<AccountStatement2> getStmt() {
        if (stmt == null) {
            stmt = new ArrayList<AccountStatement2>();
        }
        return this.stmt;
    }

}
