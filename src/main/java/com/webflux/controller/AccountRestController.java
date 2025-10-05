package com.webflux.controller;

import com.webflux.dto.AccountResponse;
import com.webflux.dto.common.BaseRequest;
import com.webflux.dto.common.BaseResponse;
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
public class AccountRestController extends CommonController {

    private final AccountService accountService;

    @GetMapping(value = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AccountResponse> getAccount(@PathVariable("accountId") String accountId) {
        return accountService.findAccountById(accountId)
                .map(accountService::getAccountResponse).onErrorResume(ex -> Mono.empty());
    }

    @GetMapping(value = "/account/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<AccountResponse> fetchAllAccount() {
        return accountService.findAllAccounts().log()
                .map(accountService::getAccountResponse).onErrorResume(ex -> Flux.empty());
    }

    @PostMapping(value = "/account/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BaseResponse<Account>> createAccount(@RequestBody BaseRequest<Account> account) {
        long startTime = System.currentTimeMillis();

        return processRestApi(
                account, Account.class
                , () -> accountService.createAccount(account)
                , res -> res
                , "createAccount"
                , startTime
        );
    }

    @PatchMapping(value = "/account/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AccountResponse> updateAccount(@RequestBody Account account) {
        return accountService.updateAccount(account).log()
                .map(accountService::getAccountResponse).onErrorResume(ex -> Mono.empty());
    }

    @DeleteMapping(value = "/account/delete/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> deleteAccount(@PathVariable("accountId") String accountId) {
        return accountService.deleteAccount(accountId).log();
    }

}