package com.webflux.controller;

import com.webflux.dto.AccountResponse;
import com.webflux.entity.Account;
import com.webflux.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class AccountGraphQLController {

    private final AccountService accountService;

    @QueryMapping
    public Mono<AccountResponse> findAccountById(@Argument String accountId) {
        return accountService.findAccountById(accountId).log()
                .map(accountService::getAccountResponse).onErrorResume(ex -> Mono.empty());
    }

    @QueryMapping
    public Flux<AccountResponse> fetchAllAccount() {
        return accountService.findAllAccounts().log()
                .map(accountService::getAccountResponse).onErrorResume(ex -> Flux.empty());
    }

//    @MutationMapping
//    public Mono<AccountResponse> createAccount(@Argument Account account) {
//        return accountService.createAccount(account).log()
//                .map(accountService::getAccountResponse).onErrorResume(ex -> Mono.empty());
//    }

    @MutationMapping
    public Mono<AccountResponse> updateAccount(@Argument Account account, @Argument String accountId) {
        return accountService.updateAccount(account).log()
                .map(accountService::getAccountResponse).onErrorResume(ex -> Mono.empty());
    }

    @MutationMapping
    public Mono<String> deleteAccount(@Argument String accountId) {
        return accountService.deleteAccount(accountId).log().thenReturn("Account deleted successfully");
    }
}