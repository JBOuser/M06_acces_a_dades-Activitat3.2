package emmagatzemat_en_xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Menu {
	
	private static List<Node> assignatures = new ArrayList<Node>();
	private static Map<String,Node> alumnes = new HashMap<String,Node>();

	private final static String[] menu = {"0.Sortir",
			"1.Llegir d'un fitxer XML pel mètode seqüencial (introduir nom del fitxer)",
			"2.Llegir d'un fitxer XML pel mètode sintàctic (introduir nom del fitxer)",
			"3.Mostrar per pantalla totes les assignatures amb les seves dades (número, nom, durada i la llista d'alumnes)",
			"4.Afegir una assignatura",
			"5.Afegir un alumne a una assignatura (Demanar número de l'assignatura i comprovar que existeix i demanar dades de l'alumne )",
			"6.Guardar a disc en XML amb les assignatures"};

	
	private static String availableOptions = "";
	
	//run menuManager
	public static void main(String[] args) {
		// TODO Auto-generated method stub

        Menu menu = new Menu();
        menu = menuManager(menu);		
		
	}
	
	//main Menu of application
	public static Menu menuManager(Menu menu)
	{
		int chosen_option = 0;
		String fileName = "/media/jbdragon/KINGSTON250/EscolaDelTreball/2n/M06_acces_a_dades/Activitat3.2/assignatures.xml";
		
		Document doc = null;
		Node rootNode = null;
		
		do
		{
			chosen_option = chooseFromMenu();
			if(chosen_option != -1)
			{
				switch(chosen_option)
				{
					case 0:
						System.out.println("Closing...");
						break;
				
					//sequencial read of entered file
					case 1:
						
						if(fileName == null)
						{
							fileName = enterText("Introdueix el nom del fitxer: ");
						}
						
						doc = getDocumentXML(fileName);
						doc.normalizeDocument();
						rootNode = doc.getDocumentElement();							
						//readXMLSequencial(rootNode);	
						System.out.println("File read -- OK");			
						
						break;
						
					//sintactic read of entered file
					case 2:
						
						if(fileName == null)
						{
							fileName = enterText("Introdueix el nom del fitxer: ");
						}

						try 
						{
							//sintactic
							doc = getDocumentXML(fileName);
							XPath pathAssignatures = XPathFactory.newInstance().newXPath();
							rootNode = (Node) pathAssignatures.compile("/assignatures").evaluate(doc,XPathConstants.NODE);	
							//readXMLSintactic(rootNode);	
							System.out.println("File read -- OK");			
						}
						catch(Exception e)
						{
							System.out.println("XPath assignatura -- ERROR");			
						}							
						
						
						break;
					
					//print loaded data
					case 3: 
						if(fileName == null)
						{
							System.out.println("No file -- ERROR");
						}
						else
						{
							if(rootNode!=null)
							{
								//sintactic
								readXMLSintactic(rootNode);	
							}
							else
							{
								System.out.println("No content (run 1 or 2 options) -- ERROR");
							}							
						}
						break;
						
					//add a new assignatura
					case 4: 
						if(fileName == null)
						{
							System.out.println("No file -- ERROR");
						}
						else
						{
							if(rootNode!=null)
							{
								//sintactic
								doc = addAssignatura(doc);	
							}
							else
							{
								System.out.println("No content (run 1 or 2 options) -- ERROR");
							}							
						}
						break;	
						
					
					//add a new alumne
					case 5: 
						if(fileName == null)
						{
							System.out.println("No file -- ERROR");
						}
						else
						{
							if(rootNode!=null)
							{
								//sintactic
								doc = addAlumne(doc);	
							}
							else
							{
								System.out.println("No content (run 1 or 2 options) -- ERROR");
							}							
						}
						break;							
					
						
					//write loaded data into entered file
					case 6: 
						if(fileName == null)
						{
							System.out.println("No file -- ERROR");
						}
						else
						{
							if(rootNode!=null)
							{
								//sintactical write
								doc = writeLoadedXMLContent(doc, fileName);	
							}
							else
							{
								System.out.println("No content (run 1 or 2 options) -- ERROR");
							}							
						}
						break;						
						
					default:
						System.out.println("Opció no vàlida: "+chosen_option);
				}				
			}
			else
			{
				System.out.println("Opció no vàlida: "+chosen_option);				
			}
		}
		while(chosen_option != 0);

		
		return menu;
	}
	

	
	public static String enterText(String text)
	{
		String content = "";
		System.out.println(text);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
		try{
			content = br.readLine();
		}
		catch(Exception e)
		{
			System.out.println("'Content' not get -- ERROR");
		}
		return content;
	}
	
	
	//print menu and generate 
	public static void printMenu()
	{
		System.out.println("------ MENU ------");
		
		for(int i = 0; i < menu.length; i++)
		{
			System.out.println(menu[i]);
			
			if(i < menu.length-1)
			{
				availableOptions += String.valueOf(i)+"|";
			}
			else
			{
				availableOptions += String.valueOf(i);
			}
		}
	}
	
	
	//1.print menu
	//2.request a value through terminal
	//3.return the value if is an integer number or "-1" if not
	public static int chooseFromMenu()
	{
		printMenu();
		String option = enterText("Escull una opció: ");
		
		if(option.matches(availableOptions))
		{
			return Integer.parseInt(option);
		}
		else
		{
			return -1;
		}
	}
	
	
	//return a file's content as Document
	public static Document getDocumentXML(String fileName)
	{
		Document doc = null;
		
		File f = new File(fileName);
		if(f.exists())
		{
			try 
			{
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);
			}
			catch(Exception e)
			{
				System.out.println("Document not parsed -- ERROR");				
			}
			//System.out.println("'"+f.getName()+"' file found");			
		}
		else
		{
			System.out.println("'"+f.getName()+"' file not found");			
		}
		
		return doc;
	}	
	
	
	//activeMatch = false. It returns the only childnode or the first one found
	//activeMatch = true. It returns an specified node if its name matches. If not the same node is returned
	public static Node getNextSublevelChildNode(Node node, boolean activeMatch, String nodeNameToMatch)
	{		
		if(activeMatch)
		{
			boolean found = false;
			//check if each childNode is an ELEMENT
			
			if(node.hasChildNodes())
			{
				NodeList nd = node.getChildNodes();
				for(int i = 0; i < nd.getLength(); i++)
				{
					if(nd.item(i).getNodeType() == Node.ELEMENT_NODE && nd.item(i).getNodeName().matches(nodeNameToMatch)) 
					{				
						node = nd.item(i);
					}						
					
				}
			}
				
		}
		else
		{
			if(node.hasChildNodes())
			{
				if(node.getChildNodes().item(0).getNodeType() == Node.ELEMENT_NODE)
				{				
					node = node.getChildNodes().item(0);
				}				
			}		
		}
	
		return node;
	}
	
	
	//read the full content of file sequentially
	public static void readXMLSequencial(Node node)
	{
		if(node.hasChildNodes())
		{
			//each assignatura
			NodeList assignaturaNodes = node.getChildNodes();
			System.out.println("assignatures : "+assignaturaNodes.getLength());

			for(int i = 0; i < assignaturaNodes.getLength(); i++)
			{
				Node id_assignatura = getNextSublevelChildNode(assignaturaNodes.item(i),true,"numero");
				System.out.println(id_assignatura.getTextContent());
				Node nom_assignatura = getNextSublevelChildNode(assignaturaNodes.item(i),true,"nom");
				System.out.println(nom_assignatura.getTextContent());
				Node durada_assignatura = getNextSublevelChildNode(assignaturaNodes.item(i),true,"durada");
				System.out.println(durada_assignatura.getTextContent());
				assignatures.add(assignaturaNodes.item(i));
				

				if(assignaturaNodes.item(i).hasChildNodes())
				{
					Node alumnesNode = getNextSublevelChildNode(assignaturaNodes.item(i), true, "alumnes");
					alumnes.put(id_assignatura.getNodeName(),alumnesNode);
					
					if(alumnesNode.hasChildNodes())
					{
						NodeList alumneNodes = alumnesNode.getChildNodes();
						System.out.println("alumnes : "+alumneNodes.getLength());

						for(int j = 0; j < alumneNodes.getLength(); j++)
						{
							Node nomAlumne = getNextSublevelChildNode(alumneNodes.item(j), true, "nom");
							System.out.println(nomAlumne.getTextContent());
							Node dniAlumne = getNextSublevelChildNode(alumneNodes.item(j), true, "dni");
							System.out.println(dniAlumne.getTextContent());
							Node repetidorAlumne = getNextSublevelChildNode(alumneNodes.item(j), true, "repetidor");
							System.out.println(repetidorAlumne.getTextContent());
						}
					}
				}
			}
		}
	}
	
	
	//read the full content of file sintactically
	public static void readXMLSintactic(Node node)
	{

		if(node.hasChildNodes())
		{
			//every assignatura
			NodeList nodesAssignatura = node.getChildNodes();
			System.out.println(node.getNodeName()+" : "+nodesAssignatura.getLength());
			for(int i = 0; i < nodesAssignatura.getLength(); i++)
			{
				//print assignatures : assignatures quantity
				System.out.println(nodesAssignatura.item(i).getNodeName()+" : ");
				
				if(nodesAssignatura.item(i).hasChildNodes())
				{
					NodeList assignaturaData = nodesAssignatura.item(i).getChildNodes();
					for(int j = 0; j < assignaturaData.getLength(); j++)
					{
						//print numero : ,nom : ,durada :  
						if(!assignaturaData.item(j).getNodeName().equals("alumnes"))
						{
							System.out.println(assignaturaData.item(j).getNodeName()+" : "+assignaturaData.item(j).getTextContent());
						}
						else
						{
							NodeList alumnesNode = assignaturaData.item(j).getChildNodes();
							//print alumnes : alumnes quantity
							System.out.println(assignaturaData.item(j).getNodeName()+" : "+alumnesNode.getLength());
							
							for(int k = 0; k < alumnesNode.getLength(); k++)
							{									
								NodeList alumneData = alumnesNode.item(k).getChildNodes();
								for(int m = 0; m < alumneData.getLength(); m++)
								{
									//print nom : ,dni : ,repetidor :  
									System.out.println(alumneData.item(m).getNodeName()+" : "+alumneData.item(m).getTextContent());
								}
							}
							
						}
					}
				}
				else
				{
					System.out.println(nodesAssignatura.item(i).getNodeName()+" : "+nodesAssignatura.item(i).getTextContent());
				}
			}
		}
		else
		{
			System.out.println(node.getNodeName()+" : "+node.getNodeValue());
		}
	}
	
	
	//add a new assignatura if it does not exists
	public static Document addAssignatura(Document doc)
	{
		try {
			String expression = "/assignatures";
			XPath pathAssignatures = XPathFactory.newInstance().newXPath();
			Node assignaturesNode = (Node) pathAssignatures.compile(expression).evaluate(doc,XPathConstants.NODE);
			
			Assignatura ass = new Assignatura();
			ass.createNewAssignatura();
			
			String subExpression1 = "/assignatures/assignatura[numero="+ass.getNumero()+"]";
			XPath pathAlumnes = XPathFactory.newInstance().newXPath();
			NodeList assignaturaNode = (NodeList) pathAlumnes.compile(subExpression1).evaluate(doc,XPathConstants.NODESET);

			if( !(0 < assignaturaNode.getLength()))
			{
				Node assignatura = doc.createElement("assignatura");		
				
				Node numero = doc.createElement("numero");
				numero.setTextContent(String.valueOf(ass.getNumero()));
			
				Node nom = doc.createElement("nom");
				nom.setTextContent(ass.getNom());
				
				Node durada = doc.createElement("durada");
				durada.setTextContent(String.valueOf(ass.getDurada()));
				
				Node alumnes = doc.createElement("alumnes");
				
				assignaturesNode.appendChild(assignatura);
				assignatura.appendChild(numero);
				assignatura.appendChild(nom);
				assignatura.appendChild(durada);						
				assignatura.appendChild(alumnes);						

				System.out.println("assignatura '"+ass.getNumero()+"' added -- OK");
			}
			else
			{
				System.out.println("assignatura '"+ass.getNumero()+"' already exists -- ERROR");
			}
		}
		catch(Exception e)
		{
			System.out.println("'id_assignatuta' not parsed -- ERROR");
		}
		
		return doc;		
	}
	
	
	//add a new alumne 
	public static Document addAlumne(Document doc)
	{
		
		try {
			String id_assignatura = Menu.enterText("Introdueix el número de l'assignatura per verificar que existeix: ");
			
			String expression = "/assignatures/assignatura[numero="+String.valueOf(id_assignatura)+"]";
			XPath pathAssignatures = XPathFactory.newInstance().newXPath();
			NodeList assignaturaNodes = (NodeList) pathAssignatures.compile(expression).evaluate(doc,XPathConstants.NODESET);
			if(0 < assignaturaNodes.getLength())
			{
				
				//get alumnes
				Node alumnesNode = getNextSublevelChildNode(assignaturaNodes.item(0), true, "alumnes");
				if(alumnesNode.getNodeType() == Node.ELEMENT_NODE)
				{
					//create a new instance of Alumne
					Alumne alu = new Alumne();
					alu.createNewAlumne();
		
					//create a new node Alumne
					Node alumne = doc.createElement("alumne");		
					
					Node nom = doc.createElement("nom");
					nom.setTextContent(alu.getNom());
				
					Node dni = doc.createElement("dni");
					dni.setTextContent(alu.getDni());
					
					Node repetidor = doc.createElement("repetidor");
					repetidor.setTextContent(String.valueOf(alu.isRepetidor()));
					
					alumne.appendChild(nom);
					alumne.appendChild(dni);
					alumne.appendChild(repetidor);	
					
					alumnesNode.appendChild(alumne);

					System.out.println("'alumne' "+alu.getNom()+" added -- OK");
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
	
	
	//write the doc's content into the entered file
	public static Document writeLoadedXMLContent(Document doc, String fileName)
	{
		try{
			Transformer tr = TransformerFactory.newInstance().newTransformer();
			tr.transform( new DOMSource(doc), new StreamResult(fileName));
			tr.setOutputProperty(OutputKeys.INDENT, "yes"); // --> create <tag></tag> instead <tag/>
			System.out.println("Data saved -- OK");			
		}
		catch(Exception e)
		{
			System.out.println("Data not saved -- ERROR");
		}
		
		return doc;
	}	
}
