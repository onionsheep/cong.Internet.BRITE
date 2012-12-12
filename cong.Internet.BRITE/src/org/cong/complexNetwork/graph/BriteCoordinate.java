package org.cong.complexNetwork.graph;

public class BriteCoordinate extends Coordinate {
  protected int hx;
  protected int hy;
  protected int lx;
  protected int ly;
  protected int ls;

  public BriteCoordinate(final int hx, final int hy, final int lx, final int ly, final int ls){
    super(hx+(ls*ls), hy+(ly*ls));
    this.hx = hx;
    this.hy = hy;
    this.lx = lx;
    this.ly = ly;
    this.ls = ls;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final BriteCoordinate other = (BriteCoordinate) obj;
    if (this.hx != other.hx) {
      return false;
    }
    if (this.hy != other.hy) {
      return false;
    }
    if (this.ls != other.ls) {
      return false;
    }
    if (this.lx != other.lx) {
      return false;
    }
    if (this.ly != other.ly) {
      return false;
    }
    return true;
  }

  public int getHx() {
    return this.hx;
  }

  public int getHy() {
    return this.hy;
  }

  public int getLs() {
    return this.ls;
  }

  public int getLx() {
    return this.lx;
  }

  public int getLy() {
    return this.ly;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = (prime * result) + this.hx;
    result = (prime * result) + this.hy;
    result = (prime * result) + this.ls;
    result = (prime * result) + this.lx;
    result = (prime * result) + this.ly;
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("BriteCoordinate [");
    builder.append(this.hx);
    builder.append(",");
    builder.append(this.hy);
    builder.append("; ");
    builder.append(this.lx);
    builder.append(",");
    builder.append(this.ly);
    builder.append("]");
    return builder.toString();
  }
}
