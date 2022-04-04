package org.essaadani.accountmscqrses.commonapi.events;

import lombok.Getter;

import java.util.Date;

public class AccountDebitedEvent extends BaseEvent<String>{
    /*
    * What to save at the event store?
    * */
    @Getter private double amount;
    @Getter private String currency;
    @Getter private Date date;

    /*
     * END What to save at the event store?
     * */

    public AccountDebitedEvent(String id, double amount, String currency, Date date) {
        super(id);
        this.amount = amount;
        this.currency = currency;
        this.date = date;
    }
}
