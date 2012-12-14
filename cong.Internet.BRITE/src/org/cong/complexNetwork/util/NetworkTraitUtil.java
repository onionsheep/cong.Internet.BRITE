package org.cong.complexNetwork.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cong.complexNetwork.graph.Edge;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.jfree.data.xy.XYDataset;

public class NetworkTraitUtil {

  public static double assortativityCoefficient(final UndirectedGraph ug) {
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

  public static void showRichClubChartByDegree(final UndirectedGraph ug) throws Exception {
    final int minD = ug.getMinDegree();
    final int maxD = ug.getMaxDegree();
    final List<Double> xl = new ArrayList<>();
    final List<Double> yl = new ArrayList<>();
    final Set<Edge> edges = ug.getEdges();
    for (int i = minD; i <= maxD; i++) {
      int l = 0;
      final Set<Node> nodes = new HashSet<>();
      for (final Edge e : edges) {
        final Node s = e.getSource();
        final Node t = e.getTarget();
        if ((s.getDegree() >= i) && (t.getDegree() >= i)) {
          nodes.add(t);
          nodes.add(s);
          l++;
        }
      }
      final int num = nodes.size();
      final double result = (2.0 * l) / (num * (num - 1));
      xl.add(i * 1.0);
      yl.add(result);
    }
    final XYDataset xyds = ChartTools.toXYDataset(xl, yl, "度-连通性");
    ChartTools.drawChart(xyds, "富人俱乐部特性");
  }

  public static void showRichClubChartByOrder(final UndirectedGraph ug) throws Exception {
    final int count = ug.getNodes().size();
    final Set<Edge> edges = ug.getEdges();
    final Node[] na = ug.getNodes().toArray(new Node[0]);
    Arrays.sort(na, new NodeDegreeComparator("desc"));
    final List<Double> xl = new ArrayList<>();
    final List<Double> yl = new ArrayList<>();
    for (int i = 1; i <= count; i++) {
      final Node[] nap = Arrays.copyOf(na, i);
      final Set<Node> ns = new HashSet<>();
      ns.addAll(Arrays.asList(nap));
      int l = 0;
      for (final Edge e : edges) {
        final Node s = e.getSource();
        final Node t = e.getTarget();
        if (ns.contains(s) && ns.contains(t)) {
          l++;
        }
      }
      final double result = (2.0 * l) / (i * (i - 1));
      xl.add(Math.log(i));
      yl.add(Math.log(result + 1));
    }
    final XYDataset xyds = ChartTools.toXYDataset(xl, yl, "个数-连通性");
    ChartTools.drawChart(xyds, "富人俱乐部特性(已取双对数)");
  }

}
