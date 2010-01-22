package socket;

import java.io.PrintWriter;
import java.net.Socket;

import java.io.*;

public class ConnectionClient {
	// Client.java
	/*public void start() throws IOException, InterruptedException {
		String ip = "127.0.0.1"; //localhost
		//String ip = "172.17.5.241";
		int port = 1111;
		//String zuSendendeNachricht = "<map><info><pos>5,15</pos><direction>3,6</direction><data>8,7</data><data>10,5</data><data>23,4</data><data>4,3</data></info></map>";
		int i=0;
		while ( i < 25) {
			Socket socket = new Socket(ip, port); // verbindet sich mit Server
			String zuSendendeNachricht = "<map><name>ich</name><pos>"+(8+i)+","+((i*2)+2)+"</pos><direction>3,6</direction><data>1,"+i+"</data>" +

					"<data>10,5</data><data>23,4</data><data>"+i+",3</data></map>"; // direction sind cm daraus ergibt sich der alpha
			schreibeNachricht(socket, zuSendendeNachricht);
			System.out.println("print i "+i);
			i++;
			Thread.sleep(1000);
		}
	}*/
	public void start() throws IOException, InterruptedException {
		String ip = "127.0.0.1"; //localhost
		//String ip = "172.17.5.241";
		int port = 1111;
		//String zuSendendeNachricht = "<map><name>ich</name><pos>"+(8+i)+","+((i*2)+2)+"</pos><direction>3,6</direction><data>1,"+i+"</data>" +

		//"<data>10,5</data><data>23,4</data><data>"+i+",3</data></map>";
		int i=0;
		while ( i < 6) {
			Socket socket = new Socket(ip, port); // verbindet sich mit Server
			String zuSendendeNachricht = "<map><name>ichj</name><pos>0,0</pos><direction>14,-14</direction><data>50,50</data></map>"; // direction sind cm daraus ergibt sich der alpha
			schreibeNachricht(socket, zuSendendeNachricht);
			System.out.println("print i "+i);
			i++;
			Thread.sleep(1000);
		}
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