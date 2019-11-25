package org.asm.labs.evotor.service.impl;

import org.asm.labs.evotor.domain.Account;
import org.asm.labs.evotor.domain.Balance;
import org.asm.labs.evotor.repository.AccountRepository;
import org.asm.labs.evotor.service.AccountService;
import org.asm.labs.evotor.service.exception.AccountAlreadyExistException;
import org.asm.labs.evotor.service.exception.AccountNotExistException;
import org.asm.labs.evotor.service.exception.IncorrectPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    
    private final AccountRepository accountRepository;
    
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Override
    public void save(String login, String password) throws AccountAlreadyExistException {
        try {
            Balance balance = new Balance(0);
            Account account = new Account(login, password);
            account.setBalance(balance);
            accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            throw new AccountAlreadyExistException("Аккаунт с таким логином уже существует");
        }
    }
    
    @Override
    public Account getUser(String login, String password) throws IncorrectPasswordException, AccountNotExistException {
        try {
            Account account = accountRepository.findByLogin(login);
            if (account.getPassword().equals(password)) {
                return account;
            } else {
                throw new IncorrectPasswordException("Не верный пароль");
                
            }
        } catch (NullPointerException e) {
            throw new AccountNotExistException("Аккаунт с таким логином не существует");
        }
    }
}
