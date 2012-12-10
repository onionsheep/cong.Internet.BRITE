package org.cong.complexNetwork.graph;

public class Point {
  protected int x;
  protected int y;

  public Point(int x, int y) {
    super();
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final Point other = (Point) obj;
    if (this.x != other.x)
      return false;
    if (this.y != other.y)
      return false;
    return true;
  }

  public Double EuclideanDistanceTo(Point other) {
    Double dis = null;
    dis = Math.sqrt(Math.pow((other.getX() - this.x), 2) + Math.pow((other.getY() - this.y), 2));
    return dis;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  @Override
  public int hashCode() {
    final int prime = 10007;
    int result = 1;
    result = prime * result + this.x;
    result = prime * result + this.y;
    return result;
  }

  public Point setX(int x) {
    this.x = x;
    return this;
  }

  public Point setY(int y) {
    this.y = y;
    return this;
  }

  public long toLong() {
    final long l = this.x;
    return l << Integer.SIZE ^ this.y;
  }

}