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
	private String message;
	private Socket socket;

	public ConnectionServer() {

	}

	public void startServer() throws IOException {
		int port = 1111;
		ServerSocket serverSocket = new ServerSocket(port);
		socket = serverSocket.accept();	
	}

	public void readMessage(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[200];
		int numberOfChar = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
		if(numberOfChar != -1) {
			message = new String(buffer, 0, numberOfChar);
		}else
			message = null;
	}

	public void writeMessage(Socket socket, String message) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(message);
		printWriter.flush();
	}
	
	public String getMessage() {
		return message;
	}

	public void run() {
		try {
			startServer();
			while (true) {
				readMessage(socket);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
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
