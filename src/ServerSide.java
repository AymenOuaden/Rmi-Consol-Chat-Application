


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerSide extends Remote {
	
	public boolean createConnexion(ClientSide instanceClient, String pseudo) throws RemoteException;
	public void sendMessage(ClientSide instanceClient, String message) throws RemoteException;
	public boolean closeConnexion(ClientSide instanceClient) throws RemoteException;

}
