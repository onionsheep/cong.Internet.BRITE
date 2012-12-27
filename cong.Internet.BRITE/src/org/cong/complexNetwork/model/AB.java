package org.cong.complexNetwork.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Edge;
import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.util.Tools;

public class AB {
  public static Logger logger = LogManager.getLogger(AB.class);

  private static long  nodeId = 0;

  public static void addNewEdges(final Graph ug, final int count) throws Exception {
    final Set<Node> nodes = ug.getNodes();
    for (int i = 0; i < count; i++) {
      final Node sNode = AB.getRandomNode(nodes);
      final Node tNode = AB.getTargetNode(nodes);
      if (ug.connect(sNode, tNode)) {
        i--;
      }
    }
  }

  public static boolean addNode(final Graph ug) throws Exception {
    final Set<Node> nodes = ug.getNodes();
    final Node tNode = AB.getTargetNode(nodes);
    final Node newNode = AB.newNode();
    ug.addNode(newNode);
    return ug.connect(newNode, tNode);
  }

  /**
   * 生成边
   * 
   * @param step
   *          步数
   * @param pNewEdges
   *          执行生成新边的概率
   * @param pRestEdge
   *          执行重连边的概率
   * @param undirectedGraph
   *          图
   * @param count
   *          生成新边和重连边，每一次的数量
   * @throws Exception
   */
  public static void generateGraph(final int step,
                                   final double pNewEdges,
                                   final double pRestEdge,
                                   final Graph undirectedGraph,
                                   final int count) throws Exception {
    for (int i = 0; i < step; i++) {
      final double r = Tools.randomDouble(1);
      if (r < pNewEdges) {
        AB.addNewEdges(undirectedGraph, count);
      } else if (r < (pNewEdges + pRestEdge)) {
        if (!AB.reSetEdges(undirectedGraph, count)) {
          i--;
          AB.logger.error("resetEdges Failed");
        }
      } else {
        AB.addNode(undirectedGraph);
      }
    }
  }

  private static Node getRandomNode(final Set<Node> nodes) {
    final int size = nodes.size();
    final int rnd = java.util.concurrent.ThreadLocalRandom.current().nextInt(size);
    final Node[] nodeArray = nodes.toArray(new Node[0]);
    final Node sNode = nodeArray[rnd];
    return sNode;
  }

  private static Node getTargetNode(final Set<Node> nodes) {
    final Node[] nodeArray = nodes.toArray(new Node[0]);
    double probability = 0.0;
    final double[] probabilities = new double[nodeArray.length];
    for (int j = 0; j < nodeArray.length; j++) {
      probability += AB.probability(nodeArray[j], nodeArray);
      probabilities[j] = probability;
    }

    Node tNode = null;
    final double rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
    probability = 0.0;
    for (int j = 0; j < nodeArray.length; j++) {
      probability = probabilities[j];
      if (rand <= probability) {
        tNode = nodeArray[j];
      }
    }
    return tNode;
  }

  public static Node newNode() {
    return new Node(AB.nodeId++);
  }

  public static double probability(final Node i, final Node[] nodeArray) {
    double probability = 0;
    int sumOfDegree = 0;
    for (final Node node : nodeArray) {
      sumOfDegree += (node.getDegree() + 1);
    }
    probability = (1.0 * (i.getDegree() + 1)) / sumOfDegree;
    return probability;
  }

  public static boolean reSetEdges(final Graph undirectedGraph, final int count) throws Exception {
    boolean result = true;
    final Set<Node> nodes = undirectedGraph.getNodes();
    int sumD = 0;
    for (final Node node : nodes) {
      sumD += node.getDegree();
    }
    if (sumD != 0) {
      for (int i = 0; i < count; i++) {
        Node sNode = null;
        int d = 0;
        while (d == 0) {
          sNode = AB.getRandomNode(nodes);
          d = sNode.getDegree();
        }
        final List<Edge> edgeList = new ArrayList<>();
        final Set<Edge> edges = undirectedGraph.getEdges();
        final Set<Node> connected = new HashSet<>();
        for (final Edge e : edges) {
          if (e.getSource().equals(sNode) || e.getTarget().equals(sNode)) {
            edgeList.add(e);
            connected.add(e.getSource());
            connected.add(e.getTarget());
          }
        }

        final int rnd = Tools.randomInt(edgeList.size());
        final Edge eg = edgeList.get(rnd);
        final Set<Node> restNodes = new HashSet<>();
        restNodes.addAll(nodes);
        restNodes.removeAll(connected);
        final Node tNode = AB.getTargetNode(restNodes);
        if (tNode != null) {
          if (undirectedGraph.connect(sNode, tNode)) {
            undirectedGraph.removeEdge(eg);
          } else {
            AB.logger.debug(eg);
            AB.logger.debug("connect Failed");
            i--;
          }
        } else {
          result = false;
          break;
        }
      }
    } else {
      result = false;
    }
    return result;
  }

}
