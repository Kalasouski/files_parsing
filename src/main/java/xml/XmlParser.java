package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class XmlParser {
    private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    //dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

    public static void parseXml(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("The file " + filePath + " does not exist");
            return;
        }

        System.out.println("The " + "'" + filePath + "'" + " is a file");
        List<Transaction> transactions;
        try {
            transactions = parse(file);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Something went wrong during parsing");
            e.printStackTrace();
            return;
        }

        System.out.println(
                """
                        File parsed successfully
                        Select operation:
                         [1] Print data to display
                         [2] Print top-5 transactions
                         [3] Print totals
                        """
        );
        int input;
        try {
            input = getCommand();
        } catch (IOException e) {
            System.out.println("Something went wrong during reading command");
            e.printStackTrace();
            return;
        }

        switch (input) {
            case 1 -> System.out.println(1);
            case 2 -> printTopFive(transactions);
            case 3 -> printTotals(transactions);
        }
    }

    private static int getCommand() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String s = br.readLine();
                int x;
                if (s.length() == 1 && Character.isDigit(s.charAt(0)) && (x = Integer.parseInt(s)) >= 1 &&
                        x <= 3)
                    return x;
                System.out.println("Bad input. Try again");
            }
        }
    }

    private static List<Transaction> parse(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        NodeList list = doc.getElementsByTagName("transactions");
        if (list.getLength() != 1) {
            throw new IllegalArgumentException();
        }
        Node transactionsNode = list.item(0);
        getElement(transactionsNode);
        var transactions = ((Element) transactionsNode).getElementsByTagName("transaction");
        List<Transaction> transactionList = new ArrayList<>();
        for (int i = 0; i < transactions.getLength(); i++) {
            Node node = transactions.item(i);
            getElement(node);
            Transaction transaction = processTransaction((Element) node);
            transactionList.add(transaction);
        }
        return transactionList;
    }

    private static Transaction processTransaction(Element transactionElement) {
        String id = transactionElement.getElementsByTagName("id").item(0).getTextContent();

        Node userNode = transactionElement.getElementsByTagName("user").item(0);
        Element userElement = getElement(userNode);
        String userId = userElement.getElementsByTagName("id").item(0).getTextContent();

        Node detailsNode = transactionElement.getElementsByTagName("payment_details").item(0);
        Element detailsElement = getElement(detailsNode);

        long amount = Long.parseLong(detailsElement.getElementsByTagName("amount").item(0).getTextContent().
                replaceAll("\\s+", ""));
        String currency = detailsElement.getElementsByTagName("currency").item(0).getTextContent();

        String date = transactionElement.getElementsByTagName("timestamp").item(0).getTextContent();
        boolean isSuccessful;
        String status = transactionElement.getElementsByTagName("status").item(0).getTextContent();
        if (status.equals(Transaction.Status.COMPLETE.toString())) {
            isSuccessful = true;
        } else if (status.equals(Transaction.Status.FAILURE.toString())) {
            isSuccessful = false;
        } else {
            throw new IllegalArgumentException();
        }

        return new Transaction(id, userId, date, amount, currency, isSuccessful);
    }

    private static Element getElement(Node node) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            throw new IllegalArgumentException();
        }
        return (Element) node;
    }

    private static void printTopFive(List<Transaction> transactions) {
        transactions.sort(Comparator.comparingLong(Transaction::getAmount).reversed());

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            System.out.println("Transaction №" + (i + 1) + " data:");
            System.out.println("\tid: " + transaction.getId());
            System.out.println("\tuser_id: " + transaction.getUserId());
            System.out.println("\tdate: " + transaction.getDate());
            System.out.println("\tamount: " + transaction.getAmount());
            System.out.println("\tresult: " + (transaction.isSuccessful() ? "successful" : "failed"));

            if (i == 1)
                break;
        }
    }

    private static void printTotals(List<Transaction> transactions) {
        System.out.println("Total number of transactions: "+transactions.size());
        long successful = transactions.stream().filter(Transaction::isSuccessful).count();
        System.out.println("\twhere successful: "+ successful);
        System.out.println("\t\t\tfailed: "+ (transactions.size()-successful));

        System.out.println("Max transaction: "+transactions.stream().mapToLong(Transaction::getAmount).max()
                .orElse(-1));
        System.out.println("Min transaction: "+transactions.stream().mapToLong(Transaction::getAmount).min()
                .orElse(-1));

    }
}