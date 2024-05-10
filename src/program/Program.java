package program;

import java.util.Scanner;

import bank.Bank;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Bank bank = new Bank();
		Interface intfc = new Interface(sc, bank);
		intfc.init();
	}

}
