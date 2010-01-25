package filterData;

import java.util.ArrayList;

public class DataContainer {
	//private int length;
	private String name;
	private int[] pos;
	private int[] direction;
	private ArrayList<int[]> data;
	
	public DataContainer() {
		data = new ArrayList<int[]>();
	}
	
	//public int getLength(){
	//	return length;
	//}
	
	//public void setLength(int length){
	//	this.length=length;
	//}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getPos() {
		return pos;
	}
	public void setPos(int[] pos) {
		this.pos = pos;
	}
	public int[] getDirection() {
		return direction;
	}
	public void setDirection(int[] direction) {
		this.direction = direction;
	}
	public ArrayList<int[]> getData() {
		return data;
	}
	public void setData(ArrayList<int[]> data) {
		this.data = data;
	}
	
	public void addData(int[] data) {
		this.data.add(data);
	}
	
	
	
}
