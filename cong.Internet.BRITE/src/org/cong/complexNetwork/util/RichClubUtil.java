package org.cong.complexNetwork.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cong.complexNetwork.graph.Edge;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;

public class RichClubUtil {
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
  
  
}
