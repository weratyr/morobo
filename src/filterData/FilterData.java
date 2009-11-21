package filterData;

import java.util.ArrayList;
import java.util.Hashtable;

public class FilterData {
	private ParserXml parserXml;
	private int id;
	private String input = "";
	
	public FilterData() {
	  parserXml = new ParserXml();
	  parserXml.xmlstinsubstr(input);
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public ArrayList<Hashtable<String, int[]>> getParsedInfos() {
		return parserXml.getArrayListHashTable();
	}
	
	
}