package socket;

import java.io.PrintWriter;
import java.net.Socket;

import java.io.*;

public class ConnectionClient {
	// Client.java

	public void start() throws IOException {
		String ip = "127.0.0.1"; // localhost
		int port = 1111;
		//String zuSendendeNachricht = "<map><info><pos>5,15</pos><direction>3,6</direction><data>8,7</data><data>10,5</data><data>23,4</data><data>4,3</data></info></map>";
		int i=0;
		while ( i < 15) {
			Socket socket = new Socket(ip, port); // verbindet sich mit Server
			String zuSendendeNachricht = "<map><info><name>laura</name><pos>"+(8+i)+","+((i*2)+2)+"</pos><direction>3,6</direction><data>8,7</data><data>10,5</data><data>23,4</data><data>"+i+",3</data></info></map>";
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

	public static void main(String[] args) {
		ConnectionClient client = new ConnectionClient();
		try {
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}