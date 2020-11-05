package emmagatzemat_en_xml;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	

	//add a new alumne to the parsed document
	public Node addAssignaturaAsNode(Node parentNode, Document doc)
	{
		Node assignatura = doc.createElement("assignatura");
		
		Node numero = doc.createElement("numero");
		numero.setTextContent(String.valueOf(this.numero));
	
		Node nom = doc.createElement("nom");
		nom.setTextContent(this.nom);
		
		Node durada = doc.createElement("durada");
		durada.setTextContent(String.valueOf(this.durada));
		
		Node alumnes = doc.createElement("alumnes");
		
		parentNode.appendChild(assignatura);
		assignatura.appendChild(numero);
		assignatura.appendChild(nom);
		assignatura.appendChild(durada);						
		assignatura.appendChild(alumnes);	
		
		return parentNode;
	}
	
	
	//add a new alumne to the parsed document
	public Document addAssignatura(Document doc)
	{
		try {
			String expression = "/assignatures";
			XPath pathAssignatures = XPathFactory.newInstance().newXPath();
			Node assignaturesNode = (Node) pathAssignatures.compile(expression).evaluate(doc,XPathConstants.NODE);
			
			this.createNewAssignatura();
			
			String subExpression1 = "/assignatures/assignatura[numero="+this.numero+"]";
			XPath pathAlumnes = XPathFactory.newInstance().newXPath();
			NodeList assignaturaNode = (NodeList) pathAlumnes.compile(subExpression1).evaluate(doc,XPathConstants.NODESET);

			if( !(0 < assignaturaNode.getLength()))
			{
				assignaturesNode = this.addAssignaturaAsNode(assignaturesNode, doc);

				System.out.println("assignatura '"+this.numero+"' added -- OK");
			}
			else
			{
				System.out.println("assignatura '"+this.numero+"' already exists -- ERROR");
			}
		}
		catch(Exception e)
		{
			System.out.println("'id_assignatuta' not parsed -- ERROR");
		}
		
		return doc;		
	}	
}
