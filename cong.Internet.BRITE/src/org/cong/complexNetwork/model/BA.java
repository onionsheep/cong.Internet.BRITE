package org.cong.complexNetwork.model;

import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.util.ArrayUtil;

public class BA {

  public static Logger logger = LogManager.getLogger(BA.class);

  /**
   * 在新节点和旧节点之间添加edgeCount条边
   * 
   * @param edgeCount
   * @param ug
   * @param nodeArray
   * @param newNode
   * @param nodeProbability
   * 
   * @throws Exception
   */
  protected static void addEdges(int edgeCount,
                                 UndirectedGraph ug,
                                 Node[] nodeArray,
                                 double[] probabilities,
                                 Node newNode) throws Exception {
    double rand;
    boolean result;
    int m = 0;
    while (m < edgeCount) {
      rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
      final int i = ArrayUtil.firstBigger(probabilities, rand);
      result = ug.connect(newNode, nodeArray[i]);
      if (result) {
        m += 1;
      }
    }
  }

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
  public static void generateEdges(Plane plane, int oneNodeEdge, int nodeCount) throws Exception {
    final UndirectedGraph ug = plane.getGraph();
    final Set<Node> nodes = ug.getNodes();
    final Node[] nodeArray = nodes.toArray(new Node[0]);
    for (int i = 0; i < nodeCount; i++) {
      final Node newNode = plane.randomNodeNoDuplication();
      final double[] probabilities = probability(nodeArray);
      // 添加oneNodeEdge条边，这oneNodeEdge添加边的时候不重新计算原来节点的度，概率
      addEdges(oneNodeEdge, ug, nodeArray, probabilities, newNode);
      ug.addNode(newNode);
    }
  }

  /**
   * 计算节点的概率，并存储在数组中，分布在(0,1)的区间上，只记录上限，数组下标与节点下标对应。
   * 不保存在Map中是为了后续的过程中方便折半查找随机数落在了哪个区间
   * 
   * @param nodeArray
   *          节点数组
   * @return 概率数组
   */
  public static double[] probability(Node[] nodeArray) {
    final int count = nodeArray.length;
    int sumOfDegree = 0;// 节点度之和
    double pbase = 0;
    final double[] probability = new double[count];
    for (final Node node : nodeArray) {
      sumOfDegree += node.getDegree();
    }
    // 把每一个节点的概率放到0,1的区间上
    for (int i = 0; i < count; i++) {
      pbase += (1.0 * nodeArray[i].getDegree()) / sumOfDegree;
      probability[i] = pbase;
    }
    return probability;
  }
}
