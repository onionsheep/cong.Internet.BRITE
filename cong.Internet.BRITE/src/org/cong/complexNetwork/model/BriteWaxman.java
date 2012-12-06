package org.cong.complexNetwork.model;

import java.util.HashSet;
import java.util.Set;

import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;

public class BriteWaxman {

  public static void generateEdges(BritePlane britePlane, double alpha, double beta) {
    double rand = 0.0;
    double probability = 0.0;
    double euclideanDistance = 0;
    double maxEuclideanDistance = britePlane.MaxEuclideanDistance();
    BriteGraph graph = britePlane.getBriteGraph();
    Set<BriteNode> nodes = graph.getBriteNodes();
    BriteNode[] nodeArray = nodes.toArray(new BriteNode[0]);
    Set<BriteNode> nodesRemain = new HashSet<>();
    nodesRemain.addAll(nodes);
    for (BriteNode u : nodeArray) {
      nodesRemain.remove(u);
      for (BriteNode v : nodesRemain) {
        rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
        euclideanDistance = britePlane.EuclideanDistanceBetween(u.getBriteCoordinate(),
                                                                v.getBriteCoordinate());
        probability = probability(alpha, beta, euclideanDistance, maxEuclideanDistance);
        if (rand <= probability) {
          graph.connect(u, v);
        }
      }
    }
  }

  public static double probability(double alpha,
                                   double beta,
                                   double euclideanDistance,
                                   double maxEuclideanDistance) {
    double probability = 0;
    probability = alpha * Math.exp(-euclideanDistance / (beta * maxEuclideanDistance));
    return probability;
  }
}
