package server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
//Class that runs the server
public class Runner {
	
	
	
	public static void main(String[] args) throws Exception {
	
		@SuppressWarnings("resource")
		ServerSocket m_ServerSocket = new ServerSocket(7777, 100 , 
				InetAddress.getByName ("127.0.0.1") ); //creates a new socket
		
		int id = 0;
		while (true) //while statement
		{
			Socket clientSocket = m_ServerSocket.accept();
			Service cliThread = new Service(clientSocket, id++);
			cliThread.start();

		} 
	}
}