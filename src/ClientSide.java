


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientSide extends Remote {

	public void retreiveMessage(String message) throws RemoteException;

}
