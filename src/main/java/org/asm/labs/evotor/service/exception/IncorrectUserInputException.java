package org.asm.labs.evotor.service.exception;

public class IncorrectUserInputException extends Exception {
    public IncorrectUserInputException() {
        super("Некорректные пользовательские данные. Логин и/или пароль не могут быть пустыми");
    }
}
