package socket;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Minimales Beispiel für die Verwendung des SAX-Parsers in Java. Die XML Datei
 * wird eingelesen und ordentlich formatiert auf der Konsole ausgegeben.
 * DefaultHandler ist eine abstrakte Klasse, die die Interfaces ContentHandler,
 * DTDHandler, EntityResolver und ErrorHandler implementiert.
 */
public class ConnectionServer extends DefaultHandler {
	private String text;
	/**
	 * Verschachtelungstiefe der Tags, wird verwendet, um das XML-Dokument
	 * formatiert auszugeben.
	 */
	private int level = 0;

	/**
	 * Leerer Konstruktor, die Initialisierung des Parsers erfolgt in der
	 * main-Methode.
	 */
	public ConnectionServer() throws Exception {
		text = new String();
		int port = 1234;

		// Neuen SAX-Parser erzeugen
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();

		// Server starten
		ServerSocket server = new ServerSocket(1234);
		System.out.println("Server gestartet on Port " + port);
		// warten auf eine neue Verbindung
		Socket s = server.accept();
		// neue Verbindung ist da, wir lesen einfach aus,
		// was sie uns so schickt und schicken dann alles in grossbuchstaben
		// wieder zurück
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

		// XML Datei parsen, die entsprechenden Methoden des DefaultHandler
		// werden als Callback aufgerufen.
		saxParser.parse(s.getInputStream(), this);

		System.out.println(text);
	
		out.write(text);
		out.newLine();
		out.flush();
		out.close();

		server.close();
	}

	/**
	 * Gibt <code>level</code> Tabs auf der Konsole aus.
	 */
	public void indent() {
		try {
			// Mit Tabs einrücken
			for (int i = 0; i < level; i++)
				text += "\t";
			// out.write("\t");
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Wird am Anfang des Dokuments aufgerufen, definiert im Interface
	 * ContentHandler.
	 */
	public void startDocument() throws SAXException {
		// out.write("Start des Dokuments");
	}

	/**
	 * Wird bei jedem öffnenden Tag aufgerufen, definiert im Interface
	 * ContentHandler. Bei leeren Tags wie zum Beispiel &img /& wird
	 * startElement und endElement direkt hintereinander aufgerufen. Mit J2SE
	 * 1.4.2 scheint nur qName gefüllt zu sein.
	 * 
	 * @param namespaceURI
	 *            URI des Namespaces für dieses Element, kann auch ein leerer
	 *            String sein.
	 * @param localName
	 *            Lokaler Name des Elements, kann auch ein leerer String sein.
	 * @param qName
	 *            Qualifizierter Name (mit Namespace-Prefix) des Elements.
	 * @param atts
	 *            Liste der Attribute.
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		indent();
		try {
			text += "<" + qName;

			// Test-Code um zu sehen, was in namespaceURI und localName steht
			// System.out.print(" " + namespaceURI);
			// System.out.print(" " + localName);

			// Attribute ausgeben
			for (int i = 0; i < atts.getLength(); i++)
				text += " " + atts.getQName(i) + "=\"" + atts.getValue(i) + "\"";

			text += ">";
		} catch (Exception e) {
			System.err.println(e);
		}

		level++;
	}

	/**
	 * Wird bei jedem schließenden Tag aufgerufen, definiert im Interface
	 * ContentHandler.
	 * 
	 * @param namespaceURI
	 *            URI des Namespaces für dieses Element, kann auch ein leerer
	 *            String sein.
	 * @param localName
	 *            Lokaler Name des Elements.
	 * @param qName
	 *            Qualifizierter Name des Elements.
	 */
	public void endElement(String namespaceURI, String localName, String qName) {
		level--;

		indent();
		try {
			text += "</" + qName + ">";
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Wird immer aufgerufen, wenn Zeichen im Dokument auftauchen.
	 * 
	 * @param ch
	 *            Character Array
	 * @param start
	 *            Startindex der Zeichen in ch
	 * @param length
	 *            Länge der Zeichenkette
	 */
	public void characters(char ch[], int start, int length) {
		String s = new String(ch, start, length).trim();
		if (s.length() > 0) {
			indent();
			try {
				text += (s);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	/**
	 * Wird aufgerufen, wenn Leerraum (" ", "\t", "\n", "\r") im Dokument
	 * auftaucht, der für die Struktur des Dokuments nicht von Bedeutung ist.
	 * 
	 * @param ch
	 *            Character Array
	 * @param start
	 *            Startindex der Zeichen in ch
	 * @param length
	 *            Länge der Zeichenkette
	 */
	public void ignorableWhitespace(char[] ch, int start, int length) {
	}

	public static void main(String args[]) {

		try {
			new ConnectionServer();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
