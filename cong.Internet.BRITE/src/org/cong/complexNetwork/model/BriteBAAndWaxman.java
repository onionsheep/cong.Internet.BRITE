package org.cong.complexNetwork.model;

import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;

public class BriteBAAndWaxman extends BriteBA {

  public static void generateEdges(BritePlane britePlane,
                                   int oneNodeEdge,
                                   int nodeCount,
                                   double alpha,
                                   double beta) throws Exception {
    final BriteGraph graph = britePlane.getBriteGraph();

    for (int i = 0; i < nodeCount; i++) {
      final BriteNode[] nodeArray = graph.getBriteNodes().toArray(new BriteNode[0]);
      final BriteNode newNode = britePlane.randomNodeNoDuplication();

      // 计算节点的概率，并存储在0到1的区间上，只记录上限
      final double[] probabilities = probability(nodeArray);

      // 添加oneNodeEdge条边，这oneNodeEdge添加边的时候不重新计算原来节点的度，概率
      addEdges(oneNodeEdge, graph, nodeArray, probabilities, newNode);

      graph.getNodes().add(newNode);
    }
  }

  public static double probability(BriteNode newNode,
                                   BriteNode oldNode,
                                   BritePlane britePlane,
                                   double alpha,
                                   double beta) {
    double probability = 0.0;
    double probabilityW = 0.0;
    final BriteNode[] nodeArray = britePlane.getBriteGraph()
                                            .getBriteNodes()
                                            .toArray(new BriteNode[0]);
    final double maxEuclideanDistance = britePlane.MaxEuclideanDistance();
    double sumOfDegree = 0.0;
    for (final BriteNode node : nodeArray) {
      probabilityW = productOfProbability(newNode,
                                          node,
                                          britePlane,
                                          alpha,
                                          beta,
                                          maxEuclideanDistance);
      sumOfDegree += node.getDegree() * probabilityW;
    }
    probability = oldNode.getDegree()
                  * productOfProbability(newNode,
                                         oldNode,
                                         britePlane,
                                         alpha,
                                         beta,
                                         maxEuclideanDistance) / sumOfDegree;
    return probability;
  }

  private static double productOfProbability(BriteNode newNode,
                                             BriteNode oldNode,
                                             BritePlane britePlane,
                                             double alpha,
                                             double beta,
                                             double maxEuclideanDistance) {
    double probabilityW;
    double euclideanDistance;
    euclideanDistance = britePlane.EuclideanDistanceBetween(newNode.getBriteCoordinate(),
                                                            oldNode.getBriteCoordinate());
    probabilityW = alpha * Math.exp(-euclideanDistance / (beta * maxEuclideanDistance));
    return probabilityW;
  }
}
