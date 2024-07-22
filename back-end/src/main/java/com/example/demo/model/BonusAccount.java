package com.example.demo.model;

public class BonusAccount extends Account {
    int score;

    public BonusAccount(int identf) {
        super(identf);
        this.score = 10;
    }

    public int addCreditScore(float value){
        this.score = (int)value/100;
        return this.score;
    }

    public int addTransferScore(float value){
        this.score = (int)value/150;
        return this.score;
    }

    public int getScore() {
        return score;
    }
}
