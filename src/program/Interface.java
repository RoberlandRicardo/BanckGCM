package program;

import java.util.Scanner;

import bank.Bank;
import model.Account;

public class Interface {
	
	private Scanner sc;
	private Bank bank;
	
	public Interface(Scanner sc, Bank bank) {
		this.sc = sc;
		this.bank = bank;
	}
	
	public void init() {
		showMenu();
	}

	// método para substituir o clearScreen
	private void pauseBeforeClearingScreen() {
		System.out.println("Pressione Enter para continuar...");
		sc.nextLine();
		sc.nextLine();
		clearScreen();
	}
	
	private void showMenu() {
		
		int response = 0;
		
		while (true) {
			System.out.print(
				  "****************************\n"
				+ "Bem vindo ao Banco do Brasil\n"
				+ "****************************\n"
				+ "\n"
				+ "- Digite o número ao lado da opção para escolhe-la:\n"
				+ "\n"
				+ "1 - Cadastrar nova conta\n"
				+ "2 - Consultar saldo\n"
				+ "3 - Adicionar crédito\n"
				+ "4 - Realizar débito\n"
				+ "5 - Realizar transferência\n"
				+ "6 - Render Juros\n"
				+ "7 - Sair do programa\n"
				+ "\n"
			);
			
			response = sc.nextInt();
			
			clearScreen();
			
			switch (response) {
				case 1: {
					showRegisterAccount();
					break;
				}
				case 2: {
					showCheckBalance();
					break;
				}
				case 3: {
					showMakeDeposit();
					break;
				}
				case 4: {
					showMakeDebit();
					break;
				}
				case 5: {
					showMakeTransfer();
					break;
				}
				case 6: {
					showMakeYieldInterest();
					break;
				}
				case 7: {
					System.out.println("Obrigado por usar o Banco do Brasil!");
					return;
				}
			}
			
			pauseBeforeClearingScreen();
		}
	}
	
	private void showRegisterAccount() {
		System.out.print(
			"Para registrar sua conta precisaremos de um número identificador escolhido por você\n"
			+ "\n"
			+ "Digite um número: "
		);
		
		int response = sc.nextInt();

		System.out.print(
			"---------------------------------------------------------\n" 
			+ "Para registrar sua conta precisaremos de tipo de conta (n: normal | b: bonus | p: poupança)\n"
			+ "\n"
			+ "Digite um caractere: "
		);
		
		Character type = sc.next().charAt(0);

		Account newAccount;

		if(type.equals('p')){
			System.out.print(
				"Diga o saldo inicial :\n"
			);

			float initialBalance = sc.nextFloat();

			newAccount = bank.registerAccount(response, type, initialBalance);
		}
		else{
			newAccount = bank.registerAccount(response, type, 0);
		}

		
		if (newAccount == null) {
			System.out.println("Erro: Já existe uma conta com esse identificador.");
		} else {
			System.out.println("Parabéns! Sua conta foi criada com o identificador: " + newAccount.getIdentf() + " .");
		}
	}
	
	private void showCheckBalance() {
		System.out.print(
				"Digite o número identificador da conta que você que verificar o saldo\n"
				+ "\n"
				+ "Digite o número: "
			);
			
		int response = sc.nextInt();

		Account account = bank.getAccountById(response);

		if (account == null) {
			System.out.println("Erro: Não existe uma conta com esse identificador.");
		} else {
			System.out.println("O saldo da conta " + account.getIdentf() + " é de: " + account.getBalance());
		}
	}
	
	private void showMakeDeposit() {
		System.out.print(
				"Digite o número identificador da conta que você quer adicionar crédito\n"
				+ "\n"
				+ "Digite o número: "
			);
			
		int ident = sc.nextInt();
		
		System.out.print(
					"---------------------------------------------------------\n"
				+ "Digite a quantidade de crédito que você pretende adicionar\n"
				+ "\n"
				+ "Digite uma quantidade: "
			);
			
		float quant = sc.nextFloat();

		bank.addCredit(ident, quant);
	}
	
	private void showMakeDebit() {
		System.out.print(
				"Digite o número identificador da conta que você quer realizar um débito\n"
				+ "\n"
				+ "Digite o número: "
			);
			
		int ident = sc.nextInt();
		
		System.out.print(
					"---------------------------------------------------------\n"
				+ "Digite a quantidade de dinheiro que você pretende remover\n"
				+ "\n"
				+ "Digite uma quantidade: "
			);
			
		float quant = sc.nextFloat();
		
		bank.addDebit(ident, quant);
	}
	
	private void showMakeTransfer() {
		System.out.print(
				"Digite o número identificador da conta de origem da transferência\n"
				+ "\n"
				+ "Digite o número: "
			);
			
		int identOrig = sc.nextInt();
		
		System.out.print(
				  "---------------------------------------------------------\n"
				+ "Digite o número identificador da conta de destino da transferência\n"
				+ "\n"
				+ "Digite o número: "
			);
			
		int identDest = sc.nextInt();
	
		System.out.print(
			"---------------------------------------------------------\n"
			+ "Digite a quantidade de dinheiro que você pretende transferir\n"
			+ "\n"
			+ "Digite uma quantidade: "
			);
			
			float quant = sc.nextFloat();
			
			bank.transfer(identOrig, identDest, quant);
		}
		
	private void clearScreen() {
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}

	private void showMakeYieldInterest() {
		System.out.print(
			"Digite o número identificador da conta que você quer adicionar juros\n"
			+ "\n"
			+ "Digite o número: "
		);
		
		int ident = sc.nextInt();
		
		System.out.print(
			"---------------------------------------------------------\n"
			+ "Digite a taxa de juros que você pretende adicionar\n"
			+ "\n"
			+ "Digite uma taxa: "
		);
		
		float rate = sc.nextFloat();
		
		bank.yieldInterest(ident, rate);
	}
}
