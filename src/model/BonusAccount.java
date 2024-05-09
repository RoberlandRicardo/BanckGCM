package model;

public class BonusAccount extends Account {
    int score;

    public BonusAccount(int identf) {
        super(identf);
        this.score = 10;
    }

    public int addCreditScore(float value){
        return this.score = (int)value/100;
    }

    public int addTransferScore(float value){
        return this.score = (int)value/200;
    }
}
