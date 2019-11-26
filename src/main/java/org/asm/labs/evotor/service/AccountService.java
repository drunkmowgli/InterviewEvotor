package org.asm.labs.evotor.service;

import org.asm.labs.evotor.domain.Account;
import org.asm.labs.evotor.service.exception.AccountAlreadyExistException;
import org.asm.labs.evotor.service.exception.AccountNotExistException;
import org.asm.labs.evotor.service.exception.IncorrectPasswordException;

public interface AccountService {
    
    /**
     * Save user to DB.
     *
     * @param login    - Account's login
     * @param password - Account's password
     */
    void save(String login, String password) throws AccountAlreadyExistException;
    
    /**
     * Get user from DB.
     *
     * @param login    - Account's login
     * @param password - Account's password
     * @return Account
     */
    Account getUser(String login, String password) throws IncorrectPasswordException, AccountNotExistException;
}
