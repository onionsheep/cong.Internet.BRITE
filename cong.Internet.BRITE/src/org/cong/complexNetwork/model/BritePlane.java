package org.cong.complexNetwork.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.BritePoint;
import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.Point;
import org.cong.complexNetwork.graph.Node;

public class BritePlane extends Plane {

  public static Logger logger = LogManager.getLogger(BritePlane.class);
  protected int hs;
  protected int ls;

  // protected BriteGraph briteGraph;

  public BritePlane(int hs, int ls) {
    super(hs * ls, hs * ls);
    this.hs = hs;
    this.ls = ls;
    this.ug = new BriteGraph();
  }

  @Override
  public double EuclideanDistanceBetween(Point u, Point v) {
    double dis = 0.0;
    final BritePoint bu = (BritePoint) u;
    final BritePoint bv = (BritePoint) v;
    final int xd = ((bu.getHx() + (bu.getLx() * this.ls)) - bu.getHy()) + (bu.getLy() * this.ls);
    final int yd = ((bv.getHx() + (bv.getLx() * this.ls)) - bv.getHy()) + (bv.getLy() * this.ls);
    dis = Math.sqrt((xd * xd) + (yd * yd));
    return dis;
  }

  public int getHs() {
    return this.hs;
  }

  public int getLs() {
    return this.ls;
  }

  //  // TODO: write a better function to calculate the max distance
  //
  //  @Override
  //  public double MaxEuclideanDistance() {
  //    double maxDis = 0.0;
  //    final Set<Node> nodes = this.ug.getNodes();
  //    final Set<Node> nodesRemain = new HashSet<>();
  //    nodesRemain.addAll(nodes);
  //    for (final Node node : nodes) {
  //      nodesRemain.remove(node);
  //      for (final Node node1 : nodesRemain) {
  //        final BriteCoordinate bc0 = (BriteCoordinate) node.getCoordinate();
  //        final BriteCoordinate bc1 = (BriteCoordinate) node1.getCoordinate();
  //        final double dis = this.EuclideanDistanceBetween(bc0, bc1);
  //        if (maxDis < dis) {
  //          maxDis = dis;
  //        }
  //      }
  //    }
  //    return maxDis;
  //  }

  @Override
  public Node randomNode() throws Exception {
    Node node = null;
    final int hx = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.hs);
    final int hy = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.hs);
    final int lx = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.ls);
    final int ly = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.ls);
    final BritePoint britePoint = new BritePoint(hx, hy, lx, ly);
    node = new Node(britePoint);
    return node;
  }

}
