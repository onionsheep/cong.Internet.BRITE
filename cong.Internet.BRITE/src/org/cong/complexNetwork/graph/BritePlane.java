package org.cong.complexNetwork.graph;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BritePlane extends Plane {

  protected int        hs;
  protected int        ls;
  public static Logger logger = LogManager.getLogger(BritePlane.class);

  public BritePlane(final int hs, final int ls) {
    super(hs * ls, hs * ls);
    this.hs = hs;
    this.ls = ls;
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
    final BriteCoordinate briteCoordinate = new BriteCoordinate(hx, hy, lx, ly, this.ls);
    node = new Node(briteCoordinate);
    return node;
  }

  public void setHs(final int hs) {
    this.hs = hs;
  }

  public void setLs(final int ls) {
    this.ls = ls;
  }

}
