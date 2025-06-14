package com.webflux.controller;

import com.webflux.dto.AccountResponse;
import com.webflux.entity.Account;
import com.webflux.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountService accountService;
    // Feature 2 - updated
    // Feature - updated 1 -> updated 2 - new 1
    // SIT
    // DEV
    @GetMapping(value = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AccountResponse> getAccount(@PathVariable("accountId") String accountId) {
        return accountService.findAccountById(accountId)
                .map(accountService::getAccountResponse).onErrorResume(ex -> Mono.empty());
    }

    @GetMapping(value = "/account/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<AccountResponse> fetchAllAccount() {
        return accountService.findAllAccounts().log()
                .map(accountService::getAccountResponse).onErrorResume(ex -> {
                    return Flux.empty();
                });
    }

    @PostMapping(value = "/account/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Account> createAccount(@RequestBody Account account) {
        return accountService.createAccount(account).log();
    }

    @PatchMapping(value = "/account/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AccountResponse> updateAccount(@RequestBody Account account) {
        return accountService.updateAccount(account).log()
                .map(accountService::getAccountResponse).onErrorResume(ex -> {
                    return Mono.empty();
                });
    }

    @DeleteMapping(value = "/account/delete/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> deleteAccount(@PathVariable("accountId") String accountId) {
        return accountService.deleteAccount(accountId).log();
    }

}