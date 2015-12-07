package Server;


 
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


 
/**
 * @author S³awek
 *
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerInt {
	// extends UnicastRemoteObject - aby nie tworzyæ samemu obiektu serwera
	/**
	 * Klasa Serwera
	 */
	private static final long serialVersionUID = 8736117189093610111L;
 
	protected RMIServer() throws RemoteException {
		super();
	}
 
	protected RMIServer(int i) throws RemoteException {
		super(i);
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see rmi.server.RMIServerInt#getDescription(java.lang.String)
	 */
	@Override
	public String getDescription(String text) throws RemoteException {
		System.out.println("RMIServer.getDescription " + text);
		if(text.equals("dupa")){
		return "getDescription: " + text+"BYLEM W SERWERZE WOW I\n JESTEM KOZAKIEM MACKIEM";
		}else{ 
			return "chuja prawda";
		}
	}
	
	public void Steruj(String text) throws RemoteException {
	
		if(text.equals("w")){
			System.out.println("Idê do przodu");
		}
		else if(text.equals("s")){
			System.out.println("Idê do ty³u");
		}
		else if(text.equals("a")){
			System.out.println("Idê w lewo");
		}
		else if(text.equals("d")){
			System.out.println("Idê w prawo");
		}
	}

	@Override
	public String pytania(int i) throws Exception{
		ExecutorService exe1 = Executors.newFixedThreadPool(1);
		String odpowiedzi_do_pytania="";
		exe1.submit(new Baza("DK"));
			
		
		
		
		return "zwrot";
		// TODO Auto-generated method stub
		
	}
}