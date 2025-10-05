package com.webflux.service;

import com.webflux.dto.AccountResponse;
import com.webflux.dto.common.BaseRequest;
import com.webflux.dto.common.BaseResponse;
import com.webflux.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Account> findAccountById(String accountId);
    Flux<Account> findAllAccounts();
    AccountResponse getAccountResponse(Account account);
    Mono<BaseResponse<Account>> createAccount(BaseRequest<Account> account);
    Mono<Account> updateAccount(Account account);
    Mono<Void> deleteAccount(String accountId);
}
