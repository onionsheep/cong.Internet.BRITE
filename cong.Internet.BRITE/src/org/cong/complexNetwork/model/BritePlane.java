package org.cong.complexNetwork.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.BriteCoordinate;
import org.cong.complexNetwork.graph.Coordinate;
import org.cong.complexNetwork.graph.Node;

public class BritePlane extends Plane {

  public static Logger logger = LogManager.getLogger(BritePlane.class);
  protected int hs;
  protected int ls;

  public BritePlane(int hs, int ls) {
    super(hs * ls, hs * ls);
    this.hs = hs;
    this.ls = ls;
  }

  @Override
  public double EuclideanDistanceBetween(Coordinate u, Coordinate v) {
    double dis = 0.0;
    if((u.getClass() == BriteCoordinate.class) && (v.getClass() == BriteCoordinate.class)){
      final BriteCoordinate bu = (BriteCoordinate) u;
      final BriteCoordinate bv = (BriteCoordinate) v;
      final int xd = ((bu.getHx() + (bu.getLx() * this.ls)) - bu.getHy()) + (bu.getLy() * this.ls);
      final int yd = ((bv.getHx() + (bv.getLx() * this.ls)) - bv.getHy()) + (bv.getLy() * this.ls);

      dis = Math.sqrt((1l * xd * xd) + (1l * yd * yd));
    }else{
      logger.debug("maybe someting wrong");
      dis = super.EuclideanDistanceBetween(u, v);
    }
    return dis;
  }

  public int getHs() {
    return this.hs;
  }

  public int getLs() {
    return this.ls;
  }

  @Override
  public Node randomNode() throws Exception {
    Node node = null;
    final int hx = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.hs);
    final int hy = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.hs);
    final int lx = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.ls);
    final int ly = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.ls);
    final BriteCoordinate briteCoordinate = new BriteCoordinate(hx, hy, lx, ly);
    node = new Node(briteCoordinate);
    return node;
  }

}
