package org.essaadani.accountmscqrses.commonapi.events;

import lombok.Getter;
import org.essaadani.accountmscqrses.commonapi.enums.AccountStatus;

public class AccountCreatedEvent extends BaseEvent<String>{
    /*
    * What to save at the event store?
    * */
    @Getter private double initialBalance;
    @Getter private String currency;
    @Getter private AccountStatus status;
    @Getter private String customerId;

    /*
     * END What to save at the event store?
     * */

    public AccountCreatedEvent(String id, double initialBalance, String currency, AccountStatus status, String customerId) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.status = status;
        this.customerId = customerId;
    }
}
