package Server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.naming.Context;
import javax.naming.InitialContext;
 
/**
 * @author S³awek
 *
 */
public class RMIServerMain {
 
	/**
	 * @param args - argumenty z lini wywo³ania
	 */
	public static void main(String[] args) {
		 try {
			 RMIServerInt rms = new RMIServer();
			 //1
		       Registry reg = LocateRegistry.createRegistry(1099);
	             reg.rebind("RemoteRMIServer", rms);
	            //2
                  Context context = new InitialContext();
                  context.bind("rmi:RemoteRMIServer2", rms);
                  System.out.println("OK...");
            } catch (Exception e) {
                  e.printStackTrace();
            }
	}
}

