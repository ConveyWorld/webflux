package com.webflux.service;

import com.webflux.dto.AccountResponse;
import com.webflux.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Account> findAccountById(String accountId);
    Flux<Account> findAllAccounts();
    AccountResponse getAccountResponse(Account account);
    Mono<Account> createAccount(Account account);
    Mono<Account> updateAccount(Account account);
    Mono<Void> deleteAccount(String accountId);
}
