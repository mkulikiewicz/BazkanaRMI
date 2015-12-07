package Client;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
 
import javax.naming.Context;
import javax.naming.InitialContext;

import Server.RMIServerInt;


 
public class RMIClient {
 
	public static void main(String[] args) {
 
		System.setSecurityManager(new SecurityManager());
		String url = "rmi://localhost/";
		
		try {
			//1
			Registry reg = LocateRegistry.getRegistry("localhost");
			RMIServerInt stub = (RMIServerInt) reg.lookup("RemoteRMIServer");
	//		String str = stub.getDescription("test RMI");
			int i=0;
			stub.pytania(i);
			System.out.print(i);
//			
//			System.out.println("Wynik 1: " + str);
//			//2
//			Context context = new InitialContext();
//			RMIServerInt rmiRemoteServer = (RMIServerInt) context.lookup(url + "RemoteRMIServer2");
//			str = rmiRemoteServer.getDescription("Ala ma kota");
//			System.out.println("Wynik 2: " + str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}