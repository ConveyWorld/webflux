package com.webflux.service.impl;

import com.webflux.dto.AccountResponse;
import com.webflux.entity.Account;
import com.webflux.repository.AccountRepository;
import com.webflux.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<Account> findAccountById(String accountId) {
        return accountRepository.findAccountByAccountId(accountId);
    }

    @Override
    public Flux<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public AccountResponse getAccountResponse(Account account) {
        AccountResponse response = mapper.map(account, AccountResponse.class);
        response.setStatus(account.getAge() > 25 ? "Adult" : "Minor");
        return response;
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        return accountRepository.save(account);
    }


    @Override
    public Mono<Account> updateAccount(Account account) {
        return accountRepository.findAccountByAccountId(account.getAccountId())
                .flatMap(existingAccount -> {
                    existingAccount.setName(account.getName());
                    return accountRepository.save(existingAccount);
                });
    }

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        return accountRepository.deleteByAccountId(accountId);
    }
}
