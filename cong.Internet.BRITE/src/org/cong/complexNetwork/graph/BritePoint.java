package org.cong.complexNetwork.graph;

public class BritePoint extends Point {
  protected Integer hx;
  protected Integer hy;
  protected Integer lx;
  protected Integer ly;

  private static int halfSizeOfInteger = Integer.SIZE / 2;
  private static int maxInt = Integer.MAX_VALUE / 2;

  public BritePoint(Integer hx, Integer hy, Integer lx, Integer ly) throws Exception {
    super(hx << halfSizeOfInteger ^ lx, hy << halfSizeOfInteger ^ ly);
    this.hx = hx;
    this.hy = hy;
    this.lx = lx;
    this.ly = ly;
    if (hx >= maxInt || hy >= maxInt || lx >= maxInt || lx >= maxInt) {
      throw new Exception("坐标过大");
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final BritePoint other = (BritePoint) obj;
    if (this.toLong() == other.toLong()) {
      return true;
    } else {
      return false;
    }
  }

  public Integer getHx() {
    return this.hx;
  }

  public Integer getHy() {
    return this.hy;
  }

  public Integer getLx() {
    return this.lx;
  }

  public Integer getLy() {
    return this.ly;
  }

  @Override
  public int hashCode() {
    final int prime = 10007;
    int result = super.hashCode();
    result = prime * result + ((this.hx == null) ? 0 : this.hx.hashCode());
    result = prime * result + ((this.hy == null) ? 0 : this.hy.hashCode());
    result = prime * result + ((this.lx == null) ? 0 : this.lx.hashCode());
    result = prime * result + ((this.ly == null) ? 0 : this.ly.hashCode());
    return result;
  }

  public void setHx(Integer hx) {
    this.hx = hx;
  }

  public void setHy(Integer hy) {
    this.hy = hy;
  }

  public void setLx(Integer lx) {
    this.lx = lx;
  }

  public void setLy(Integer ly) {
    this.ly = ly;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("BriteCoordinate [");
    builder.append(this.hx);
    builder.append(".");
    builder.append(this.hy);
    builder.append(", ");
    builder.append(this.lx);
    builder.append(".");
    builder.append(this.ly);
    builder.append("]");
    return builder.toString();
  }
}
