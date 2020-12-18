package com.vezenkov.springdataintro.services;

import com.vezenkov.springdataintro.exceptions.IllegalBankOperationException;
import com.vezenkov.springdataintro.models.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface AccountService {
    Account getAccount(Long id);

    void withdrawMoney(BigDecimal money, Long id) throws IllegalBankOperationException;

    void depositMoney(BigDecimal money, Long id) throws IllegalBankOperationException;

    void transferMoney(Long fromId, Long toId, BigDecimal amount) throws IllegalBankOperationException;
}