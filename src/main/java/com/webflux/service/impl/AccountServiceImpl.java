package com.webflux.service.impl;

import com.webflux.constant.ResponseCodeMapper;
import com.webflux.dto.AccountResponse;
import com.webflux.dto.common.BaseRequest;
import com.webflux.dto.common.BaseResponse;
import com.webflux.dto.common.BaseResult;
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
    public Mono<BaseResponse<Account>> createAccount(BaseRequest<Account> request) {
        BaseResponse<Account> response = new BaseResponse<>();
        response.setResult(BaseResult.builder()
                .code(ResponseCodeMapper.SUCCESSFULLY.getCompositCode())
                .message(ResponseCodeMapper.SUCCESSFULLY.getDescription())
                .build());
        response.setRequestId(request.getRequestId());
        response.setRequestType(request.getRequestType());

        return  accountRepository.save(request.getData())
                .doOnNext(response::setData).thenReturn(response);
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
