package at.htl.iea.business.parser.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _Document_QNAME = new QName("urn:iso:std:iso:20022:tech:xsd:camt.053.001.02", "Document");

    public ObjectFactory() {
    }

    public Document createDocument() {
        return new Document();
    }

    public DateAndDateTimeChoice createDateAndDateTimeChoice() {
        return new DateAndDateTimeChoice();
    }

    public ActiveOrHistoricCurrencyAndAmount createActiveOrHistoricCurrencyAndAmount() {
        return new ActiveOrHistoricCurrencyAndAmount();
    }

    public BankToCustomerStatementV02 createBankToCustomerStatementV02() {
        return new BankToCustomerStatementV02();
    }

    public RemittanceInformation5 createRemittanceInformation5() {
        return new RemittanceInformation5();
    }

    public CashAccount16 createCashAccount16() {
        return new CashAccount16();
    }

    public EntryTransaction2 createEntryTransaction2() {
        return new EntryTransaction2();
    }

    public PartyIdentification32 createPartyIdentification32() {
        return new PartyIdentification32();
    }

    public AccountIdentification4Choice createAccountIdentification4Choice() {
        return new AccountIdentification4Choice();
    }

    public EntryDetails1 createEntryDetails1() {
        return new EntryDetails1();
    }

    public TransactionParty2 createTransactionParty2() {
        return new TransactionParty2();
    }

    public AccountStatement2 createAccountStatement2() {
        return new AccountStatement2();
    }

    public ReportEntry2 createReportEntry2() {
        return new ReportEntry2();
    }

    @XmlElementDecl(namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02", name = "Document")
    public JAXBElement<Document> createDocument(Document value) {
        return new JAXBElement<Document>(_Document_QNAME, Document.class, null, value);
    }
}
