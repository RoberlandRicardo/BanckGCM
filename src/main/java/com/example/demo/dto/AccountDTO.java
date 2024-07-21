package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccountDTO {
    private int identf;
    private float balance;
    private char accountType;

    // Adicionando o construtor
    public AccountDTO(int identf, char accountType, float balance) {
        this.identf = identf;
        this.accountType = accountType;
        this.balance = balance;
    }
}
