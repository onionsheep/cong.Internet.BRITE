package org.cong.complexNetwork.test;

import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Edge;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.model.BriteBAWaxman;
import org.cong.complexNetwork.model.BritePlane;
import org.cong.complexNetwork.model.BriteWaxman;
import org.cong.complexNetwork.model.Plane;
import org.cong.complexNetwork.util.ChartTools;
import org.cong.complexNetwork.util.NetworkTraitUtil;
import org.jfree.data.xy.XYDataset;

public class TestBrite {

  public static Logger logger = LogManager.getLogger(TestBrite.class);

  public static void  log(Plane bp){
    final Set<Node> ns = bp.getGraph().getNodes();
    final Set<Edge> es = bp.getGraph().getEdges();
    int d = 0;
    for (final Node n : ns) {
      d += n.getDegree();
    }
    logger.debug("节点数: " + ns.size());
    logger.debug("边数: " + es.size() + (" * 2 = " + (es.size()*2)));
    logger.debug("节点度之和: " + d);
    final UndirectedGraph ug = bp.getGraph();
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
    //final BriteCirclePlane bp = new BriteCirclePlane(10, 1000, 100.0);
    final BritePlane bcp = new BritePlane(10, 1000);
    bcp.addRandomNodes(100);
    BriteWaxman.generateEdges(bcp, 0.1, 0.9);
    log(bcp);
    //BriteBA.generateEdges(bcp, 5, 19000);
    BriteBAWaxman.generateEdges(bcp, 2, 900, 0.1, 0.9);
    // BriteTang.generateEdges(bcp, 5, 900, 0.1);
    // BA.generateEdges(bcp, 5, 10);
    // Tang.generateEdges(bcp, 5, 900, 0.0);
    log(bcp);


    final UndirectedGraph ug = bcp.getGraph();
    NetworkTraitUtil.showRichClubChartByDegree(ug);
    final XYDataset xyds1 = ChartTools.toXYDatasetFD(ug);
    final XYDataset xyds = ChartTools.toXYDatasetRD(ug);
    ChartTools.drawChart(xyds);
    ChartTools.drawChart(xyds1);
  }

}
