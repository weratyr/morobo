package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import filterData.FilterData;

public class ConnectionServer {
	private FilterData filterData; 
	private Socket client;
	public ConnectionServer() {
		
	}
	
	public void startServer() throws IOException {
		int port = 1111;
		ServerSocket serverSocket = new ServerSocket(port);
		client = warteAufAnmeldung(serverSocket);
	}

	public Socket warteAufAnmeldung(ServerSocket serverSocket) throws IOException {
		Socket socket = serverSocket.accept(); 
		return socket;
	}

	public String leseNachricht(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[200];
		int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}

	public void schreibeNachricht(Socket socket, String nachricht) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}
	
	public Socket getClient() {
		return client;
	}


	public void setClient(Socket client) {
		this.client = client;
	}


	
	
	
	
//	public static void main(String[] args) {
//		ConnectionServer server = new ConnectionServer();
//		try {
//			server.startServer();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
