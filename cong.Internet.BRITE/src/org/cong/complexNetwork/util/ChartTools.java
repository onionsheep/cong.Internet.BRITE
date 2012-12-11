package org.cong.complexNetwork.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class ChartTools {
  public static Logger logger = LogManager.getLogger(ChartTools.class);

  public static void drawChart(XYDataset xyds, String title) {
    final NumberAxis localNumberAxis1 = new NumberAxis("X");
    localNumberAxis1.setAutoRangeIncludesZero(false);
    final NumberAxis localNumberAxis2 = new NumberAxis("Y");
    localNumberAxis2.setAutoRangeIncludesZero(false);
    final XYSplineRenderer localXYSplineRenderer = new XYSplineRenderer();
    final XYPlot localXYPlot = new XYPlot(xyds,
                                          localNumberAxis1,
                                          localNumberAxis2,
                                          localXYSplineRenderer);
    final JFreeChart localJFreeChart = new JFreeChart(title,
                                                      JFreeChart.DEFAULT_TITLE_FONT,
                                                      localXYPlot,
                                                      true);

    final ChartPanel cp = new ChartPanel(localJFreeChart);

    final ApplicationFrame af = new ApplicationFrame("");
    af.setContentPane(cp);
    af.pack();
    RefineryUtilities.centerFrameOnScreen(af);
    af.setVisible(true);
  }

  public static XYDataset toLogLogXYDataset(List<Double> xl, List<Double> yl, String title)
      throws Exception {
    final XYSeriesCollection xysc = new XYSeriesCollection();
    final XYSeries xys = new XYSeries(title);
    if (xl.size() == yl.size()) {
      final int l = xl.size();
      for (int i = 0; i < l; i++) {
        final double x = Math.log(xl.get(i));
        final double y = Math.log(yl.get(i));
        xys.add(x, y);
      }
    } else {
      throw new Exception("two list must have the same size");
    }
    xysc.addSeries(xys);
    return xysc;
  }

  public static XYDataset toXYDataset(List<Double> xl, List<Double> yl, String title)
      throws Exception {
    final XYSeriesCollection xysc = new XYSeriesCollection();
    final XYSeries xys = new XYSeries(title);
    if (xl.size() == yl.size()) {
      final int l = xl.size();
      for (int i = 0; i < l; i++) {
        final double x = xl.get(i);
        final double y = yl.get(i);
        xys.add(x, y);
      }
    } else {
      throw new Exception("two list must have the same size");
    }
    xysc.addSeries(xys);
    return xysc;
  }

  public static XYDataset toXYDatasetFD(UndirectedGraph g) throws Exception {
    final Set<Node> nodes = g.getNodes();
    final Node[] na = nodes.toArray(new Node[0]);
    Arrays.sort(na, new NodeDegreeComparator("desc"));

    final List<Double> lx = new ArrayList<>();
    final List<Double> ly = new ArrayList<>();

    final Set<Integer> ds = new HashSet<>();
    int f = 1;
    for (final Node element : na) {
      final int d = element.getDegree();
      if (!ds.contains(d) && (d != 0)) {
        ds.add(d);
        lx.add(1.0 * f);
        ly.add(1.0 * d);
        f += 1;
      }
    }

    return toLogLogXYDataset(lx, ly, "频-度");
  }

  public static XYDataset toXYDatasetRD(UndirectedGraph g) throws Exception {
    final Set<Node> nodes = g.getNodes();
    final Node[] na = nodes.toArray(new Node[0]);
    Arrays.sort(na, new NodeDegreeComparator("desc"));

    final List<Double> lx = new ArrayList<>();
    final List<Double> ly = new ArrayList<>();

    for (int i = 0; i < na.length; i++) {
      final int d = na[i].getDegree();
      if (d != 0) {
        lx.add(1.0 + i);
        ly.add(1.0 * na[i].getDegree());
      }

    }

    return toLogLogXYDataset(lx, ly, "秩-度");
  }

}
