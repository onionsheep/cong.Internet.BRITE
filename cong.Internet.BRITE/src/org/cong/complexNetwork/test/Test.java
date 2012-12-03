package org.cong.complexNetwork.test;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;

import java.util.Set;

import jmathlib.toolbox.jmathlib.matrix._private.Jama.Matrix;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.BriteNode;
import org.cong.complexNetwork.model.BriteCirclePlane;
import org.cong.complexNetwork.model.BriteTang;
import org.cong.complexNetwork.model.BriteWaxman;
import org.cong.complexNetwork.util.Tools;

public class Test {

  public static Logger logger = LogManager.getLogger(Test.class);

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    BriteCirclePlane bp = new BriteCirclePlane(10, 1000, 100.0);
    // BritePlane bp = new BritePlane(10, 1000);
    bp.addRandomNodes(100);
    BriteWaxman.generateEdges(bp, 0.6, 0.3);

    logger.debug("Nodes " + bp.getBriteGraph().getNodes().size());
    logger.debug("Edges " + bp.getBriteGraph().getEdges().size());

    // BriteBA.generateEdges(bp, 5, 900);
    // BriteBAAndWaxman.generateEdges(bp, 5, 900, 0.6, 0.3);
    BriteTang.generateEdges(bp, 5, 900, 0.1);
    // BA.generateEdges(bp, 5, 10);
    // Tang.generateEdges(bp, 5, 900, 0.0);
    logger.debug("Nodes " + bp.getBriteGraph().getNodes().size());
    logger.debug("Edges " + bp.getBriteGraph().getEdges().size());

    Matrix m = new Matrix(bp.getBriteGraph().toAdjacentMatrix());
    logger.debug("Matrix ok");
    logger.debug("Martrix's rank is " + m.rank());

    Set<BriteNode> nodes = bp.getBriteGraph().getBriteNodes();
    Integer dSum0 = 0;
    Integer dSum1 = 0;
    for (BriteNode n : nodes) {
      dSum0 += n.getDegree();
      dSum1 += n.getConnectedNodes().size();
    }

    logger.debug("Sum of Degree0 " + dSum0);
    logger.debug("Sum of Degree1 " + dSum1);

    Gexf gexf = bp.getBriteGraph().toGexf();
    logger.debug("generating file...");
    Tools.writeGexfFile(gexf, "test.gexf");
  }

}
