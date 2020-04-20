package at.htl.iea.business.parser;

import at.htl.iea.business.parser.model.*;

import javax.xml.bind.*;
import java.io.StringReader;

public class Camt053Parser {
    public Document parse(String inputString) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Document camt053Document = ((JAXBElement<Document>) unmarshaller.unmarshal(new StringReader(inputString))).getValue();

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        return camt053Document;
    }
}
