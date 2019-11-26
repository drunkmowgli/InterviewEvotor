package org.asm.labs.evotor.service.exception;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException() {
        super("Не верный пароль");
    }
}
