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

class CsvParserTest {
    @ParameterizedTest
    @MethodSource
    void givenGetTransactionsListMethodWhenPassingCsvStringThenReturnsCorrespondingList(String csvStr,
                                                                                        List<Transaction> expected)
            throws TransactionParserException {
        assertEquals(expected, new CsvParser().parseFile(csvStr));
    }

    // parser.parseFile("samples/csv_with_one_line.csv")

    private static Stream<Arguments> givenGetTransactionsListMethodWhenPassingCsvStringThenReturnsCorrespondingList() {
        return Stream.of(
                arguments(
                        """
                                timestamp,transaction_id,user_id,amount,currency,transaction_result
                                1619674154,1001,u_001,34623,usd,success
                                1619676321,1002,u_002,24843,usd,rejected
                                """,
                        Arrays.asList(
                                // public static Transaction generateTransaction(String status, Long amount)
                                new Transaction("1c22f114-8251-404e-8c3f-73b70bd0ec80",
                                        "a00a7fb0-3a72-454d-865d-8f6818f8dd62",
                                        "1619674154",
                                        34623,
                                        "usd",
                                        "success"),
                                new Transaction("fb3e82e1-8608-41cd-9148-b604f4f1c5a4",
                                        "bebb0888-2a6f-4f38-b8e7-800a2a3145e8",
                                        "1619676321",
                                        24843,
                                        "usd",
                                        "rejected"
                                )
                        )
                )
        );
    }
}