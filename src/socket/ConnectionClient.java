package socket;

import java.io.PrintWriter;
import java.net.Socket;

import java.io.*;

public class ConnectionClient {

	public void start() throws IOException, InterruptedException {
		String ip = "127.0.0.1"; //localhost
		//String ip = "172.17.5.241";
		int port = 5678;
		//String zuSendendeNachricht = "<map><name>ich</name><pos>"+(8+i)+","+((i*2)+2)+"</pos><direction>3,6</direction><data>1,"+i+"</data>" +

		//"<data>10,5</data><data>23,4</data><data>"+i+",3</data></map>";
<<<<<<< HEAD
		//for(int x=0;x<20;x++)
		//{
			for(int y=0;y<10;y++)
=======
		for(int x=0;x<5;x++)
		{
			for(int y=0;y<5;y++)
>>>>>>> e255a66ba6cceef4dd6f41b3a46db36a670c02bd
			{
				Socket socket = new Socket(ip, port); // verbindet sich mit Server
		
				//	String zuSendendeNachricht = "<map><name>ichj</name><pos>"+(i+15)+",1</pos><direction>"+(i+1)+",0</direction><data>10,10</data></map>"; // direction sind cm daraus ergibt sich der alpha
<<<<<<< HEAD
			String zuSendendeNachricht = "<map><name>ichj</name><pos>10,10</pos><direction>0,0</direction><data>3,-3</data><data>11,10</data><data>12,10</data><data>13,10</data><data>14,10</data><data>15,10</data></map>";
//+"<direction>0,0</direction><data>10,11</data><data>11,10</data><data>12,10</data><data>13,10</data><data>14,10</data><data>15,11</data>"
//+"<direction>0,0</direction><data>10,12</data><data>11,10</data><data>12,10</data><data>13,10</data><data>14,10</data><data>15,12</data>"
//+"<direction>0,0</direction><data>10,13</data><data>11,10</data><data>12,10</data><data>13,10</data><data>14,10</data><data>15,13</data>"
//+"<direction>0,0</direction><data>10,14</data><data>11,10</data><data>12,10</data><data>13,10</data><data>14,10</data><data>15,14</data>"
//+"<direction>0,0</direction><data>10,15</data><data>11,15</data><data>12,15</data><data>13,15</data><data>14,15</data><data>15,15</data></map>"; // direction sind cm daraus ergibt sich der alpha
=======
				String zuSendendeNachricht = "<map><name>ichj</name><pos>"+ x +","+ y +"</pos><direction>0,0</direction><data>10,10</data><data>11,10</data><data>20,35</data><data>20,45</data><data>20,55</data><data>50,15</data><data>60,15</data></map>"; // direction sind cm daraus ergibt sich der alpha
>>>>>>> e255a66ba6cceef4dd6f41b3a46db36a670c02bd
			
				int len = zuSendendeNachricht.length();
				zuSendendeNachricht =  len + zuSendendeNachricht;
				schreibeNachricht(socket, zuSendendeNachricht);
			//	System.out.println("print i "+x+","+y + "length "+ len);
				y++;
				//Thread.sleep(1000);
			for(int i=0;i<1000000;i++)
				{}
				//}
		//x++;
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