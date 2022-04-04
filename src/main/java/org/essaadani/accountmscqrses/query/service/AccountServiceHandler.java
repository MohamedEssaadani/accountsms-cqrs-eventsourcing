package org.essaadani.accountmscqrses.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.essaadani.accountmscqrses.commonapi.enums.OperationType;
import org.essaadani.accountmscqrses.commonapi.events.*;
import org.essaadani.accountmscqrses.commonapi.queries.GetAccountByIdQuery;
import org.essaadani.accountmscqrses.commonapi.queries.GetAllAccountsQuery;
import org.essaadani.accountmscqrses.query.entities.Account;
import org.essaadani.accountmscqrses.query.entities.Customer;
import org.essaadani.accountmscqrses.query.entities.Operation;
import org.essaadani.accountmscqrses.query.repository.AccountRepository;
import org.essaadani.accountmscqrses.query.repository.CustomerRepository;
import org.essaadani.accountmscqrses.query.repository.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private CustomerRepository customerRepository;

    /*
    * Listen to events
    * */
    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("***********************************************");
        log.info("AccountCreatedEvent received...");
        // we have no BL in query side. we do just projection.
        accountRepository.save(
                new Account( accountCreatedEvent.getId(),
                        accountCreatedEvent.getInitialBalance(),
                        accountCreatedEvent.getStatus(),
                        accountCreatedEvent.getCurrency(),
                        accountCreatedEvent.getCustomerId(),
                        null
                )
        );
    }

    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        log.info("***********************************************");
        log.info("AccountActivatedEvent received...");
        Account account = accountRepository.findById(accountActivatedEvent.getId()).get();
        account.setStatus(accountActivatedEvent.getAccountStatus());
    }

    @EventHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        log.info("***********************************************");
        log.info("AccountDebitedEvent received...");
        Account account = accountRepository.findById(accountDebitedEvent.getId()).get();

        // create operation
        Operation operation = new Operation();
        operation.setAmount(accountDebitedEvent.getAmount());
        operation.setDate(accountDebitedEvent.getDate());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        // debit account
        account.setBalance(account.getBalance()-accountDebitedEvent.getAmount());
    }

    @EventHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        log.info("***********************************************");
        log.info("AccountCreditedEvent received...");
        Account account = accountRepository.findById(accountCreditedEvent.getId()).get();

        // create operation
        Operation operation = new Operation();
        operation.setAmount(accountCreditedEvent.getAmount());
        operation.setDate(accountCreditedEvent.getDate());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        // credit account
        account.setBalance(account.getBalance() + accountCreditedEvent.getAmount());
    }

    @EventHandler
    public void on(org.essaadani.customermscqrseventsourcing.commonapi.events.CustomerCreatedEvent event){
        log.info("*******************************************");
        log.info("CustomerCreatedEvent received!");
        log.info("*******************************************");
        Customer customer = new Customer();
        customer.setCustomerId(event.getId());
        customer.setFirstName(event.getFirstName());
        customer.setLastName(event.getLastName());
        customer.setCin(event.getCin());
        customer.setBirthDate(event.getBirthDate());

        customerRepository.save(customer);
    }
    /*
    * Queries Handlers
    * */
    @QueryHandler
    public List<Account> getAllAccounts(GetAllAccountsQuery getAllAccountsQuery){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account getAccount(GetAccountByIdQuery query ){
        return accountRepository.findById(query.getId()).get();
    }
}

