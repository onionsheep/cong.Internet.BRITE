package org.cong.complexNetwork.model;

import java.util.Set;

import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;

public class Tang extends BA {

  /**
   * 按照给定的参数生成边
   * 
   * @param plane
   *          平面
   * @param oneNodeEdge
   *          一个节点伴随添加的边
   * @param nodeCount
   *          节点数量
   * @throws Exception
   */
  public static void generateEdges(Plane plane, int oneNodeEdge, int nodeCount, double epsilon) throws Exception {
    final UndirectedGraph ug = plane.getGraph();
    final Set<Node> nodes = ug.getNodes();
    for (int i = 0; i < nodeCount; i++) {
      final Node[] nodeArray = nodes.toArray(new Node[0]);
      final Node newNode = plane.randomNodeNoDuplication();
      final double[] probabilities = probability(nodeArray, epsilon);
      // 添加oneNodeEdge条边，这oneNodeEdge添加边的时候不重新计算原来节点的度，概率
      addEdges(oneNodeEdge, ug, nodeArray, probabilities, newNode);
      ug.addNode(newNode);
    }
  }


  /**
   * 按照Tang模型添加边的法则,添加边。添加一条新节点与旧节点之间的边，其余的边在旧节点之中选择。
   * 
   * @param edgeCount
   *          添加边的条数
   * @param ug
   *          图
   * @param nodeArray
   *          节点数组
   * @param probabilities
   *          概率数组，与节点数组对应
   * @param newNode
   *          新节点
   * @throws Exception
   */
  protected static void addEdges(int edgeCount,
                                 UndirectedGraph ug,
                                 Node[] nodeArray,
                                 double[] probabilities,
                                 Node newNode) throws Exception {

    boolean result = false;
    while (!result) {
      final int i = randomWithProbablities(probabilities);
      result = ug.connect(newNode, nodeArray[i]);
    }

    int m = 0;
    final int count = edgeCount - 1;
    while (m < count) {
      final int i = randomWithProbablities(probabilities);
      final int j = randomWithProbablities(probabilities);
      result = ug.connect(nodeArray[i], nodeArray[j]);
      if (result) {
        m += 1;
      }
    }
  }

  /**
   * 计算节点的概率，并存储在数组中，分布在(0,1)的区间上，只记录上限，数组下标与节点下标对应。
   * 不保存在Map中是为了后续的过程中方便折半查找随机数落在了哪个区间
   * 
   * @param nodeArray
   *          节点数组
   * @param epsilon
   *          Tang模型参数
   * @return 概率数组
   */
  public static double[] probability(Node[] nodeArray, double epsilon) {
    final int count = nodeArray.length;
    double denominator = 0;// 概率的分母
    final double[] probability = new double[count];
    final double[] numerators = new double[count];
    for (int i = 0; i < count; i++) {
      numerators[i] = Math.pow(nodeArray[i].getDegree(), 1 + epsilon);
      denominator += numerators[i];
    }
    // 把每一个节点的概率放到0,1的区间上
    double p = 0;
    for (int i = 0; i < count; i++) {
      p += numerators[i] / denominator;
      probability[i] = p;
    }
    return probability;
  }
}
