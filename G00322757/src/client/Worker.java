package client;




import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;



public class Worker {
	
	String ss ; 
 
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	Parseator parse; //calls parseator class
	Context ctx; //calls context class
	String message = "";
	

	UI input = new UI(); //calls ui class
	String s = ""; 
	Boolean running = true;
	
	//Main method 
	public static void main(String args[]) throws Throwable {
		
	}
	
	
	

	public void run() throws Throwable {
		try {
			while (running) {
				//User Options
				System.out.println("1. Connect to Server");
				System.out.println("2. Print File Listing");
				System.out.println("3. Download File");
				System.out.println("4. Quit");
				s = input.getString(); //gets string 
				
				
				//loop
				while (true) {
					if (s.equals("1")) {
						if (ss == null) {
							System.out.println("Connecting "); 
							connection();
							
							System.out.println();
						} else {
							System.out.println(" You have already connected"); //If already connected display this.
						}
						break;	
					} else if (s.equals("2")) {
						if (ss == null) {
							System.out.println("Please Connect"); //if not connected display this
						}
						else {
							getList(); //calls get list method
						}
						
			break;
					} else if (s.equals("3")) {
						if (ss == null) {
							System.out.println("Please connect ");
							System.out.println();
						} else {
							downloadFile(); //calls download file method
						}
						break;
					} else if (s.equals("4")) {
						System.out.println("Exiting");
						running = false;
						break;
					} else {
						System.out.println("Select valid option");
						
						break;
					}
				}
			}
			
		} catch (ClassNotFoundException | IOException e) {
		
		} finally {
			
			try {
				in.close();
				out.close();
				requestSocket.close(); //closes sockets and data streams
			} catch (IOException ioException) {
				// ioException.printStackTrace();
			}
		}
	} 


	void connection() throws Throwable {
		try {


			parse = new Parseator(new Context());
			
			parse.init();
			ctx = parse.getCtx();
			String ipaddress = ctx.getServer_host(); //gets ip address 
			
			
			// socket 
			requestSocket = new Socket(ipaddress, 7777);
			System.out.println("Connected to " + ipaddress + " in port 2004");
			System.out.println();
			
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			message = (String) in.readObject();
			System.out.println(" " + message + " ");
			ss = "ok"; 

		} catch (UnknownHostException unknownHost) {
			System.err.println("Error cannot find host");
		} catch (IOException ioException) {
			// ioException.printStackTrace();
		}
	}
	


	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client> " + msg);
		} catch (IOException ioException) {
			// ioException.printStackTrace();
		}
	}
	


	void getList() throws ClassNotFoundException, IOException {
		sendMessage("list");
		message = (String) in.readObject();
		int sum = Integer.parseInt(message); // get the amount of files
		System.out.println("file list :");
		System.out.println();
		for (int i = 0; i < sum; i++) {
			message = (String) in.readObject();
			System.out.println(message);
		}
		System.out.println();
	}

	
	void downloadFile() throws ClassNotFoundException, IOException {
		sendMessage("download");
		while (true) {
			System.out.println("Input the name of the file to be downloaded");
			s = input.getString();
			sendMessage(s);
			message = (String) in.readObject();
			if (message.equals("ok")) {
				break;
			}
			if (message.equals("error")) {
				System.out.println("File does not exist");
			}
		}
		sendMessage(s);
		receiveFile();
	}


	
	void receiveFile() throws IOException {
		byte[] inputByte = null;
		int length = 0;
		//sendMessage(requestSocket.getInetAddress().getHostAddress());
		try {

			FileOutputStream fout = new FileOutputStream(new File(ctx.getDownload_dir() + in.readUTF()));
			inputByte = new byte[1024];
			System.out.println("Started Download  ");
		
			while (true) {
				if (in != null) {
					length = in.read(inputByte, 0, inputByte.length);
				}
				if (length == -1) {
					break;
				}
				System.out.println(length);
				fout.write(inputByte, 0, length);
				fout.flush();
			}
			System.out.println("Download Complete");
			fout.close();
			running = false;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
	