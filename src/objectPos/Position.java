package objectPos;

public class Position {
	
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setPosArray(int[] pos) {
		this.x = pos[0];
		this.y = pos[1];
	}
	
	public int[] getPosArray() {
		int pos[] = {x,y};
		return pos;
	}
}
