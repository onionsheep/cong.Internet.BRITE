package org.cong.complexNetwork.test;

import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Edge;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.model.BriteBA;
import org.cong.complexNetwork.model.BriteCirclePlane;
import org.cong.complexNetwork.model.BriteTangWaxman;
import org.cong.complexNetwork.model.BriteWaxman;
import org.cong.complexNetwork.model.Plane;
import org.cong.complexNetwork.util.ChartTools;
import org.cong.complexNetwork.util.NetworkTraitUtil;
import org.jfree.data.xy.XYDataset;

/**
 * 测试类，模型的启动方式这里即可。
 * 
 * @author onion_sheep(onion_sheep@163.com)
 */
public class TestBrite {

  public static Logger logger = LogManager.getLogger(TestBrite.class);

  public static void log(final UndirectedGraph ug) {
    final Set<Node> ns = ug.getNodes();
    final Set<Edge> es = ug.getEdges();
    int d = 0;
    for (final Node n : ns) {
      d += n.getDegree();
    }
    logger.debug("节点数: " + ns.size());
    logger.debug("边数: " + es.size() + (" * 2 = " + (es.size() * 2)));
    logger.debug("节点度之和: " + d);
    logger.debug("同配性系数: " + NetworkTraitUtil.assortativityCoefficient(ug));
    logger.debug("最小度： " + ug.getMinDegree());
    logger.debug("最大度： " + ug.getMaxDegree());
  }

  /**
   * @param args
   * @throws Exception
   */
  public static void main(final String[] args) throws Exception {
    logger.debug("开始");
    final Plane bcp = new BriteCirclePlane(10, 1000, 1000.0);
    // final BritePlane bcp = new BritePlane(10, 1000);
    bcp.addRandomNodes(100);
    BriteWaxman.generateEdges(new BriteWaxman(bcp, 0.1, 0.9));
    final UndirectedGraph ug = bcp.getGraph();
    log(ug);
    // BriteBA.generateEdges(new BriteBA(bcp, 2, 4900));
    // BriteBA.generateEdges(new BriteBAWaxman(bcp, 2, 4900, 0.1, 0.9));
    // BriteBA.generateEdges(new BriteTang(bcp, 2, 4900, 0.5));
    BriteBA.generateEdges(new BriteTangWaxman(bcp, 2, 4900, 0.5, 0.1, 0.9));
    log(ug);

    NetworkTraitUtil.showRichClubChartByDegree(ug);
    NetworkTraitUtil.showRichClubChartByOrder(ug);
    final XYDataset xydsfd = ChartTools.toXYDatasetFD(ug);
    final XYDataset xydsrf = ChartTools.toXYDatasetRD(ug);
    ChartTools.drawChart(xydsfd, "频数--度,幂律分布（已取双对数）");
    ChartTools.drawChart(xydsrf, "秩---度,幂律分布（已取双对数）");
  }

}
