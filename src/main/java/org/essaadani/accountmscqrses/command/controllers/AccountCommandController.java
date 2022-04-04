package org.essaadani.accountmscqrses.command.controllers;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.essaadani.accountmscqrses.commonapi.commands.CreateAccountCommand;
import org.essaadani.accountmscqrses.commonapi.commands.CreditAccountCommand;
import org.essaadani.accountmscqrses.commonapi.commands.DebitAccountCommand;
import org.essaadani.accountmscqrses.commonapi.dtos.CreateAccountRequestDTO;
import org.essaadani.accountmscqrses.commonapi.dtos.CreditAccountRequestDTO;
import org.essaadani.accountmscqrses.commonapi.dtos.DebitAccountRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        /*
        * create new command to be handled by CommandHandler
        * */
        // send is async function. after finishing the task, send will return the id of the command
       CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency(),
               request.getCustomerId()));

       return commandResponse;
    }

    @PutMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        /*
        * create new command to be handled by CommandHandler in(Aggregate)
        * */
        // send is async function. after finishing the task, send will return the id of the command
       CompletableFuture<String> commandResponse = commandGateway.send(
               new CreditAccountCommand(
                       request.getAccountId(),
                       request.getAmount(),
                       request.getCurrency()
               )
       );

       return commandResponse;
    }

    @PutMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        /*
         * create new command to be handled by CommandHandler in(Aggregate)
         * */
        // send is async function. after finishing the task, send will return the id of the command
        CompletableFuture<String> commandResponse = commandGateway.send(
                new DebitAccountCommand(
                        request.getAccountId(),
                        request.getAmount(),
                        request.getCurrency()
                )
        );

        return commandResponse;
    }

    /**
     * Handle Exceptions
     */
    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity<String> response = new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return response;
    }

    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
}
