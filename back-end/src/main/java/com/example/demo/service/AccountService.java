package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.Bank;
import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.TransferDTO;
import com.example.demo.model.Account;

@Service
public class AccountService {
    
    Bank bank = new Bank();
    
    public Account adicionar(AccountDTO account){
        Account response = bank.registerAccount(account.getIdentf(), account.getAccountType(), account.getBalance());
        return response;
    }

    public Account consultarConta(Integer id) {
         Account response = bank.getAccountById(id);
         return response;
    }

    public float consultarSaldo(Integer id) {
        Float response = bank.getBalance(id);
        return response;
    }

    public void creditar(Integer id, Float value) {
        bank.addCredit(id, value);
    }

    public void debitar(Integer id, Float value) {
        bank.addDebit(id, value);
    }

    public void transferir(TransferDTO data) { // Corrigido o nome do método
        bank.transfer(data.getFrom(), data.getTo(), data.getAmount());
    }

    public void renderJuros(int id, float rate) {
        bank.yieldInterest(id, rate);
    }
}
