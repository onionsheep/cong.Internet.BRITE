package org.cong.complexNetwork.graph;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Node {

  public static Logger logger = LogManager.getLogger(Node.class);

  protected long id;
  protected double weight;
  protected int degree;
  protected Set<Node> connectedNodes;
  protected Coordinate coordinate;

  private static final double defaultWeight = 0;

  /**
   * weight default = 0, treat as a weightless Node
   * 
   * @param id
   */
  public Node(long id) {
    this(id, defaultWeight, null);
  }

  public Node(long id, Coordinate coordinate) {
    this(id, defaultWeight, coordinate);
  }

  /**
   * id is a String that can identify a Node. one Node, one id. Warn:Nothing
   * will happpen if you create two Node with the same id
   * 
   * @param id
   * @param weight
   */
  public Node(long id, double weight, Coordinate coordinate) {
    this.id = id;
    this.weight = weight;
    this.connectedNodes = new HashSet<>();
    this.degree = 0;
    this.coordinate = coordinate;
  }

  public boolean connectNode(Node node) {
    boolean result = this.connectedNodes.add(node);
    if (result) {
      this.degree += 1;
    }
    return result;
  }

  public boolean disConnectNode(Node node) {
    boolean result = this.connectedNodes.remove(node);
    if (result) {
      this.degree -= 1;
    }
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
    if (id != other.id)
      return false;
    return true;
  }

  public Set<Node> getConnectedNodes() {
    return connectedNodes;
  }

  public Coordinate getCoordinate() {
    return coordinate;
  }

  public int getDegree() {
    return degree;
  }

  public long getId() {
    return id;
  }

  public double getWeight() {
    return weight;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    return result;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Node [id=");
    builder.append(id);
    builder.append(", degree=");
    builder.append(degree);
    builder.append("]");
    return builder.toString();
  }

}
