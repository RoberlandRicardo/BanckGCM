package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Account;
import com.example.demo.model.BonusAccount;
import com.example.demo.model.SavingsAccount;

public class Bank {

    private List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    private static final String MENSAGEM_VALOR_INVALIDO = "O valor não pode ser negativo ou zero.";

    public Account registerAccount(int identf, char typeOfAccount, float initialBalance) {
        for (Account ac : accounts) {
            if (ac.getIdentf() == identf) {
                return null;
            }
        }

        Account newAccount;

        if (typeOfAccount == 'n') {
            newAccount = new Account(identf);
            newAccount.increaseBalance(initialBalance);
        } else if (typeOfAccount == 'b') {
            newAccount = new BonusAccount(identf);
        } else {
            newAccount = new SavingsAccount(identf);
            newAccount.increaseBalance(initialBalance);
        }

        accounts.add(newAccount);
        return newAccount;
    }

    public void transfer(int idOrigin, int idDestiny, float value) {
        if (value <= 0) {
            System.out.println(MENSAGEM_VALOR_INVALIDO);
            return;
        }

        Account account1 = getAccountById(idOrigin);
        Account account2 = getAccountById(idDestiny);

        boolean bothExists = account1 != null && account2 != null;

        if (bothExists && account1.getBalance() >= value) {
            addDebit(idOrigin, value);
            addCredit(idDestiny, value);
            if (account2 instanceof BonusAccount) {
                int pontos = ((BonusAccount) account2).addTransferScore(value);
                System.out.println(pontos + " pontos adicionados à conta " + account2.getIdentf());
            }
        } else if (account1 != null && account1.getBalance() < value) {
            System.out.println("Conta com saldo insuficiente.");
        } else {
            System.out.println("Conta não encontrada");
        }
    }

    public void addDebit(int id, float value) {
        if (value <= 0) {
            System.out.println(MENSAGEM_VALOR_INVALIDO);
            return;
        }
        Account account = getAccountById(id);
        if (account != null && account.getBalance() >= value) {
            account.decreaseBalance(value);
        } else if (account != null && account.getBalance() < value) {
            if (!(account instanceof SavingsAccount) && (account.getBalance() - value >= -1000)) {
                account.decreaseBalance(value);
            } else {
                System.out.println("Conta com saldo insuficiente.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public Account getAccountById(int id) {
        for (Account account : this.accounts) {
            if (account.getIdentf() == id) {
                return account;
            }
        }
        return null;
    }

    public void addCredit(int id, float value) {
        if (value <= 0) {
            System.out.println(MENSAGEM_VALOR_INVALIDO);
            return;
        }
        Account account = getAccountById(id);
        if (account != null) {
            account.increaseBalance(value);
            if (account instanceof BonusAccount) {
                int pontos = ((BonusAccount) account).addCreditScore(value);
                System.out.println(pontos == 1 ? pontos + " Ponto adicionado, "
                        : pontos + " Pontos adicionados, " + "id da conta : " + account.getIdentf());
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public void yieldInterest(int id, float rate) {
        for (Account account : this.accounts) {
            if (account.getIdentf() == id) {
                if (account instanceof SavingsAccount) {
                    ((SavingsAccount) account).yieldInterest(rate);
                }
                return;
            }
        }
    }

    public float getBalance(int id) {
        for (Account account : accounts) {
            if (account.getIdentf() == id) {
                return account.getBalance();
            }
        }
        return -1;
    }
}
