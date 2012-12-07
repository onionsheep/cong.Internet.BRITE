package org.cong.complexNetwork.util;

import java.util.HashSet;
import java.util.Set;

import org.cong.complexNetwork.graph.Edge;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;

public class RichClubUtil {
  public static double RichConnectedness(UndirectedGraph ug, int degree) {
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
  
  
  
}
