package org.cong.complexNetwork.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Edge;
import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.Node;
import org.jfree.data.xy.XYDataset;

public class NetworkTraitUtil {
  public static Logger logger = LogManager.getLogger(NetworkTraitUtil.class);

  public static double assortativityCoefficient(final Graph ug) {
    double ac = 0;
    final Set<Edge> edges = ug.getEdges();
    final double m = 1.0 / edges.size();

    double a = 0;
    double b = 0;
    double c = 0;
    for (final Edge e : edges) {
      final int d1 = e.getSource().getDegree();
      final int d2 = e.getTarget().getDegree();
      a += d1 * d2;
      b += d1 + d2;
      c += ((d1 * d1) + (d2 * d2));
    }
    a *= m;

    b = b * m * 0.5;
    b = b * b;

    c = c * m * 0.5;
    ac = (a - b) / (c - b);
    return ac;
  }

  public static void showRichClubChartByDegree(final Graph ug) throws Exception {
    final int minD = ug.getMinDegree();
    final int maxD = ug.getMaxDegree();
    final List<Double> xl = new ArrayList<>();
    final List<Double> yl = new ArrayList<>();
    final Set<Edge> edges = new HashSet<>();
    edges.addAll(ug.getEdges());
    final Set<Node> nodes = new HashSet<>();
    int l = 0;// 实际存在的边的数量
    for (int i = maxD; i >= minD; i--) {
      final Set<Edge> egdesToRemove = new HashSet<>();
      for (final Edge e : edges) {
        final Node s = e.getSource();
        final Node t = e.getTarget();
        if ((s.getDegree() >= i) && (t.getDegree() >= i)) {
          nodes.add(t);
          nodes.add(s);
          l++;
          egdesToRemove.add(e);
        }
      }
      edges.removeAll(egdesToRemove);
      final int num = nodes.size();// 节点的数量
      final double result = (2.0 * l) / (num * (num - 1));
      xl.add(i * 1.0);
      yl.add(result);
    }
    final XYDataset xyds = ChartTools.toXYDataset(xl, yl, "度-连通性");
    ChartTools.showChart(xyds, "富人俱乐部特性");
  }

  public static void showRichClubChartByOrder(final Graph ug) throws Exception {
    final List<Double> xl = new ArrayList<>();
    final List<Double> yl = new ArrayList<>();
    final int count = ug.getNodes().size();
    final Node[] na = ug.getNodes().toArray(new Node[0]);
    Arrays.sort(na, new NodeDegreeComparator("desc"));
    // 用Map存放节点和其对应的顺序,方便查找
    final Map<Node, Integer> map = new HashMap<>();
    for (int i = 0; i < count; i++) {
      map.put(na[i], i);
    }
    int l = 0;// 实际存在的边的数量
    for (int i = 1; i < count; i++) {
      final Set<Node> s = na[i].getConnectedNodes();
      for (final Node n : s) {
        if (map.get(n) < i) {
          l++;
        }
      }
      final double result = (2.0 * l) / (i * (i + 1));
      final double y = Math.log(result);
      // logger.debug(y);
      if ((result > Double.MIN_VALUE) && !Double.isInfinite(y) && !Double.isNaN(y)) {
        xl.add(Math.log(i));
        yl.add(y);
      }
    }

    final XYDataset xyds = ChartTools.toXYDataset(xl, yl, "个数-连通性");
    ChartTools.showChart(xyds, "富人俱乐部特性(已取双对数)");
  }

}
