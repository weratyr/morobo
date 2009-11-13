package map;

import java.awt.Color;
import java.util.ArrayList;

public interface IMap {
	public ArrayList<ArrayList<Color>> getMap();
	public void setMap(ArrayList<ArrayList<Color>> matrix);

}
