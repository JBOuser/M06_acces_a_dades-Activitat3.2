package emmagatzemat_en_xml;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	
	
	//return the parent's node with the Alumne added
	public Node addAlumneAsNode(Node parentNode, Document doc)
	{
		
		Node alumne = doc.createElement("alumne");		
		
		Node nom = doc.createElement("nom");
		nom.setTextContent(this.nom);
	
		Node dni = doc.createElement("dni");
		dni.setTextContent(this.dni);
		
		Node repetidor = doc.createElement("repetidor");
		repetidor.setTextContent(String.valueOf(this.repetidor));
		
		alumne.appendChild(nom);
		alumne.appendChild(dni);
		alumne.appendChild(repetidor);	
		parentNode.appendChild(alumne);
		
		return parentNode;
	}
	
	
	//add a new alumne to the parsed document
	public Document addAlumne(Document doc)
	{
		
		try {
			String id_assignatura = Menu.enterText("Introdueix el número de l'assignatura per verificar que existeix: ");
			
			String expression = "/assignatures/assignatura[numero="+String.valueOf(id_assignatura)+"]";
			XPath pathAssignatures = XPathFactory.newInstance().newXPath();
			NodeList assignaturaNodes = (NodeList) pathAssignatures.compile(expression).evaluate(doc,XPathConstants.NODESET);
			
			if(0 < assignaturaNodes.getLength())
			{
				
				//get alumnes
				Node alumnesNode = Menu.getNextSublevelChildNode(assignaturaNodes.item(0), true, "alumnes");
				if(alumnesNode.getNodeType() == Node.ELEMENT_NODE)
				{
					//request data and create a new Alumne
					this.createNewAlumne();

					//add alumne to the entered parent node
					alumnesNode = this.addAlumneAsNode(alumnesNode, doc);

					System.out.println("'alumne' "+this.nom+" added -- OK");
				}
				else
				{
					System.out.println("'alumne' not added -- ERROR");
				}
					
			}
			else
			{
				System.out.println("assignatura with numero '"+String.valueOf(id_assignatura)+"' not found -- ERROR");
			}
		}
		catch(Exception e)
		{
			System.out.println("'id_assignatuta' not parsed -- ERROR ("+e+")");
		}
		
		return doc;
	}	
	
}
