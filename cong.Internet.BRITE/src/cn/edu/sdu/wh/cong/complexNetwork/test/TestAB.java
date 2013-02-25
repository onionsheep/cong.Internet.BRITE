package cn.edu.sdu.wh.cong.complexNetwork.test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cn.edu.sdu.wh.cong.complexNetwork.graph.Graph;
import cn.edu.sdu.wh.cong.complexNetwork.model.AB;

public class TestAB {
  public static Logger logger = LogManager.getLogger(TestAB.class);

  public static void main(final String[] args) throws Exception {
    final Graph ug = new Graph();
    final int m0 = 10;
    final int steps = 3000;

    for (int i = 0; i < m0; i++) {
      ug.addNode(AB.newNode());
    }

    TestAB.logger.debug("初始边添加OK");
    TestAB.logger.debug(ug.getNodes().size());
    AB.generateGraph(steps, 0.2, 0.5, ug, 3);

    TestBrite.log(ug);
    // double[][] m = ug.toAdjacentMatrix();
    //
    // for (int i = 1; i < m.length; i++) {
    // for (int j = 0; j < m[0].length; j++) {
    // System.out.print((int) m[i][j] + "\t");
    // }
    // System.out.println();
    // }

    // final Gexf gexf = ug.toGexf();
    // logger.debug("generating file...");
    // Tools.writeGexfFile(gexf, "testAB.gexf");
  }

}
