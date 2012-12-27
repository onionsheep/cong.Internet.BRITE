package org.cong.complexNetwork.test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.util.ChartTools;
import org.cong.complexNetwork.util.Tools;

public class TestLNK {
  public static Logger logger = LogManager.getLogger(TestLNK.class);

  public static void main(final String[] args) throws Exception {
    tLNK();
  }

  public static void tLNK() throws Exception {
    logger.debug("开始");
    // final UndirectedGraph ug = Tools.readIPNodesFromLnkFile("ips.txt");
    //final UndirectedGraph ug = Tools.readIPNodesFromLnkFile("a-r20030421sr");
    final Graph ug = Tools.readIPNodesFromLnkFile("test.txt");
    TestBrite.log(ug);

    // String s = ug.toSparse().toMatlabString();
    // FileUtils.writeStringToFile(new
    // File("C:\\users\\cong\\desktop\\Sparse.m"), s);
    // logger.debug("file C:\\users\\cong\\desktop\\Sparse.m");

    //Tools.writeGexfFile(ug.toGexf(), "C:\\users\\cong\\desktop\\net.gexf");
    // logger.debug("file C:\\users\\cong\\desktop\\net.gexf");

    //ug.toAdjacentMatrixFile("C:\\users\\cong\\desktop\\matrix.m");
    // NetworkTraitUtil.showRichClubChartByDegree(ug);
    //NetworkTraitUtil.showRichClubChartByOrder(ug);
     ChartTools.drawChart(ChartTools.toXYDatasetPowerLaw(ug), "三个幂率分布图像");
    // ChartTools.drawChart(ChartTools.eigPowerLaw(ug), "null");
  }
}
