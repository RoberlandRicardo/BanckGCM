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

	public void transfer(int idOrigin, int idDestiny, float value){
		Account account1 = getAccountById(idOrigin);
		Account account2 = getAccountById(idDestiny);

		boolean bothExists = account1 != null && account2 != null;

		if(bothExists && account1.getBalance() >= value){
			addDebit(idOrigin, value);
			addCredit(idDestiny, value);
		}
		else if(account1.getBalance() < value){
			System.out.println("Conta com saldo insuficiente.");
		}
		else{
			System.out.println("Conta não encontrada");
		}
	}


	public void addDebit(int id, float value) {
		Account account = getAccountById(id);
		if (account != null && account.getBalance() >= value) {
			account.decreasseBalance(value);
		}
		else if(account.getBalance() < value){
			System.out.println("Conta com saldo insuficiente.");
		}
		else {
			System.out.println("Conta não encontrada.");
		}
	}

	public Account getAccountById(int id) {
		for(Account account : this.accounts) {
			if(account.getIdentf() == id) {
				return account;
			}
		}
		return null;
	}

	public void addCredit(int id, float value) {
    	Account account = getAccountById(id);
    	if (account != null) {
        	account.increasseBalance(value);
    	} 
		else {
        	System.out.println("Conta não encontrada.");
    	}
	}
}
