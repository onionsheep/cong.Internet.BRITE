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

  public static double assortativityCoefficient(UndirectedGraph ug) {
    double ac = 0;
    Set<Edge> edges = ug.getEdges();
    double m = 1.0 / edges.size();

    double a = 0;
    double b = 0;
    double c = 0;
    for (Edge e : edges) {
      int d1 = e.getSource().getDegree();
      int d2 = e.getTarget().getDegree();
      a += d1 * d2;
      b += d1 + d2;
      c += (d1 * d1 + d2 * d2);
    }
    a *= m;

    b = b * m * 0.5;
    b = b * b;

    c = c * m * 0.5;
    ac = (a - b) / (c - b);
    return ac;
  }

  public static double RichConnectednessByDegree(UndirectedGraph ug, int degree) {
    Set<Edge> edges = ug.getEdges();
    int l = 0;
    Set<Node> nodes = new HashSet<>();
    for (Edge e : edges) {
      Node s = e.getSource();
      Node t = e.getTarget();
      if (s.getDegree() >= degree && t.getDegree() >= degree) {
        nodes.add(t);
        nodes.add(s);
        l++;
      }
    }
    int num = nodes.size();
    double result = 2.0 * l / (num * (num - 1));
    return result;
  }

  public static double RichConnectednessByOrder(UndirectedGraph ug, int order) {
    Set<Edge> edges = ug.getEdges();
    int l = 0;
    Node[] na = ug.getNodes().toArray(new Node[0]);
    Arrays.sort(na, new NodeDegreeComparator("desc"));
    na = Arrays.copyOf(na, order);
    List<Node> nl = Arrays.asList(na);
    for (Edge e : edges) {
      Node s = e.getSource();
      Node t = e.getTarget();
      if (nl.contains(s) && nl.contains(t)) {
        l++;
      }
    }
    double result = 2.0 * l / (order * (order - 1));
    return result;
  }

  public static void showRichClubChartByDegree(UndirectedGraph ug) throws Exception {
    int minD = ug.getMinDegree();
    int maxD = ug.getMaxDegree();
    List<Double> xl = new ArrayList<>();
    List<Double> yl = new ArrayList<>();
    for (int i = minD; i <= maxD; i++) {
      xl.add(i * 1.0);
      yl.add(RichConnectednessByDegree(ug, i));
    }
    XYDataset xyds = ChartTools.toXYDataset(xl, yl, "度-连通性");
    ChartTools.drawChart(xyds);
  }

  public static void showRichClubChartByOrder(UndirectedGraph ug) throws Exception {
    int count = ug.getNodes().size();
    List<Double> xl = new ArrayList<>();
    List<Double> yl = new ArrayList<>();
    for (int i = 0; i <= count; i++) {
      xl.add(i * 1.0);
      yl.add(RichConnectednessByOrder(ug, i));
    }
    XYDataset xyds = ChartTools.toXYDataset(xl, yl, "个数-连通性");
    ChartTools.drawChart(xyds);
  }

}
