package org.cong.complexNetwork.graph;

public class BriteNode extends Node {
  protected BriteCoordinate briteCoordinate;

  public BriteNode(BriteCoordinate briteCoordinate) {
    this(briteCoordinate.toLong(), briteCoordinate);
  }

  public BriteNode(long id, BriteCoordinate briteCoordinate) {
    super(id);
    this.briteCoordinate = briteCoordinate;
    super.coordinate = briteCoordinate;
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
    if (briteCoordinate == null) {
      if (other.briteCoordinate != null)
        return false;
    } else if (!briteCoordinate.equals(other.briteCoordinate))
      return false;
    return true;
  }

  public BriteCoordinate getBriteCoordinate() {
    return briteCoordinate;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((briteCoordinate == null) ? 0 : briteCoordinate.hashCode());
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("BriteNode [");;
    builder.append("degree=");
    builder.append(degree);
    builder.append(", id=");
    builder.append(id);
    builder.append("]");
    return builder.toString();
  }

}
