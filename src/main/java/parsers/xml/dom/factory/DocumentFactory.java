package parsers.xml.dom.factory;

import exceptions.TransactionParserException;
import lombok.Singleton;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Singleton
public class DocumentFactory {
    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public Document getDocument(String path) throws TransactionParserException {
        Document doc;
        try {
            doc = factory.newDocumentBuilder().parse(new File(path));
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new TransactionParserException(e);
        }
        doc.getDocumentElement().normalize();
        return doc;
    }
}
