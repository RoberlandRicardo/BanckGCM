package bank;

import java.util.ArrayList;
import java.util.List;

import model.Account;

public class Bank {
	
	private List<Account> accounts;
	
	public Bank() {
		 accounts = new ArrayList();
	}
	
	public Account registerAccount(int identf) {
		for (Account ac : accounts) {
			if (ac.getIdentf() == identf) {
				return null;
			}
		}
		Account newAccount = new Account(identf);
		accounts.add(newAccount);
		return newAccount;
	}
}
