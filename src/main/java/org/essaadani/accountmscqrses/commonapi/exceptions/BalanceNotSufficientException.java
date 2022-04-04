package org.essaadani.accountmscqrses.commonapi.exceptions;

public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String msg) {
        super(msg);
    }
}
