package com.example.demo;

import com.example.demo.model.Account;
import com.example.demo.model.BonusAccount;
import com.example.demo.model.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    private static final int ACCOUNT_ID_1 = 1;
    private static final int ACCOUNT_ID_2 = 2;
    private static final int ACCOUNT_ID_3 = 3;
    private static final int INITIAL_BALANCE_100 = 100;
    private static final int INITIAL_BALANCE_0 = 0;
    private static final int BONUS_ACCOUNT_SCORE = 10;
    private static final float CREDIT_AMOUNT_50 = 50f;
    private static final float DEBIT_AMOUNT_50 = 50f;
    private static final float CREDIT_AMOUNT_200 = 200f;
    private static final float BONUS_ACCOUNT_EXPECTED_SCORE = 2;

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void testRegisterAccount() {
        Account account = bank.registerAccount(ACCOUNT_ID_1, 'n', INITIAL_BALANCE_0);
        assertNotNull(account);
        assertEquals(ACCOUNT_ID_1, account.getIdentf());
        assertEquals(INITIAL_BALANCE_0, account.getBalance());
        assertFalse(account instanceof BonusAccount);
        assertFalse(account instanceof SavingsAccount);

        account = bank.registerAccount(ACCOUNT_ID_2, 'b', INITIAL_BALANCE_0);
        assertNotNull(account);
        assertTrue(account instanceof BonusAccount);
        assertEquals(BONUS_ACCOUNT_SCORE, ((BonusAccount) account).getScore());

        account = bank.registerAccount(ACCOUNT_ID_3, 's', INITIAL_BALANCE_100);
        assertNotNull(account);
        assertTrue(account instanceof SavingsAccount);
        assertEquals(INITIAL_BALANCE_100, account.getBalance());
    }

    @Test
    void testConsultarConta() {
        Account account = bank.registerAccount(ACCOUNT_ID_1, 'n', INITIAL_BALANCE_0);
        Account foundAccount = bank.getAccountById(ACCOUNT_ID_1);
        assertNotNull(foundAccount);
        assertEquals(account.getIdentf(), foundAccount.getIdentf());
    }

    @Test
    void testConsultarSaldo() {
        Account account = bank.registerAccount(ACCOUNT_ID_1, 's', INITIAL_BALANCE_100);
        assertNotNull(account);
        assertEquals(ACCOUNT_ID_1, account.getIdentf());
        assertTrue(account instanceof SavingsAccount);
        float balance = bank.getBalance(ACCOUNT_ID_1);
        assertEquals(INITIAL_BALANCE_100, balance);
    }

    @Test
    void testCreditar() {
        Account account = bank.registerAccount(ACCOUNT_ID_1, 'n', INITIAL_BALANCE_0);
        bank.addCredit(ACCOUNT_ID_1, CREDIT_AMOUNT_50);
        assertEquals(CREDIT_AMOUNT_50, account.getBalance());

        bank.addCredit(ACCOUNT_ID_1, -10f);
        assertEquals(CREDIT_AMOUNT_50, account.getBalance());

        account = bank.registerAccount(ACCOUNT_ID_2, 'b', INITIAL_BALANCE_0);
        bank.addCredit(ACCOUNT_ID_2, CREDIT_AMOUNT_200);
        assertEquals(CREDIT_AMOUNT_200, account.getBalance());
        assertEquals(BONUS_ACCOUNT_EXPECTED_SCORE, ((BonusAccount) account).getScore());
    }

    @Test
    void testDebitar() {
        Account account = bank.registerAccount(ACCOUNT_ID_1, 'n', INITIAL_BALANCE_100);
        bank.addDebit(ACCOUNT_ID_1, DEBIT_AMOUNT_50);
        assertEquals(INITIAL_BALANCE_100 - DEBIT_AMOUNT_50, account.getBalance());

        bank.addDebit(ACCOUNT_ID_1, -10f);
        assertEquals(INITIAL_BALANCE_100 - DEBIT_AMOUNT_50, account.getBalance());
    }

    @Test
    void testTransfer() {
        Account fromAccount = bank.registerAccount(ACCOUNT_ID_1, 'n', INITIAL_BALANCE_100);
        Account toAccount = bank.registerAccount(ACCOUNT_ID_2, 'b', INITIAL_BALANCE_0);

        bank.transfer(ACCOUNT_ID_1, ACCOUNT_ID_2, DEBIT_AMOUNT_50);
        assertEquals(INITIAL_BALANCE_100 - DEBIT_AMOUNT_50, fromAccount.getBalance());
        assertEquals(DEBIT_AMOUNT_50, toAccount.getBalance());
        assertEquals(0, ((BonusAccount) toAccount).getScore());

        bank.transfer(ACCOUNT_ID_1, ACCOUNT_ID_2, -10f);
        assertEquals(INITIAL_BALANCE_100 - DEBIT_AMOUNT_50, fromAccount.getBalance());
        assertEquals(DEBIT_AMOUNT_50, toAccount.getBalance());

        bank.transfer(ACCOUNT_ID_1, ACCOUNT_ID_2, INITIAL_BALANCE_100);
        assertEquals(INITIAL_BALANCE_100 - DEBIT_AMOUNT_50, fromAccount.getBalance());
        assertEquals(DEBIT_AMOUNT_50, toAccount.getBalance());

        bank.transfer(ACCOUNT_ID_1, ACCOUNT_ID_2, DEBIT_AMOUNT_50 - 10f);
        assertEquals(INITIAL_BALANCE_100 - DEBIT_AMOUNT_50 - (DEBIT_AMOUNT_50 - 10f), fromAccount.getBalance());
        assertEquals(DEBIT_AMOUNT_50 + DEBIT_AMOUNT_50 - 10f, toAccount.getBalance());
        assertEquals(0, ((BonusAccount) toAccount).getScore());
    }

    @Test
    void testRenderJuros() {
        SavingsAccount savingsAccount = (SavingsAccount) bank.registerAccount(ACCOUNT_ID_1, 's', INITIAL_BALANCE_100);
        bank.yieldInterest(ACCOUNT_ID_1, 5f);
        assertEquals(105.00f, savingsAccount.getBalance());
    }
}
