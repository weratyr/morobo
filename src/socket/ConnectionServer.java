package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import filterData.FilterData;

public class ConnectionServer implements Runnable{
	
	public ConnectionServer() {

	}

//	public void startServer() throws IOException {
//		int port = 1111;
//		serverSocket = new ServerSocket(port);	
//	}

	public String readMessage(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[200];
		int numberOfChar = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
		String	message = new String(buffer, 0, numberOfChar);
		System.out.println("readMessage "+message);
		return message;
	}

	public void writeMessage(Socket socket, String message) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(message);
		printWriter.flush();
	}
	
	public Socket waitForConnection(ServerSocket serverSocket) throws IOException {
		Socket socket = serverSocket.accept();
		return socket;
	}
	
	public String getMessage() {
		return message;
	}

	public void run() {
		try {
			int port = 1111;
			ServerSocket serverSocket = new ServerSocket(port);	
			Socket client = waitForConnection(serverSocket);
			
			while (true) {
				String message = readMessage(client);
				System.out.println(client.getRemoteSocketAddress());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

	// public static void main(String[] args) {
	// ConnectionServer server = new ConnectionServer();
	// try {
	// server.startServer();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}
