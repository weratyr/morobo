package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.io.*;

public class ConnectionClient {

	public void start() throws IOException, InterruptedException {
		String ip = "127.0.0.1"; //localhost
		int port = 8000;
				Socket socket = new Socket(ip, port); // verbindet sich mit Server
				readMessage(socket);
				//schreibeNachricht(socket, );
			//	System.out.println("print i "+x+","+y + "length "+ len);
				//y++;
				//Thread.sleep(1000);
			//for(int i=0;i<1000000;i++)
			//	{}
				
		//x++;
			}
	//}
	
	
	public void readMessage(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[4];
		int numberOfChar = bufferedReader.read(buffer, 0, 4); // blockiert
		
		System.out.println("message "+ new String(buffer, 0, numberOfChar));
		socket.close();
	}

	public void schreibeNachricht(Socket socket, String nachricht) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}

	public static void main(String[] args) throws InterruptedException {
		ConnectionClient client = new ConnectionClient();
		try {
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

//direction in float, data, Map gr�sser buttons ausrichten