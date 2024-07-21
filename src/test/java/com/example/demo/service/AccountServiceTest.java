package com.example.demo.service;

import com.example.demo.Bank;
import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.TransferDTO;
import com.example.demo.model.Account;
import com.example.demo.model.BonusAccount;
import com.example.demo.model.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @BeforeEach
    void setUp() {
        // Reset the bank for each test
        accountService.bank = new Bank();
    }

    @Test
    void testAdicionarConta() {
        // Savings Account
        AccountDTO accountDTO = new AccountDTO(1, 's', 100);
        Account account = accountService.adicionar(accountDTO);
        assertNotNull(account);
        assertTrue(account instanceof SavingsAccount);
        assertEquals(100, account.getBalance());

        // Normal Account
        accountDTO = new AccountDTO(2, 'n', 50);
        account = accountService.adicionar(accountDTO);
        assertNotNull(account);
        assertFalse(account instanceof SavingsAccount);
        assertEquals(0, account.getBalance());

        // Bonus Account
        accountDTO = new AccountDTO(3, 'b', 50);
        account = accountService.adicionar(accountDTO);
        assertNotNull(account);
        assertTrue(account instanceof BonusAccount);
        assertEquals(0, account.getBalance());
        assertEquals(10, ((BonusAccount) account).getScore());
    }

    @Test
    void testConsultarConta() {
        // Register accounts
        AccountDTO accountDTO = new AccountDTO(1, 's', 100);
        accountService.adicionar(accountDTO);

        accountDTO = new AccountDTO(2, 'n', 50);
        accountService.adicionar(accountDTO);

        accountDTO = new AccountDTO(3, 'b', 50);
        accountService.adicionar(accountDTO);

        // Consult accounts
        Account account = accountService.consultarConta(1);
        assertNotNull(account);
        assertTrue(account instanceof SavingsAccount);

        account = accountService.consultarConta(2);
        assertNotNull(account);
        assertFalse(account instanceof SavingsAccount);

        account = accountService.consultarConta(3);
        assertNotNull(account);
        assertTrue(account instanceof BonusAccount);
    }

    @Test
    void testConsultarSaldo() {
        AccountDTO accountDTO = new AccountDTO(1, 's', 100);
        accountService.adicionar(accountDTO);

        float balance = accountService.consultarSaldo(1);
        assertEquals(100, balance);
    }

    @Test
    void testCreditar() {
        // Normal Account
        AccountDTO accountDTO = new AccountDTO(1, 'n', 0);
        accountService.adicionar(accountDTO);

        accountService.creditar(1, 50f);
        Account account = accountService.consultarConta(1);
        assertEquals(50, account.getBalance());

        // Negative value
        accountService.creditar(1, -10f);
        assertEquals(50, account.getBalance());

        // Bonus Account
        accountDTO = new AccountDTO(2, 'b', 0);
        accountService.adicionar(accountDTO);
        accountService.creditar(2, 200f);
        account = accountService.consultarConta(2);
        assertEquals(200, account.getBalance());
        assertEquals(2, ((BonusAccount) account).getScore());
    }

    @Test
    void testDebitar() {
        // Normal Account
        AccountDTO accountDTO = new AccountDTO(1, 'n', 100);
        accountService.adicionar(accountDTO);

        accountService.debitar(1, 50f);
        Account account = accountService.consultarConta(1);
        assertEquals(50, account.getBalance());

        // Negative value
        accountService.debitar(1, -10f);
        assertEquals(50, account.getBalance());

        // Exceeding balance
        accountService.debitar(1, 150f); // Attempting to overdraft
        assertEquals(50, account.getBalance());
    }

    @Test
    void testTransferencia() {
        AccountDTO fromAccountDTO = new AccountDTO(1, 'n', 100);
        accountService.adicionar(fromAccountDTO);

        AccountDTO toAccountDTO = new AccountDTO(2, 'b', 0);
        accountService.adicionar(toAccountDTO);

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setFrom(1);
        transferDTO.setTo(2);
        transferDTO.setAmount(50f);
        accountService.transferir(transferDTO); // Corrigido o nome do método

        Account fromAccount = accountService.consultarConta(1);
        Account toAccount = accountService.consultarConta(2);
        assertEquals(50, fromAccount.getBalance());
        assertEquals(50, toAccount.getBalance());
        assertEquals(0, ((BonusAccount) toAccount).getScore());

        // Negative value
        transferDTO.setAmount(-10f);
        accountService.transferir(transferDTO); // Corrigido o nome do método
        assertEquals(50, fromAccount.getBalance());
        assertEquals(50, toAccount.getBalance());

        // Exceeding balance
        transferDTO.setAmount(60f);
        accountService.transferir(transferDTO); // Corrigido o nome do método
        assertEquals(50, fromAccount.getBalance());
        assertEquals(50, toAccount.getBalance());

        // Bonus transfer
        transferDTO.setAmount(40f);
        accountService.transferir(transferDTO); // Corrigido o nome do método
        assertEquals(10, fromAccount.getBalance());
        assertEquals(90, toAccount.getBalance());
        assertEquals(0, ((BonusAccount) toAccount).getScore());
    }

    @Test
    void testRenderJuros() {
        AccountDTO accountDTO = new AccountDTO(1, 's', 100);
        accountService.adicionar(accountDTO);

        accountService.renderJuros(1, 5f);
        Account account = accountService.consultarConta(1);
        assertEquals(105.00f, account.getBalance());
    }
}
