package org.cong.complexNetwork.graph;

import java.util.HashSet;
import java.util.Set;

public class Node {
	protected String		id;
	protected Double		weight;
	protected Integer		degree;
	protected Set<Node>	connectedNodes;

	/**
	 * id is a String that can identify a Node. one Node, one id. Warn:Nothing
	 * will happpen if you create two Node with the same id
	 * 
	 * @param id
	 * @param weight
	 */
	public Node(String id, Double weight) {
		this.id = id;
		this.weight = weight;
		this.connectedNodes = new HashSet<>();
		this.degree = 0;
	}

	/**
	 * weight default = 0, treat as a weightless Node
	 * 
	 * @param id
	 */
	public Node(String id) {
		this(id, 0.0);
	}

	public boolean connectNode(Node node) {
		boolean result = this.connectedNodes.add(node);
		if (result) {
			this.degree += 1;
		}
		return result;
	}

	public boolean disconnectNode(Node node) {
		boolean result = this.connectedNodes.remove(node);
		if (result) {
			this.degree -= 1;
		}
		return result;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public Set<Node> getConnectedNodes() {
		return connectedNodes;
	}

	public Integer getDegree() {
		return degree;
	}

	@Override
	public int hashCode() {
		final int prime = 1009;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Node other = (Node) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Node [id=");
		builder.append(id);
		builder.append(", weight=");
		builder.append(weight);
		builder.append("]");
		return builder.toString();
	}
}
