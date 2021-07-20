package parsers;

import exceptions.TransactionParserException;
import models.Transaction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class XmlParserTest {
    @ParameterizedTest
    @MethodSource
    void givenGetTransactionsListMethodWhenPassingXmlStringThenReturnsCorrespondingList(String xmlStr,
                                                                                        List<Transaction> expected)
            throws TransactionParserException {
        assertEquals(expected, new XmlParser().getTransactionsList(xmlStr));
    }

    private static Stream<Arguments> givenGetTransactionsListMethodWhenPassingXmlStringThenReturnsCorrespondingList() {
        return Stream.of(
                arguments(
                        """
                                <root>
                                     <transactions>
                                         <transaction>
                                             <id>2154364a-8157-4099-871f-ffd6225b5c76</id>
                                             <user>
                                                 <id>03fe30f5-1758-4a80-b925-00293cf31607</id>
                                                 <first_name>Will</first_name>
                                                 <last_name>Smith</last_name>
                                                 <email>Will.Smith@example.com</email>
                                             </user>
                                             <timestamp>2021-04-29 16:46:13</timestamp>
                                             <payment_details>
                                                 <amount>9 669</amount>
                                                 <currency>USD</currency>
                                             </payment_details>
                                             <status>COMPLETE</status>
                                         </transaction>
                                         <transaction>
                                             <id>c5dd4886-225a-4966-a7f1-fd93d1357d5e</id>
                                             <user>
                                                 <id>297dd03d-c482-4b2f-a6cb-eaa262e49754</id>
                                                 <first_name>Johny</first_name>
                                                 <last_name>Depp</last_name>
                                                 <email>Johny.Depp@example.com</email>
                                             </user>
                                             <timestamp>2021-03-01 12:00:00</timestamp>
                                             <payment_details>
                                                 <amount>5 000</amount>
                                                 <currency>USD</currency>
                                             </payment_details>
                                             <status>FAILURE</status>
                                         </transaction>
                                     </transactions>
                                </root>
                                                                 """,
                        Arrays.asList(
                                new Transaction(
                                        "2154364a-8157-4099-871f-ffd6225b5c76",
                                        "03fe30f5-1758-4a80-b925-00293cf31607",
                                        "2021-04-29 16:46:13",
                                        9669,
                                        "USD",
                                        "COMPLETE"
                                ),
                                new Transaction(
                                        "c5dd4886-225a-4966-a7f1-fd93d1357d5e",
                                        "297dd03d-c482-4b2f-a6cb-eaa262e49754",
                                        "2021-03-01 12:00:00",
                                        5000,
                                        "USD",
                                        "FAILURE"
                                )
                        )
                )
        );
    }
}