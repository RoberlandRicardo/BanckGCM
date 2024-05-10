package model;

public class SavingsAccount extends Account {

    public SavingsAccount(int identf) {
        super(identf);
        //TODO Auto-generated constructor stub
    }

    public void yieldInterest(float rate) {
        float interest = getBalance() * (1 + rate/100);
        setBalance(interest);
        System.out.println("O juros é de: " + getBalance() + " e o id da conta é: " + getIdentf());
    }
}
