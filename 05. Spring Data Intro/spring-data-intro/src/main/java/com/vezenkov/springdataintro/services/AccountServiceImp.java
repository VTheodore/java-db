package com.vezenkov.springdataintro.services;

import com.vezenkov.springdataintro.exceptions.IllegalBankOperationException;
import com.vezenkov.springdataintro.models.Account;
import com.vezenkov.springdataintro.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImp implements AccountService{
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImp(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account getAccount(Long id) {
        return this.accountRepository.findAccountById(id);
    }

    @Transactional
    @Override
    public void withdrawMoney(BigDecimal money, Long id) throws IllegalBankOperationException {
        Account account = accountRepository.findAccountById(id);
        if (account.getBalance().compareTo(money) < 0) throw new IllegalBankOperationException();

        account.setBalance(account.getBalance().subtract(money));
        this.accountRepository.save(account);
    }

    @Transactional
    @Override
    public void depositMoney(BigDecimal money, Long id) throws IllegalBankOperationException {
        Account account = this.accountRepository.findAccountById(id);

        if (account == null) throw new IllegalBankOperationException();
        account.setBalance(account.getBalance().add(money));
        this.accountRepository.save(account);
    }

    @Transactional
    @Override
    public void transferMoney(Long fromId, Long toId, BigDecimal amount) throws IllegalBankOperationException {
        withdrawMoney(amount, fromId);
        depositMoney(amount, toId);
    }
}
