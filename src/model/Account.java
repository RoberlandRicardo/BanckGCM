package model;

public class Account {
	
	private int identf;
	private float balance;
	
	public Account(int identf) {
		this.identf = identf;
		this.balance = 0;
	}
	
	public int getIdentf() {
		return identf;
	}
	
	public void setIdentf(int identf) {
		this.identf = identf;
	}
	
	public float getBalance() {
		return balance;
	}
	
	public void increasseBalance(float balance) {
		this.balance += balance;
	}
	
	public void decreasseBalance(float balance) {
		this.balance -= balance;
	}
	
}
