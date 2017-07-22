package bns.bean;

public class ColorBean {
	private boolean exceptThisColor = false;
	
	private int x;
	
	private int y;
	
	private int red;
	
	private int green;
	
	private int blue;

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

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public boolean isExceptThisColor() {
		return exceptThisColor;
	}

	public void setExceptThisColor(boolean exceptThisColor) {
		this.exceptThisColor = exceptThisColor;
	}
}
