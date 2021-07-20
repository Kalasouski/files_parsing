package parsers;

import exceptions.TransactionParserException;
import models.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XmlParser implements Parser {
    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public List<Transaction> getTransactionsList(String text) throws TransactionParserException {
        return getTransactionsList(convertStringToXMLDocument(text));
    }

    private static Document convertStringToXMLDocument(String xmlString) throws TransactionParserException {
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new TransactionParserException(e);
        }
    }

    private List<Transaction> getTransactionsList(Document doc) {
        doc.getDocumentElement().normalize();
        NodeList transactions = ((Element) doc.getElementsByTagName("transactions").item(0)).
                getElementsByTagName("transaction");
        if (transactions.getLength() == 0)
            return null;
        List<Transaction> transactionList = new ArrayList<>();
        for (int i = 0; i < transactions.getLength(); i++) {
            transactionList.add(getTransaction((Element) transactions.item(i)));
        }
        return transactionList;
    }

    private Transaction getTransaction(Element transactionElement) {
        String id = transactionElement.getElementsByTagName("id").item(0).getTextContent();
        String userId = ((Element) (transactionElement.getElementsByTagName("user").item(0))).
                getElementsByTagName("id").item(0).getTextContent();
        //Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(
        //        transactionElement.getElementsByTagName("timestamp").item(0).getTextContent());
        String date = transactionElement.getElementsByTagName("timestamp").item(0).getTextContent();
        long amount = Long.parseLong(((Element) (transactionElement.getElementsByTagName("payment_details").
                item(0))).getElementsByTagName("amount").item(0).getTextContent().
                replaceAll("\\s+", ""));
        String currency = ((Element) (transactionElement.getElementsByTagName("payment_details").item(0))).
                getElementsByTagName("currency").item(0).getTextContent();
        String status = transactionElement.getElementsByTagName("status").item(0).getTextContent();
        return new Transaction(id, userId, date, amount, currency, status);
    }
}