package by.itechart.parsers.xml;


import by.itechart.exceptions.TransactionFileParsingException;
import by.itechart.models.Transaction;
import by.itechart.parsers.Parser;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component("xml")
public class XmlParser implements Parser {

    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();


    private Document getDocument(String path) throws TransactionFileParsingException {
        Document doc;
        try {
            doc = factory.newDocumentBuilder().parse(new File(path));
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new TransactionFileParsingException(e);
        }
        doc.getDocumentElement().normalize();
        return doc;
    }

    @Override
    public String getSupportedExtension() {
        return "xml";
    }

    @Override
    public List<Transaction> parseFile(String path) throws TransactionFileParsingException {
        DOMTransactionsParser domTransactionsParser = new DOMTransactionsParser(getDocument(path));
        return domTransactionsParser.getTransactionsList();
    }
}