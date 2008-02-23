package main;

import java.io.File;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class xmlReader{

	public static String read(String nombrePuzzle, String atributo) {
		// Copiado de http://www.developerfusion.co.uk/show/2064/
		String r = "";
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (new File("config"+File.separator+"config.xml"));

			// normalize text representation
			doc.getDocumentElement().normalize();

			NodeList listaDePuzzles = doc.getElementsByTagName("puzzle");

			for(int s=0; s<listaDePuzzles.getLength(); s++){
				Node puzzle = listaDePuzzles.item(s);
				Element puzzleElement = (Element) puzzle;
				if (puzzleElement.getAttribute("nombre").equals(nombrePuzzle)) {
					NodeList data = puzzleElement.getElementsByTagName(atributo);
					Element dataElement = (Element)data.item(0);
					NodeList textDataList = dataElement.getChildNodes();
					r = ((Node)textDataList.item(0)).getNodeValue().trim();
				}
			}
		}catch (SAXParseException err) {
			System.out.println ("** Parsing error" + ", line " 
					+ err.getLineNumber () + ", uri " + err.getSystemId ());
			System.out.println(" " + err.getMessage ());

		}catch (SAXException e) {
			Exception x = e.getException ();
			((x == null) ? e : x).printStackTrace ();

		}catch (Throwable t) {
			t.printStackTrace ();
		}
		return r;
	}
}