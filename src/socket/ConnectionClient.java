package socket;

import java.io.PrintWriter;
import java.net.Socket;

import java.io.*;
import java.net.Socket;

public class ConnectionClient {
	public static void main(String[] args) {
		try {
			Socket s = new Socket("127.0.0.1", 1234);
			// etwas über den socket versenden
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			out.write("<AmbrosiaEx><head><typ>1</typ></head><body><a>test</a></body></AmbrosiaEx>");
			// zeilenumbruch senden
			out.newLine();
			out.flush();

			// BufferedReader konstruieren
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			// eine zeile lesen
			String text = in.readLine();
			// und ausgeben
			System.out.print("Received: ");
			System.out.println(text);

			// am ende schliessen wir alle offenen Reader und Writer, der
			// Socket wird dabei automatisch geschlossen
			out.close();
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}