package org.cong.complexNetwork.model;

import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;

public class BriteBAWaxman extends BA {
  public static Logger logger = LogManager.getLogger(BriteBAWaxman.class);

  /**
   * 根据给出的参数，生成边
   * @param plane 节点所在的平面
   * @param oneNodeEdge 一个节点对应添加边的条数
   * @param nodeCount 节点数量
   * @param alpha Waxman参数
   * @param beta Waxman参数
   * @throws Exception
   */
  public static void generateEdges(Plane plane,
                                   int oneNodeEdge,
                                   int nodeCount,
                                   double alpha,
                                   double beta) throws Exception {
    final UndirectedGraph ug = plane.getGraph();
    final Set<Node> nodes = ug.getNodes();
    final Node[] nodeArray = nodes.toArray(new Node[0]);
    for (int i = 0; i < nodeCount; i++) {
      logger.debug("这是第" + i + "个节点");
      final Node newNode = plane.randomNodeNoDuplication();

      final double[] probabilities = probability(nodeArray, newNode, alpha, beta, plane);
      // 添加oneNodeEdge条边，这oneNodeEdge添加边的时候不重新计算原来节点的度，概率

      addEdges(oneNodeEdge, ug, nodeArray, probabilities, newNode);
      ug.addNode(newNode);
    }
  }

  /**
   * 计算一个节点和其余节点之间连接的概率
   * 
   * @param node计算该节点和其他节点之间连接的概率
   * @param nodeArray节点数组
   * @param alpha
   *          Waxman参数
   * @param beta
   *          Waxman参数
   * @param plane
   *          节点所在平面
   * @return 所有节点概率组成的数组，分布在（0,1）的区间上。顺序与所给的nodeArray一致
   */
  public static double[] probability(Node[] nodeArray,
                                     Node node,
                                     double alpha,
                                     double beta,
                                     Plane plane) {
    final int count = nodeArray.length;
    final double maxEd = plane.MaxEuclideanDistance();
    final double bm = beta * maxEd;
    double denominator = 0;// 概率的分母
    double p = 0;
    final double[] probability = new double[count];
    final double[] numerators = new double[count];
    for (int i = 0; i < count; i++) {
      final double pw = alpha
          * Math.exp(-plane.EuclideanDistanceBetween(node.getPoint(),
                                                     nodeArray[i].getPoint())
                                                     / bm);
      numerators[i] = nodeArray[i].getDegree() * pw;
      denominator += numerators[i];
    }
    // 把每一个节点的概率放到0,1的区间上
    for (int i = 0; i < count; i++) {
      p += numerators[i] / denominator;
      probability[i] = p;
    }

    return probability;
  }

}
