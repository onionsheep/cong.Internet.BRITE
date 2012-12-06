package org.cong.complexNetwork.test;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.model.AB;
import org.cong.complexNetwork.util.Tools;

public class TestAB {
  public static Logger logger = LogManager.getLogger(TestAB.class);

  public static void main(String[] args) {
    UndirectedGraph ug = new UndirectedGraph();
    int m0 = 10;
    int steps = 3000;

    for (int i = 0; i < m0; i++) {
      ug.getNodes().add(AB.newNode());
    }

    logger.debug("初始边添加OK");
    logger.debug(ug.getNodes().size());
    AB.generateGraph(steps, 0.1, 0.6, ug, 4);

    // double[][] m = ug.toAdjacentMatrix();
    //
    // for (int i = 1; i < m.length; i++) {
    // for (int j = 0; j < m[0].length; j++) {
    // System.out.print((int) m[i][j] + "\t");
    // }
    // System.out.println();
    // }

    Gexf gexf = ug.toGexf();
    logger.debug("generating file...");
    Tools.writeGexfFile(gexf, "testAB.gexf");
  }

}
