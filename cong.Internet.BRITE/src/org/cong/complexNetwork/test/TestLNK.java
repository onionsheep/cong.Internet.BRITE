package org.cong.complexNetwork.test;

import java.io.File;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.util.ChartTools;
import org.cong.complexNetwork.util.RichClubUtil;
import org.cong.complexNetwork.util.Tools;
import org.jfree.data.xy.XYDataset;

public class TestLNK {
  public static Logger logger = LogManager.getLogger(TestLNK.class);

  public static void main(String[] args) throws Exception {
    tLNK();
  }

  public static void tLNK() throws Exception {
    UndirectedGraph ug = Tools.readIPNodesFromLnkFile("E:\\WORKSPACE\\gitHome\\repository\\cong.Internet.BRITE\\cong.Internet.BRITE\\ips.txt");
    Set<Node> nodes = ug.getNodes();
    
    logger.debug(nodes.size());
    
    File f = new File("E:\\Users\\cong\\Documents\\MATLAB\\Sparse.m");
    FileUtils.writeStringToFile(f, ug.toSparse().toMatlabString());
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

    //RichClubUtil.showRichClubChartByOrder(ug);
    RichClubUtil.showRichClubChartByDegree(ug);
    XYDataset xyds1 = ChartTools.toXYDatasetFD(ug);
    XYDataset xyds = ChartTools.toXYDatasetRD(ug);
    ChartTools.drawChart(xyds);
    ChartTools.drawChart(xyds1);
  }
}
