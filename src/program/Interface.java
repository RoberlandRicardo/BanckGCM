package program;

import java.util.Scanner;

public class Interface {
	
	private Scanner sc;
	
	public Interface(Scanner sc) {
		this.sc = sc;
	}
	
	public void init() {
		showMenu();
	}
	
	private void showMenu() {
		System.out.print(
			  "****************************\n"
			+ "Bem vindo ao Banco do Brasil\n"
			+ "****************************\n"
			+ "\n"
			+ "- Digite o número ao lado da opção para escolhe-la:\n"
			+ "\n"
			+ "1 - Cadastrar nova conta\n"
			+ "2 - Consultar saldo\n"
			+ "3 - Adiciona crédito\n"
			+ "4 - Realizar débito\n"
			+ "5 - Realizar transferência\n"
			+ "\n"
		);
		
		int response = sc.nextInt();
		
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
		}
	}
	
	private void showRegisterAccount() {
		System.out.print(
			"Para registrar sua conta precisaremos de um número identificador escolhido por você\n"
			+ "\n"
			+ "Digite um número: "
		);
		
		int response = sc.nextInt();
	}
	
	private void showCheckBalance() {
		System.out.print(
				"Digite o número identificador da conta que você que verificar o saldo\n"
				+ "\n"
				+ "Digite o número: "
			);
			
		int response = sc.nextInt();
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
	}
	
	private void showMakeTransfer() {
		System.out.print(
				"Digite o número identificador da conta de origem da transferência\n"
				+ "\n"
				+ "Digite o número: "
			);
			
		int identOrig = sc.nextInt();
		
		System.out.print(
				"Digite o número identificdo da conta de destino da transferência\n"
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
	}
	
	private void clearScreen() {
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}
}
