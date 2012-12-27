package org.cong.complexNetwork.model;

import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.Node;

public class BriteTang extends BriteBA {
  protected double epsilon; // Tang模型参数,此处为提高效率，内部默认加一

  public BriteTang(final Plane plane,
                   final int oneNodeEdge,
                   final int nodeCount,
                   final double epsilon) {
    super(plane, oneNodeEdge, nodeCount);
    this.epsilon = epsilon + 1;
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
  @Override
  protected void addEdges(final int edgeCount,
                          final Graph ug,
                          final Node[] nodeArray,
                          final double[] probabilities,
                          final Node newNode) throws Exception {
    boolean result = false;
    while (!result) {
      final int i = BriteBA.randomWithProbablities(probabilities);
      result = ug.connect(newNode, nodeArray[i]);
    }

    int m = 0;
    final int count = edgeCount - 1;
    while (m < count) {
      final int i = BriteBA.randomWithProbablities(probabilities);
      final int j = BriteBA.randomWithProbablities(probabilities);
      if (ug.connect(nodeArray[i], nodeArray[j])) {
        m += 1;
      }
    }
  }

  public double getEpsilon() {
    return this.epsilon - 1;
  }

  @Override
  protected double probability(final Node n, final Node newNode) {
    return Math.pow(n.getDegree(), this.epsilon);
  }

  public void setEpsilon(final double epsilon) {
    this.epsilon = epsilon;
  }
}
