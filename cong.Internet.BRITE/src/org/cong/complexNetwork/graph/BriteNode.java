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
    if (this.getClass() != obj.getClass())
      return false;
    final BriteNode other = (BriteNode) obj;
    if (this.briteCoordinate == null) {
      if (other.briteCoordinate != null)
        return false;
    } else if (!this.briteCoordinate.equals(other.briteCoordinate))
      return false;
    return true;
  }

  public BriteCoordinate getBriteCoordinate() {
    return this.briteCoordinate;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime
             * result
             + ((this.briteCoordinate == null) ? 0 : this.briteCoordinate.hashCode());
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("BriteNode [");
    ;
    builder.append("degree=");
    builder.append(this.degree);
    builder.append(", id=");
    builder.append(this.id);
    builder.append("]");
    return builder.toString();
  }

}
