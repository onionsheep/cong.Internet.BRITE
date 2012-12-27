package org.cong.complexNetwork.graph;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Node {

  protected Set<Node>         connectedNodes;

  protected Coordinate        coordinate;

  // protected int degree;
  protected int               inDegree;
  protected int               outDegree;
  protected long              id;
  protected double            weight;
  private static final double defaultWeight = 0;
  private static long         idCounter     = -1;

  public static Logger        logger        = LogManager.getLogger(Node.class);

  public Node() {
    this(idCounter--);
  }

  public Node(final Coordinate coordinate) {
    this(coordinate.toLong(), coordinate);
  }

  /**
   * weight default = 0, treat as a weightless Node
   * 
   * @param id
   */
  public Node(final long id) {
    this(id, defaultWeight, null);
  }

  public Node(final long id, final Coordinate coordinate) {
    this(id, defaultWeight, coordinate);
  }

  /**
   * id is a String that can identify a Node. one Node, one id. Warn:Nothing
   * will happpen if you create two Node with the same id
   * 
   * @param id
   * @param weight
   */
  public Node(final long id, final double weight, final Coordinate coordinate) {
    this.id = id;
    this.weight = weight;
    this.connectedNodes = new HashSet<>();
    // this.degree = 0;
    this.inDegree = 0;
    this.outDegree = 0;
    this.coordinate = coordinate;
  }

  public boolean connectNode(final Node node) {
    if (this.connectedNodes.add(node)) {
      this.outDegree += 1;
      node.inDegree += 1;
      // this.degree += 1;
      return true;
    } else {
      return false;
    }
  }

  public boolean disConnectNode(final Node node) {
    final boolean result = this.connectedNodes.remove(node);
    if (result) {
      this.outDegree -= 1;
      node.inDegree -= 1;
      // this.degree -= 1;
    }
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final Node other = (Node) obj;
    if (this.id != other.id) {
      return false;
    }
    return true;
  }

  public Set<Node> getConnectedNodes() {
    return this.connectedNodes;
  }

  public Coordinate getCoordinate() {
    return this.coordinate;
  }

  public int getDegree() {
    return this.inDegree + this.outDegree;
  }

  public long getId() {
    return this.id;
  }

  public double getWeight() {
    return this.weight;
  }

  public int getInDegree() {
    return inDegree;
  }

  public int getOutDegree() {
    return outDegree;
  }

  @Override
  public int hashCode() {
    final int prime = 1009;
    int result = 1;
    result = prime + (int) (this.id ^ (this.id >>> 32));
    return result;
  }

  public void setWeight(final double weight) {
    this.weight = weight;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("Node [id=");
    builder.append(this.id);
    builder.append(", degree=");
    builder.append(this.inDegree + this.outDegree);
    builder.append("]");
    return builder.toString();
  }

}
