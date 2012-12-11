package org.cong.complexNetwork.test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.model.BriteBAWaxman;
import org.cong.complexNetwork.model.BriteCirclePlane;
import org.cong.complexNetwork.model.BriteWaxman;
import org.cong.complexNetwork.util.ChartTools;
import org.cong.complexNetwork.util.Tools;
import org.jfree.data.xy.XYDataset;

public class TestChart {

  public static Logger logger = LogManager.getLogger(TestChart.class);

  public static void main(String[] args) throws Exception {
    final BriteCirclePlane bp = new BriteCirclePlane(10, 1000, 100.0);
    // BritePlane bp = new BritePlane(10, 1000);
    bp.addRandomNodes(30);
    BriteWaxman.generateEdges(bp, 0.7, 0.3);

    logger.debug("Nodes " + bp.getGraph().getNodes().size());
    logger.debug("Edges " + bp.getGraph().getEdges().size());

    // BriteBA.generateEdges(bp, 6, 470);
    BriteBAWaxman.generateEdges(bp, 5, 970, 0.6, 0.3);
    // BriteTang.generateEdges(bp, 5, 900, 0.1);

    logger.debug("Nodes " + bp.getGraph().getNodes().size());
    logger.debug("Edges " + bp.getGraph().getEdges().size());
    final UndirectedGraph ug = bp.getGraph();
    // UndirectedGraph ug = new UndirectedGraph();
    // int m0 = 10;
    // int steps = 9000;
    //
    // for (int i = 0; i < m0; i++) {
    // ug.getNodes().add(AB.newNode());
    // }
    //
    // logger.debug("初始边添加OK");
    // logger.debug(ug.getNodes().size());
    // AB.generateGraph(steps, 0.1, 0.6, ug, 4);

    final double[][] am = bp.getGraph().toAdjacentMatrix();
    logger.debug("generating file...");
    Tools.writeAdjacentMatrixtoFile(am, "E:\\Users\\cong\\Documents\\MATLAB\\AdjacentMatrix.m");
    logger.debug("Done.");

    final XYDataset xyds1 = ChartTools.toXYDatasetFD(ug);

    final XYDataset xyds = ChartTools.toXYDatasetRD(ug);
    ChartTools.drawChart(xyds);
    ChartTools.drawChart(xyds1);
  }

}
