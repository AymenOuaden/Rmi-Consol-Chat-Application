import java.rmi.Naming;
import java.util.Scanner;

public class Server {

	Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		ServerSideImplementation objserv = null;
		try {
			objserv = new ServerSideImplementation();
			Naming.rebind("TodoService", objserv);
			System.out.println("Server On.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
