package parsers.xml;

import exceptions.TransactionParserException;
import models.Transaction;
import parsers.Parser;
import parsers.xml.dom.DOMTransactionsParser;
import parsers.xml.dom.factory.DocumentFactory;

import java.util.List;

public class XmlParser implements Parser {

    DocumentFactory documentFactory = new DocumentFactory();

    @Override
    public String getSupportedExtension() {
        return "xml";
    }

    @Override
    public List<Transaction> parseFile(String path) throws TransactionParserException {
        return new DOMTransactionsParser(documentFactory.getDocument(path)).getTransactionsList();
    }
}