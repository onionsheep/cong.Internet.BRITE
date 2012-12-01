package org.cong.complexNetwork.graph;

public class Coordinate {
	protected int	x;
	protected int	y;

	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Double EuclideanDistanceTo(Coordinate other){
		Double dis = null;
		dis = Math.sqrt(Math.pow((other.getX() - x), 2) + Math.pow((other.getY() - y), 2));
		return dis;
	}

	public int getX() {
		return x;
	}

	public Coordinate setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Coordinate setY(int y) {
		this.y = y;
		return this;
	}

}
