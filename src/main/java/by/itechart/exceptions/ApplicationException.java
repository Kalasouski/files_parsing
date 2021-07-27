package by.itechart.exceptions;

public class ApplicationException extends Exception {
    public ApplicationException(Throwable e) {
        super(e);
    }

    public ApplicationException(String msg) {
        super(msg);
    }
}
