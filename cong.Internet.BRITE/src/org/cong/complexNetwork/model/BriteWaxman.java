package org.cong.complexNetwork.model;

import java.util.HashSet;
import java.util.Set;

import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.Plane;

public class BriteWaxman {
  protected double alpha;
  protected double beta;
  protected Plane  plane;

  /**
   * 
   * @param bp
   *          平面
   * @param a
   *          alpha
   * @param b
   *          beta
   * @throws Exception
   */
  public static void generateEdges(final BriteWaxman bw) throws Exception {
    final Plane plane = bw.getPlane();
    final double alpha = bw.getAlpha();
    final double beta = bw.getBeta();
    double rand = 0.0;
    double p = 0; // 概率
    double ed = 0; // 欧氏距离
    final double maxEdb = beta * plane.MaxEuclideanDistance();// 最大欧氏距离
    final Graph ug = plane.getGraph();
    final Set<Node> nodes = ug.getNodes();
    final Node[] na = nodes.toArray(new Node[0]);
    final Set<Node> nodesR = new HashSet<>();
    nodesR.addAll(nodes);
    for (final Node u : na) {
      nodesR.remove(u);
      for (final Node v : nodesR) {
        rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
        ed = Plane.EuclideanDistanceBetween(u.getCoordinate(), v.getCoordinate());
        p = alpha * Math.exp(-ed / maxEdb);
        if (rand <= p) {
          ug.connect(u, v);
        }
      }
    }
  }

  public BriteWaxman(final Plane plane, final double alpha, final double beta) {
    super();
    this.plane = plane;
    this.alpha = alpha;
    this.beta = beta;
  }

  public double getAlpha() {
    return this.alpha;
  }

  public double getBeta() {
    return this.beta;
  }

  public Plane getPlane() {
    return this.plane;
  }

  public void setAlpha(final double alpha) {
    this.alpha = alpha;
  }

  public void setBeta(final double beta) {
    this.beta = beta;
  }

  public void setPlane(final Plane plane) {
    this.plane = plane;
  }
}
