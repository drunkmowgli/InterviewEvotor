package org.asm.labs.evotor.service;

import org.asm.labs.evotor.domain.Account;
import org.asm.labs.evotor.service.exception.AccountAlreadyExistException;
import org.asm.labs.evotor.service.exception.AccountNotExistException;
import org.asm.labs.evotor.service.exception.IncorrectPasswordException;

public interface AccountService {
    
    /**
     * Сохранение пользовательского аккаунта в репозитории.
     *
     * @param login    - Account's login
     * @param password - Account's password
     */
    void save(String login, String password) throws AccountAlreadyExistException;
    
    /**
     * Получение пользовательского аккаунта из репозитория.
     *
     * @param login    - Account's login
     * @param password - Account's password
     * @return Account
     */
    Account getUser(String login, String password) throws IncorrectPasswordException, AccountNotExistException;
}
