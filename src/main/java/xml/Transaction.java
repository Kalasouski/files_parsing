package xml;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
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
    private final boolean isSuccessful;

    public enum Status {
        COMPLETE,
        FAILURE
    }
}