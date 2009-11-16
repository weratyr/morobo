package matrix;

import java.awt.Color;
import java.util.ArrayList;

public class MatrixCreator implements IMatrixCreator {

	private int height = 50;
	private int width =  50;
	private ArrayList<ArrayList<Color>> matrix;
	
	public MatrixCreator() {
		matrix = new ArrayList<ArrayList<Color>>();
		matrix.add(new ArrayList<Color>());
		
	}
		
	public void createMatrix() {
		for(int i=0; i<width; i++) {
			matrix.add(new ArrayList<Color>());
			for(int j=0; j<height; j++) {
				matrix.get(i).add(new Color(125,125,125));
			}
		}
		for(int i=20; i<25; i++) {
			for(int j=20; j< 30; j++){
				if(i==22 && j>22 && j<29) {
					matrix.get(i).set(j, new Color(0,0,255));
				}else {
					matrix.get(i).set(j, new Color(255,0,0));
				}
			}
		}
		
		for(int x=2; x<5; x++) {
			for(int y=1; y< 10; y++){
				matrix.get(x).set(y, new Color(255,0,0));
			}
		}

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
