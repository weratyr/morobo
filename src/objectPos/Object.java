package objectPos;

import java.awt.Color;

public class Object {
	private String name;
	private int color;
	private Color currentColor;
	private Position position;
	private Position oldPosition;
	
	public Object() {
		
	}
	
	public Position getOldPosition() {
		return oldPosition;
	}
	public void setOldPosition(Position oldPosition) {
		this.oldPosition = oldPosition;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
	public void setCurrentColorObject(Color currentColor) {
		this.currentColor = currentColor;
	}
	
	public Color getCurrentColorObject() {
		return currentColor;
	}

}
