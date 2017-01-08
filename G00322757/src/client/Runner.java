package client;


public class Runner { //class for running the client side
	
	public static void main(String args[]) throws Throwable {
		Worker work = new Worker(); //creates a new instance of the worker class
		work.run(); //runs the worker class
	}

}