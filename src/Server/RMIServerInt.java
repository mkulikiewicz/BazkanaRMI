package Server;


 
import java.rmi.Remote;
import java.rmi.RemoteException;
 
public interface RMIServerInt extends Remote{
	String getDescription(String text) throws RemoteException;

	String pytania(int i) throws RemoteException , Exception;

}
