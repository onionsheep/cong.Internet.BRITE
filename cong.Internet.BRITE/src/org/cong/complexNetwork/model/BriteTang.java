package org.cong.complexNetwork.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;

public class BriteTang extends Tang {
  private static boolean addAndConnectNewBriteNode(BriteGraph briteGraph,
                                                   Set<BriteNode> BriteNodes,
                                                   Map<BriteNode, Double> briteNodeProbability,
                                                   BriteNode newBriteNode) {
    double probability;
    double rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
    probability = 0.0;
    boolean result = false;
    for (BriteNode oldBriteNode : BriteNodes) {
      probability = briteNodeProbability.get(oldBriteNode);
      if (rand <= probability) {
        result = briteGraph.connect(newBriteNode, oldBriteNode);
        break;
      }
    }
    return result;
  }

  private static Map<BriteNode, Double> generateBriteNodesProbability(double epsilon,
                                                                      Set<BriteNode> briteNodes) {
    double probability;
    Map<BriteNode, Double> BriteNodeProbability = new HashMap<>();
    probability = 0.0;
    for (BriteNode oldBriteNode : briteNodes) {
      probability += probability(oldBriteNode, briteNodes, epsilon);
      BriteNodeProbability.put(oldBriteNode, probability);
    }
    return BriteNodeProbability;
  }

  public static void generateEdges(BritePlane britePlane,
                                   int oneBriteNodeEdge,
                                   int briteNodeCount,
                                   double epsilon) throws Exception {

    BriteGraph BriteGraph = britePlane.getBriteGraph();
    Set<BriteNode> BriteNodes = BriteGraph.getBriteNodes();
    for (int i = 0; i < briteNodeCount; i++) {
      // 计算节点的概率，并存储在0到1的区间上，只记录上限
      Map<BriteNode, Double> BriteNodeProbability = generateBriteNodesProbability(epsilon,
                                                                                  BriteNodes);
      // 添加oneBriteNodeEdge-1条边
      int m = oneBriteNodeEdge - 1;
      for (int j = 0; j < m; j++) {
        BriteNode u = getBriteNodeByProbablity(BriteNodes, BriteNodeProbability);
        BriteNode v = getBriteNodeByProbablity(BriteNodes, BriteNodeProbability);
        boolean success = BriteGraph.connect(u, v);
        if (!success) {
          j -= 1;
        }
      }
      // 新节点
      BriteNode newBriteNode = britePlane.randomNodeNoDuplication();
      // 在原来的图中找一个节点，与新节点相连
      boolean result = addAndConnectNewBriteNode(BriteGraph,
                                                 BriteNodes,
                                                 BriteNodeProbability,
                                                 newBriteNode);
      if (result) {
        BriteGraph.getBriteNodes().add(newBriteNode);
      } else {
        i -= 1;
      }
    }
  }

  private static BriteNode getBriteNodeByProbablity(Set<BriteNode> briteNodes,
                                                    Map<BriteNode, Double> briteNodeProbability) {
    BriteNode n = null;
    double rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
    for (BriteNode briteNode : briteNodes) {
      double probability = briteNodeProbability.get(briteNode);
      if (rand <= probability) {
        n = briteNode;
        break;
      }
    }
    return n;
  }

  public static double probability(BriteNode i, Set<BriteNode> BriteNodes, double epsilon) {
    double probability = 0;
    double sumOfDegree = 0.0;
    for (BriteNode BriteNode : BriteNodes) {
      sumOfDegree += Math.pow(BriteNode.getDegree(), 1 + epsilon);
    }
    probability = Math.pow(i.getDegree(), 1 + epsilon) / sumOfDegree;
    return probability;
  }

}
