package org.cong.complexNetwork.graph;

public class Coordinate {
	protected Integer	x;
	protected Integer	y;

	public Coordinate(Integer x, Integer y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Double EuclideanDistanceTo(Coordinate other){
		Double dis = null;
		dis = Math.sqrt(Math.pow((other.getX() - x), 2) + Math.pow((other.getY() - y), 2));
		return dis;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

}
