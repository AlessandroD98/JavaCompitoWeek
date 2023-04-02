package Esercizio;

public class Libro extends ElementoCatalogo {

	public String autore;
	public String genere;
	private Boolean check;
	
	public Libro() {}
	
	public Libro(Boolean c) {
		super();
		this.setAutore();
		this.setGenere();
		this.check = c;
	}
	
	public Libro(long ISBN, String Titolo, int AnnoPubb, int numOfPage, String autore, String genere) {
		this.ISBN = ISBN;
		this.Titolo = Titolo;
		this.AnnoPubb = AnnoPubb;
		this.numOfPage = numOfPage;
		this.autore = autore;
		this.genere = genere;
	}
	
	public Boolean getCheck() {
		return this.check;
	}
	
	public void setCheck(Boolean check) {
		this.check = check;
	}
	
	public String getAutore() {
		return this.autore;
	}
	
	public String getGenere() {
		return this.autore;
	}
	
	@Override
	protected void setTitolo() {
		System.out.println("Inserisci il Titolo del Libro");
		this.Titolo = Archivio.s.nextLine().toUpperCase();
	}
	
	private void setAutore() {
		System.out.println("Inserisci l'autore del Libro");
		this.autore = Archivio.s.nextLine().toUpperCase();
	}
	
	private void setGenere() {
		System.out.println("Inserisci il genere del Libro");
		this.genere = Archivio.s.nextLine();
	}
}
