package objectPos;

public class Position {
	
	private int x;
	private int y;
	private int[] pos;
	
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
		this.pos = pos;
	}
	
	public int[] getPosArray() {
		return pos;
	}
	
}
