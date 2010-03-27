import socket.ConnectionServer;

public class RunMapApp {
	public static void main(String[] arg) throws InterruptedException {
		ConnectionServer cs = null;
		int port = 5678;
		cs = new ConnectionServer(port);
		Thread tcpServerThread = new Thread(cs);
		tcpServerThread.start();	
	}
}
