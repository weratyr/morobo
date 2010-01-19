package matrix;

import java.awt.Color;
import java.util.ArrayList;

import filterData.FilterData;
import filterData.ParserXml;
import objectPos.Object;
import objectPos.Position;

public class MatrixCreator implements IMatrixCreator
	{
		private FilterData fd;
		private static final int MAX_BLUE = 240;
		private static final int MIN_BLUE = 10;

		private int height = 150;
		private int width = 150;
		private int defaultColor = 100;
		private int red;
		private int green;
		private int blue;
		private ArrayList<ArrayList<Color>> matrix;

		public MatrixCreator()
			{
				matrix = new ArrayList<ArrayList<Color>>();
				// matrix.add(new ArrayList<Color>());
			}

		public void setfilterData(FilterData fd)
			{
				this.fd = fd;
			}

		public void createMatrix()
			{
				for (int i = 0; i < width; i++)
					{
						matrix.add(new ArrayList<Color>());
						for (int j = 0; j < height; j++)
							{
								matrix.get(i).add(new Color(defaultColor, defaultColor, defaultColor));
							}
					}
			}

		public void resetMatrix()
			{
				matrix.clear();
				for (int i = 0; i < width; i++)
					{
						matrix.add(new ArrayList<Color>());
						for (int j = 0; j < height; j++)
							{
								matrix.get(i).add(new Color(defaultColor, defaultColor, defaultColor));
							}
					}
			}

		public void setPixelinMatrix(int x, int y, int r, int g, int b)
			{
				matrix.get(x).set(y, new Color(r, g, b));
			}

		public void setNewPosition(Object obj)
			{
				if (obj.getOldPosition() == null)
					{
						setPixelinMatrix(obj.getPosition().getX(), obj.getPosition().getY(), 0, 0, obj.getColor());
						obj.setOldPosition(obj.getPosition());
					} else if (obj.getOldPosition().getX() == obj.getPosition().getX() && obj.getOldPosition().getY() == obj.getPosition().getY() ) {
						
					}else
					{
						// with delete old pos
						setPixelinMatrix(obj.getPosition().getX(), obj.getPosition().getY(), 0, 0, obj.getColor());
						setPixelinMatrix(obj.getOldPosition().getX(), obj.getOldPosition().getY(), defaultColor, defaultColor, defaultColor);
						obj.setOldPosition(obj.getPosition());
					}
			}

		public void setColorFromScannedPixel(int x, int y)
			{
				blue = matrix.get(x).get(y).getBlue();
				green = matrix.get(x).get(y).getGreen();
				red = matrix.get(x).get(y).getRed();
			}

		public void setScanData(ArrayList<int[]> data)
			{
				for (int i = 0; i < data.size(); i++)
					{
						int x = data.get(i)[0];
						int y = data.get(i)[1];
						setColorFromScannedPixel(x, y);

						if (blue < MAX_BLUE)
							{
								blue += 10;
								green += 10;
								red += 10;
							}
						setPixelinMatrix(x, y, red, green, blue);
						// System.out.println("pixel scanned" + x + "," + y +
						// "color" + blue + " size data" + data.size());
						updateMatrix();
						System.out.println("x,y "+ x+","+y);
					}
				data.clear();
			}

		public ArrayList<ArrayList<Color>> getCreatedMatrix()
			{
				return matrix;
			}

		public void drawLine(Position scanPos, Position mypos)// in verŠnderter
																// Form von
																// http://www-lehre.informatik.uni-osnabrueck.de
			{
				System.out.println("Scan pos: " + scanPos.getX()+","+scanPos.getY() + " MyPos: " + mypos.getX() + "," + mypos.getY());
				//directionvector(scanPos);
				// Jacks Algorithmus ist schneller als Geradengleichung
				int x, y, error, delta, schritt, dx, dy, inc_x, inc_y;

				x = mypos.getX(); // 
				y = mypos.getY(); // As i am center

				dx = scanPos.getX() - x;
				dy = scanPos.getY() - y; // Hoehenzuwachs
				// Schrittweite

				if (dx > 0) // Linie nach rechts?
					inc_x = 1; // x inkrementieren
				else
					// Linie nach links
					inc_x = -1; // x dekrementieren

				if (dy > 0) // Linie nach oben ?
					inc_y = 1; // y inkrementieren
				else
					// Linie nach unten
					inc_y = -1; // y dekrementieren

				if (Math.abs(dy) < Math.abs(dx))
					{ // flach nach oben oder unten
						error = -Math.abs(dx); // Fehler bestimmen
						delta = 2 * Math.abs(dy); // Delta bestimmen
						schritt = 2 * error; // Schwelle bestimmen
						while (x != scanPos.getX())
							{
								if (x != mypos.getX())
									{
										incPix(x + mypos.getX(), y + mypos.getY()); // Fuer
																					// jede
																					// x-Koordinate
										System.out.println("inc pos "+ (x + mypos.getX()));
									}
								// Pixel
								x += inc_x; // naechste x-Koordinate
								error = error + delta; // Fehler aktualisieren
								if (error > 0)
									{ // neue Spalte erreicht?
										y += inc_y; // y-Koord. aktualisieren
										error += schritt; // Fehler
										// aktualisieren
									}
							}
					} else
					{ // steil nach oben oder unten
						error = -Math.abs(dy); // Fehler bestimmen
						delta = 2 * Math.abs(dx); // Delta bestimmen
						schritt = 2 * error; // Schwelle bestimmen
						while (y != scanPos.getY())
							{
								if (y != mypos.getY())
									{// fuer jede y-Koordinate
										incPix(x + mypos.getX(), y + mypos.getY());
									}// setze
								// Pixel
								y += inc_y; // naechste y-Koordinate
								error = error + delta; // Fehler aktualisieren
								if (error > 0)
									{ // neue Zeile erreicht?
										x += inc_x; // x-Koord. aktualisieren
										error += schritt; // Fehler
										// aktualisieren
									}
							}
					}
				if ((x != scanPos.getX() && y != scanPos.getY()))
					{
						decPix(scanPos.getX() + x, scanPos.getY() + y);
					}
			}
	/*	public void drawLine(Position position, Position mypos)
			{
				int dx,dy;
				double m,t ;
				dx= position.getX()-mypos.getX();
				dy= position.getY()-mypos.getY();
				m=dy/dx;
				t=(m*mypos.getX())+mypos.getY();
				System.out.println("P1:"+position.getX()+","+position.getY());
				System.out.println("P2:"+mypos.getX()+","+mypos.getY());
				System.out.println("m:"+m+ " t:"+t );
			}*/

		public void decPix(int x, int y)
			{
				if(x > width || y > height) {
					x = 149;
					y = 149;
				}

				if (matrix.get(x).get(y).getBlue() > MIN_BLUE && matrix.get(x).get(y).getGreen() > MIN_BLUE) {
					//System.out.println("dec"+matrix.get(x).get(y).getGreen());
					setPixelinMatrix(x, y, (matrix.get(x).get(y).getBlue() - 10), (matrix.get(x).get(y).getGreen() - 10), (matrix.get(x).get(y).getBlue() - 10));
					
				}
			}

		public void incPix(int x, int y)
			{
				if(x > width || y > height) {
				x = 149;
				y = 149;
			}
				if (matrix.get(x).get(y).getBlue() < MAX_BLUE && matrix.get(x).get(y).getGreen() > MIN_BLUE) {
					//System.out.println("inc"+matrix.get(x).get(y).getGreen());
					setPixelinMatrix(x, y, matrix.get(x).get(y).getBlue() + 10, matrix.get(x).get(y).getGreen() + 10, matrix.get(x).get(y).getBlue() + 10);
				}
			}

		public void updateMatrix()
			{

				int[] actPos;
				ArrayList<int[]> vectorHead;
				Position myPos = new Position();
				Position zielPos = new Position();
				actPos = fd.getParsedInfos().getPos();
				
				myPos.setX(actPos[0]);
				myPos.setY(actPos[1]);
				vectorHead = fd.getParsedInfos().getData();
				
				if (actPos.length > 0 && !vectorHead.isEmpty())
					{
						for (int i = 0; i < vectorHead.size(); i++)
							{
								int[] scanPos  = vectorHead.get(i);
								zielPos.setX(scanPos[0]);
								zielPos.setY(scanPos[1]);
								
								//System.out.println("i "+ i + " Name:" + fd.getParsedInfos().getName() + "scan pix " + fd.getParsedInfos().getData().get(0)[0]+","+ fd.getParsedInfos().getData().get(0)[1]);
								drawLine( zielPos, myPos);
								i++;
							}
					}
			}
		//public Position directionvector (Position position)
		public void directionvector (Position position)
			{	
				int x,y,x2,y2,wheelwidth;
				wheelwidth=18;
				float alpha;
				int lchain=fd.getParsedInfos().getDirection()[0];
				int rchain=fd.getParsedInfos().getDirection()[1];
				alpha=(float)Math.round((lchain-rchain)/wheelwidth*180/Math.PI);
				alpha=(int)alpha;
				x=position.getX();
				y=position.getY();
				
				x2=(int) Math.round(Math.sqrt(Math.pow(x, 2)+Math.pow(y,2))*Math.cos(alpha)*x);
				y2=(int) Math.round(Math.sqrt(Math.pow(x, 2)+Math.pow(y,2))*Math.cos(alpha)*y);
				position.setX(x2);
				position.setY(y2);
				System.out.println("Ypos"+position.getX()+"Xpos"+position.getY());
			//	return position;
				 
			}
		/*
		 * public void drawLine(Position position) { int xpos=0, ypos=0; //As
		 * i'm center int x, y, dx, dy; int x1=position.getX(),
		 * y1=position.getY(); double steigung, error;
		 * 
		 * dy = y1 - ypos; dx = x1 - xpos; x = xpos; y = ypos;
		 * 
		 * error = 0.0; steigung = (double) (dy / dx);
		 * 
		 * if (Math.signum(dy)>0) //Quad I+II {int ecount=1; if
		 * (Math.signum(dx)>0) //Quad I { while (x <= x1) { decPix(position);
		 * 
		 * x++; error += steigung; if (error > 0.5) { y++; error+=ecount ; } } }
		 * else //Quad II { while (x>=x1) { decPix(position); //die
		 * zwischenpunkte dekrementieren x--; error +=steigung; if(error >0.5) {
		 * y++; error+=ecount; } } }
		 * 
		 * 
		 * }
		 * 
		 * else //Quad III+IV {int ecount=-1; if(Math.signum(dx)<0) //Quad III {
		 * while(x>=x1) { decPix(position); //zwischenpunkt x--; error
		 * -=steigung; if(error <0.5) { y--; error+=ecount; } } } else //Quad IV
		 * { while(x<=x1) { decPix(position); //zwischenpunkt x++; error
		 * -=steigung; if(error <0.5) { y--; error+=ecount; } }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * incPix(position); //den tatsŠchlichen Punkt inkrementieren }
		 */
		/*
		 * public static void main(String[] args) {
		 * 
		 * 
		 * MatrixCreator mc = new MatrixCreator(); mc.createMatrix();
		 * ArrayList<ArrayList<Color>> matrix = mc.getCreatedMatrix();
		 * 
		 * System.out.println(matrix.get(1).get(1).getRGB());
		 * 
		 * }
		 */

	}