package org.cong.complexNetwork.test;

import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.util.ChartTools;
import org.cong.complexNetwork.util.NetworkTraitUtil;
import org.cong.complexNetwork.util.Tools;
import org.jfree.data.xy.XYDataset;

public class TestLNK {
  public static Logger logger = LogManager.getLogger(TestLNK.class);

  public static void main(String[] args) throws Exception {
    tLNK();
  }

  public static void tLNK() throws Exception {
    logger.debug("开始");
    // UndirectedGraph ug = Tools.readIPNodesFromLnkFile("ips.txt");
    final UndirectedGraph ug = Tools.readIPNodesFromLnkFile("a-r20030421sr");

    final Set<Node> nodes = ug.getNodes();

    logger.debug("节点个数： " + nodes.size());

    // File f = new File("E:\\Users\\cong\\Documents\\MATLAB\\Sparse.m");
    // FileUtils.writeStringToFile(f, ug.toSparse().toMatlabString());

    // for (Node n : nodes) {
    // logger.debug(n + "\t" + n.getDegree());
    // }
    // logger.debug("toAdjacentMatrix");
    // double[][] am = ug.toAdjacentMatrix();
    // logger.debug("writing file...");
    // ug.toAdjacentMatrixFile("E:\\Users\\cong\\Documents\\MATLAB\\AdjacentMatrix.m");
    // Tools.writeAdjacentMatrixtoFile(am,
    // "E:\\Users\\cong\\Documents\\MATLAB\\AdjacentMatrix.m");
    // logger.debug("done.");

    // RichClubUtil.showRichClubChartByOrder(ug);
    logger.debug("同配性系数： " + NetworkTraitUtil.assortativityCoefficient(ug));

    NetworkTraitUtil.showRichClubChartByDegree(ug);
    final XYDataset xydsfd = ChartTools.toXYDatasetFD(ug);
    final XYDataset xydsrf = ChartTools.toXYDatasetRD(ug);
    ChartTools.drawChart(xydsfd, "频数--度,幂律分布（已取双对数）");
    ChartTools.drawChart(xydsrf, "秩---度,幂律分布（已取双对数）");
  }
}
