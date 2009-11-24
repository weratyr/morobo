package matrix;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Set;
import java.util.Map.Entry;

import objectPos.Position;

import filterData.FilterData;

public class MatrixCreator implements IMatrixCreator {

	private int height = 150;
	private int width =  150;
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
		for(int i=0; i<width; i++) {
			matrix.add(new ArrayList<Color>());
			for(int j=0; j<height; j++) {
				matrix.get(i).add(new Color(200,200,200));
			}
		}
	}

	public void setPixelinMatrix(int x, int y, int r, int g, int b) {
		matrix.get(y).set(x,new Color(r,g,b));
	}
	
	public void setScanPixelinMatrix(int x, int y) {
		blue = matrix.get(y).get(x).getBlue();
		green = matrix.get(y).get(x).getGreen();
		red = matrix.get(y).get(x).getRed();
		
	}
	
	public void setNewPosition(Position pos) {
		
	}
	
	public void resetMatrix() {
		createMatrix();
	}
	
	
	
	
	public ArrayList<ArrayList<Color>> getCreatedMatrix() {
		return matrix;
	}

//	public static void main(String[] args) {
//		
//		
//		MatrixCreator mc = new MatrixCreator();
//		mc.createMatrix();
//		ArrayList<ArrayList<Color>> matrix = mc.getCreatedMatrix();
//		System.out.println(matrix.get(1).get(1).getRGB());
//	}
	
}
