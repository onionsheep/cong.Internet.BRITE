package org.cong.complexNetwork.graph;

/**
 * Two Edges that has the same source and targets will be equals; weight and
 * directed will not be considered when compares
 * 
 * @author cong
 * 
 */
public class Edge {
  protected Node source;
  protected Node target;
  protected boolean directed;
  protected int weight;

  public Edge(Node source, Node target) throws Exception {
    this(source, target, false, 1);
  }

  public Edge(Node source, Node target, boolean directed) throws Exception {
    this(source, target, directed, 1);
  }

  /**
   * Edge will be undirected and weightless by default if omitted
   * 
   * @param source
   * @param target
   * @param directed
   * @param weight
   * @throws Exception
   */
  public Edge(Node source, Node target, boolean directed, int weight) throws Exception {
    if ((source != null) && (target != null)) {
      this.source = source;
      this.target = target;
      this.directed = directed;
      this.weight = weight;
      source.connectNode(target);
      target.connectNode(source);
    } else {
      throw new Exception("source and target can not be null");
    }
  }

  public Edge(Node source, Node target, int weight) throws Exception {
    this(source, target, false, weight);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final Edge other = (Edge) obj;
    if (!this.directed) {
      if ((this.source.equals(other.getSource()) && this.target.equals(other.getTarget()))
          || (this.source.equals(other.getTarget()) && this.target.equals(other.getSource()))) {
        return true;
      } else {
        return false;
      }
    } else {
      if (this.source == null) {
        if (other.source != null) {
          return false;
        }
      } else if (!this.source.equals(other.source)) {
        return false;
      }
      if (this.target == null) {
        if (other.target != null) {
          return false;
        }
      } else if (!this.target.equals(other.target)) {
        return false;
      }
      return true;
    }
  }

  public boolean getDirected() {
    return this.directed;
  }

  public Node getSource() {
    return this.source;
  }

  public Node getTarget() {
    return this.target;
  }

  public int getWeight() {
    return this.weight;
  }

  @Override
  public int hashCode() {
    final int prime = 10007;
    int result = 1;
    result = (prime * result) + ((this.source == null) ? 0 : this.source.hashCode());
    result = (prime * result) + ((this.target == null) ? 0 : this.target.hashCode());
    return result;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("Edge [source=");
    builder.append(this.source);
    builder.append(", target=");
    builder.append(this.target);
    builder.append(", directed=");
    builder.append(this.directed);
    builder.append(", weight=");
    builder.append(this.weight);
    builder.append("]");
    return builder.toString();
  }
}
