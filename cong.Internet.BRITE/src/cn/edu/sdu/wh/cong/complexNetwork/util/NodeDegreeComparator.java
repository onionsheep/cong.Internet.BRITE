package cn.edu.sdu.wh.cong.complexNetwork.util;

import java.util.Comparator;

import cn.edu.sdu.wh.cong.complexNetwork.graph.Node;

public class NodeDegreeComparator implements Comparator<Node> {

  private final String order;

  public NodeDegreeComparator() {
    this.order = "asc";
  }

  /**
   * @param order
   *          only can be asc or other, default to be asc
   */
  public NodeDegreeComparator(final String order) {
    this.order = order;
  }

  @Override
  public int compare(final Node n1, final Node n2) {
    int result = 0;
    // 未考虑对象为null的情况

    final int d = n1.getDegree() - n2.getDegree();
    if (d > 0) {
      result = 1;
    } else if (d < 0) {
      result = -1;
    } else {
      result = 0;
    }
    if (!this.order.equals("asc")) {
      result = -result;
    }
    return result;
  }

}
