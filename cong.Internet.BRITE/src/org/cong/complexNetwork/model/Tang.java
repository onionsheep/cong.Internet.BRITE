package org.cong.complexNetwork.model;

import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;

public class Tang extends BA {
  private double epsilon; // Tang模型参数

  public Tang(double epsilon) {
    this.epsilon = epsilon;
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
  protected void addEdges(int edgeCount,
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

  public double getEpsilon() {
    return this.epsilon;
  }

  @Override
  protected double probability(Node n, Node newNode) {
    double p = 0;
    p = Math.pow(n.getDegree(), 1 + this.epsilon);
    return p;
  }


  public void setEpsilon(double epsilon) {
    this.epsilon = epsilon;
  }
}
