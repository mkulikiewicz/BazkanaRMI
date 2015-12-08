package Server;


 
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;






 
/**
 * @author S³awek
 *
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerInt  {
	// extends UnicastRemoteObject - aby nie tworzyæ samemu obiektu serwera
	/**
	 * Klasa Serwera
	 */
	boolean istnieje=false;
	String oodpowiedzi="";
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
	public String pytania(int i) throws Exception{
		ExecutorService exe1 = Executors.newFixedThreadPool(1);
		exe1.submit(new Baza("KM"));
		Future<String> pytania=exe1.submit(new Baza("select * from baza_pytan where id='"+i+"'" , "zwroc"));
		return pytania.get();

	}

	@Override
	public void wyslij_odpowiedz_studenta(String numer_studenta, String zmienna_do_odpo , int i) throws InterruptedException, ExecutionException {
			
		String odpowiedzi_studenta="";
					ExecutorService exe1 = Executors.newFixedThreadPool(1);
					Future <String> future =exe1.submit(new Baza( "SELECT * FROM `odpowiedzi_studenta` WHERE numer_studenta='"+numer_studenta+"';" , "zwroc" ));
				
				//	future.get().charAt(future.get().length());
					odpowiedzi_studenta=future.get()+zmienna_do_odpo;
						
					exe1.submit(new Baza( "UPDATE `odpowiedzi_studenta` SET `odpowiedzi`=\""+odpowiedzi_studenta+"\" WHERE `numer_studenta`='"+numer_studenta+"';" , "dodaj" ));;
					
				System.out.println(exe1.submit(new Baza( "SELECT * FROM `odpowiedzi_studenta` WHERE numer_studenta='"+numer_studenta+"';" , "zwroc" )).get());
				if(i==3){
					popraw(numer_studenta);
				}
			
	}
		

		private void popraw(String numer_studenta) throws InterruptedException, ExecutionException {
			ExecutorService exe1 = Executors.newFixedThreadPool(1);
			
			Future <String> future =exe1.submit(new Baza( "SELECT * FROM `odpowiedzi_studenta` WHERE numer_studenta='"+numer_studenta+"';" , "zwroc" ));
			String odpowiedzi="";
			for(int i=-8 ; i<-1 ; i++)
			{
				odpowiedzi=odpowiedzi+future.get().charAt(future.get().length()+i);
			}
			odpowiedzi.replaceAll("\n", "");
			exe1.submit(new Baza( "UPDATE `odpowiedzi_studenta` SET `odpowiedzi`=\""+odpowiedzi+"\" WHERE `numer_studenta`='"+numer_studenta+"';" , "dodaj" ));
		
	}

		@Override
		public void stworz(String string) throws RemoteException, FileNotFoundException {
			
			
			try {
				BufferedReader pytania = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream("baza_pytan.txt"))));
				BufferedReader odpowiedzi = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream("baza_odpowiedzi.txt"))));//odczyt odpowiedzi
				//----utworzenie nowej bazy i dodaj pytania
				if(istnieje != true){
				utworz_baze(pytania,odpowiedzi);
				//wysylanie pytañ do bazy pytañ
				
				istnieje=true;
				}else{
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//odczyt pytan
			
			
		}
		public void utworz_baze(BufferedReader pytania,BufferedReader odpowiedzi) throws InterruptedException, IOException ,ExecutionException
		{	
			ExecutorService exe1 = Executors.newFixedThreadPool(1);
			
			String odpowiedzi_do_pytania="";
				exe1.submit(new Baza("KM"));
				exe1.submit(new Baza( "CREATE TABLE if not exists `km`.`baza_pytan` ( `id` INT NOT NULL , `pytanie` VARCHAR(255) NOT NULL , `odpowiedzi` VARCHAR(255) NOT NULL ) ENGINE = InnoDB;" , "dodaj" ));
				exe1.submit(new Baza( "CREATE TABLE if not exists `km`.`odpowiedzi_studenta` ( `numer_studenta` INT NOT NULL , `ocena` INT NOT NULL , `odpowiedzi` VARCHAR(255) NOT NULL ) ENGINE = InnoDB;" , "dodaj" ));
				exe1.submit(new Baza( "CREATE TABLE if not exists `km`.`baza_odpowiedzi` ( `id` INT NOT NULL , `odpowiedzi` TEXT NOT NULL ) ENGINE = InnoDB;" , "dodaj" ));
				exe1.submit(new Baza( "DELETE FROM `baza_pytan` WHERE 1" , "dodaj" ));
				dodaj_poprawne_odpowiedzi(odpowiedzi);
				for(int i=0;i<4;i++)
				{	
					exe1.submit( new Baza( "INSERT INTO `baza_pytan` (`id`, `pytanie`, `odpowiedzi`) VALUES ('"+i+"', '"+pytania.readLine()+"', '')" , "dodaj" ));  
					odpowiedzi_do_pytania="";
					for(int j=0;j<4; j++)
					{
						odpowiedzi_do_pytania= odpowiedzi_do_pytania+pytania.readLine()+"\n";
					}
					exe1.submit( new Baza( "UPDATE  baza_pytan set odpowiedzi='"+odpowiedzi_do_pytania+"' where id="+i+"" , "dodaj" ));
				}
		}
		
		private void dodaj_poprawne_odpowiedzi(BufferedReader odpowiedzi) throws IOException {
			ExecutorService exe1 = Executors.newFixedThreadPool(1);
			exe1.submit(new Baza( "DELETE FROM `baza_odpowiedzi` WHERE 1" , "dodaj" ));
			for(int i=0;i<4;i++)
			{	
				exe1.submit( new Baza( "INSERT INTO `baza_odpowiedzi` (`id`, `odpowiedzi`) VALUES ('"+i+"',  '"+odpowiedzi.readLine()+"')" , "dodaj" ));  
			}
}

		@Override
		public String wypisz_wynik(String numer_studenta) throws RemoteException, Exception {
			return numer_studenta;
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dodaj_studenta(String numer_studenta) throws RemoteException, Exception {
			ExecutorService exe1 = Executors.newFixedThreadPool(1);
			exe1.submit(new Baza( "INSERT INTO `odpowiedzi_studenta`(`numer_studenta` , `ocena` ,`odpowiedzi`) VALUES ("+numer_studenta+" , 0, \"\" )" , "dodaj" ));
		}

		@Override
		public String getDescription(String text) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String wyswietl_wynik(String numer_studenta) throws RemoteException, Exception {
			ExecutorService exe1 = Executors.newFixedThreadPool(1);
			Future <String> future =exe1.submit(new Baza( "SELECT * FROM `odpowiedzi_studenta` WHERE numer_studenta='"+numer_studenta+"';" , "zwroc" ));
			String odpowiedzi= "";
		//	System.out.println("future zwraca :"+future.get().toString()+"*******<----takie cos");
			
			for(int i=-8 ; i<-1 ; i++)
			{
				odpowiedzi=odpowiedzi+future.get().charAt(future.get().length()+i);
			}
			//System.out.println(odpowiedzi+"\n***********************");
			double wynik=0;
			
			for(int i=0; i<4;i++)
			{
				Future <String> future2 =exe1.submit(new Baza( "SELECT * FROM `baza_odpowiedzi` WHERE id='"+i+"';" , "zwroc" ));
			//	System.out.println("Dla odp:"+odpowiedzi.charAt(i*2)+" i:"+ future2.get()+"*******<----takie cos");
				
				if(odpowiedzi.charAt(i*2) == future2.get().charAt(0))
				{
					wynik++;
				}
			}
			if ((int)wynik!=0)
			wynik=(wynik/4)*100;
			exe1.submit( new Baza( "UPDATE  `odpowiedzi_studenta` set ocena='"+(int)wynik+"' where numer_studenta="+numer_studenta+"" , "dodaj" ));
			return "Twoj wynik to : "+(int)wynik+"\nGratulacje!!";
		}
	
}
	
	