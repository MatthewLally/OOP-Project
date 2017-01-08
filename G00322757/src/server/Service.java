package server;
//Imports
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Service extends Thread {

	Socket clientSocket; //creates a socket client socket
	int clientId;
	private String message; //creates a string for holding the messages recieved
	
	ObjectOutputStream out; //creates a object ouput stream
	ObjectInputStream in; //creates a object input stream

	Service(Socket s, int i) {
		clientSocket = s; 
		clientId = i;
		
	}

	void sendMessage(String msg) {
		try { //try catch statement
			out.writeObject(msg); 
			out.flush();
			System.out.println(" " + msg); //prints a message of what the server is doing 
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public void run() {
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Accepted Client : ID - " + clientId
					+ " : Address - "
					+ clientSocket.getInetAddress().getHostName());
			sendMessage("Connection successful");

			whatToDo();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Client : ID - " + clientId + "   exiting");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				clientSocket.close();
			} catch (IOException ioException) {
				// ioException.printStackTrace();
			}
		}
	}

	void whatToDo() throws IOException, ClassNotFoundException { //method to tell the server what to do
		while (true) {
			message = (String) in.readObject();
			if (message.equals("list")) { //if the message sent to the server says list, go the get list method
				getList();
			}
			if (message.equals("download")) { //if the message sent to the server says download, go to the download method
				downloadFile();
			}
		}
	}

	void downloadFile() throws IOException, ClassNotFoundException {
		String signal = "no"; 
		String fileName;
		while (true) {
			fileName = (String) in.readObject();
			String[] str = new File("src//server//files//"
					+ File.separator).list();
			for (int i = 0; i < str.length; i++) {
				if (str[i].equals(fileName)) {
					signal = "yes";
					break;
				}
			}
			if (signal.equals("yes")) {
				sendMessage("ok");
				break;
			} else {
				sendMessage("error");
			}
		}
		fileName = (String) in.readObject();
		sendFile(fileName);
	}

	void sendFile(String filename) throws IOException {
		int length = 0;
		byte[] sendByte = null;
		DataOutputStream dout = null;
		FileInputStream fin = null;
		dout = new DataOutputStream(out);
		File file = new File("src//server//files//"+filename);
		fin = new FileInputStream(file);
		sendByte = new byte[1024];
		dout.writeUTF(file.getName());
		while ((length = fin.read(sendByte, 0, sendByte.length)) > 0) {
			dout.write(sendByte, 0, length);
			dout.flush();
		}
		fin.close();
		dout.close();
	}

	void getList() {
		String[] str = new File("src//server//files//"
				+ File.separator).list();
		sendMessage(str.length + "");
		for (int i = 0; i < str.length; i++) {
			// System.out.println(str[i]);
			sendMessage(str[i]);
		}
	}

}