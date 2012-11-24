package org.cong.complexNetwork.graph;

/**
 * Two Edges that has the same source and targets will be equals;
 * weight and directed will not be considered when compares
 * @author cong
 * 
 */
public class Edge {
	protected Node		source;
	protected Node		target;
	protected Boolean	directed;
	protected Double	weight;

	/**
	 * Edge will be undirected and weightless by default if omitted
	 * 
	 * @param source
	 * @param target
	 * @param directed
	 * @param weight
	 */
	public Edge(Node source, Node target, Boolean directed, Double weight) {
		this.source = source;
		this.target = target;
		this.directed = directed;
		this.weight = weight;
	}

	public Edge(Node source, Node target, Double weight) {
		this(source, target, false, weight);
	}

	public Edge(Node source, Node target, Boolean directed) {
		this(source, target, directed, 0.0);
	}

	public Edge(Node source, Node target) {
		this(source, target, false, 0.0);
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Node getSource() {
		return source;
	}

	public Node getTarget() {
		return target;
	}

	public Boolean getDirected() {
		return directed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
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
		Edge other = (Edge) obj;
		if (!directed) {
			if ((source.equals(other.getSource()) && target.equals(other.getTarget()))
					|| (source.equals(other.getTarget()) && target.equals(other.getSource()))) {
				return true;
			} else {
				return false;
			}
		} else {
			if (source == null) {
				if (other.source != null)
					return false;
			} else if (!source.equals(other.source))
				return false;
			if (target == null) {
				if (other.target != null)
					return false;
			} else if (!target.equals(other.target))
				return false;
			return true;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Edge [source=");
		builder.append(source);
		builder.append(", target=");
		builder.append(target);
		builder.append(", directed=");
		builder.append(directed);
		builder.append(", weight=");
		builder.append(weight);
		builder.append("]");
		return builder.toString();
	}
}
