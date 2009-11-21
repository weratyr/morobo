package socket;

import java.io.PrintWriter;
import java.net.Socket;

import java.io.*;

public class ConnectionClient {
	// Client.java

	public void start() throws IOException {
		String ip = "127.0.0.1"; // localhost
		int port = 1111;
		Socket socket = new Socket(ip, port); // verbindet sich mit Server
		String zuSendendeNachricht = "<map><info><pos>5,15</pos><direction>3,6</direction><data>45,65</data><data>34,45</data><data>23,45</data><data>45,43</data></info></map>";
		int i=0;
		while ( i < 10) {
			schreibeNachricht(socket, zuSendendeNachricht);
			System.out.println("print i "+i);
			i++;
		}
	}

	public void schreibeNachricht(Socket socket, String nachricht) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}

	public String leseNachricht(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[200];
		int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert
		// bis
		// Nachricht
		// empfangen
		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}

	public static void main(String[] args) {
		ConnectionClient client = new ConnectionClient();
		try {
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}