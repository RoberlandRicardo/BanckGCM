package bank;

import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.BonusAccount;
import model.SavingsAccount;

public class Bank {
	
	private List<Account> accounts;
	
	public Bank() {
		 accounts = new ArrayList();
	}
	
	public Account registerAccount(int identf, char typeOfAccount, float initialBalance) {
		for (Account ac : accounts) {
			if (ac.getIdentf() == identf) {
				return null;
			}
		}
		
		Account newAccount;
		
		if(typeOfAccount == 'n'){
			newAccount = new Account(identf);
		}
		else if(typeOfAccount == 'b'){
			newAccount = new BonusAccount(identf);
		}
		else{
			newAccount = new SavingsAccount(identf);
			newAccount.increasseBalance(initialBalance);
		}

		accounts.add(newAccount);
		return newAccount;
		
	}

	public void transfer(int idOrigin, int idDestiny, float value){
		if (value < 0) {
            System.out.println("O valor não pode ser negativo.");
			return;
        }
		
		Account account1 = getAccountById(idOrigin);
		Account account2 = getAccountById(idDestiny);

		boolean bothExists = account1 != null && account2 != null;

		if(bothExists && account1.getBalance() >= value){
			addDebit(idOrigin, value);
			addCredit(idDestiny, value);
			if(account2 instanceof BonusAccount){
				int pontos = ((BonusAccount) account2).addTransferScore(value);	
			}
		}
		else if(account1.getBalance() < value){
			System.out.println("Conta com saldo insuficiente.");
		}
		else{
			System.out.println("Conta não encontrada");
		}
	}


	public void addDebit(int id, float value) {
		if (value < 0) {
            System.out.println("O valor não pode ser negativo.");
			return;
        }
		Account account = getAccountById(id);
		if (account != null && account.getBalance() >= value) {
			account.decreasseBalance(value);
		}
		else if(account.getBalance() < value){
			if (!(account instanceof SavingsAccount) && (account.getBalance() - value >= - 1000)) {
				account.decreasseBalance(value);
			} else {
				System.out.println("Conta com saldo insuficiente.");
			}
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
		if (value < 0) {
            System.out.println("O valor não pode ser negativo.");
			return;
        }
    	Account account = getAccountById(id);
    	if (account != null) {
        	account.increasseBalance(value);
			if(account instanceof BonusAccount){
				int pontos = ((BonusAccount) account).addCreditScore(value);	
				System.out.println( pontos == 1 ? pontos + " Ponto adicionado, " : pontos + " Pontos adicionados, " + "id da conta : "+ account.getIdentf());
			}
    	} 
		else {
        	System.out.println("Conta não encontrada.");
    	}
	}

	public void yieldInterest(int id, float rate) {
		for(Account account : this.accounts) {
			if(account.getIdentf() == id) {
				if (account instanceof SavingsAccount) {
					((SavingsAccount) account).yieldInterest(rate);
				}
				/* caso precise verificar se a conta é poupança
				else {
					System.out.println("Conta não é uma conta poupança.");
				}
				*/
				return;
			}
		}
	}
}
