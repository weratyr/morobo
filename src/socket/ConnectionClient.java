package socket;

import java.io.PrintWriter;
import java.net.Socket;

import java.io.*;

public class ConnectionClient {

	public void start() throws IOException, InterruptedException {
		String ip = "127.0.0.1"; //localhost
		//String ip = "172.17.5.241";
		int port = 1111;
		//String zuSendendeNachricht = "<map><name>ich</name><pos>"+(8+i)+","+((i*2)+2)+"</pos><direction>3,6</direction><data>1,"+i+"</data>" +

		//"<data>10,5</data><data>23,4</data><data>"+i+",3</data></map>";
		//for(int x=0;x<7;x++)
		//{
			for(int y=0;y<7;y++)
			{
				Socket socket = new Socket(ip, port); // verbindet sich mit Server
		
				//	String zuSendendeNachricht = "<map><name>ichj</name><pos>"+(i+15)+",1</pos><direction>"+(i+1)+",0</direction><data>10,10</data></map>"; // direction sind cm daraus ergibt sich der alpha
				String zuSendendeNachricht = "<map><name>ichj</name><pos>0,0</pos><direction>0,0</direction><data>10,10</data><data>11,10</data><data>12,10</data><data>13,10</data><data>14,10</data><data>15,10</data></map>"; // direction sind cm daraus ergibt sich der alpha
			
				int len = zuSendendeNachricht.length();
				zuSendendeNachricht = len + zuSendendeNachricht;
				schreibeNachricht(socket, zuSendendeNachricht);
			//	System.out.println("print i "+x+","+y + "length "+ len);
				y++;
				//Thread.sleep(1000);
			}
		//x++;
		//}
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