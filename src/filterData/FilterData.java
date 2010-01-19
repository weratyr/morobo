package filterData;

public class FilterData {
	private ParserXml parserXml;
	private String input="";
	
	public FilterData() {
	  parserXml = new ParserXml();
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public DataContainer getParsedInfos() {
		return parserXml.getDatenContainer();
	}
	
	public void filterInputData(String input) {
//		if(parserXml.checkValidString(input)) {
			parserXml.xmlstinsubstr(input);			
//		} else {
//			System.out.println("input stream not valid!!");
//			System.exit(-1);
//		}
		
	}
	
//	public static void main(String[] args) {
//		FilterData fd = new FilterData();
//		fd.setInput("<map><info><name>Ketten</name></info></map><map><info><pos>3,4</pos><direction>3,6</direction><data>8,7</data><data>10,5</data><data>23,4</data><data>4,3</data></info></map>");
//		for(int i=0; i<9; i++) {
//			fd.filterData();
//			System.out.println(fd.getParsedInfos().getName());
//			for(Entry es : fd.getParsedInfos().get(i).entrySet()){
//				System.out.println(fd.getParsedInfos().get(i).size());
//				int[] xy = (int[]) es.getValue();
//				System.out.println("Key: "+es.getKey()+" Value: "+ xy[0]+","+xy[1] +" "+i );				
//			}
//			System.out.println();
//		}
//	}
	
	
}
