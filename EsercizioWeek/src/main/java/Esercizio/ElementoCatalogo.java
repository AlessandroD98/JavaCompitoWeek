package Esercizio;

import java.util.Random;

public class ElementoCatalogo {

	public long ISBN;
	public String Titolo;
	public int AnnoPubb;
	public int numOfPage;
	
	public ElementoCatalogo() {
		this.setISBN();
		this.setTitolo();
		this.setAnnoPubb();
		this.setNumOfPage();
		
	}
	
	public int getNumOfPage() {
		return this.numOfPage;
	}
	
	public int getAnnoPubb() {
		return this.AnnoPubb;
	}
	
	public long getISBN() {
		return this.ISBN;
	}
	
	public String getTitolo() {
		return this.Titolo;
	}
	
	private void setNumOfPage() {
		Random num = new Random();
		int max=200 , min= 10;
		this.numOfPage = num.nextInt(max - min + 1) + min; 
	}
	
	private void setISBN () {
		
		long num = System.currentTimeMillis();
		Random rnum = new Random(num);
		long isbn = Math.abs(rnum.nextLong()) % 9000000000000L + 1000000000000L;
		isbn *= 10L;
		this.ISBN = isbn;
	}
	
	protected void setTitolo() {
			System.out.println(">> Inserisci il Titolo");
			this.Titolo = Archivio.s.nextLine().toUpperCase();
	}
	
	private void setAnnoPubb() {
		
		System.out.println(">> Inserisci l'anno di pubblicazione");
		String anno = Archivio.s.nextLine();
		
		
		while(true) {
			if(anno == null)
				break;
			try {
				this.AnnoPubb = Integer.parseInt(anno);
				break;
			}catch(NumberFormatException e) {
				System.out.println("Utilizza un valore valido es. 2020");
			 anno = Archivio.s.nextLine();
			}
		}	
	}
	
	public static String toString(ElementoCatalogo e) {
		if (e instanceof Libro ) {
			Libro l = (Libro) e;
			return "Tipologia : Libro" + " \n Titolo: " + l.getTitolo() + " \n Autore: " + l.getAutore() + " \n Anno di pubblicazione: " + l.getAnnoPubb() + " \n Genere: " + l.getGenere() + " \n Numero di pagine: " + l.getNumOfPage() + " \n Codice ISBN: " + l.getISBN() + "\n";
		} else if (e instanceof Rivista) {
			Rivista r = (Rivista) e;
			return "Tipologia : Rivista" +  " \n Titolo: " + r.getTitolo()+ " \n Anno di pubblicazione: " + r.getAnnoPubb() + " \n Numero di pagine: " + r.getNumOfPage() + " \n Periodicità: " + r.getUscitaPeriodicita() + " \n Codice ISBN: " + r.getISBN() + "\n";
		} else return ">>Inserisci un Libro o una Rivista";
	}
	
	public static int getIntFromS() {
		int num = Archivio.s.nextInt();
		Archivio.s.nextLine();
		return num;
	}
	
}
