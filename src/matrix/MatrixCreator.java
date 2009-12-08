package matrix;

import java.awt.Color;
import java.util.ArrayList;
import objectPos.Object;

public class MatrixCreator implements IMatrixCreator {

	private int height = 150;
	private int width = 150;
	private int defaultColor = 100;
	private int red;
	private int green;
	private int blue;
	private ArrayList<ArrayList<Color>> matrix;


	public MatrixCreator() {
		matrix = new ArrayList<ArrayList<Color>>();
		// matrix.add(new ArrayList<Color>());
	}
	/**
	 * 
	 */
	public void updateMatrix() {

	}

	public void createMatrix() {
		for (int i = 0; i < width; i++) {
			matrix.add(new ArrayList<Color>());
			for (int j = 0; j < height; j++) {
				matrix.get(i).add(new Color(defaultColor, defaultColor, defaultColor));
			}
		}
	}

	public void resetMatrix() {
		matrix.clear();
		for (int i = 0; i < width; i++) {
			matrix.add(new ArrayList<Color>());
			for (int j = 0; j < height; j++) {
				matrix.get(i).add(new Color(defaultColor, defaultColor, defaultColor));
			}
		}
	}

	public void setPixelinMatrix(int x, int y, int r, int g, int b) {
		matrix.get(x).set(y, new Color(r, g, b));
	}

	public void setNewPosition(Object obj) {
		if (obj.getOldPosition() == null) {
			setPixelinMatrix(obj.getPosition().getX(), obj.getPosition().getY(), 0, 0, obj.getColor());
			obj.setOldPosition(obj.getPosition());
		} else {
			// with delete old pos
			setPixelinMatrix(obj.getPosition().getX(), obj.getPosition().getY(), 0, 0, obj.getColor());
			setPixelinMatrix(obj.getOldPosition().getX(), obj.getOldPosition().getY(), defaultColor, defaultColor, defaultColor);
			obj.setOldPosition(obj.getPosition());
		}
	}

	public void setColorFromScannedPixel(int x, int y) {
		blue = matrix.get(x).get(y).getBlue();
		green = matrix.get(x).get(y).getGreen();
		red = matrix.get(x).get(y).getRed();
	}

	public void setScanData(ArrayList<int[]> data) {
		for (int i = 0; i < data.size(); i++) {
			int x = data.get(i)[0];
			int y = data.get(i)[1];
			setColorFromScannedPixel(x, y);

			if (blue < 250) {
				blue += 10;
				green += 10;
				red += 10;
			}
			setPixelinMatrix(x, y, red, green, blue);
//			System.out.println("pixel scanned" + x + "," + y + "color" + blue + " size data" + data.size());
		}
		data.clear();
	}

	public ArrayList<ArrayList<Color>> getCreatedMatrix() {
		return matrix;
	}

	// public static void main(String[] args) {
	//		
	//		
	// MatrixCreator mc = new MatrixCreator();
	// mc.createMatrix();
	// ArrayList<ArrayList<Color>> matrix = mc.getCreatedMatrix();

	// System.out.println(matrix.get(1).get(1).getRGB());
	// }

}