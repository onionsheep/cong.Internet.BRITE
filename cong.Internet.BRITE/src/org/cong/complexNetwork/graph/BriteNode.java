package org.cong.complexNetwork.graph;

import java.util.Set;

public class BriteNode extends Node {
	protected BriteCoordinate	coordinate;

	public BriteNode(String id, BriteCoordinate coordinate) {
		super(id);
		this.coordinate = coordinate;
	}

	public BriteCoordinate getCoordinate() {
		return coordinate;
	}

	public Set<Node> getConnectedNodes() {
		return connectedNodes;
	}

	public Integer getDegree() {
		return degree;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BriteNode other = (BriteNode) obj;
		if (coordinate == null) {
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BriteNode [coordinate=");
		builder.append(coordinate);
		builder.append(", connectedNodes=");
		builder.append(connectedNodes);
		builder.append(", degree=");
		builder.append(degree);
		builder.append(", id=");
		builder.append(id);
		builder.append(", weight=");
		builder.append(weight);
		builder.append("]");
		return builder.toString();
	}

}
