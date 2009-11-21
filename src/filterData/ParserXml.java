package filterData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class ParserXml {
	private String xmlstring1;
	private String xmlstring2;
	private String xmlstring3;
	private String tagcontent;//
	private int counter = 0;
	private String array[] = new String[25];
//	private Hashtable<String,Integer[]> hashSet = new Hashtable<String,Integer[]>();
	private Hashtable<String,int[]> hashTable = new Hashtable<String,int[]>();
	private ArrayList<Hashtable<String,int[]>> infoArrayList = new ArrayList<Hashtable<String,int[]>>();
	

	public void xmlstinsubstr(String xmlstring) {
		int endtag_anfang = 0;
		int endtag_ende = 0;
		int starttag_anfang = 0;
		int starttag_ende = 0;
		
		endtag_anfang = xmlstring.indexOf("</");// ende des hinteren tags
		endtag_ende = xmlstring.indexOf(">", endtag_anfang);// ende des hinteren tags
		starttag_anfang = xmlstring.lastIndexOf("<", endtag_anfang - 1);// anfang des vorderen tags
		starttag_ende = xmlstring.indexOf(">", starttag_anfang);//ende des vorderen tags
		
		if (xmlstring.length() > 0) {
			tagcontent = xmlstring.substring(starttag_anfang + 1, starttag_ende);
			String newstring = xmlstring.substring(starttag_ende + 1, endtag_anfang);
			xmlstring1 = xmlstring.substring(0, starttag_anfang);
			xmlstring2 = xmlstring.substring(endtag_ende + 1, xmlstring.length());
			xmlstring3 = xmlstring1 + xmlstring2;
			String[] xy = newstring.split(",");
			if(xy.length > 1){
				int[] xyInt = new int[] { Integer.parseInt(xy[0]), Integer.parseInt(xy[1])};
				hashTable.put(tagcontent,  xyInt );
				infoArrayList.add(hashTable);
			}
			
//			System.out.println(tagcontent + ": " + newstring);
			array[counter] = newstring;
			counter++;
			xmlstinsubstr(xmlstring3);
		}
	}
	
	
	public ArrayList<Hashtable<String,int[]>> getArrayListHashTable() {
		return infoArrayList;
	}
	
	

//	public static void main(String[] args) {
//		ParserXml communicator = new ParserXml();
//		communicator.xmlstinsubstr("<map><info><pos>a,b </pos>     <direction>c,d</direction><data>e,f</data><data>e,f</data><data>e,f</data><data>g,h</data></info></map>");
//	}
	
	
	
}
