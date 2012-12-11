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
   * 按照给定的参数生成边
   * 
   * @param plane
   *          平面
   * @param oneNodeEdge
   *          一个节点伴随添加的边
   * @param nodeCount
   *          节点数量
   * @param ba
   *          一个BA或者其子类的模型，模型中带有参数
   * @throws Exception
   */
  public static void generateEdges(Plane plane, int oneNodeEdge, int nodeCount, BA ba)
      throws Exception {
    final UndirectedGraph ug = plane.getGraph();
    final Set<Node> nodes = ug.getNodes();
    for (int i = 0; i < nodeCount; i++) {
      final Node[] nodeArray = nodes.toArray(new Node[0]);
      final Node newNode = plane.randomNodeNoDuplication();
      final double[] probabilities = probability(nodeArray, newNode, ba);
      // 添加oneNodeEdge条边，这oneNodeEdge添加边的时候不重新计算原来节点的度，概率
      ba.addEdges(oneNodeEdge, ug, nodeArray, probabilities, newNode);
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
  protected static double[] probability(Node[] nodeArray, Node newNode, BA ba) {
    final int count = nodeArray.length;
    double denominator = 0;// 概率的分母,就是节点度的和
    double p = 0;
    final double[] probability = new double[count];
    final double[] numerators = new double[count];
    for (int i = 0; i < count; i++) {
      numerators[i] = ba.probability(nodeArray[i], newNode);
      denominator += numerators[i];
    }
    // 把每一个节点的概率放到0,1的区间上
    for (int i = 0; i < count; i++) {
      p += numerators[i] / denominator;
      probability[i] = p;
    }
    return probability;
  }

  /**
   * 以给定的概率，产生一个随机数。随机数的范围是概率数组的下标，概率数组是（0,1）的一个区间，只记录上限。
   * 
   * @param probabilities
   *          概率数组
   * @return 随机数落在区间的下标
   */
  protected static int randomWithProbablities(double[] probabilities) {
    double rand;
    rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
    final int i = ArrayUtil.firstBigger(probabilities, rand);
    return i;
  }

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
  protected void addEdges(int edgeCount,
                          UndirectedGraph ug,
                          Node[] nodeArray,
                          double[] probabilities,
                          Node newNode) throws Exception {
    boolean result;
    int m = 0;
    while (m < edgeCount) {
      final int i = randomWithProbablities(probabilities);
      result = ug.connect(newNode, nodeArray[i]);
      if (result) {
        m += 1;
      }
    }
  }

  /**
   * 按照给定的节点产生概率的分子，对于BA，其实就是节点的度
   * 
   * @param n
   *          节点
   * @return 概率的分子
   */
  protected double probability(Node n, Node newNode) {
    return n.getDegree();
  }
}
