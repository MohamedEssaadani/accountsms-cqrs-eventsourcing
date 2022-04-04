package org.essaadani.accountmscqrses.query.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.essaadani.accountmscqrses.commonapi.queries.GetAccountByIdQuery;
import org.essaadani.accountmscqrses.commonapi.queries.GetAllAccountsQuery;
import org.essaadani.accountmscqrses.query.entities.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
@Slf4j
public class AccountQueryController {
    private QueryGateway queryGateway;

    @GetMapping
    public List<Account> getAccounts(){
        // send query to query bus to be handled by query handler
        // query(query, responsetype), multipleInstancesOf => list of account
        List<Account> response = queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();

        return response;
    }

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable String accountId){
        // send query to query bus to be handled by query handler
        // query(query, responsetype), multipleInstancesOf => list of account
        return queryGateway.query(new GetAccountByIdQuery(accountId), ResponseTypes.instanceOf(Account.class)).join();
    }
}
