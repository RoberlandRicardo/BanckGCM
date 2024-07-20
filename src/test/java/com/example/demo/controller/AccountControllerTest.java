package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


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
        mockMvc.perform(
                post("/banco/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\t\"identf\": 1,\n" +
                                "\t\"accountType\" : \"s\",\n" +
                                "\t\"balance\": 100\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.identf", is(1)))
                .andExpect(jsonPath("$.balance", is(100)));

        mockMvc.perform(
                        post("/banco/conta")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "\t\"identf\": 2,\n" +
                                        "\t\"accountType\" : \"n\",\n" +
                                        "\t\"balance\": 100\n" +
                                        "}")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.identf", is(2)))
                .andExpect(jsonPath("$.balance", is(100)));

        mockMvc.perform(
                        post("/banco/conta")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "\t\"identf\": 3,\n" +
                                        "\t\"accountType\" : \"b\",\n" +
                                        "\t\"balance\": 100\n" +
                                        "}")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.identf", is(3)))
                .andExpect(jsonPath("$.balance", is(100)))
                .andExpect(jsonPath("$.balance", is(10)));
    }

    @Test
    void consultarConta() {
        Account account = new Account();
        account.setIdentf(1);
        account.setBalance(100);

    }

    @Test
    void consultarSaldo() {
    }

    @Test
    void creditar() {
    }

    @Test
    void debitar() {
    }

    @Test
    void testDebitar() {
    }

    @Test
    void renderJuros() {
    }
}