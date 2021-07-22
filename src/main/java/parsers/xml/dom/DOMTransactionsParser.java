package parsers.xml.dom;

import lombok.AllArgsConstructor;
import models.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DOMTransactionsParser {

    private final Document doc;

    private NodeList getTransactionNodeList() {
        return ((Element) doc.getElementsByTagName("transactions").item(0)).getElementsByTagName("transaction");
    }

    public List<Transaction> getTransactionsList() {
        NodeList transactionNodeList = getTransactionNodeList();
        List<Transaction> transactionList = new ArrayList<>();
        for (int i = 0; i < transactionNodeList.getLength(); i++) {
            transactionList.add(getTransaction((Element) transactionNodeList.item(i)));
        }
        return transactionList;
    }

    private Transaction getTransaction(Element transactionElement) {
        String id = transactionElement.getElementsByTagName("id").item(0).getTextContent();
        String userId = ((Element) (transactionElement.getElementsByTagName("user").item(0))).
                getElementsByTagName("id").item(0).getTextContent();
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
