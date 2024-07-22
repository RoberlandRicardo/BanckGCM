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

    private static final int ACCOUNT_ID_1 = 1;
    private static final int ACCOUNT_ID_2 = 2;
    private static final int ACCOUNT_ID_3 = 3;
    private static final float INITIAL_BALANCE_100 = 100;
    private static final int INITIAL_BALANCE_0 = 0;
    private static final int BONUS_ACCOUNT_SCORE = 10;
    private static final float CREDIT_AMOUNT_50 = 50f;
    private static final float DEBIT_AMOUNT_50 = 50f;
    private static final float CREDIT_AMOUNT_200 = 200f;
    private static final float BONUS_ACCOUNT_EXPECTED_SCORE = 2;

    @Autowired
    AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService.bank = new Bank();
    }

    @Test
    void testAdicionarConta() {
        AccountDTO accountDTO = new AccountDTO(ACCOUNT_ID_1, 's', INITIAL_BALANCE_100);
        Account account = accountService.adicionar(accountDTO);
        assertNotNull(account);
        assertInstanceOf(SavingsAccount.class, account);
        assertEquals(INITIAL_BALANCE_100, account.getBalance());

        accountDTO = new AccountDTO(ACCOUNT_ID_2, 'n', INITIAL_BALANCE_0);
        account = accountService.adicionar(accountDTO);
        assertNotNull(account);
        assertFalse(account instanceof SavingsAccount);
        assertEquals(INITIAL_BALANCE_0, account.getBalance());

        accountDTO = new AccountDTO(ACCOUNT_ID_3, 'b', INITIAL_BALANCE_0);
        account = accountService.adicionar(accountDTO);
        assertNotNull(account);
        assertInstanceOf(BonusAccount.class, account);
        assertEquals(INITIAL_BALANCE_0, account.getBalance());
        assertEquals(BONUS_ACCOUNT_SCORE, ((BonusAccount) account).getScore());
    }

    @Test
    void testConsultarConta() {
        AccountDTO accountDTO = new AccountDTO(ACCOUNT_ID_1, 's', INITIAL_BALANCE_100);
        accountService.adicionar(accountDTO);

        accountDTO = new AccountDTO(ACCOUNT_ID_2, 'n', INITIAL_BALANCE_0);
        accountService.adicionar(accountDTO);

        accountDTO = new AccountDTO(ACCOUNT_ID_3, 'b', INITIAL_BALANCE_0);
        accountService.adicionar(accountDTO);

        Account account = accountService.consultarConta(ACCOUNT_ID_1);
        assertNotNull(account);
        assertTrue(account instanceof SavingsAccount);

        account = accountService.consultarConta(ACCOUNT_ID_2);
        assertNotNull(account);
        assertFalse(account instanceof SavingsAccount);

        account = accountService.consultarConta(ACCOUNT_ID_3);
        assertNotNull(account);
        assertTrue(account instanceof BonusAccount);
    }

    @Test
    void testConsultarSaldo() {
        AccountDTO accountDTO = new AccountDTO(ACCOUNT_ID_1, 's', INITIAL_BALANCE_100);
        accountService.adicionar(accountDTO);

        float balance = accountService.consultarSaldo(ACCOUNT_ID_1);
        assertEquals(INITIAL_BALANCE_100, balance);
    }

    @Test
    void testCreditar() {
        AccountDTO accountDTO = new AccountDTO(ACCOUNT_ID_1, 'n', INITIAL_BALANCE_0);
        accountService.adicionar(accountDTO);

        accountService.creditar(ACCOUNT_ID_1, CREDIT_AMOUNT_50);
        Account account = accountService.consultarConta(ACCOUNT_ID_1);
        assertEquals(CREDIT_AMOUNT_50, account.getBalance());

        accountService.creditar(ACCOUNT_ID_1, -10f);
        assertEquals(CREDIT_AMOUNT_50, account.getBalance());

        accountDTO = new AccountDTO(ACCOUNT_ID_2, 'b', INITIAL_BALANCE_0);
        accountService.adicionar(accountDTO);
        accountService.creditar(ACCOUNT_ID_2, CREDIT_AMOUNT_200);
        account = accountService.consultarConta(ACCOUNT_ID_2);
        assertEquals(CREDIT_AMOUNT_200, account.getBalance());
        assertEquals(BONUS_ACCOUNT_EXPECTED_SCORE, ((BonusAccount) account).getScore());
    }

    @Test
    void testDebitar() {
        AccountDTO accountDTO = new AccountDTO(ACCOUNT_ID_1, 'n', INITIAL_BALANCE_100);
        accountService.adicionar(accountDTO);

        accountService.debitar(ACCOUNT_ID_1, DEBIT_AMOUNT_50);
        Account account = accountService.consultarConta(ACCOUNT_ID_1);
        assertEquals(INITIAL_BALANCE_100 - DEBIT_AMOUNT_50, account.getBalance());

        accountService.debitar(ACCOUNT_ID_1, -10f);
        assertEquals(INITIAL_BALANCE_100 - DEBIT_AMOUNT_50, account.getBalance());

        accountService.debitar(ACCOUNT_ID_1, INITIAL_BALANCE_100);
        assertEquals(-INITIAL_BALANCE_100 + DEBIT_AMOUNT_50, account.getBalance());
    }

    @Test
    void testTransferencia() {
        AccountDTO fromAccountDTO = new AccountDTO(ACCOUNT_ID_1, 'n', INITIAL_BALANCE_100);
        accountService.adicionar(fromAccountDTO);

        AccountDTO toAccountDTO = new AccountDTO(ACCOUNT_ID_2, 'b', INITIAL_BALANCE_0);
        accountService.adicionar(toAccountDTO);

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setFrom(ACCOUNT_ID_1);
        transferDTO.setTo(ACCOUNT_ID_2);
        transferDTO.setAmount(CREDIT_AMOUNT_50);
        accountService.transferir(transferDTO);

        Account fromAccount = accountService.consultarConta(ACCOUNT_ID_1);
        Account toAccount = accountService.consultarConta(ACCOUNT_ID_2);
        assertEquals(INITIAL_BALANCE_100 - CREDIT_AMOUNT_50, fromAccount.getBalance());
        assertEquals(CREDIT_AMOUNT_50, toAccount.getBalance());
        assertEquals(0, ((BonusAccount) toAccount).getScore());

        transferDTO.setAmount(-10f);
        accountService.transferir(transferDTO);
        assertEquals(INITIAL_BALANCE_100 - CREDIT_AMOUNT_50, fromAccount.getBalance());
        assertEquals(CREDIT_AMOUNT_50, toAccount.getBalance());

        transferDTO.setAmount(INITIAL_BALANCE_100);
        accountService.transferir(transferDTO);
        assertEquals(INITIAL_BALANCE_100 - CREDIT_AMOUNT_50, fromAccount.getBalance());
        assertEquals(CREDIT_AMOUNT_50, toAccount.getBalance());

        transferDTO.setAmount(CREDIT_AMOUNT_50 - 10f);
        accountService.transferir(transferDTO);
        assertEquals(INITIAL_BALANCE_100 - CREDIT_AMOUNT_50 - (CREDIT_AMOUNT_50 - 10f), fromAccount.getBalance());
        assertEquals(CREDIT_AMOUNT_50 + CREDIT_AMOUNT_50 - 10f, toAccount.getBalance());
        assertEquals(0, ((BonusAccount) toAccount).getScore());
    }

    @Test
    void testRenderJuros() {
        AccountDTO accountDTO = new AccountDTO(ACCOUNT_ID_1, 's', INITIAL_BALANCE_100);
        accountService.adicionar(accountDTO);

        accountService.renderJuros(ACCOUNT_ID_1, 5f);
        Account account = accountService.consultarConta(ACCOUNT_ID_1);
        assertEquals(105.00f, account.getBalance());
    }
}
