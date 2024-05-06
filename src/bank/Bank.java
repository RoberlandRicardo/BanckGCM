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
			debit(account1, value);
			//account2.credit(value);
			return true;
		}
		else{
			return false;
		}
	}

	public void debit(Account account, float value){
		account.decreasseBalance(value);
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
    } else {
        System.out.println("Conta não encontrada.");
    }
}
}
