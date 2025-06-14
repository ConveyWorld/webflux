package com.webflux.repository;

import com.webflux.entity.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveCrudRepository<Account, String> {
    Mono<Account> findAccountByAccountId(String accountId);
    Mono<Void> deleteByAccountId(String accountId);
}
