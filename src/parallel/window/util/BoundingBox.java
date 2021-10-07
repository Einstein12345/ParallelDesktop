package parallel.window.util;

import java.awt.Dimension;

public class BoundingBox {
	private long x, y, width, height;

	public BoundingBox(long x, long y, long width, long height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public BoundingBox(Dimension d) {
		this.width = d.width;
		this.height = d.height;
		this.x = 0;
		this.y = 0;
	}

	public void setX(long x) {
		this.x = x;
	}

	public void setY(long y) {
		this.y = y;
	}

	public void setWidth(long width) {
		this.width = width;
	}

	public void setHeight(long height) {
		this.height = height;
	}

	public long getX() {
		return x;
	}

	public long getY() {
		return y;
	}

	public long getWidth() {
		return width;
	}

	public long getHeight() {
		return height;
	}

	public boolean doesIntersect(BoundingBox b) {
		// x greater than b, but less than b.x+width;
		// y greater than b, but less than b.y+height;

		return false;
	}

}
