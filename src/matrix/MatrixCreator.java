package matrix;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Set;
import java.util.Map.Entry;

import filterData.FilterData;

public class MatrixCreator implements IMatrixCreator {

	private int height = 130;
	private int width =  130;
	private int red;
	private int green;
	private int blue;
	private ArrayList<ArrayList<Color>> matrix;
	private FilterData filterData;
	private ArrayList<Hashtable<String, int[]>> inputInfos;
	
	public MatrixCreator() {
		
//		inputInfos =  filterData.getParsedInfos();
//		if(inputInfos.size() > 0){
//			System.out.println(inputInfos.get(0) + "length "+inputInfos.size()+ " key data "+ inputInfos.get(0).entrySet());
//		}
		
		matrix = new ArrayList<ArrayList<Color>>();
		matrix.add(new ArrayList<Color>());
		
		
	}
	public void updateMatrix() {
		
	}
		
	public void createMatrix() {
		for(int i=0; i<width; i++) {
			matrix.add(new ArrayList<Color>());
			for(int j=0; j<height; j++) {
				matrix.get(i).add(new Color(125,125,125));
			}
		}
		setPixelinMatrix(10,10,0,0,255);
	}

	public void setPixelinMatrix(int x, int y, int r, int g, int b) {
		matrix.get(y).set(x,new Color(r,g,b));
		System.out.println("x,y setPixel "+x +" "+y);
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
