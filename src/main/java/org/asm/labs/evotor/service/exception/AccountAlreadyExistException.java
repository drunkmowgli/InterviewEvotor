package org.asm.labs.evotor.service.exception;

public class AccountAlreadyExistException extends Exception {
    public AccountAlreadyExistException() {
        super("Аккаунт с таким логином уже существует");
    }
}
