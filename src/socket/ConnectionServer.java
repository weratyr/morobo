package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

import filterData.FilterData;

public class ConnectionServer implements Runnable {
	private Hashtable<Integer, Socket> ht;
	private Socket client;
	private String message;
	private int sleepThread;

	public ConnectionServer(int sleepThread) {
		this.sleepThread = sleepThread;
		ht = new Hashtable<Integer, Socket>();
	}

	public void readMessage(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[200];
		int numberOfChar = bufferedReader.read(buffer, 0, 200); // blockiert bis
																// Nachricht
																// empfangen
		// if( numberOfChar== der Ÿbergebenen LŠnge)
		message = new String(buffer, 0, numberOfChar);
		socket.close();
		
	}

	public void writeMessage(Socket socket, String message) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(message);
		printWriter.flush();
	}

	public void waitForConnection(ServerSocket serverSocket) throws IOException {
		Socket socket = serverSocket.accept();
		client = socket;
	}

	 public String getMessage() {
		return message;
	}

	public void run() {
		try {
			int port = 1111;
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				waitForConnection(serverSocket);
				readMessage(client);
				System.out.println("run tcp server");
				try {
					Thread.sleep(sleepThread);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				message = null;
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
