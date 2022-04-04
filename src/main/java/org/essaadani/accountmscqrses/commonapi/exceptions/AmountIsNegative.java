package org.essaadani.accountmscqrses.commonapi.exceptions;

public class AmountIsNegative extends RuntimeException {
    public AmountIsNegative(String msg) {
        super(msg);
    }
}
