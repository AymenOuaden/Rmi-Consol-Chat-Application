import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client implements ClientSide, Serializable {

	private static final long serialVersionUID = 1L;
	static Scanner scan = new Scanner(System.in);

	// Constructeur
	public Client() throws RemoteException {
		UnicastRemoteObject.exportObject(this, 0);
	}

	public static void main(String[] args) {
		try {
			Client c = new Client();
			dialogue(c, "localhost");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void dialogue(Client instanceClient, String ip) {
		try {
			String url = "rmi://" + ip + "/TodoService";
			ServerSide objdist = (ServerSide) Naming.lookup(url);
			// First operation, create connexion
			System.out.print("choose a pseudo : ");
			String pseudo = scan.nextLine();
			boolean connexion = objdist.createConnexion(instanceClient, pseudo);
			// If connexion == false
			if (!connexion) {
				System.err.println("Connexion Error.");
				System.exit(0);
			}
			// If connexion == true
			System.out.println("Welcome " + pseudo + "\nWrite \"End Chat\" To close the connexion.");
			boolean keepChating = true;
			while (keepChating) {
				String message = scan.nextLine();
				if (message.equals("End Chat"))
					keepChating = false;
				else {
					Thread t = new Thread() {
						public void run() {
							try {
								objdist.sendMessage(instanceClient, message);
							} catch (RemoteException e) {
								System.err.println("The server is not responding ... ");
							}
						}
					};
					t.start();
				}
			}
			boolean close = objdist.closeConnexion(instanceClient);
			if (!close)
				System.err.println("The connection is not closed properly.");
			else
				System.exit(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Server Error.");
		}
	}

	@Override
	public void retreiveMessage(String message) throws RemoteException {
		System.out.println(message);
	}

}
