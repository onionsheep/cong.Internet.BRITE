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

  public static void drawChart(XYDataset xyds) {
    NumberAxis localNumberAxis1 = new NumberAxis("X");
    localNumberAxis1.setAutoRangeIncludesZero(false);
    NumberAxis localNumberAxis2 = new NumberAxis("Y");
    localNumberAxis2.setAutoRangeIncludesZero(false);
    XYSplineRenderer localXYSplineRenderer = new XYSplineRenderer();
    XYPlot localXYPlot = new XYPlot(xyds, localNumberAxis1, localNumberAxis2, localXYSplineRenderer);
    JFreeChart localJFreeChart = new JFreeChart("",
                                                JFreeChart.DEFAULT_TITLE_FONT,
                                                localXYPlot,
                                                true);

    ChartPanel cp = new ChartPanel(localJFreeChart);

    ApplicationFrame af = new ApplicationFrame("");
    af.setContentPane(cp);
    af.pack();
    RefineryUtilities.centerFrameOnScreen(af);
    af.setVisible(true);
  }

  public static XYDataset toLogLogXYDataset(List<Double> xl, List<Double> yl, String title)
      throws Exception {
    XYSeriesCollection xysc = new XYSeriesCollection();
    XYSeries xys = new XYSeries(title);
    if (xl.size() == yl.size()) {
      int l = xl.size();
      for (int i = 0; i < l; i++) {
        double x = Math.log(xl.get(i));
        double y = Math.log(yl.get(i));
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
    XYSeriesCollection xysc = new XYSeriesCollection();
    XYSeries xys = new XYSeries(title);
    if (xl.size() == yl.size()) {
      int l = xl.size();
      for (int i = 0; i < l; i++) {
        double x = xl.get(i);
        double y = yl.get(i);
        xys.add(x, y);
      }
    } else {
      throw new Exception("two list must have the same size");
    }
    xysc.addSeries(xys);
    return xysc;
  }

  public static XYDataset toXYDatasetRD(UndirectedGraph g) throws Exception {
    Set<Node> nodes = g.getNodes();
    Node[] na = nodes.toArray(new Node[0]);
    Arrays.sort(na, new NodeDegreeComparator("desc"));

    List<Double> lx = new ArrayList<>();
    List<Double> ly = new ArrayList<>();

    Set<Integer> ds = new HashSet<>();
    int f = 1;
    for (int i = 0; i < na.length; i++) {
      int d = na[i].getDegree();
      if (!ds.contains(d)) {
        ds.add(d);
        lx.add(1.0 * f);
        ly.add(1.0 * d);
        f += 1;
      }
    }
    return toLogLogXYDataset(lx, ly, "秩-度");
  }

  public static XYDataset toXYDatasetFD(UndirectedGraph g) throws Exception {
    Set<Node> nodes = g.getNodes();
    Node[] na = nodes.toArray(new Node[0]);
    Arrays.sort(na, new NodeDegreeComparator("desc"));

    List<Double> lx = new ArrayList<>();
    List<Double> ly = new ArrayList<>();

    for (int i = 0; i < na.length; i++) {
      lx.add(1.0 + i);
      ly.add(1.0 * na[i].getDegree());
    }
    return toLogLogXYDataset(lx, ly, "频-度");
  }

}
