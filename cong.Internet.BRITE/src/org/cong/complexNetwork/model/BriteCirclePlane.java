package org.cong.complexNetwork.model;

import org.cong.complexNetwork.graph.BritePoint;
import org.cong.complexNetwork.graph.Node;

public class BriteCirclePlane extends BritePlane {

  protected double radius;

  public BriteCirclePlane(int hs, int ls) {
    this(hs, ls, ls * 0.5);
  }

  public BriteCirclePlane(int hs, int ls, double radius) {
    super(hs, ls);
    if ((2 * radius) > ls) {
      radius = ls / 2.0;
    }
    this.radius = radius;
  }

  @Override
  public Node randomNode() throws Exception {
    Node node = null;
    boolean outCircle = true;
    while (outCircle) {
      node = super.randomNode();
      final BritePoint britePoint = (BritePoint)node.getPoint();
      int x = britePoint.getLx();
      int y = britePoint.getLy();
      x = x - (this.ls / 2);
      y = y - (this.ls / 2);
      if (((x * x) + (y * y)) < (this.radius * this.radius)) {
        outCircle = false;
      }
    }
    return node;
  }

}
