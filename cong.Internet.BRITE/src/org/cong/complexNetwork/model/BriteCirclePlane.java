package org.cong.complexNetwork.model;

import org.cong.complexNetwork.graph.BriteCoordinate;
import org.cong.complexNetwork.graph.Node;

public class BriteCirclePlane extends BritePlane {

  protected double radius;

  public BriteCirclePlane(final int hs, final int ls) {
    this(hs, ls, ls * 0.5);
  }

  public BriteCirclePlane(final int hs, final int ls, double radius) {
    super(hs, ls);
    if ((2 * radius) > ls) {
      radius = ls / 2.0;
    }
    this.radius = radius;
  }

  public double getRadius() {
    return this.radius;
  }

  @Override
  public Node randomNode() throws Exception {
    Node node = null;
    boolean outCircle = true;
    while (outCircle) {
      node = super.randomNode();
      final BriteCoordinate briteCoordinate = (BriteCoordinate) node.getCoordinate();
      int x = briteCoordinate.getLx();
      int y = briteCoordinate.getLy();
      x = x - (this.ls / 2);
      y = y - (this.ls / 2);
      if (((x * x) + (y * y)) < (this.radius * this.radius)) {
        outCircle = false;
      }
    }
    return node;
  }

  public void setRadius(final double radius) {
    this.radius = radius;
  }

}
