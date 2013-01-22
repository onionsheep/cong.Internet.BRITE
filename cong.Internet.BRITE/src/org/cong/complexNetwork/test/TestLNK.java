package org.cong.complexNetwork.test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.util.ChartTools;
import org.cong.complexNetwork.util.Tools;

public class TestLNK {
  public static Logger logger = LogManager.getLogger(TestLNK.class);

  public static void main(final String[] args) throws Exception {
    TestLNK.tLNK();
  }

  public static void tLNK() throws Exception {
    TestLNK.logger.debug("开始");
    // final Graph ug = Tools.readStringNodesFromFile("ips.txt");
     final Graph ug = Tools.readStringNodesFromFile("CAIDA_DATA/a-r20030421sr", false);
    //final Graph ug = Tools.readStringNodesFromFile("test.txt", false);
    TestBrite.log(ug);

    // String s = ug.toSparse().toMatlabString();
    // FileUtils.writeStringToFile(new
    // File("C:\\users\\cong\\desktop\\Sparse.m"), s);
    // logger.debug("file C:\\users\\cong\\desktop\\Sparse.m");

    // Tools.writeGexfFile(ug.toGexf(), "C:\\users\\cong\\desktop\\net.gexf");
    // logger.debug("file C:\\users\\cong\\desktop\\net.gexf");

    // ug.toAdjacentMatrixFile("C:\\users\\cong\\desktop\\matrix.m");
    // NetworkTraitUtil.showRichClubChartByDegree(ug);
    // NetworkTraitUtil.showRichClubChartByOrder(ug);
    //ChartTools.showChart(ChartTools.toXYDatasetPowerLaw(ug), "三个幂率分布图像");
    ChartTools.writeChartToFile(ChartTools.drawChart(ChartTools.toXYDatasetPowerLaw(ug), "三个幂率分布图像"), "Charts/test.png");
    logger.debug("finished");
    // ChartTools.drawChart(ChartTools.eigPowerLaw(ug), "null");
  }
}
