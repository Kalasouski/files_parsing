package parsers;

import models.Transaction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface Parser {
    List<Transaction> getTransactionsList(String filePath) throws IOException, ParserConfigurationException,
            SAXException;
}
