package filterData;

public class ParserXml
	{
		String newstring;
		String xmlstring1;
		String xmlstring2;
		String xmlstring3;
		String tagcontent;//
		int counter = 0;
		String array[] = new String[25];

		public void xmlstinsubstr(String xmlstring)
			{
				int endtag_anfang = 0;
				int endtag_ende = 0;
				int starttag_anfang = 0;
				int starttag_ende = 0;
					{
						endtag_anfang = xmlstring.indexOf("</");// ende des
																// hinteren tags
						
						endtag_ende = xmlstring.indexOf(">", endtag_anfang);// ende
																			// des
																			// hinteren
																			// tags
						
						starttag_anfang = xmlstring.lastIndexOf("<", endtag_anfang - 1);// anfang
																						// des
																						// vorderen
																						// tags
			
						starttag_ende = xmlstring.indexOf(">", starttag_anfang);// ende
																				// des
																				// vorderen
																				// tags
	
					}


				if (xmlstring.length() > 0)
					{
						tagcontent=xmlstring.substring(starttag_anfang+1, starttag_ende);
						String newstring = xmlstring.substring(starttag_ende + 1, endtag_anfang);
						xmlstring1 = xmlstring.substring(0, starttag_anfang);
						xmlstring2 = xmlstring.substring(endtag_ende + 1, xmlstring.length());
						xmlstring3 = xmlstring1 + xmlstring2;
						System.out.println(tagcontent+": " +newstring);
						array[counter] = newstring;
						counter++;
						xmlstinsubstr(xmlstring3);
					}
			}
		public static void main(String[] args)
			{
				ParserXml communicator = new ParserXml();
				communicator.xmlstinsubstr("<map><info><pos>a,b</pos>\t<direction>c,d</direction><data>e,f</data><data>e,f</data><data>e,f</data><data>g,h</data></info></map>");
			}
}
