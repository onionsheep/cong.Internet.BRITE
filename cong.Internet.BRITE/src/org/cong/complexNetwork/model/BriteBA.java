package org.cong.complexNetwork.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BriteBA extends BA {

  public static Logger logger = LogManager.getLogger(BriteBA.class);

  // public static void generateEdges(BritePlane britePlane, int oneNodeEdge,
  // int nodeCount)
  // throws Exception {
  // BriteGraph graph = britePlane.getBriteGraph();
  // for (int i = 0; i < nodeCount; i++) {
  // BriteNode[] nodeArray = graph.getBriteNodes().toArray(new BriteNode[0]);
  // BriteNode newNode = britePlane.randomNodeNoDuplication();
  // // 计算节点的概率，并存储在0到1的区间上，只记录上限
  // double[] probabilities = probability(nodeArray);
  // // 添加oneNodeEdge条边，这oneNodeEdge添加边的时候不重新计算原来节点的度，概率
  // addEdges(oneNodeEdge, graph, nodeArray, probabilities, newNode);
  // graph.getNodes().add(newNode);
  // }
  // }
}
