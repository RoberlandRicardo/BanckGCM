package com.example.demo.service;

import com.example.demo.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Test
    void adicionar() {
        Account contaSalario = new Account();
        accountService.adicionar()
    }

    @Test
    void consultarConta() {
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