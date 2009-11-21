package map;

import java.awt.Color;
import java.util.ArrayList;

import matrix.IMatrixCreator;
import matrix.MatrixCreator;

public class Map implements IMap {

	private ArrayList<ArrayList<Color>> matrix;
	private MatrixCreator mc;
	
	public Map() {
		mc = new MatrixCreator();
		mc.createMatrix();
		matrix = mc.getCreatedMatrix();
	}
	
	public ArrayList<ArrayList<Color>> getMap() {
		return matrix;
	}

	public void setMap(ArrayList<ArrayList<Color>> matrix) {
		this.matrix = matrix;
	}

	public Map getMapObject() {
		return this;
	}
	
	public MatrixCreator getMatrixCreatorObject() {
		return mc;
	}
}
