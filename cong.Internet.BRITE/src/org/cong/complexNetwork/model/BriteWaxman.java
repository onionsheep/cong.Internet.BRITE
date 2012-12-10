package org.cong.complexNetwork.model;

import java.util.HashSet;
import java.util.Set;

import org.cong.complexNetwork.graph.BriteNode;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;

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
    final UndirectedGraph ug = bp.getGraph();
    final Set<Node> nodes = ug.getNodes();
    final BriteNode[] na = nodes.toArray(new BriteNode[0]);
    final Set<Node> nodesR = new HashSet<>();
    nodesR.addAll(nodes);
    for (final BriteNode u : na) {
      nodesR.remove(u);
      for (final Node v : nodesR) {
        rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
        ed = bp.EuclideanDistanceBetween(u.getBriteCoordinate(), ((BriteNode)v).getBriteCoordinate());
        p = a * Math.exp(-ed / (b * maxEd));
        if (rand <= p) {
          ug.connect(u, v);
        }
      }
    }
  }
}
