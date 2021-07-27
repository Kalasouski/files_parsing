package by.itechart.parsers;


import by.itechart.exceptions.TransactionFileParsingException;
import by.itechart.models.Transaction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import by.itechart.parsers.xml.XmlParser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;


class XmlParserTest extends ParserTest {
    protected XmlParserTest() {
        super(new XmlParser());
    }

    @ParameterizedTest
    @MethodSource
    void givenGetTransactionsListMethodWhenPassingXmlStringThenReturnsCorrespondingList(String path,
                                                                                        List<Transaction> expected)
            throws TransactionFileParsingException {
        assertEquals(expected, super.parseFile(path));
    }

    private static Stream<Arguments> givenGetTransactionsListMethodWhenPassingXmlStringThenReturnsCorrespondingList() {
        return Stream.of(
                arguments(
                        "xml/trans_1.xml",
                        Arrays.asList(
                                new Transaction(
                                        "t_001",
                                        "u_001",
                                        "2021-04-29 16:46:13",
                                        9669,
                                        "USD",
                                        "COMPLETE"
                                ),
                                new Transaction(
                                        "t_002",
                                        "u_002",
                                        "2021-03-01 12:00:00",
                                        5000,
                                        "USD",
                                        "FAILURE"
                                )
                        )
                ),
                arguments(
                        "xml/trans_2.xml",
                        Arrays.asList(
                                new Transaction(
                                        "t_001",
                                        "u_001",
                                        "2021-04-29 16:46:13",
                                        9669,
                                        "USD",
                                        "COMPLETE"
                                ),
                                new Transaction(
                                        "t_002",
                                        "u_002",
                                        "2021-03-01 12:00:00",
                                        5000,
                                        "USD",
                                        "FAILURE"
                                ),
                                new Transaction(
                                        "t_003",
                                        "u_003",
                                        "2021-03-01 12:00:00",
                                        123,
                                        "USD",
                                        "FAILURE"
                                ),
                                new Transaction(
                                        "t_004",
                                        "u_004",
                                        "2021-03-01 12:00:00",
                                        7896,
                                        "USD",
                                        "FAILURE"
                                ),
                                new Transaction(
                                        "t_005",
                                        "u_005",
                                        "2021-03-01 12:00:00",
                                        2445,
                                        "USD",
                                        "FAILURE"
                                ),
                                new Transaction(
                                        "t_006",
                                        "u_006",
                                        "2021-03-01 12:00:00",
                                        3113,
                                        "USD",
                                        "FAILURE"
                                )
                        )
                )
        );
    }
}