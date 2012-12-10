package org.cong.complexNetwork.test;

import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.BriteCoordinate;
import org.cong.complexNetwork.graph.BriteEdge;
import org.cong.complexNetwork.graph.BriteNode;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.model.BA;
import org.cong.complexNetwork.model.BritePlane;
import org.cong.complexNetwork.model.BriteWaxman;

public class TestBA {

  private static Logger logger = LogManager.getLogger("TestBA");

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    // testBriteNodeEquals();
    testBA1();
  }

  private static void testBA1() throws Exception {
    final BritePlane bp = new BritePlane(10000, 10000);
    bp.addRandomNodes(100);
    BriteWaxman.generateEdges(bp, 0.5, 0.2);
    System.out.println("Nodes " + bp.getGraph().getNodes().size());
    System.out.println("Edges " + bp.getGraph().getEdges().size());

    final Set<Node> nodes = bp.getGraph().getNodes();
    Integer dSum = 0;
    for (final Node n : nodes) {
      dSum += n.getDegree();
    }
    System.out.println(dSum);
    logger.error(dSum);
    BA.generateEdges(bp, 7, 900);

    System.out.println("Nodes " + bp.getGraph().getNodes().size());
    System.out.println("Edges " + bp.getGraph().getEdges().size());

    dSum = 0;
    for (final Node n : nodes) {
      dSum += n.getDegree();
    }
    System.out.println(dSum);

  }

  public static void testBriteNodeEquals() throws Exception {
    final BriteCoordinate c0 = new BriteCoordinate(0, 1, 2, 3);
    final BriteCoordinate c1 = new BriteCoordinate(1, 1, 2, 3);
    // BriteCoordinate c2 = new BriteCoordinate(2, 1, 2, 3);
    // BriteCoordinate c3 = new BriteCoordinate(3, 1, 2, 3);
    final BriteNode n0 = new BriteNode(0, c0);
    final BriteNode n1 = new BriteNode(1L, c1);
    // BriteNode n2 = new BriteNode("2", c2);
    // BriteNode n3 = new BriteNode("3", c3);

    final BriteEdge e0 = new BriteEdge(n0, n1);
    final BriteEdge e1 = new BriteEdge(n1, n0);
    final BriteEdge e2 = new BriteEdge(n1, n0);

    System.out.println(e0.equals(e1));
    System.out.println(e2.equals(e1));
  }

}
