package org.asm.labs.evotor.service.impl;


import lombok.SneakyThrows;
import org.asm.labs.evotor.domain.Account;
import org.asm.labs.evotor.repository.AccountRepository;
import org.asm.labs.evotor.service.AccountService;
import org.asm.labs.evotor.service.exception.AccountAlreadyExistException;
import org.asm.labs.evotor.service.exception.AccountNotExistException;
import org.asm.labs.evotor.service.exception.IncorrectPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DisplayName("Тестирование сервиса по работе с аккаунтом")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AccountServiceImpl.class})
class AccountServiceImplTest {
    
    @Autowired
    private AccountService accountService;
    
    @MockBean
    private AccountRepository accountRepository;
    
    @Captor
    private ArgumentCaptor<Account> argumentCaptor;
    
    @Test
    @DisplayName("Должен корректно сохранить аккаунт пользователя")
    @SneakyThrows
    void shouldCorrectSaveUserAccount() {
        accountService.save("testLogin", "testPassword");
        verify(accountRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues()).isNotNull();
        assertEquals("testLogin", argumentCaptor.getValue().getLogin());
        assertEquals("testPassword", argumentCaptor.getValue().getPassword());
    }
    
    @Test
    @DisplayName("Должен корректно возвращать аккаунт пользователя")
    @SneakyThrows
    void shouldCorrectReturnUserAccount() {
        Account account = new Account("testLogin", "testPassword");
        when(accountRepository.findByLogin("testLogin")).thenReturn(Optional.of(account));
        Account actualAccount = accountService.getUser("testLogin", "testPassword");
        assertEquals("testLogin", actualAccount.getLogin());
        assertEquals("testPassword", actualAccount.getPassword());
    }
    
    @Test
    @DisplayName("Должен выбросить пользовательское исключение AccountAlreadyExistException, " +
        "если аккаунт с таким логином уже существует")
    void shouldThrowAccountAlreadyExistException() {
        when(accountRepository.save(any())).thenThrow(new DataIntegrityViolationException("Duplicate keys"));
        assertThrows(AccountAlreadyExistException.class,
            () -> accountService.save("testLogin", "testPassword"));
    }
    
    @Test
    @DisplayName("Должен выбросить пользовательское исключение IncorrectPasswordException, " +
        "если пароль не соответствует логину")
    void shouldThrowIncorrectPasswordException() {
        Account account = new Account("testLogin", "testPassword");
        when(accountRepository.findByLogin("testLogin")).thenReturn(Optional.of(account));
        assertThrows(IncorrectPasswordException.class,
            () -> accountService.getUser("testLogin", "testPassword1"));
    }
    
    @Test
    @DisplayName("Должен выбросить пользовательское исключение AccountNotExistException, " +
        "если пользователя с таким логином не существует")
    void shouldThrowAccountNotExistException() {
        Account account = new Account("testLogin", "testPassword");
        when(accountRepository.findByLogin("testLogin")).thenReturn(Optional.of(account));
        assertThrows(AccountNotExistException.class,
            () -> accountService.getUser("testLogin1", "testPassword"));
    }
}