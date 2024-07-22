package com.example.demo.model;

public class SavingsAccount extends Account {

    private static final Logger logger = Logger.getLogger(SavingsAccount.class.getName());

    public SavingsAccount(int identf) {
        super(identf);
    }

    public void yieldInterest(float rate) {
        float newBalance = getBalance() * (1 + rate / 100);
        newBalance = Math.round(newBalance * 100.0f) / 100.0f;
        setBalance(newBalance);
        logger.info("O juros é de: " + newBalance + " e o id da conta é: " + getIdentf());
    }
}
