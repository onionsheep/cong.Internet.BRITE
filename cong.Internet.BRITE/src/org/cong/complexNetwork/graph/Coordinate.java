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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
