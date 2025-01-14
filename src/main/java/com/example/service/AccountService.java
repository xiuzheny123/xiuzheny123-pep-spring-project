package com.example.service;

import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;


@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) {
        
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank.");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long.");
        }

        // Check for duplicate username
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already exists.");
        }

        // Save and return the account
        return accountRepository.save(account);
    }
    public Account login(Account account) throws AuthenticationException{
        return accountRepository.findByUsernameAndPassword(account.getUsername(),account.getPassword())
            .orElseThrow(()->new AuthenticationException("Invalid username or password"));
        
    }
}
