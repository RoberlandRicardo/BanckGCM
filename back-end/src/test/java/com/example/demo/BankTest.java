package com.example.demo;

import com.example.demo.model.Account;
import com.example.demo.model.BonusAccount;
import com.example.demo.model.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void testRegisterAccount() {
        // Normal account
        Account account = bank.registerAccount(1, 'n', 0);
        assertNotNull(account);
        assertEquals(1, account.getIdentf());
        assertEquals(0, account.getBalance());
        assertFalse(account instanceof BonusAccount);
        assertFalse(account instanceof SavingsAccount);

        // Bonus account
        account = bank.registerAccount(2, 'b', 0);
        assertNotNull(account);
        assertTrue(account instanceof BonusAccount);
        assertEquals(10, ((BonusAccount) account).getScore());

        // Savings account
        account = bank.registerAccount(3, 's', 100);
        assertNotNull(account);
        assertTrue(account instanceof SavingsAccount);
        assertEquals(100, account.getBalance());
    }

    @Test
    void testConsultarConta() {
        Account account = bank.registerAccount(1, 'n', 0);
        Account foundAccount = bank.getAccountById(1);
        assertNotNull(foundAccount);
        assertEquals(account.getIdentf(), foundAccount.getIdentf());
    }

    @Test
    void testConsultarSaldo() {
        Account account = bank.registerAccount(1, 's', 100);
        assertNotNull(account); // Ensure the account was created
        assertEquals(1, account.getIdentf()); // Validate the identifier
        assertTrue(account instanceof SavingsAccount); // Ensure the account is a SavingsAccount
        float balance = bank.getBalance(1);
        assertEquals(100, balance);
    }

    @Test
    void testCreditar() {
        Account account = bank.registerAccount(1, 'n', 0);
        bank.addCredit(1, 50f);
        assertEquals(50, account.getBalance());

        // Negative value
        bank.addCredit(1, -10f);
        assertEquals(50, account.getBalance());

        // Bonus account
        account = bank.registerAccount(2, 'b', 0);
        bank.addCredit(2, 200f);
        assertEquals(200, account.getBalance());
        assertEquals(2, ((BonusAccount) account).getScore());
    }

    @Test
    void testDebitar() {
        Account account = bank.registerAccount(1, 'n', 100);
        bank.addDebit(1, 50f);
        assertEquals(-50, account.getBalance()); // o valor que entrar será divída

        // Negative value
        bank.addDebit(1, -10f);
        assertEquals(-50, account.getBalance()); // o valor não pode ser negativo ou zero
    }

    @Test
    void testTransfer() {
        Account fromAccount = bank.registerAccount(1, 'n', 100);
        Account toAccount = bank.registerAccount(2, 'b', 0);

        // Normal transfer
        bank.transfer(1, 2, 50f);
        assertEquals(50, fromAccount.getBalance());
        assertEquals(50, toAccount.getBalance());
        assertEquals(0, ((BonusAccount) toAccount).getScore()); // Score should not update

        // Negative value
        bank.transfer(1, 2, -10f);
        assertEquals(50, fromAccount.getBalance());
        assertEquals(50, toAccount.getBalance());

        // Exceeding balance
        bank.transfer(1, 2, 60f);
        assertEquals(50, fromAccount.getBalance());
        assertEquals(50, toAccount.getBalance());

        // Bonus transfer
        bank.transfer(1, 2, 40f);
        assertEquals(10, fromAccount.getBalance());
        assertEquals(90, toAccount.getBalance());
        assertEquals(0, ((BonusAccount) toAccount).getScore());
    }

    @Test
    void testRenderJuros() {
        SavingsAccount savingsAccount = (SavingsAccount) bank.registerAccount(1, 's', 100);
        bank.yieldInterest(1, 5f);
        assertEquals(105.00f, savingsAccount.getBalance());
    }
}
