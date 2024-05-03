package program;

public class Interface {
	
	public void init() {
		showMenu();
	}
	
	static private void showMenu() {
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
			
		);
		
	}
}
