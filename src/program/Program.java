package program;

import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Interface intfc = new Interface(sc);
		intfc.init();
	}

}
