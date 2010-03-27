package filterData;

public class FilterData {
	private ParserXml parserXml;
	@SuppressWarnings("unused")
	private String input="";
	
	public FilterData() {
	  parserXml = new ParserXml();
	}
	
	/**
	 * 
	 */
	public void setInput(String input) {
		this.input = input;
	}
	
	/**
	 * 
	 */
	public synchronized DataContainer getParsedInfos() {
		return parserXml.getDatenContainer();
	}
	
	/**
	 * 
	 */
	public void filterInputData(String input) {
			parserXml.xmlstinsubstr(input);			
	}
	

	
}
