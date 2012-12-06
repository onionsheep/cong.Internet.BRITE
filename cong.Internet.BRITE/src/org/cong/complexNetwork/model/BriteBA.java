package org.cong.complexNetwork.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;

public class BriteBA extends BA {

  public static Logger logger = LogManager.getLogger(BriteBA.class);

  public static void generateEdges(BritePlane britePlane, int oneNodeEdge, int nodeCount)
      throws Exception {
    double probability = 0.0;
    BriteGraph graph = britePlane.getBriteGraph();

    for (int i = 0; i < nodeCount; i++) {
      BriteNode[] nodeArray = graph.getBriteNodes().toArray(new BriteNode[0]);
      BriteNode newNode = britePlane.randomNodeNoDuplication();

      // 计算节点的概率，并存储在0到1的区间上，只记录上限
      probability = 0.0;
      double[] probabilities = new double[nodeArray.length];
      for (int j = 0; j < nodeArray.length; j++) {
        probability += BriteBA.probability(nodeArray[j], nodeArray);
        probabilities[j] = probability;
      }

      // 添加oneNodeEdge条边，这oneNodeEdge添加边的时候不重新计算原来节点的度，概率
      addEdges(oneNodeEdge, graph, nodeArray, probabilities, newNode);
      graph.getNodes().add(newNode);
    }
  }

  public static double probability(BriteNode i, BriteNode[] nodeArray) {
    double probability = 0;
    int sumOfDegree = 0;
    for (BriteNode node : nodeArray) {
      sumOfDegree += node.getDegree();
    }
    probability = 1.0 * i.getDegree() / sumOfDegree;
    return probability;
  }

}
