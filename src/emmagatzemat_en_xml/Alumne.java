package emmagatzemat_en_xml;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Alumne {

	private String nom;
	private String dni;
	private boolean repetidor;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public boolean isRepetidor() {
		return repetidor;
	}

	public void setRepetidor(boolean repetidor) {
		this.repetidor = repetidor;
	}
	
	public void imprimir()
	{
		System.out.println("nom : "+this.nom);
		System.out.println("dni : "+this.dni);
		System.out.println("repetidor : "+this.repetidor);
	}
	
	public void createNewAlumne()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Assignatura as = new Assignatura();
		
		try{
			this.nom = Menu.enterText("Introdueix el nom de l'alumne: ");
		}
		catch(Exception e)
		{
			System.out.println("'nom' not parsed -- ERROR");
		}

		try{
			this.dni = Menu.enterText("Introdueix el dni de l'alumne: ");
		}
		catch(Exception e)
		{
			System.out.println("'dni' not saved -- ERROR");
		}
		
		try{
			this.repetidor = Boolean.parseBoolean(Menu.enterText("Introdueix si l'alumne és repetidor (true|false): "));
		}
		catch(Exception e)
		{
			System.out.println("'repetidor' not parsed -- ERROR");
		}		
	}	
	
}
