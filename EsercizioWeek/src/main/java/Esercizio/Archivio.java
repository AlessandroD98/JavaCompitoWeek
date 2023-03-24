package Esercizio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import Esercizio.Rivista.Uscita;

public class Archivio {

	public static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) {
				
		List<ElementoCatalogo> Catalogo = new ArrayList<ElementoCatalogo>();
		GestioneCatalogo(Catalogo);
	}

	public static void GestioneCatalogo(List<ElementoCatalogo> arr)  {
		
		while(true) {
		System.out.println("Seleziona una delle seguenti opzioni: \n 1 AGGIUNGI ELEMENTO \n 2 ELIMINA ELEMENTO(ISBN) \n 3 RICERCA UN ELEMENTO \n 4 SALVA CATALOGO \n 5 CARICA CATALOGO ");
		int scelta = s.nextInt();
		s.nextLine(); // consume newline character

        switch (scelta) {
            case 1:
                AggiungiElemento(arr);
                break;
            case 2:
            	if(arr.isEmpty()) {	
            		System.out.println("Non ci sono elemnti da eliminare, inizia aggiungendone uno! \n");
            	} else {       		
            		arr = removeByISBN(arr);
            	}
            	break;
            case 3:
            	if(arr.isEmpty()) {	
            		System.out.println("Non ci sono elemnti da cercare, inizia aggiungendone uno! \n");
            	} else {
                ricerca(arr, s);
            	}
            	break;
            case 4:
            	if(arr.isEmpty()) {
            		System.out.println("Non ci sono elementi da salvare, inizia aggiungendone uno! \n");
            	} else {
            		try {
            			SalvaCatalogo(arr);
            			System.out.println("Catalogo salvato correttamente.");
            		} catch (IOException e) {
            			System.out.println("Errore durante il salvataggio del catalogo.");
            		}
            		
            	}
                break;
            case 5:
            	try {
                    arr = caricaCatalogo();
                    if (arr.isEmpty()) {
                        System.out.println("Il file del catalogo è vuoto o non esiste. Prova a aggiungere un catalogo prima di caricarlo.");
                    } else {
                        System.out.println("Catalogo caricato correttamente.");
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File del catalogo non trovato. \n");
                } catch (IOException e) {
                    System.out.println("Errore durante la lettura del catalogo.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Errore durante la lettura del catalogo: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Scelta non valida.");
        	}
		}
    }
	

	public static void AggiungiElemento(List<ElementoCatalogo> arr) {
		
		String exit = "E";
		while(exit.equals("E")) {
			System.out.println("Seleziona l'elemento da aggiungere L = Libro R = Rivista | E per uscire.");
			String agg = s.nextLine().toUpperCase();
			if(agg.equals("E")) {
				exit = "esci";
			}else if (agg.equals("L")) {
				arr.add(new Libro());
			} else if (agg.equals("R")) {
				arr.add(new Rivista());
			}
		}
		PrintArray(arr);
		
	}
	
	public static List<ElementoCatalogo> removeByISBN (List<ElementoCatalogo> arr) {
		
		List<ElementoCatalogo> newarr = new ArrayList<ElementoCatalogo>();
		
		boolean exit = false;
	    while (!exit) {
	        System.out.println(">> Inserisci il codice ISBN da eliminare | 0 per uscire.");
	        try {
	        	long ISBNtoRemove = s.nextLong();
	            if (ISBNtoRemove == 0) {
	                return arr;
	            }
	            exit = true;
	            if (arr.stream().anyMatch(e -> e.ISBN == ISBNtoRemove)) {
	                newarr = arr.stream().filter(e -> e.ISBN != ISBNtoRemove).collect(Collectors.toList());
	                PrintArray(newarr);
	            } else {
	                System.out.println("Il codice inserito non esiste");
	                return arr;
	            }
	        } catch (InputMismatchException e) {
	            System.out.println(">> Inserisci un numero");
	            s.nextLine();
	        }
	    }
		return newarr;
	}

	public static void ricerca(List<ElementoCatalogo> arr, Scanner input ) {
		System.out.println("Inserisci la parola chivare con cui vuoi cercare il Libro o la rivista \n autore - anno - ISBN ");
		Object query = null;
		
		if(input.hasNextLong()) {
			query = input.nextLong();
		} else if (input.hasNextLine()) {
			query = input.nextLine().toUpperCase();
		} else if (input.hasNextInt()) {
			query = input.nextInt();
		}  else {
			System.out.println("Spice");
		}
		
		Predicate<ElementoCatalogo> filtro = null;

	    if (query instanceof String) {
	        String Nome = (String) query;
	        filtro = e -> {
	            if (e instanceof Libro) {
	                Libro libro = (Libro) e;
	                return libro.getAutore().equals(Nome);
	            } else {
	                return false;
	            }
	        };
	        arr.stream().filter(filtro).forEach(res -> System.out.println(ElementoCatalogo.toString(res)));
	    } else if (query instanceof Long) {
	        Long CodiceLibro = (Long) query;
	        arr.stream().filter(e -> e.ISBN == CodiceLibro).forEach(res -> System.out.println(ElementoCatalogo.toString(res)));
	    } else if (query instanceof Integer) {
	        Integer Anno = (Integer) query;
	        arr.stream().filter(e -> e.AnnoPubb == Anno).forEach(res -> System.out.println(ElementoCatalogo.toString(res)));
	    } else {
	        System.out.println("Tipo non supportato: " + query.getClass().getSimpleName());
	        return;
	    }
	}
	
	public static  void SalvaCatalogo(List<ElementoCatalogo> arr) throws IOException {
		if (arr == null || arr.isEmpty()) {
            throw new IllegalStateException("La lista degli elementi è vuota");
        }
		List<String> righeCatalogo = new ArrayList<String>();
	    for (ElementoCatalogo elemento : arr) {
	        if (elemento instanceof Libro) {
	            Libro libro = (Libro) elemento;
	            righeCatalogo.add("Libro;" + libro.getISBN() + ";" + libro.getTitolo() + ";" + libro.getAnnoPubb() + ";" + libro.getNumOfPage() + ";" + libro.getAutore() + ";" + libro.getGenere());
	        } else if (elemento instanceof Rivista) {
	            Rivista rivista = (Rivista) elemento;
	            righeCatalogo.add("Rivista;" + rivista.getISBN() + ";" + rivista.getTitolo() + ";" + rivista.getAnnoPubb() + ";" + rivista.getNumOfPage() + ";" + rivista.getUscitaPeriodicita());
	        }
	    }

	    File file = new File("catalogo.txt");
	    FileUtils.writeLines(file, righeCatalogo);
		
	}
	
	public static  List<ElementoCatalogo> caricaCatalogo() throws FileNotFoundException {
		 List<ElementoCatalogo> catalogo = new ArrayList<ElementoCatalogo>();
		 File file = new File("catalogo.txt");
		    if (!file.exists()) {
		        throw new FileNotFoundException("File del catalogo non trovato");
		    }

		   // List<String> righeCatalogo = FileUtils.readLines(file, StandardCharsets.UTF_8);
		    Path path = Paths.get(file.getPath());
		    List<String> righeCatalogo;
		    try {
		        righeCatalogo = Files.readAllLines(path, StandardCharsets.UTF_8);
		    } catch (IOException e) {
		        throw new RuntimeException("Errore durante la lettura del file del catalogo");
		    }
		    for (String riga : righeCatalogo) {
		        String[] campi = riga.split(";");
		        if (campi.length < 6) {
		            throw new IllegalArgumentException("Errore Riga");
		        }

		        String tipoElemento = campi[0];
		        long ISBN = Long.parseLong(campi[1]);
		        String titolo = campi[2];
		        int annoPubb = Integer.parseInt(campi[3]);
		        int numOfPage = Integer.parseInt(campi[4]);

		        if (tipoElemento.equals("Libro")) {
		            if (campi.length < 7) {
		                throw new IllegalArgumentException("Errore Riga Libro");
		            }
		            String autore = campi[5];
		            String genere = campi[6];
		            catalogo.add(new Libro(ISBN, titolo, annoPubb, numOfPage, autore, genere));
		        } else if (tipoElemento.equals("Rivista")) {
		            if (campi.length < 6) {
		                throw new IllegalArgumentException("Errore Riga Rivista");
		            }
		            String uscitaPeriodicita = campi[5];
		            catalogo.add(new Rivista(ISBN, titolo, annoPubb, numOfPage, Uscita.valueOf(uscitaPeriodicita)));
		        }
		    }

		    return catalogo;
	}
	
	public static void PrintArray(List<ElementoCatalogo> arr) {
		System.out.println("\n Il catalogo comprende: " + arr.size() + (arr.size() > 1 ? " Elementi \n" : " Elemento \n"));
		ToArray(arr);
	}
	
	public static void ToArray(List<ElementoCatalogo> arr) {
		for (ElementoCatalogo i : arr ) {
			System.out.println(ElementoCatalogo.toString(i));
		}
	}
}
