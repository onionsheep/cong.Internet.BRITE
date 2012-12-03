package org.cong.complexNetwork.test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.model.AB;

public class TestAB {
  public static Logger logger = LogManager.getLogger(TestAB.class);

  public static void main(String[] args) {
    UndirectedGraph ug = new UndirectedGraph();
    int m0 = 10;
    int steps = 40;

    for (int i = 0; i < m0; i++) {
      ug.getNodes().add(AB.newNode());
    }

    logger.debug("初始边添加OK");
    logger.debug(ug.getNodes().size());
    AB.generateGraph(steps, 0.1, 0.4, ug, 3);
    
    double[][] m = ug.toAdjacentMatrix();

    for (int i = 1; i < m.length; i++) {
      for (int j = 0; j < m[0].length; j++) {
        System.out.print((int) m[i][j] + "\t");
      }
      System.out.println();
    }
    
  }

}
