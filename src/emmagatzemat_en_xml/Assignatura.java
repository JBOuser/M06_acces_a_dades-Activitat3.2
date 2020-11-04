package emmagatzemat_en_xml;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Assignatura {

	private int numero;
	private String nom;
	private int durada;
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getDurada() {
		return durada;
	}

	public void setDurada(int durada) {
		this.durada = durada;
	}
	
	public void createNewAssignatura()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Assignatura as = new Assignatura();
		
		try{
			this.numero = Integer.parseInt(Menu.enterText("Introdueix el numero (id) de la assignatura: "));
		}
		catch(Exception e)
		{
			System.out.println("'id' not parsed -- ERROR");
		}

		try{
			this.nom = Menu.enterText("Introdueix el nom de la assignatura: ");
		}
		catch(Exception e)
		{
			System.out.println("'nom' not saved -- ERROR");
		}
		
		try{
			this.durada = Integer.parseInt(Menu.enterText("Introdueix la durada de la assignatura: "));
		}
		catch(Exception e)
		{
			System.out.println("'durada' not parsed -- ERROR");
		}		
	}	
}
