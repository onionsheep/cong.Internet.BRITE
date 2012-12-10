package org.cong.complexNetwork.graph;

import it.uniroma1.dis.wiserver.gexf4j.core.EdgeType;
import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.Mode;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.GexfImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.util.Sparse;

public class UndirectedGraph {
  protected Set<Node> nodes;
  protected Set<Edge> edges;
  protected Map<Node, Node> nodeMap;
  public static Logger logger = LogManager.getLogger(UndirectedGraph.class);

  public UndirectedGraph() {
    this.nodes = new HashSet<>();
    this.edges = new HashSet<>();
    this.nodeMap = new HashMap<>();
  }

  public boolean addEdge(Edge edge) {
    boolean result;
    result = this.edges.add(edge);
    return result;
  }

  public boolean addNode(Node node) {
    return this.nodes.add(node);
  }

  public int adjacentMatrixRank() {
    int r = 0;
    for (final Node n : this.nodes) {
      if (n.getDegree() > 0) {
        r++;
      }
    }
    return r;
  }

  public boolean connect(Node source, Node target) throws Exception {
    boolean result = false;
    final Edge edge = new Edge(source, target);
    result = this.addEdge(edge);
    return result;
  }

  public boolean disConnect(Node source, Node target) throws Exception {
    boolean result = false;
    final Edge edge = new Edge(source, target);
    result = this.removeEdge(edge);
    return result;
  }

  public Set<Edge> getEdges() {
    return this.edges;
  }

  public int getMaxDegree() {
    int maxD = 0;
    for (final Node n : this.nodes) {
      if (maxD < n.getDegree()) {
        maxD = n.getDegree();
      }
    }
    return maxD;
  }

  public int getMinDegree() {
    int minD = Integer.MAX_VALUE;
    for (final Node n : this.nodes) {
      if (minD > n.getDegree()) {
        minD = n.getDegree();
      }
    }
    return minD;
  }

  public Node getNode(Node node) {
    Node n0 = null;
    for (final Node n : this.nodes) {
      if (n.equals(node)) {
        n0 = n;
        break;
      }
    }
    return n0;
  }

  public Set<Node> getNodes() {
    return this.nodes;
  }

  public boolean removeEdge(Edge edge) {
    boolean result;
    result = this.edges.remove(edge);
    final Node source = edge.getSource();
    final Node target = edge.getTarget();
    if (result) {
      source.disConnectNode(target);
      target.disConnectNode(source);
    }
    return result;
  }

  public double[][] toAdjacentMatrix() {
    final int nodeCount = this.nodes.size();
    final double[][] martrix = new double[nodeCount][nodeCount];
    final Node[] nodeArray = this.nodes.toArray(new Node[0]);
    final Edge[] edgeArray = this.edges.toArray(new Edge[0]);
    // List<Node> nodeList = Arrays.asList(nodeArray);

    for (final Edge e : edgeArray) {
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

  public void toAdjacentMatrixFile(String filePath) throws IOException {
    final Node[] nodeArray = this.nodes.toArray(new Node[0]);
    // Edge[] edgeArray = edges.toArray(new Edge[0]);
    final File f = new File(filePath);
    StringBuffer sb;
    FileUtils.writeStringToFile(f, "AM = [\n", "UTF-8");
    for (final Node element : nodeArray) {
      sb = new StringBuffer();
      for (final Node element2 : nodeArray) {
        if (element.getConnectedNodes().contains(element2)) {
          sb.append("1,");
        } else {
          sb.append("0,");
        }
      }
      sb.deleteCharAt(sb.length() - 1);
      sb.append("\n");
      FileUtils.writeStringToFile(f, sb.toString(), "UTF-8", true);
    }
    FileUtils.writeStringToFile(f, "]", "UTF-8", true);
  }

  public Gexf toGexf() {

    final Gexf gexf = new GexfImpl();
    final it.uniroma1.dis.wiserver.gexf4j.core.Graph graph = gexf.getGraph();
    graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.STATIC);
    final Map<Node, it.uniroma1.dis.wiserver.gexf4j.core.Node> nodeMap = new HashMap<>();

    for (final Node node : this.nodes) {
      nodeMap.put(node, graph.createNode(Long.toHexString(node.getId())));
    }
    for (final Edge edge : this.edges) {
      final Node source = edge.getSource();
      final Node target = edge.getTarget();

      if (source == null) {
        logger.debug("source null");
      }

      nodeMap.get(source).connectTo(nodeMap.get(target));
    }
    return gexf;
  }

  public Sparse<Integer> toSparse() {
    final Sparse<Integer> s = new Sparse<>();
    final List<Node> nl = new ArrayList<>();
    nl.addAll(this.nodes);
    for (final Edge e : this.edges) {
      final int i = 1 + nl.indexOf(e.getSource());
      final int j = 1 + nl.indexOf(e.getTarget());
      s.addElement(i, j, e.getWeight());
      s.addElement(j, i, e.getWeight());
    }
    return s;
  }

}
