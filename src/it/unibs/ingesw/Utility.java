package it.unibs.ingesw;

import java.util.*;
import java.util.Scanner;

public final class Utility {
	
	
	private static Scanner scanner = creaScanner();
	private final static String ERRORE_INSERIMENTO = "Errore inserimento, per favore inserisci un valore appropriato";
	
	private static Scanner creaScanner() {
		Scanner creato = new Scanner(System.in);
		return creato;
	}
	
	public static int readInt() {
		int i=42;
		boolean finito = false;
		
		do {
			try {
				i = scanner.nextInt();
				finito = true;
			}catch (InputMismatchException e) {
				System.out.println(ERRORE_INSERIMENTO);
				var throwable = scanner.nextLine();
			}
		}while (!finito);
		return i;
	}
	
	public static int readLimitedInt(int min, int max) { //legge interi con estremi compresi
		int i=min-1;
		boolean finito = false;
		do {
			i = readInt();
			if(i>=min && i<= max)
				finito = true;
			else System.out.println("valore non compreso tra gli estremi");
			
		}while(!finito);
		return i;
		
	}
	
	public static String readString() {
		String name = scanner.next();
		return name;
	}
	
	public static void close() {
		scanner.close();
	}
	
	public static int getMax(ArrayList<Integer> integer) {
	 int max = 0;
	 for(Integer n : integer) {
		 if(n>max) max = n;
	 }
	 return max;
	}
	
	
	
	public static boolean nameCheck(NameGiver n, String s) {
		if (n.getName().toUpperCase().equals(s.toUpperCase()))
			return true;
		return false;
	}
}
