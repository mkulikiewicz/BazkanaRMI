package Server;


 
import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;
 
public interface RMIServerInt extends Remote{
	String getDescription(String text) throws RemoteException;

	String pytania(int i) throws RemoteException , Exception;


	void stworz(String string) throws RemoteException, FileNotFoundException;

	void wyslij_odpowiedz_studenta(String numer_studenta, String zmienna_do_odpo, int i)throws RemoteException , InterruptedException, ExecutionException;

	String wypisz_wynik(String numer_studenta)throws RemoteException , Exception;

	void dodaj_studenta(String numer_studenta)throws RemoteException , Exception;

	String wyswietl_wynik(String numer_studenta) throws RemoteException, Exception;;
}
