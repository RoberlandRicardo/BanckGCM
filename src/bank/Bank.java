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

	public boolean transfer(int idOrigin, int idDestiny, float value){
		Account account1 = null;
		Account account2 = null;
		
		boolean bothExists = false;
		
		for (Account ac : accounts) {
			if (ac.getIdentf() == idOrigin) {
				account1 = ac;		
			}
			if (ac.getIdentf() == idDestiny) {
				account2 = ac;		
			}
			if (account1 != null && account2 != null) {
				bothExists = true;
				break;
			}
		}

		if(bothExists){
			//account1.debit(value);
			//account2.credit(value);
			return true;
		}
		else{
			return false;
		}
	}
}
