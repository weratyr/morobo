package filterData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

public class FilterData {
	private ParserXml parserXml;
	private int id;
	private String input="";
	
	public FilterData() {
	  parserXml = new ParserXml();
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public ArrayList<Hashtable<String, int[]>> getParsedInfos() {
		return parserXml.getArrayListHashTable();
	}
	
	public void filterData() {
		parserXml.xmlstinsubstr(input);
	}
	
//	public static void main(String[] args) {
//		FilterData fd = new FilterData();
//		for(int i=0; i<8; i++) {
//			fd.setInput("<map><info><pos>5,15</pos><direction>3,6</direction><data>8,7</data><data>10,5</data><data>23,4</data><data>"+i+",3</data></info></map>");
//			fd.filterData();
//			for(Entry es : fd.getParsedInfos().get(i).entrySet()){
//				int[] xy = (int[]) es.getValue();
//				System.out.println("Key: "+es.getKey()+" Value: "+ xy[0]+","+xy[1] );				
//			}
//			System.out.println();
//		}
//	}
	
	
}
