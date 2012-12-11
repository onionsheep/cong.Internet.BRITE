package org.cong.complexNetwork.test;

import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Edge;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.model.BriteBAWaxman;
import org.cong.complexNetwork.model.BriteCirclePlane;
import org.cong.complexNetwork.model.BriteWaxman;
import org.cong.complexNetwork.util.ChartTools;
import org.cong.complexNetwork.util.NetworkTraitUtil;
import org.jfree.data.xy.XYDataset;

public class TestBrite {

  public static Logger logger = LogManager.getLogger(TestBrite.class);

  public static void log(UndirectedGraph ug) {
    final Set<Node> ns = ug.getNodes();
    final Set<Edge> es = ug.getEdges();
    int d = 0;
    for (final Node n : ns) {
      d += n.getDegree();
    }
    logger.debug("节点数: " + ns.size());
    logger.debug("边数: " + es.size() + (" * 2 = " + (es.size() * 2)));
    logger.debug("节点度之和: " + d);
    logger.debug("同配性系数： " + NetworkTraitUtil.assortativityCoefficient(ug));
    logger.debug("最小度： " + ug.getMinDegree());
    logger.debug("最大度： " + ug.getMaxDegree());
  }

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    logger.debug("开始");
    final BriteCirclePlane bcp = new BriteCirclePlane(10, 1000, 100.0);
    final UndirectedGraph ug = bcp.getGraph();
    // final BritePlane bcp = new BritePlane(10, 1000);
    bcp.addRandomNodes(100);
    BriteWaxman.generateEdges(bcp, 0.1, 0.9);
    log(ug);
    //BA.generateEdges(bcp, 2, 5900, new Tang(0.5));
    BriteBAWaxman.generateEdges(bcp, 2, 900,new BriteBAWaxman(0.1, 0.9,  bcp));
    //Tang.generateEdges(bcp, 2, 2900, 0.5);
    // BriteTang.generateEdges(bcp, 5, 900, 0.1);
    // BA.generateEdges(bcp, 5, 10);
    // Tang.generateEdges(bcp, 5, 900, 0.0);
    log(ug);

    NetworkTraitUtil.showRichClubChartByDegree(ug);
    final XYDataset xyds1 = ChartTools.toXYDatasetFD(ug);
    final XYDataset xyds = ChartTools.toXYDatasetRD(ug);
    ChartTools.drawChart(xyds);
    ChartTools.drawChart(xyds1);
  }

}
