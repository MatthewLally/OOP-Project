package client;
//Matthew Lally G00322757
//Class just contains getters and setters
public class Context {
	//Variables
	public static final String CON_FILE="src/client/conf.xml"; //creates a string called CON_FILE which contains the address for the xml file
	private String username;
	private String server_host;
	private String server_port;
	private String download_dir;
	
	public Context() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getServer_host() {
		return server_host;
	}

	public void setServer_host(String server_host) {
		this.server_host = server_host;
	}

	public String getServer_port() {
		return server_port;
	}

	public void setServer_port(String server_port) {
		this.server_port = server_port;
	}

	public String getDownload_dir() {
		return download_dir;
	}

	public void setDownload_dir(String download_dir) {
		this.download_dir = download_dir;
	}

	@Override
	public String toString() {
		return "Context [username=" + username + ", server_host=" + server_host + ", server_port=" + server_port
				+ ", download_dir=" + download_dir + "]";
	}
	
	
	
}