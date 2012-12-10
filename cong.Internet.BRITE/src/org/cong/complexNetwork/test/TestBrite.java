package org.cong.complexNetwork.test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.model.BA;
import org.cong.complexNetwork.model.BriteCirclePlane;
import org.cong.complexNetwork.model.BriteWaxman;
import org.cong.complexNetwork.util.ChartTools;
import org.cong.complexNetwork.util.NetworkTraitUtil;
import org.jfree.data.xy.XYDataset;

public class TestBrite {

  public static Logger logger = LogManager.getLogger(TestBrite.class);

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    logger.debug("开始");
    final BriteCirclePlane bp = new BriteCirclePlane(10, 1000, 100.0);
    // BritePlane bp = new BritePlane(10, 1000);
    bp.addRandomNodes(100);
    BriteWaxman.generateEdges(bp, 0.5, 0.2);

    logger.debug("Nodes " + bp.getGraph().getNodes().size());
    logger.debug("Edges " + bp.getGraph().getEdges().size());

    BA.generateEdges(bp, 4, 900);
    // BriteBAAndWaxman.generateEdges(bp, 5, 9900, 0.6, 0.3);
    // BriteTang.generateEdges(bp, 5, 900, 0.1);
    // BA.generateEdges(bp, 5, 10);
    // Tang.generateEdges(bp, 5, 900, 0.0);
    logger.debug("Nodes " + bp.getGraph().getNodes().size());
    logger.debug("Edges " + bp.getGraph().getEdges().size());

    // Jama.Matrix m = new Jama.Matrix(bp.getBriteGraph().toAdjacentMatrix());
    // logger.debug("Matrix ok");
    // logger.debug("Martrix's rank is " + m.rank());

    // Set<BriteNode> nodes = bp.getBriteGraph().getBriteNodes();
    // Integer dSum0 = 0;
    // Integer dSum1 = 0;
    // for (BriteNode n : nodes) {
    // dSum0 += n.getDegree();
    // dSum1 += n.getConnectedNodes().size();
    // }
    //
    // logger.debug("Sum of Degree0 " + dSum0);
    // logger.debug("Sum of Degree1 " + dSum1);

    // Gexf gexf = bp.getBriteGraph().toGexf();
    // logger.debug("generating file...");
    // Tools.writeGexfFile(gexf, "testBrite.gexf");
    final UndirectedGraph ug = bp.getGraph();
    logger.debug("同配性系数： " + NetworkTraitUtil.assortativityCoefficient(ug));
    logger.debug("最小度: " + ug.getMinDegree());
    NetworkTraitUtil.showRichClubChartByDegree(ug);
    final XYDataset xyds1 = ChartTools.toXYDatasetFD(ug);
    final XYDataset xyds = ChartTools.toXYDatasetRD(ug);
    ChartTools.drawChart(xyds);
    ChartTools.drawChart(xyds1);
  }

}
