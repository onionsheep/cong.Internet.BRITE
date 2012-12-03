package org.cong.complexNetwork.graph;

import it.uniroma1.dis.wiserver.gexf4j.core.EdgeType;
import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.Mode;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.GexfImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UndirectedGraph {
  protected Set<Node> nodes;
  protected Set<Edge> edges;

  public UndirectedGraph() {
    nodes = new HashSet<>();
    edges = new HashSet<>();
  }

  public Set<Node> getNodes() {
    return nodes;
  }

  public Set<Edge> getEdges() {
    return edges;
  }

  public boolean connect(Node source, Node target) {
    boolean result = false;
    Edge edge = new Edge(source, target);
    result = addEdge(edge);
    return result;
  }

  public boolean addEdge(Edge edge) {
    boolean result;
    result = this.edges.add(edge);
    Node source = edge.getSource();
    Node target = edge.getTarget();
    if (result) {
      source.connectNode(target);
      target.connectNode(source);
    }
    return result;
  }

  public boolean disConnect(Node source, Node target) {
    boolean result = false;
    Edge edge = new Edge(source, target);
    result = removeEdge(edge);
    return result;
  }

  public boolean removeEdge(Edge edge) {
    boolean result;
    result = this.edges.remove(edge);
    Node source = edge.getSource();
    Node target = edge.getTarget();
    if (result) {
      source.disConnectNode(target);
      target.disConnectNode(source);
    }
    return result;
  }

  public Gexf toGexf() {

    Gexf gexf = new GexfImpl();
    it.uniroma1.dis.wiserver.gexf4j.core.Graph graph = gexf.getGraph();
    graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.STATIC);
    Map<Node, it.uniroma1.dis.wiserver.gexf4j.core.Node> nodeMap = new HashMap<>();

    for (Node node : nodes) {
      nodeMap.put(node, graph.createNode(Long.toHexString(node.getId())));
    }
    for (Edge edge : edges) {
      Node source = edge.getSource();
      Node target = edge.getTarget();

      nodeMap.get(source).connectTo(nodeMap.get(target));
    }
    return gexf;
  }

  public double[][] toAdjacentMatrix() {
    int nodeCount = nodes.size();
    double[][] martrix = new double[nodeCount][nodeCount];
    Node[] nodeArray = nodes.toArray(new Node[0]);
    Edge[] edgeArray = edges.toArray(new Edge[0]);
    // List<Node> nodeList = Arrays.asList(nodeArray);

    for (Edge e : edgeArray) {
      for (int i = 0; i < nodeArray.length; i++) {
        if (e.getSource().equals(nodeArray[i])) {
          for (int j = 0; j < nodeArray.length; j++) {
            if (e.getTarget().equals(nodeArray[j])) {
              martrix[i][j] = e.getWeight();
              martrix[j][i] = e.getWeight();
            }
          }
        }
      }
    }
    return martrix;
  }

}
