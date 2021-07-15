package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlParser {
    private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    //dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

    public static void parse(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(fileName));
        doc.getDocumentElement().normalize();
        NodeList list = doc.getElementsByTagName("transactions");
        if (list.getLength() != 1) {
            throw new IllegalArgumentException();
        }
        Node transactionsNode = list.item(0);
        getElement(transactionsNode);
        var transactions = ((Element) transactionsNode).getElementsByTagName("transaction");
        for (int i = 0; i < transactions.getLength(); i++) {
            Node node = transactions.item(i);
            getElement(node);
            Transaction transaction = processTransaction((Element) node);
            System.out.println("Transaction №"+(i+1)+" data:");
            System.out.println("\tid: "+transaction.getId());
            System.out.println("\tuser_id: "+transaction.getUserId());
            System.out.println("\tdate: "+transaction.getDate());
            System.out.println("\tamount: "+transaction.getAmount());
            System.out.println("\tresult: "+(transaction.isSuccessful() ? "successful" : "failed"));
        }

    }

    private static Transaction processTransaction(Element transactionElement) {
        String id = transactionElement.getElementsByTagName("id").item(0).getTextContent();

        Node userNode = transactionElement.getElementsByTagName("user").item(0);
        Element userElement = getElement(userNode);
        String userId = userElement.getElementsByTagName("id").item(0).getTextContent();

        Node detailsNode = transactionElement.getElementsByTagName("payment_details").item(0);
        Element detailsElement = getElement(detailsNode);

        long amount = Long.parseLong(detailsElement.getElementsByTagName("amount").item(0).getTextContent().
                replaceAll("\\s+",""));
        String currency = detailsElement.getElementsByTagName("currency").item(0).getTextContent();

        String date = transactionElement.getElementsByTagName("timestamp").item(0).getTextContent();
        boolean isSuccessful;
        String status = transactionElement.getElementsByTagName("status").item(0).getTextContent();
        if(status.equals(Transaction.Status.COMPLETE.toString())) {
            isSuccessful = true;
        }
        else if (status.equals(Transaction.Status.FAILURE.toString())) {
            isSuccessful = false;
        }
        else {
            throw new IllegalArgumentException();
        }

        return new Transaction(id,userId,date, amount, currency,isSuccessful);
    }

    private static Element getElement(Node node) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            throw new IllegalArgumentException();
        }
        return (Element) node;
    }
}