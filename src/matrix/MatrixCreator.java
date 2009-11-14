package matrix;

import java.awt.Color;
import java.util.ArrayList;

public class MatrixCreator implements IMatrixCreator {

	private int height = 230;
	private int width =  40;
	private ArrayList<ArrayList<Color>> matrix;
	
	public MatrixCreator() {
		matrix = new ArrayList<ArrayList<Color>>();
		matrix.add(new ArrayList<Color>());
		
	}
		
	public void createMatrix() {
		for(int i=0; i<width; i++) {
			matrix.add(new ArrayList<Color>());
			for(int j=0; j<height; j++) {
				matrix.get(i).add(new Color(155,155,155));
			}
		}
		matrix.get(20).set(20, new Color(255,0,0));

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
