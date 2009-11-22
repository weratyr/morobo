import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.Dictionary ;

import gui.GuiFrame;

	 public class LunchApp {
		   
		    void test() throws IOException {
		 	int port = 1111;
		 	java.net.ServerSocket serverSocket = new java.net.ServerSocket(port);
		 	java.net.Socket client = warteAufAnmeldung(serverSocket);
		 	while(true) {
		 		String nachricht = leseNachricht(client);
		 		System.out.println(nachricht);
		 	}
		 	//schreibeNachricht(client, nachricht);
		    }
		  
		    java.net.Socket warteAufAnmeldung(java.net.ServerSocket serverSocket) throws IOException {
		 	java.net.Socket socket = serverSocket.accept(); // blockiert, bis sich ein Client angemeldet hat
		 	return socket;
		    }
		  
		    
		    String leseNachricht(java.net.Socket socket) throws IOException {
		  	BufferedReader bufferedReader = 
		 	    new BufferedReader(
		 	 	new InputStreamReader(
		 		    socket.getInputStream()));
		 	char[] buffer = new char[200];
		 	int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
		 	String nachricht = new String(buffer, 0, anzahlZeichen);
		 	return nachricht;
		    }
		  
		    
		    void schreibeNachricht(java.net.Socket socket, String nachricht) throws IOException {
		 	PrintWriter printWriter =
		 	    new PrintWriter(
		 	        new OutputStreamWriter(
		 	 	    socket.getOutputStream()));
		 	printWriter.print(nachricht);
		 	printWriter.flush();
		    }

	
	
	public static void main(String[] arg) throws IOException {
		new LunchApp().test();
	}
}
