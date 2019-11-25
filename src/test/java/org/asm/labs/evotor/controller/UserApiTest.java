package org.asm.labs.evotor.controller;

import lombok.SneakyThrows;
import org.asm.labs.evotor.repository.AccountRepository;
import org.asm.labs.evotor.service.AccountService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserApi.class)
@TestPropertySource("classpath:application.yml")
class UserApiTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AccountService accountService;
    
    @MockBean
    private AccountRepository accountRepository;
    
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
        "содержащий 1, если пользователь уже есть")
    @SneakyThrows
    void shouldReturnStatusCode200onRequestCreateUserAccountAndOneIfUserAccountAlreadyExist() {
        String payload = "{\"type\":\"create\", \"login\":\"testLogin\", \"password\":\"testPassword\"}";
        mockMvc.perform(post(requestPath)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(payload)
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$['result']", is(1)))
               .andDo(print());
        
    }
}