package service;

import models.Transaction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TransactionServiceTest {
    @ParameterizedTest
    @MethodSource
    void givenGetTopFiveMethodWhenPassingTransactionListThenReturnsCorrespondingList(List<Transaction> transactions,
                                                                                     List<Transaction> expected) {
        List<Transaction> topFiveList = TransactionService.getTopFive(transactions);
        assertTrue(topFiveList.containsAll(expected) && expected.containsAll(topFiveList));
    }

    @ParameterizedTest
    @MethodSource
    void givenGetNumberOfSuccessTransactionsMethodWhenPassingTransactionListThenReturnsCorrespondingNumber(List<Transaction> transactions,
                                                                                                           int expected) {
        assertEquals(expected, TransactionService.getNumberOfSuccessTransactions(transactions));
    }

    @ParameterizedTest
    @MethodSource
    void givenGetMinAmountMethodWhenPassingTransactionListThenReturnsCorrespondingNumber(List<Transaction> transactions,
                                                                                                           int expected) {
        assertEquals(expected, TransactionService.getMinAmount(transactions));
    }

    @ParameterizedTest
    @MethodSource
    void givenGetMaxAmountMethodWhenPassingTransactionListThenReturnsCorrespondingNumber(List<Transaction> transactions,
                                                                                         int expected) {
        assertEquals(expected, TransactionService.getMaxAmount(transactions));
    }

    private static Stream<Arguments> givenGetTopFiveMethodWhenPassingTransactionListThenReturnsCorrespondingList() {
        return Stream.of(
                arguments(
                        Arrays.asList(
                                new Transaction("4", "g876", "23-04-1982", 900, "USD", "SUCCESS"),
                                new Transaction("5", "g876", "23-04-1982", 123, "USD", "SUCCESS"),
                                new Transaction("1", "g876", "23-04-1982", 5000, "USD", "SUCCESS"),
                                new Transaction("2", "g876", "23-04-1982", 700, "USD", "SUCCESS"),
                                new Transaction("3", "g876", "23-04-1982", 1234, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 989, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 356, "USD", "SUCCESS")
                        ),
                        Arrays.asList(
                                new Transaction("4", "g876", "23-04-1982", 900, "USD", "SUCCESS"),
                                new Transaction("1", "g876", "23-04-1982", 5000, "USD", "SUCCESS"),
                                new Transaction("2", "g876", "23-04-1982", 700, "USD", "SUCCESS"),
                                new Transaction("3", "g876", "23-04-1982", 1234, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 989, "USD", "SUCCESS")
                        )
                ),
                arguments(
                        Arrays.asList(
                                new Transaction("2", "g876", "23-04-1982", 700, "USD", "SUCCESS"),
                                new Transaction("3", "g876", "23-04-1982", 1234, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 989, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 356, "USD", "SUCCESS")
                        ),
                        Arrays.asList(
                                new Transaction("2", "g876", "23-04-1982", 700, "USD", "SUCCESS"),
                                new Transaction("3", "g876", "23-04-1982", 1234, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 989, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 356, "USD", "SUCCESS")
                        )

                )
        );
    }

    private static Stream<Arguments> givenGetNumberOfSuccessTransactionsMethodWhenPassingTransactionListThenReturnsCorrespondingNumber() {
        return Stream.of(
                arguments(
                        Arrays.asList(
                                new Transaction("4", "g876", "23-04-1982", 900, "USD", "SUCCESS"),
                                new Transaction("5", "g876", "23-04-1982", 123, "USD", "FAILED"),
                                new Transaction("1", "g876", "23-04-1982", 5000, "USD", "REJECTED"),
                                new Transaction("2", "g876", "23-04-1982", 700, "USD", "SUCCESS"),
                                new Transaction("3", "g876", "23-04-1982", 1234, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 989, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 356, "USD", "FAILED")
                        ), 4),
                arguments(
                        Arrays.asList(
                                new Transaction("3", "g876", "23-04-1982", 1234, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 989, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 356, "USD", "FAILED")
                        ), 2)

        );
    }

    private static Stream<Arguments> givenGetMinAmountMethodWhenPassingTransactionListThenReturnsCorrespondingNumber() {
        return Stream.of(
                arguments(
                        Arrays.asList(
                                new Transaction("4", "g876", "23-04-1982", 900, "USD", "SUCCESS"),
                                new Transaction("5", "g876", "23-04-1982", 123, "USD", "SUCCESS"),
                                new Transaction("1", "g876", "23-04-1982", 5000, "USD", "SUCCESS"),
                                new Transaction("2", "g876", "23-04-1982", 700, "USD", "SUCCESS"),
                                new Transaction("3", "g876", "23-04-1982", 1234, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 989, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 356, "USD", "SUCCESS")
                        ),123
                ),
                arguments(
                        Arrays.asList(
                                new Transaction("3", "g876", "23-04-1982", 1234, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 989, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 356, "USD", "FAILED")
                        ), 356
                )


        );
    }

    private static Stream<Arguments> givenGetMaxAmountMethodWhenPassingTransactionListThenReturnsCorrespondingNumber() {
        return Stream.of(
                arguments(
                        Arrays.asList(
                                new Transaction("4", "g876", "23-04-1982", 900, "USD", "SUCCESS"),
                                new Transaction("5", "g876", "23-04-1982", 123, "USD", "SUCCESS"),
                                new Transaction("1", "g876", "23-04-1982", 5000, "USD", "SUCCESS"),
                                new Transaction("2", "g876", "23-04-1982", 700, "USD", "SUCCESS"),
                                new Transaction("3", "g876", "23-04-1982", 1234, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 989, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 356, "USD", "SUCCESS")
                        ),5000
                ),
                arguments(
                        Arrays.asList(
                                new Transaction("3", "g876", "23-04-1982", 1234, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 989, "USD", "SUCCESS"),
                                new Transaction("7", "g876", "23-04-1982", 356, "USD", "FAILED")
                        ), 1234
                )
        );
    }
}