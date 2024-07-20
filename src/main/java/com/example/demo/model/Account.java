package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class Account {
	
	private int identf;
	private float balance;
	
	public Account(int identf) {
		this.identf = identf;
		this.balance = 0;
	}
	
	public void increasseBalance(float balance) {
		this.balance += balance;
	}
	
	public void decreasseBalance(float balance) {
		this.balance -= balance;
	}
	
}
