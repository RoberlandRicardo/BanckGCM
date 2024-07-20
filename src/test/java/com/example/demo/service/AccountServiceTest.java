package com.example.demo.service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.model.Account;
import com.example.demo.model.BonusAccount;
import com.example.demo.model.SavingsAccount;
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
        AccountDTO contaSalario = new AccountDTO();
        contaSalario.setIdentf(1);
        contaSalario.setBalance(300);
        contaSalario.setAccountType('s');
        accountService.adicionar(contaSalario);

        Account contaCadastrada = accountService.consultarConta(1);

        assertNotNull(contaCadastrada);
        assertEquals(contaCadastrada.getBalance(), 300);
        assertEquals(contaCadastrada.getIdentf(), 1);
        assertFalse(contaCadastrada instanceof BonusAccount);
        assertInstanceOf(SavingsAccount.class, contaCadastrada);

        AccountDTO contaNormal = new AccountDTO();
        contaNormal.setIdentf(2);
        contaNormal.setBalance(50);
        contaNormal.setAccountType('n');
        accountService.adicionar(contaNormal);

        contaCadastrada = accountService.consultarConta(2);

        assertNotNull(contaCadastrada);
        assertEquals(contaCadastrada.getBalance(), 0);
        assertEquals(contaCadastrada.getIdentf(), 2);
        assertFalse(contaCadastrada instanceof BonusAccount);
        assertFalse(contaCadastrada instanceof SavingsAccount);
        assertInstanceOf(Account.class, contaCadastrada);

        AccountDTO contaBonus = new AccountDTO();
        contaBonus.setIdentf(3);
        contaBonus.setBalance(50);
        contaBonus.setAccountType('b');
        accountService.adicionar(contaBonus);

        contaCadastrada = accountService.consultarConta(3);

        assertNotNull(contaCadastrada);
        assertEquals(contaCadastrada.getBalance(), 0);
        assertEquals(contaCadastrada.getIdentf(), 3);
        assertFalse(contaCadastrada instanceof SavingsAccount);
        assertInstanceOf(BonusAccount.class, contaCadastrada);
        assertEquals(((BonusAccount) contaCadastrada).getScore(), 10);
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