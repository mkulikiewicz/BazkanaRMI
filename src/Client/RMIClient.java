package Client;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;

import Server.RMIServerInt;


 
public class RMIClient extends Thread{
 
	public static void main(String[] args) {
 
		System.setSecurityManager(new SecurityManager());
		String url = "rmi://localhost/";
		
		try {
			//1
			Registry reg = LocateRegistry.getRegistry("localhost");
			RMIServerInt lacznik = (RMIServerInt) reg.lookup("RemoteRMIServer");
			System.out.println("Podaj niu");
			String numer_studenta=new Scanner(System.in).nextLine();
			lacznik.stworz("KM");
			
			lacznik.dodaj_studenta(numer_studenta);
			sleep(500);
			for(int i=0; i<4 ; i++)
			{
				System.out.print(lacznik.pytania(i));
				lacznik.wyslij_odpowiedz_studenta(numer_studenta,new Scanner(System.in).nextLine(),i);
			}
			
			System.out.println(lacznik.wyswietl_wynik(numer_studenta));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}