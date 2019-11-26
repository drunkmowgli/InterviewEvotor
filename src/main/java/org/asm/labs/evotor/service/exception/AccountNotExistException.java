package org.asm.labs.evotor.service.exception;

public class AccountNotExistException extends Exception {
    public AccountNotExistException() {
        super("Аккаунт с таким логином не существует");
    }
}
