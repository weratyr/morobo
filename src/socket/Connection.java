package socket;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Connection  extends UnicastRemoteObject {
	private String bindingName;
	private int port;
	
	
	public Connection(String bindingName, int port)  throws RemoteException {
		this.bindingName = bindingName;
		this.port = port;
	}
	
	/**
	 * make the RMI registry and start up the service
	 */
	public void startupServer() throws RemoteException {
		try {
			System.out.println("-----------------------");
			System.out.println("port: " + port + " bind name: " + bindingName);

			Registry registry = LocateRegistry.getRegistry(port);
			registry.bind(bindingName, this);
			System.out.println("Server is online!!");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}


	
	
}
