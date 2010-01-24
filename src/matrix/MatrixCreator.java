package matrix;

import java.awt.Color;
import java.util.ArrayList;

import filterData.FilterData;
import filterData.ParserXml;
import objectPos.Object;
import objectPos.Position;

public class MatrixCreator
	{
		private FilterData fd;
		private static final int MAX_BLUE = 240;
		private static final int MIN_BLUE = 10;
		
		private int height = 120;
		private int width = 120;
		private int defaultColor = 100;
		private int red;
		private int green;
		private int blue;
		private int wheelwidth = 18;
		private ArrayList<ArrayList<Color>> matrix;
		
		
		  public MatrixCreator() { matrix = new ArrayList<ArrayList<Color>>();
		  // matrix.add(new ArrayList<Color>());
		  }
		 

	/*	public MatrixCreator()
			{
				matrix = new ArrayList<ArrayList<Color>>();
				Position scanPos = new Position();
				Position myPos = new Position();
				myPos.setX(0);
				myPos.setY(0);
				scanPos.setX(5);
				scanPos.setY(-5);
				absolutePosofobstacle(myPos, scanPos);
		}*/
		
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
		
		public boolean checkMatrixSize(int x, int y)
			{
				if (x >= 0 && x < width && y >= 0 && y < height)
					{
						return true;
					}
				return false;
			}
		
		public void setPixelinMatrix(int x, int y, int r, int g, int b)
			{
				if (checkMatrixSize(x, y))
					{
						matrix.get(x).set(y, new Color(r, g, b));
					}
			}
		
		public void setPixelinMatrix(int x, int y, Color color)
			{
				if (checkMatrixSize(x, y))
					{
						matrix.get(x).set(y, color);
					}
			}
		
		public void setNewPosition(Object obj)
			{
				if (obj.getOldPosition() == null)
					{
						obj.setCurrentColorObject(matrix.get(obj.getPosition().getX()).get(obj.getPosition().getY()));  
						setPixelinMatrix(obj.getPosition().getX(), obj.getPosition().getY(), 0, 0, obj.getColor());
						obj.setOldPosition(obj.getPosition());
					}
				else if (obj.getOldPosition().getX() == obj.getPosition().getX() && obj.getOldPosition().getY() == obj.getPosition().getY())
					{
						System.out.println("object do not move");
						
					}
				else
					{
						// with delete old pos
						setPixelinMatrix(obj.getOldPosition().getX(), obj.getOldPosition().getY(), obj.getCurrentColorObject());
						obj.setCurrentColorObject(matrix.get(obj.getPosition().getX()).get(obj.getPosition().getY()));
						setPixelinMatrix(obj.getPosition().getX(), obj.getPosition().getY(), 0, 0, obj.getColor());
						obj.setOldPosition(obj.getPosition());
					}
			}
		
		public void setColorFromScannedPixel(int x, int y)
			{
				if (checkMatrixSize(x, y))
					{
						blue = matrix.get(x).get(y).getBlue();
						green = matrix.get(x).get(y).getGreen();
						red = matrix.get(x).get(y).getRed();
					}
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
						updateMatrix();
					}
				data.clear();
			}
		
		public ArrayList<ArrayList<Color>> getCreatedMatrix()
			{
				return matrix;
			}
		
		public void drawLine(Position myPos, Position scanPos)// in
		// ver�nderter
		// Form von
		// http://www-lehre.informatik.uni-osnabrueck.de
			{
				scanPos = absolutePosofobstacle(myPos, scanPos);
				// System.out.println("scanPosabsX"+scanPos.getX()+","+"scanPosabsY"+scanPos.getY());
				// Jacks Algorithmus ist schneller als Geradengleichung
				int x, y, error, delta, schritt, dx, dy, inc_x, inc_y;
				
				x = myPos.getX();
				y = myPos.getY();
				System.out.println("mypos:"+myPos.getX()+","+myPos.getY());
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
								
								if (x != myPos.getX())
									{
										decPix(x, y); // Fuer
										// jede
										// x-Koordinate
										// System.out.println("inc pos "+ (x +
										// myPos.getX()));
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
					}
				else
					{ // steil nach oben oder unten
						error = -Math.abs(dy); // Fehler bestimmen
						delta = 2 * Math.abs(dx); // Delta bestimmen
						schritt = 2 * error; // Schwelle bestimmen
						while (y != scanPos.getY())
							{
								if (y != myPos.getY())
									{// fuer jede y-Koordinate
										decPix(x, y);
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
						incPix(scanPos.getX() + x, scanPos.getY() + y);
					}
			}
		

		public void decPix(int x, int y)
			{
				if (checkMatrixSize(x, y))
					{
						if (matrix.get(x).get(y).getBlue() > MIN_BLUE && matrix.get(x).get(y).getGreen() > MIN_BLUE)
							{
								setPixelinMatrix(x, y, (matrix.get(x).get(y).getBlue() - 10), (matrix.get(x).get(y).getGreen() - 10), (matrix.get(x).get(y).getBlue() - 10));
								
							}
					}
			}
		
	
		public double getAngleToObstacle(Position myPos, Position scanPos)
			{
				
				double dax = 0, day = 10;
				double dbx, dby;
				double lengtha, lengthb;
				dbx = scanPos.getX() - myPos.getX();
				dby = scanPos.getY() - myPos.getY();
				lengtha = Math.sqrt(Math.pow(dax, 2) + Math.pow(day, 2));
				lengthb = Math.sqrt(Math.pow(dbx, 2) + Math.pow(dby, 2));
				double beta = Math.toDegrees(Math.acos((dax * dbx + day * dby) / (lengtha * lengthb)));
				
				if (scanPos.getY() > myPos.getY())// oberhalb
					{
						
						if (scanPos.getX() > myPos.getX())// rechts
							{
							}
						else
							{
								beta = beta + 90;
							}
					}
				else
					{
						if (scanPos.getX() > myPos.getX())
							{
								beta = beta + 180;
							}
						else
							{
								beta = beta + 90;
							}
					}
				// System.out.println("beta" + beta);
				return beta;
			}
		
		public double getAngleToX()
			{
				double alpha;
				double lchain = fd.getParsedInfos().getDirection()[0];
				double rchain = fd.getParsedInfos().getDirection()[1];
				
				alpha = ((lchain - rchain) / wheelwidth) * 180 / Math.PI;
				// System.out.println("alpha" + alpha);
				return alpha;
			}
		
		public void incPix(int x, int y)
			{
				if (checkMatrixSize(x, y))
					{
						if (matrix.get(x).get(y).getBlue() < MAX_BLUE && matrix.get(x).get(y).getGreen() > MIN_BLUE)
							{
								setPixelinMatrix(x, y, matrix.get(x).get(y).getBlue() + 10, matrix.get(x).get(y).getGreen() + 10, matrix.get(x).get(y).getBlue() + 10);
								
							}
					}
			}
		
		public Position absolutePosofobstacle(Position myPos, Position scanPos)
			{double dbx = scanPos.getX() - myPos.getX();
			 double dby = scanPos.getY() - myPos.getY();
			double length = Math.sqrt(Math.pow(dbx, 2) + Math.pow(dby, 2));
				Position absPos = new Position();
				double gamma = getAngleToX() + getAngleToObstacle(myPos, scanPos);
				
				absPos.setX((int) Math.round((length * Math.cos(Math.toRadians(gamma)))));
				absPos.setY((int) Math.round((length * Math.sin(Math.toRadians(gamma)))));
				absPos.setX(absPos.getX() + myPos.getX());
				absPos.setY(absPos.getY() + myPos.getY());//
				
				System.out.println("Alpha: " + getAngleToX() + " Beta: " + getAngleToObstacle(myPos, scanPos) + " Gamma: " + gamma);
				System.out.println("mypos:"+myPos.getX()+","+myPos.getY());
				System.out.println("relativePos:"+scanPos.getX()+","+scanPos.getY());
				System.out.println("absolutePos:" + absPos.getX() + "," + absPos.getY());
				return absPos;
			}
		
		public void updateMatrix()
			{
				int[] actPos;
				ArrayList<int[]> vectorHead;
				Position myPos = new Position();
				Position zielPos = new Position();
				actPos = fd.getParsedInfos().getPos();
				
				myPos.setX(actPos[0]);							//FIXME: die aktuelle position verschiebt sich dauernd
				myPos.setY(actPos[1]);
				vectorHead = fd.getParsedInfos().getData();
				if (actPos.length > 0 && !vectorHead.isEmpty())
					{
						for (int i = 0; i < vectorHead.size(); i++)
							{
								int[] scanPos = vectorHead.get(i);
								zielPos.setX(scanPos[0]);
								zielPos.setY(scanPos[1]);
								drawLine(zielPos, myPos);
								
							}
					}
			}
		
		/*public static void main(String[] args)
			{
				
				MatrixCreator mc = new MatrixCreator();
				mc.createMatrix();
				ArrayList<ArrayList<Color>> matrix = mc.getCreatedMatrix();
				
				// System.out.println(matrix.get(1).get(1).getRGB());
				
			}*/
		
	}
