package filterData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserXml
	{
		private boolean stringComplete;
		private int length;
		private String xmlstring1;
		private String xmlstring2;
		private String xmlstring3;
		private String tagcontent;//
		private DataContainer dContainer;
		private Hashtable<String, Integer> xmlData;
		private int tagIndex;

		public ParserXml()
			{

				dContainer = new DataContainer();
				xmlData = new Hashtable<String, Integer>();
				xmlData.put("data", 1);
				xmlData.put("pos", 2);
				xmlData.put("direction", 3);
				xmlData.put("name", 4);
				xmlData.put("info", 5);
				xmlData.put("map", 6);
				xmlData.put("length", 7);
			}

		// "<length>80</length><map><name>ketten</name><pos>"+(8+i)+","+((i*2)+2)+"</pos><direction>3,6</direction><data>1,"+i+"</data>"
		// +
		// "<data>10,5</data><data>23,4</data><data>"+i+",3</data></map>"
		public int getByteLengthofString(String xmlstring)
			{
				xmlstring = "<length>3457</length>";
				// length=Integer.parseInt(xmlstring.substring(xmlstring.indexOf("<length>")+1
				// xmlstring.in,xmlstring.indexOf("</length>")));
				Pattern p = Pattern.compile("<length>(\\d+)</length>");
				Matcher m = p.matcher(xmlstring);
				if (m.find())
					{
						System.out.println(m.group(1));

					}
				return 2;// length;
			}

		public boolean checkValidString(String xmlstring)
			{
				int length = getByteLengthofString(xmlstring);
				int actlength = xmlstring.length();

				if (length <= actlength)
					{
						return true;
					}
				return false;
			}

		public void xmlstinsubstr(String xmlstring)
			{

				// System.out.println("xmlstring "+xmlstring);

				int endtag_anfang = 0;

				int endtag_ende = 0;
				int starttag_anfang = 0;
				int starttag_ende = 0;

				endtag_anfang = xmlstring.indexOf("</");// ende des hinteren
				// tags
				endtag_ende = xmlstring.indexOf(">", endtag_anfang);// ende des
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
				if (xmlstring.length() > 0)
					{
						tagcontent = xmlstring.substring(starttag_anfang + 1, starttag_ende);

						String newstring = xmlstring.substring(starttag_ende + 1, endtag_anfang);
						xmlstring1 = xmlstring.substring(0, starttag_anfang);
						xmlstring2 = xmlstring.substring(endtag_ende + 1, xmlstring.length());
						xmlstring3 = xmlstring1 + xmlstring2;

						String[] xy = newstring.split(",");
						int[] xyInt;
						if (xy.length > 1)
							{
								xyInt = new int[]
									{ Integer.parseInt(xy[0]), Integer.parseInt(xy[1]) };
							} else
							{
								xyInt = new int[]
									{ 0, 0 };
							}

						switch (tagIndex)
							{
							case 1: // data
								dContainer.addData(xyInt);
								break;
							case 2: // pos
								dContainer.setPos(xyInt);
								break;
							case 3: // direction
								dContainer.setDirection(xyInt);
								break;
							case 4: // name
								dContainer.setName(newstring);
								// case 7: // length
								// dContainer.setLength(length);
							}

						// System.out.println(tagcontent + ": " + newstring);
						xmlstinsubstr(xmlstring3);
					}

			}

		public DataContainer getDatenContainer()
			{
				return dContainer;
			}

		// public static void main(String args[]) {
		// ParserXml p = new ParserXml();
		// p.getByteLengthofString("dkf");
		// }

	}

// gelesene Bytes hochzählen und mit der übgergebenen länge vergleichen,
// abbruchkriterium Gelesene=übbergebene länge!
// tag anfanganfang= suche "<"
// tag anfangende=posanfanf anfang suche 1. ">"
// tag content=dazwischen
// endetag="</"+tagcontent"+">"