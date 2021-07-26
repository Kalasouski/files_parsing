package parsers.xml;

import exceptions.TransactionFileParsingException;
import models.Transaction;
import parsers.Parser;
import parsers.xml.dom.DOMTransactionsParser;
import parsers.xml.dom.DocumentFactory;

import java.util.List;

public class XmlParser implements Parser {

    DocumentFactory documentFactory = new DocumentFactory();

    @Override
    public String getSupportedExtension() {
        return "xml";
    }

    @Override
    public List<Transaction> parseFile(String path) throws TransactionFileParsingException {
        DOMTransactionsParser domTransactionsParser = new DOMTransactionsParser(documentFactory.getDocument(path));
        return domTransactionsParser.getTransactionsList();
    }
}