package model;

public class BonusAccount extends Account {
    int score;

    public BonusAccount(int identf) {
        super(identf);
        this.score = 10;
    }

    private void addScore(float value){
        this.score = (int)value/100;
    }   


    
}
