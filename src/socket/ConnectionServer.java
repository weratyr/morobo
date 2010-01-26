package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import filterData.FilterData;

public class ConnectionServer implements Runnable {
	private Socket client;
	private String message;
	private int sleepThread;

	public ConnectionServer(int sleepThread) {
		this.sleepThread = sleepThread;
	}

	public void readMessage(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[4];
		int numberOfChar = bufferedReader.read(buffer, 0, 4); // blockiert
		int toReceiveLength;
		toReceiveLength = (buffer[0] << 24);
		toReceiveLength += (buffer[1] << 16);
		toReceiveLength += (buffer[2] << 8);
		toReceiveLength += (buffer[3]);
		System.out.println(toReceiveLength);
		message = null;
		// bis Nachricht empfangen

		String receive = "";
		buffer = new char[toReceiveLength];
		int readBlock = 200;
		// liest so lange bis alle bytes empfangen sind
		while (readBlock < toReceiveLength) {
			numberOfChar = bufferedReader.read(buffer, 0, toReceiveLength - numberOfChar);
			receive += new String(buffer, 0, numberOfChar);
			readBlock += numberOfChar;
		}
		message = receive;
		
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
			int port = 5678;
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("server is online");
			while (true) {
				waitForConnection(serverSocket);
				readMessage(client);
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

	// }
}
