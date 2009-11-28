package matrix;

import java.awt.Color;
import java.util.ArrayList;
import objectPos.Object;

public class MatrixCreator implements IMatrixCreator {

	private int height = 150;
	private int width = 150;
	private int defaultColor = 200;
	private int red;
	private int green;
	private int blue;
	private ArrayList<ArrayList<Color>> matrix;
	private Color oldColor;
	private Color newColor;

	public MatrixCreator() {
		matrix = new ArrayList<ArrayList<Color>>();
		matrix.add(new ArrayList<Color>());
	}

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
		matrix.get(y).set(x, new Color(r, g, b));
	}

	public void setScanPixelinMatrix(int x, int y) {
		blue = matrix.get(y).get(x).getBlue();
		green = matrix.get(y).get(x).getGreen();
		red = matrix.get(y).get(x).getRed();

	}

	public void setNewPosition(Object obj) {

		if (obj.getOldPosition() == null) {
			setPixelinMatrix(obj.getPosition().getX(), obj.getPosition().getY(), 0,0,obj.getColor());
			obj.setOldPosition(obj.getPosition());
			System.out.println("old pos is null");
		} else {
			// with delete old pos
			System.out.println("old pos " + obj.getOldPosition().getX()+ " new pos"+ obj.getPosition().getX()+ " color "+ obj.getColor());
			setPixelinMatrix(obj.getPosition().getX(), obj.getPosition().getY(), 0, 0, obj.getColor());
			setPixelinMatrix(obj.getOldPosition().getX(), obj.getOldPosition().getY(), defaultColor, defaultColor, defaultColor);
			obj.setOldPosition(obj.getPosition());
		}
		
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