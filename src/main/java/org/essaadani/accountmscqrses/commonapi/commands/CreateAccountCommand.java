package org.essaadani.accountmscqrses.commonapi.commands;

import lombok.Getter;

public class CreateAccountCommand extends BaseCommand<String>{
    /*
    * What we need to create an account?
    * */
    @Getter private double initialBalance;
    @Getter private String currency;
    @Getter private String customerId;

    /*
     * END What we need to create an account?
     * */

    public CreateAccountCommand(String id, double initialBalance, String currency, String customerId) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.customerId = customerId;
    }
}
