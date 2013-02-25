package org.cong.complexNetwork.test;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.StaxGraphWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import cong.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Edge;
import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.Plane;
import org.cong.complexNetwork.model.BriteBA;
import org.cong.complexNetwork.model.BriteCirclePlane;
import org.cong.complexNetwork.model.BriteTangWaxman;
import org.cong.complexNetwork.model.BriteWaxman;
import org.cong.complexNetwork.util.ChartTools;
import org.cong.complexNetwork.util.NetworkTraitUtil;

/**
 * 测试类，模型的启动方式这里即可。
 * 
 * @author onion_sheep(onion_sheep@163.com)
 */
public class TestBrite {

  public static Logger logger = LogManager.getLogger(TestBrite.class);

  public static void log(final Graph ug) {
    final Set<Node> ns = ug.getNodes();
    final Set<Edge> es = ug.getEdges();
    int d = 0;
    for (final Node n : ns) {
      d += n.getDegree();
    }
    TestBrite.logger.debug("节点数: " + ns.size());
    TestBrite.logger.debug("边数: " + es.size() + (" * 2 = " + (es.size() * 2)));
    TestBrite.logger.debug("节点度之和: " + d);
    TestBrite.logger.debug("同配性系数: " + NetworkTraitUtil.assortativityCoefficient(ug));
    TestBrite.logger.debug("最小度： " + ug.getMinDegree());
    TestBrite.logger.debug("最大度： " + ug.getMaxDegree());
  }

  /**
   * @param args
   * @throws Exception
   */
  public static void main(final String[] args) throws Exception {
    TestBrite.logger.debug("开始");
    final Plane bcp = new BriteCirclePlane(10, 1000, 1000.0);
    // final BritePlane bcp = new BritePlane(10, 1000);
    bcp.addRandomNodes(100);
    BriteWaxman.generateEdges(new BriteWaxman(bcp, 0.1, 0.9));
    final Graph ug = bcp.getGraph();
    TestBrite.log(ug);
    // BriteBA.generateEdges(new BriteBA(bcp, 2, 9900));
    // BriteBA.generateEdges(new BriteBAWaxman(bcp, 2, 9900, 0.1, 0.9));
    // BriteBA.generateEdges(new BriteTang(bcp, 2, 9900, 0.5));
    BriteBA.generateEdges(new BriteTangWaxman(bcp, 2, 9900, 0.5, 0.1, 0.9));
    TestBrite.log(ug);
    //TestBrite.savetoSparse(ug, "C:\\users\\cong\\desktop\\Sparse.m");
    // NetworkTraitUtil.showRichClubChartByDegree(ug);
    // NetworkTraitUtil.showRichClubChartByOrder(ug);
     ChartTools.showChart(ChartTools.toXYDatasetPowerLaw(ug), "三个幂率分布图像");
    //ChartTools.drawChart(ChartTools.eigPowerLaw(ug), "部分特征向量的幂律分布(已取双对数)");
  }

  public static void saveToGexf(final Plane plane, final String filePath) {
    final Gexf gexf = plane.getGraph().toGexf();
    TestBrite.logger.debug("正在输出为文件...");
    final StaxGraphWriter graphWriter = new StaxGraphWriter();
    final File f = new File(filePath);
    Writer out;
    try {
      out = new FileWriter(f, false);
      graphWriter.writeToStream(gexf, out, "UTF-8");
      TestBrite.logger.info("文件已保存" + f.getAbsolutePath());
    }
    catch (final IOException e) {
      TestBrite.logger.error("保存文件失败");
      e.printStackTrace();
    }
  }

  public static void savetoSparse(final Graph ug, final String filePath) throws IOException {
    final String s = ug.toSparse().toMatlabString();
    FileUtils.writeStringToFile(new File(filePath), s, false);
    TestBrite.logger.debug(filePath);
  }

}
