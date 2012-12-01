package org.cong.complexNetwork.graph;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Node {
	
	public static Logger	logger	= LogManager.getLogger(Node.class);
	
	protected String		id;
	protected double		weight;
	protected int		degree;
	protected Set<Node>	connectedNodes;
	protected Coordinate coordinate; 

	/**
	 * id is a String that can identify a Node. one Node, one id. Warn:Nothing
	 * will happpen if you create two Node with the same id
	 * 
	 * @param id
	 * @param weight
	 */
	public Node(String id, double weight, Coordinate coordinate) {
		this.id = id;
		this.weight = weight;
		this.connectedNodes = new HashSet<>();
		this.degree = 0;
		this.coordinate = coordinate;
	}

	public Node(String id, Coordinate coordinate){
		this(id, 0.0, coordinate);
	}
	
	/**
	 * weight default = 0, treat as a weightless Node
	 * 
	 * @param id
	 */
	public Node(String id) {
		this(id, 0.0, null);
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

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public Set<Node> getConnectedNodes() {
		return connectedNodes;
	}

	public int getDegree() {
		return degree;
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}

	@Override
	public int hashCode() {
		final int prime = 10007;
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
