package org.essaadani.accountmscqrses.command.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.essaadani.accountmscqrses.commonapi.commands.CreateAccountCommand;
import org.essaadani.accountmscqrses.commonapi.commands.CreditAccountCommand;
import org.essaadani.accountmscqrses.commonapi.commands.DebitAccountCommand;
import org.essaadani.accountmscqrses.commonapi.enums.AccountStatus;
import org.essaadani.accountmscqrses.commonapi.events.AccountActivatedEvent;
import org.essaadani.accountmscqrses.commonapi.events.AccountCreatedEvent;
import org.essaadani.accountmscqrses.commonapi.events.AccountCreditedEvent;
import org.essaadani.accountmscqrses.commonapi.events.AccountDebitedEvent;
import org.essaadani.accountmscqrses.commonapi.exceptions.AmountIsNegative;
import org.essaadani.accountmscqrses.commonapi.exceptions.BalanceNotSufficientException;

import java.util.Date;

@Aggregate
public class AccountAggregate {
    /*
    * Application Current State
    * */
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;
    private String customerId;

    /*
    * No args constructor is required by AXON
    * */
    public AccountAggregate() {
    }

    /*
    * COMMANDS HANDLERS
    * */

    /*
    * Command handler for CreateAccountCommand (fct de decision)
    * Now we subscribe to command bus for CreateAccountCommand
    * */
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        /*
        * Business Logic
        * */
        if(createAccountCommand.getInitialBalance() <0 )
            throw new RuntimeException("Impossible to create an account with balance < 0!");

        // if everything is good we emit an event(s) => AccountCreatedEvent
        // after that axon will persist data to event store.
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED,
                createAccountCommand.getCustomerId()));
    }

    /*
     * Command handler for CreditAccountCommand (fct de decision)
     * Now we subscribe to command bus for CreditAccountCommand
     * */
    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand){
        /*
         * Business Logic
         * */
        if(creditAccountCommand.getAmount() < 0)
            throw new AmountIsNegative("Amount shouldn't be negative!");

        /*
        * if everything is good now we emit AccountCreditedEvent & persist data to event store
        * */
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency(),
                new Date()));
    }

    /*
     * Command handler for DebitAccountCommand (fct de decision)
     * Now we subscribe to command bus for DebitAccountCommand
     * */
    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand){
        /*
         * Business Logic
         * */
        if(debitAccountCommand.getAmount() < 0)
            throw new AmountIsNegative("Amount shouldn't be negative!");

        if(this.balance < debitAccountCommand.getAmount())
            throw new BalanceNotSufficientException("Balance not sufficient : "+this.balance);

        // BL: check if is the account is suspended

        /*
         * if everything is good now we emit AccountCreditedEvent & persist data to event store
         * */
        AggregateLifecycle.apply(new AccountDebitedEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                debitAccountCommand.getCurrency(),
                new Date()));
    }

    /*
     * EVENTS HANDLERS
     * */

    /*
    * fct de evolution. will be executed after the AccountCreatedEvent emitted to update the state
    */
    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        // update state
        this.accountId = accountCreatedEvent.getId();
        this.balance = accountCreatedEvent.getInitialBalance();
        this.currency = accountCreatedEvent.getCurrency();
        this.status = AccountStatus.CREATED;
        this.customerId = accountCreatedEvent.getCustomerId();

        // activate the account
        // emit AccountActivatedEvent & persist data to event store
        AggregateLifecycle.apply(new AccountActivatedEvent(
                accountCreatedEvent.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    /*
     * fct d evolution. will be executed after the AccountActivatedEvent emitted to update the state
     */
    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        this.status = accountActivatedEvent.getAccountStatus();
    }

    /*
    * fct de evolution. will be executed after the AccountCreditedEvent to update the app state
    * */
    @EventSourcingHandler
    public void on(AccountCreditedEvent  accountCreditedEvent){
        this.balance += accountCreditedEvent.getAmount();
    }

    /*
     * fct de evolution. will be executed after the AccountDebitedEvent to update the app state
     * */
    @EventSourcingHandler
    public void on(AccountDebitedEvent  accountDebitedEvent){
        this.balance -= accountDebitedEvent.getAmount();
    }

}
