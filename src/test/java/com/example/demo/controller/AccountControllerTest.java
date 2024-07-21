package com.example.demo.controller;

import com.example.demo.dto.AccountDTO;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    @DisplayName("Testando cadastro de conta")
    void adicionar() throws Exception {
        AccountDTO accountDTO = new AccountDTO(1, 's', 100);
        Account account = new Account();
        account.setIdentf(1);
        account.setBalance(100);

        when(accountService.adicionar(accountDTO)).thenReturn(account);

        mockMvc.perform(
                post("/banco/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\t\"identf\": 1,\n" +
                                "\t\"accountType\" : \"s\",\n" +
                                "\t\"balance\": 100\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.identf", is(1)))
                .andExpect(jsonPath("$.balance", is(100.0))); // Ensure float comparison
    }
}