package com.vezenkov.springdataintro;

import com.vezenkov.springdataintro.models.Account;
import com.vezenkov.springdataintro.models.User;
import com.vezenkov.springdataintro.services.AccountService;
import com.vezenkov.springdataintro.services.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;

    private final AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @SneakyThrows
    @Override
    public void run(String... args) throws Exception {
        User pesho = new User("Pesho");
        pesho.setAge(20);

        Account accountP = new Account();
        accountP.setBalance(new BigDecimal(2000));
        accountP.setUser(pesho);

        pesho.getAccounts().add(accountP);
        this.userService.registerUser(pesho);

        User gosho = new User("Gosho");
        gosho.setAge(20);

        Account accountG = new Account();
        accountG.setBalance(new BigDecimal(4000));
        accountG.setUser(gosho);

        gosho.getAccounts().add(accountG);
        this.userService.registerUser(gosho);

        System.out.println("PESHO: " + accountService.getAccount(accountP.getId()).getBalance());
        System.out.println("GOSHO: " + accountService.getAccount(accountG.getId()).getBalance());

        this.accountService.transferMoney(accountG.getId(), accountP.getId(), BigDecimal.valueOf(2000));

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        System.out.println("PESHO: " + accountService.getAccount(accountP.getId()).getBalance());
        System.out.println("GOSHO: " + accountService.getAccount(accountG.getId()).getBalance());
    }
}
