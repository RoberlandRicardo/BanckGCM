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
			+ "3 - Fazer depósito\n"
			+ "4 - Realizar saque\n"
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
	
	private void clearScreen() {
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}
}
