package org.cong.complexNetwork.util;

import java.util.Comparator;

import org.cong.complexNetwork.graph.Node;

public class NodeDegreeComparator implements Comparator<Node> {

  private String order;

  /**
   * @param order
   *          only can be asc or other, default to be asc
   */
  public NodeDegreeComparator(String order) {
    this.order = order;
  }

  public NodeDegreeComparator() {
    this.order = "asc";
  }

  @Override
  public int compare(Node n1, Node n2) {
    int result = 0;
    // 未考虑对象为null的情况

    int d = n1.getDegree() - n2.getDegree();
    if (d > 0) {
      result = 1;
    } else if (d < 0) {
      result = -1;
    } else {
      result = 0;
    }
    if (!order.equals("asc")) {
      result = -result;
    }
    return result;
  }

}
