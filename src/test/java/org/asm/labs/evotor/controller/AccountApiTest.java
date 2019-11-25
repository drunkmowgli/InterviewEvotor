package org.asm.labs.evotor.controller;

import lombok.SneakyThrows;
import org.asm.labs.evotor.domain.Account;
import org.asm.labs.evotor.domain.Balance;
import org.asm.labs.evotor.service.AccountService;
import org.asm.labs.evotor.service.exception.AccountAlreadyExistException;
import org.asm.labs.evotor.service.exception.AccountNotExistException;
import org.asm.labs.evotor.service.exception.IncorrectPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AccountApi.class)
@TestPropertySource("classpath:application.yml")
class AccountApiTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AccountService accountService;
    
    @Value("${app.account}")
    private String requestPath;
    
    @Test
    @DisplayName("Должен вернуть статус код 200 на запрос создания пользовательского аккаунта")
    @SneakyThrows
    void shouldReturnStatusCode200onRequestCreateUserAccount() {
        String payload = "{\"type\":\"create\", \"login\":\"testLogin\", \"password\":\"testPassword\"}";
        mockMvc.perform(post(requestPath)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(payload)
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$['result']", is(0)))
               .andDo(print());
    }
    
    @Test
    @DisplayName("Должен вернуть статус код 200 на запрос создания пользовательского аккаунта и ответ, " +
        "содержащий 1, если пользователь с таким логином уже существует")
    @SneakyThrows
    void shouldReturnStatusCode200onRequestCreateUserAccountAnd1inBodyIfUserAccountAlreadyExist() {
        String payload = "{\"type\":\"create\", \"login\":\"testLogin\", \"password\":\"testPassword\"}";
        doThrow(new AccountAlreadyExistException("Аккаунт с таким логином уже существует"))
            .when(accountService).save("testLogin", "testPassword");
        mockMvc.perform(post(requestPath)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(payload)
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$['result']", is(1)))
               .andDo(print());
        
    }
    
    @Test
    @DisplayName("Должен вернуть статус код 200 на запрос получения баланса пользователльского аккаунта и ответ, " +
        "содержащий 0 с балансом")
    @SneakyThrows
    void shouldReturnStatusCode200onRequestGetUserAccountBalance() {
        Account account = new Account("testLogin", "testPassword");
        Balance balance = new Balance(12.53f);
        account.setBalance(balance);
        when(accountService.getUser("testLogin", "testPassword")).thenReturn(account);
        String payload = "{\"type\":\"get-balance\", \"login\":\"testLogin\", \"password\":\"testPassword\"}";
        mockMvc.perform(post(requestPath)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(payload)
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$['result']", is(0)))
               .andExpect(jsonPath("$['extras']['balance']", is(12.53)))
               .andDo(print());
    }
    
    
    @Test
    @DisplayName("Должен вернуть статус код 200 на запрос получения баланса пользовательского аккаунта и ответ, " +
        "содержащий 3, если пользователя не существует")
    @SneakyThrows
    void shouldReturnStatusCode200onRequestGetUserAccountBalanceAnd3inBodyIfUserAccountNotExist() {
        when(accountService.getUser("testLogin1", "testPassword"))
            .thenThrow(new AccountNotExistException("Аккаунт с таким логином не существует"));
        String payload = "{\"type\":\"get-balance\", \"login\":\"testLogin1\", \"password\":\"testPassword\"}";
        mockMvc.perform(post(requestPath)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(payload)
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$['result']", is(3)))
               .andDo(print());
        
    }
    
    @Test
    @DisplayName("Должен вернуть статус код 200 на запрос получения баланса пользовательского аккаунта и ответ, " +
        "содержащий 4, если пароль для пользовательского аккаунта неверный")
    @SneakyThrows
    void shouldReturnStatusCode200onRequestGetUserAccountBalanceAnd4inBodyIfUserAccountPasswordNotValid() {
        when(accountService.getUser("testLogin", "testPassword1"))
            .thenThrow(new IncorrectPasswordException("Неверный пароль"));
        String payload = "{\"type\":\"get-balance\", \"login\":\"testLogin\", \"password\":\"testPassword1\"}";
        mockMvc.perform(post(requestPath)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(payload)
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$['result']", is(4)))
               .andDo(print());
    }
    
    @Test
    @DisplayName("Должен вернуть статус код 200 на запрос с невалидным телом и ответ, " +
        "содержащий 2")
    @SneakyThrows
    void shouldReturnStatusCode200onRequestWithInvalidBody() {
        String payload = "{\"type\":\"create-account\", \"login\":\"testLogin\", \"password\":\"testPassword\"}";
        doThrow(new RuntimeException("Аккаунт с таким логином уже существует"))
            .when(accountService).save("testLogin", "testPassword");
        mockMvc.perform(post(requestPath)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(payload)
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$['result']", is(2)))
               .andDo(print());
    }
}