package org.cong.complexNetwork.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;

public class BriteTang extends Tang {
  private static boolean addAndConnectNewBriteNode(UndirectedGraph briteGraph,
                                                   Set<Node> BriteNodes,
                                                   Map<Node, Double> briteNodeProbability,
                                                   Node newBriteNode) throws Exception {
    double probability;
    final double rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
    probability = 0.0;
    boolean result = false;
    for (final Node oldBriteNode : BriteNodes) {
      probability = briteNodeProbability.get(oldBriteNode);
      if (rand <= probability) {
        result = briteGraph.connect(newBriteNode, oldBriteNode);
        break;
      }
    }
    return result;
  }

  private static Map<Node, Double> generateBriteNodesProbability(double epsilon,
                                                                 Set<Node> nodes) {
    double probability;
    final Map<Node, Double> BriteNodeProbability = new HashMap<>();
    probability = 0.0;
    for (final Node oldNode : nodes) {
      probability += probability(oldNode, nodes, epsilon);
      BriteNodeProbability.put(oldNode, probability);
    }
    return BriteNodeProbability;
  }

  public static void generateEdges(BritePlane britePlane,
                                   int oneBriteNodeEdge,
                                   int briteNodeCount,
                                   double epsilon) throws Exception {

    final UndirectedGraph BriteGraph = britePlane.getGraph();
    final Set<Node> nodes = BriteGraph.getNodes();
    for (int i = 0; i < briteNodeCount; i++) {
      // 计算节点的概率，并存储在0到1的区间上，只记录上限
      final Map<Node, Double> BriteNodeProbability = generateBriteNodesProbability(epsilon,
                                                                                   nodes);
      // 添加oneBriteNodeEdge-1条边
      final int m = oneBriteNodeEdge - 1;
      for (int j = 0; j < m; j++) {
        final Node u = getBriteNodeByProbablity(nodes, BriteNodeProbability);
        final Node v = getBriteNodeByProbablity(nodes, BriteNodeProbability);
        final boolean success = BriteGraph.connect(u, v);
        if (!success) {
          j -= 1;
        }
      }
      // 新节点
      final Node newBriteNode = britePlane.randomNodeNoDuplication();
      // 在原来的图中找一个节点，与新节点相连
      final boolean result = addAndConnectNewBriteNode(BriteGraph,
                                                       nodes,
                                                       BriteNodeProbability,
                                                       newBriteNode);
      if (result) {
        BriteGraph.getNodes().add(newBriteNode);
      } else {
        i -= 1;
      }
    }
  }

  private static Node getBriteNodeByProbablity(Set<Node> briteNodes,
                                               Map<Node, Double> briteNodeProbability) {
    Node n = null;
    final double rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
    for (final Node node : briteNodes) {
      final double probability = briteNodeProbability.get(node);
      if (rand <= probability) {
        n = node;
        break;
      }
    }
    return n;
  }

  public static double probability(Node i, Set<Node> BriteNodes, double epsilon) {
    double probability = 0;
    double sumOfDegree = 0.0;
    for (final Node BriteNode : BriteNodes) {
      sumOfDegree += Math.pow(BriteNode.getDegree(), 1 + epsilon);
    }
    probability = Math.pow(i.getDegree(), 1 + epsilon) / sumOfDegree;
    return probability;
  }

}
