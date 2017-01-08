package client;

import java.util.Scanner;

public class UI { //class to get the user input
	
	
	public String getString(){
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		return scan.nextLine(); //return whats on the next line
	}
	
	public int getInt(){
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		return scan.nextInt(); //return the next int
	}
}
