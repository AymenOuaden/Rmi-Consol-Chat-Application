
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

@SuppressWarnings("serial")
public class ServerSideImplementation extends UnicastRemoteObject implements ServerSide {

	// Store clients and pseudo
	HashMap<ClientSide, String> mapUsers = new HashMap<ClientSide, String>();

	// Constructeur
	public ServerSideImplementation() throws RemoteException {
		super();
	}

	@Override
	public synchronized boolean createConnexion(ClientSide instanceClient, String pseudo) throws RemoteException {
		for (ClientSide c : mapUsers.keySet()) {
			if (pseudo.equals(mapUsers.get(c))) {
				instanceClient.retreiveMessage("Pseudo Exist.");
				return false;
			}
		}
		mapUsers.put(instanceClient, pseudo);
		System.out.println(countUsers());
		for (ClientSide user : mapUsers.keySet()) {
			user.retreiveMessage(pseudo+ " has added to the chat.");
		}
		return true;
	}
	
	private int countUsers() {
		return mapUsers.size();
	}

	@Override
	public synchronized boolean closeConnexion(ClientSide instanceClient) throws RemoteException {
		if (mapUsers.containsKey(instanceClient)) {
			for (ClientSide instance : mapUsers.keySet()) {
				instance.retreiveMessage(mapUsers.get(instanceClient)+ " left the conversation.");
			}
			mapUsers.remove(instanceClient);
			return true;
		} else
			return false;
	}

	@Override
	public void sendMessage(ClientSide instanceClient, String message) throws RemoteException {
		for (ClientSide c : mapUsers.keySet()) {
			if (!instanceClient.equals(c))
				c.retreiveMessage(mapUsers.get(instanceClient)+ " : " + message);
			else
				c.retreiveMessage("Message sent");
		}
	}
}
