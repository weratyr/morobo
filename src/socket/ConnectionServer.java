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
		message = null;
		int numberOfChar = bufferedReader.read(buffer, 0, 200); // blockiert
		// bis Nachricht empfangen
		String receive = new String(buffer, 0, numberOfChar);
		Pattern p = Pattern.compile("(\\d+)<");
		Matcher m = p.matcher(receive);

		if (m.find() && m.groupCount() > 0) {
			receive = receive.replace((m.group(1) ), "");
			
			int toReceiveLength = Integer.parseInt(m.group(1));
			int receiveNumberLength = (m.group(1).length() );
			buffer = new char[toReceiveLength + receiveNumberLength];
			int readBlock = 200; 
			// liest so lange bis alle bytes empfangen sind
			while (readBlock < toReceiveLength) 
			{
				numberOfChar = bufferedReader.read( buffer, 0, toReceiveLength - numberOfChar - receiveNumberLength);
				receive += new String(buffer, 0, numberOfChar );
				readBlock+=numberOfChar;
			}
			message = receive; 
			
			socket.close();
		} else {
			System.out.println("not match");
		}
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
	
	// }
}
