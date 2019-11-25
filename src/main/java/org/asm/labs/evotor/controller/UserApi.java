package org.asm.labs.evotor.controller;

import org.asm.labs.evotor.controller.request.AccountPostRequestBody;
import org.asm.labs.evotor.controller.response.AccountPostResponseBody;
import org.asm.labs.evotor.domain.Account;
import org.asm.labs.evotor.service.AccountService;
import org.asm.labs.evotor.service.exception.AccountAlreadyExistException;
import org.asm.labs.evotor.service.exception.AccountNotExistException;
import org.asm.labs.evotor.service.exception.IncorrectPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@PropertySource("classpath:application.yml")
public class UserApi {
    
    private final AccountService accountService;
    
    @Autowired
    public UserApi(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping("${app.account}")
    public AccountPostResponseBody create(@RequestBody AccountPostRequestBody accountPostRequestBody) {
        try {
            String type = accountPostRequestBody.getType();
            String login = accountPostRequestBody.getLogin();
            String password = accountPostRequestBody.getPassword();
            switch (type) {
                case ("create"):
                    accountService.save(login, password);
                    return new AccountPostResponseBody(0);
                case ("get-balance"):
                    Account account = accountService.getUser(login, password);
                    Map<String, Object> extras = new HashMap<>();
                    extras.put("balance", account.getBalance().getBalance());
                    return new AccountPostResponseBody(0, extras);
                default:
                    throw new RuntimeException();
            }
        } catch (AccountAlreadyExistException e) {
            return new AccountPostResponseBody(1);
        } catch (AccountNotExistException e) {
            return new AccountPostResponseBody(3);
        } catch (IncorrectPasswordException e) {
            return new AccountPostResponseBody(4);
        } catch (RuntimeException e) {
            return new AccountPostResponseBody(2);
        }
    }
    
}
