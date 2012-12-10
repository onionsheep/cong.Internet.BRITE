package org.cong.complexNetwork.model;

import java.util.HashSet;
import java.util.Set;

import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;

public class BriteWaxman {
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
  public static void generateEdges(BritePlane bp, double a, double b) throws Exception {
    double rand = 0.0;
    double p = 0; // 概率
    double ed = 0; // 欧氏距离
    final double maxEd = bp.MaxEuclideanDistance();// 最大欧氏距离
    final BriteGraph bg = bp.getBriteGraph();
    final Set<BriteNode> nodes = bg.getBriteNodes();
    final BriteNode[] na = nodes.toArray(new BriteNode[0]);
    final Set<BriteNode> nodesR = new HashSet<>();
    nodesR.addAll(nodes);
    for (final BriteNode u : na) {
      nodesR.remove(u);
      for (final BriteNode v : nodesR) {
        rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
        ed = bp.EuclideanDistanceBetween(u.getBriteCoordinate(), v.getBriteCoordinate());
        p = a * Math.exp(-ed / (b * maxEd));
        if (rand <= p) {
          bg.connect(u, v);
        }
      }
    }
  }
}
