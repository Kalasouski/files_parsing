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
    void givenGetTransactionsListMethodWhenPassingCsvStringThenReturnsCorrespondingList(String path,
                                                                                        List<Transaction> expected)
            throws TransactionParserException {
        assertEquals(expected, new CsvParser().parseFile(getClass().getClassLoader().getResource(path).getPath()));
    }

    private static Stream<Arguments> givenGetTransactionsListMethodWhenPassingCsvStringThenReturnsCorrespondingList() {
        return Stream.of(
                arguments(
                        "csv/trans_1.csv",
                        Arrays.asList(
                                new Transaction("t_001",
                                        "u_001",
                                        "1619674154",
                                        34623,
                                        "usd",
                                        "success"),
                                new Transaction("t_002",
                                        "u_002",
                                        "1619676213",
                                        124,
                                        "usd",
                                        "success"
                                )
                        )
                )
        );
    }
}