package org.cong.complexNetwork.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Edge;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.util.Tools;

public class AB {
  public static Logger logger = LogManager.getLogger(AB.class);

  private static long nodeId = 0;

  public static double probability(Node i, Node[] nodeArray) {
    double probability = 0;
    int sumOfDegree = 0;
    for (Node node : nodeArray) {
      sumOfDegree += (node.getDegree() + 1);
    }
    probability = 1.0 * (i.getDegree() + 1) / sumOfDegree;
    return probability;
  }

  public static void generateGraph(int step,
                                   double pNewEdges,
                                   double pRestEdge,
                                   UndirectedGraph undirectedGraph,
                                   int count) {
    for (int i = 0; i < step; i++) {
      double r = Tools.randomDouble(1);
      //logger.debug(r);
      if (r < pNewEdges) {
        logger.debug("newEdges");
        addNewEdges(undirectedGraph, count);
      } else if (r < pNewEdges + pRestEdge) {
        logger.debug("resetEdges");
        if (!reSetEdges(undirectedGraph, count)) {
          i--;
          logger.debug("resetEdges Failed");
        }
      } else {
        logger.debug("newNode");
        addNode(undirectedGraph);
      }
      logger.debug(i);
    }
  }

  public static void addNewEdges(UndirectedGraph undirectedGraph, int count) {
    Set<Node> nodes = undirectedGraph.getNodes();
    for (int i = 0; i < count; i++) {
      Node sNode = getRandomNode(nodes);
      Node tNode = getTargetNode(nodes);
      if (undirectedGraph.connect(sNode, tNode)) {
        i--;
      }
    }
  }

  private static Node getTargetNode(Set<Node> nodes) {
    Node[] nodeArray = nodes.toArray(new Node[0]);
    double probability = 0.0;
    double[] probabilities = new double[nodeArray.length];
    for (int j = 0; j < nodeArray.length; j++) {
      probability += AB.probability(nodeArray[j], nodeArray);
      probabilities[j] = probability;
    }

    Node tNode = null;
    double rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
    probability = 0.0;
    for (int j = 0; j < nodeArray.length; j++) {
      probability = probabilities[j];
      if (rand <= probability) {
        tNode = nodeArray[j];
      }
    }
    return tNode;
  }

  private static Node getRandomNode(Set<Node> nodes) {
    int size = nodes.size();
    int rnd = java.util.concurrent.ThreadLocalRandom.current().nextInt(size);
    Node[] nodeArray = nodes.toArray(new Node[0]);
    Node sNode = nodeArray[rnd];
    return sNode;
  }

  public static boolean reSetEdges(UndirectedGraph undirectedGraph, int count) {
    boolean result = true;
    Set<Node> nodes = undirectedGraph.getNodes();
    int sumD = 0;
    for (Node node : nodes) {
      sumD += node.getDegree();
    }
    if (sumD != 0) {
      for (int i = 0; i < count; i++) {
        Node sNode = null;
        int d = 0;
        while (d == 0) {
          sNode = AB.getRandomNode(nodes);
          d = sNode.getDegree();
          // logger.debug(sNode);
        }
        //logger.debug("select Node : " + sNode);
        List<Edge> edgeList = new ArrayList<>();
        Set<Edge> edges = undirectedGraph.getEdges();
        Set<Node> connected = new HashSet<>();
        for (Edge e : edges) {
          if (e.getSource().equals(sNode) || e.getTarget().equals(sNode)) {
            edgeList.add(e);
            connected.add(e.getSource());
            connected.add(e.getTarget());
          }
        }

        int rnd = Tools.randomInt(edgeList.size());
        Edge eg = edgeList.get(rnd);

        // Node tNode = AB.getTargetNode(nodes);

        Set<Node> restNodes = new HashSet<>();
        restNodes.addAll(nodes);
        restNodes.removeAll(connected);

        Node tNode = AB.getTargetNode(restNodes);
        //logger.debug("target Node : " + tNode);
        // Node tNode = null;
        // boolean connected = true;
        // while(connected){
        // tNode = AB.getTargetNode(nodes);
        // connected = false;
        // for (Edge e : edges) {
        // if (e.getSource().equals(sNode) || e.getTarget().equals(sNode)) {
        // connected = true;
        // break;
        // }
        // }
        // }
        if (tNode != null) {
          if (undirectedGraph.connect(sNode, tNode)) {
            undirectedGraph.removeEdge(eg);
          } else {
            logger.debug(eg);
            logger.debug("connect Failed");
            i--;
          }
        }else{
          result = false;
          break;
        }
      }
    } else {
      result = false;
    }
    return result;
  }

  public static boolean addNode(UndirectedGraph undirectedGraph) {
    Set<Node> nodes = undirectedGraph.getNodes();
    Node tNode = getTargetNode(nodes);
    Node newNode = newNode();
    nodes.add(newNode);
    return undirectedGraph.connect(newNode, tNode);
  }

  public static Node newNode() {
    return new Node(nodeId++);
  }

}
