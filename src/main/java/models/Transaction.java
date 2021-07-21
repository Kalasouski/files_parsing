package models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class Transaction {
    @Getter
    private final String id;
    @Getter
    private final String userId;
    @Getter
    private final String date;
    @Getter
    private final long amount;
    @Getter
    private final String currency;
    @Getter
    private final String status;

    @Override
    public String toString() {
        String msg = """
                id: %s
                user_id: %s
                date: %s
                amount: %d
                currency: %s
                result: %s
                """;
        return String.format(msg, id, userId, date, amount, currency, status);
    }
}