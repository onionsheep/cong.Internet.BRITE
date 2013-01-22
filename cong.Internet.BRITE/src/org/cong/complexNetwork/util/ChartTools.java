package org.cong.complexNetwork.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.Node;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import Jama.Matrix;

public class ChartTools {
  public static Logger logger = LogManager.getLogger(ChartTools.class);

  public static void showChart(final XYDataset xyds, final String title) {
    final JFreeChart localJFreeChart = drawChart(xyds, title);

    final ChartPanel cp = new ChartPanel(localJFreeChart);

    final ApplicationFrame af = new ApplicationFrame("");
    af.setContentPane(cp);
    af.pack();
    RefineryUtilities.centerFrameOnScreen(af);
    af.setVisible(true);
  }

  /**
   * @param xyds
   * @param title
   * @return
   */
  public static JFreeChart drawChart(final XYDataset xyds, final String title) {
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
    return localJFreeChart;
  }

  public static boolean writeChartToFile(JFreeChart chart, File file) {
    try (FileOutputStream fos = new FileOutputStream(file);) {
      ChartUtilities.writeChartAsPNG(fos, chart, 800, 600);
      return true;
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean writeChartToFile(JFreeChart chart, String filePath) {
    return writeChartToFile(chart, new File(filePath));
  }

  public static XYDataset eigPowerLaw(final Graph ug) throws Exception {
    // SimpleMatrix m = new SimpleMatrix(ug.toAdjacentMatrix());
    // SimpleEVD<?> evd = m.eig();
    // int ecount = evd.getNumberOfEigenvalues();
    // double[] es = new double[ecount];
    // for(int i = 0; i < ecount; i++){
    // Complex64F c = evd.getEigenvalue(i);
    // es[i] = c.getReal();
    // }
    final Matrix ma = new Matrix(ug.toAdjacentMatrix());
    final double[] es = ma.eig().getRealEigenvalues();
    final int ecount = es.length;
    Arrays.sort(es);
    final List<Double> xl0 = new ArrayList<>();
    final List<Double> yl0 = new ArrayList<>();
    for (int i = 0; i < ecount; i++) {
      if (es[i] > 1) {
        final double x = Math.log(ecount - i);
        final double y = Math.log(es[i]);
        if (!Double.isInfinite(y) && !Double.isNaN(y)) {
          xl0.add(x);
          yl0.add(y);
        }
      }
    }
    return ChartTools.toXYDataset(xl0, yl0, "序号--特征向量");
  }

  public static XYDataset toLogLogXYDataset(final List<Double> xl,
                                            final List<Double> yl,
                                            final String title) throws Exception {
    final XYSeriesCollection xysc = new XYSeriesCollection();
    final XYSeries xys = ChartTools.toLogLogXYSeries(xl, yl, title);
    xysc.addSeries(xys);
    return xysc;
  }

  public static XYSeries toLogLogXYSeries(final List<Double> xl,
                                             final List<Double> yl,
                                             final String title) throws Exception {
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
    return xys;
  }

  public static XYDataset toXYDataset(final List<Double> xl,
                                      final List<Double> yl,
                                      final String title) throws Exception {
    final XYSeriesCollection xysc = new XYSeriesCollection();
    final XYSeries xys = ChartTools.toXYSeries(xl, yl, title);
    xysc.addSeries(xys);
    return xysc;
  }

  public static XYDataset toXYDatasetPowerLaw(final Graph g) throws Exception {
    final Set<Node> nodes = g.getNodes();
    final Node[] na = nodes.toArray(new Node[0]);
    Arrays.sort(na, new NodeDegreeComparator("desc"));

    final List<Double> xl0 = new ArrayList<>();
    final List<Double> yl0 = new ArrayList<>();
    final List<Double> xl1 = new ArrayList<>();
    final List<Double> yl1 = new ArrayList<>();
    final List<Double> xl2 = new ArrayList<>();
    final List<Double> yl2 = new ArrayList<>();

    int lastd = na[0].getDegree();
    int f = 0;

    for (int i = 0; i < na.length; i++) {
      final int d = na[i].getDegree();
      if (d != 0) {
        xl0.add(1.0 + i);
        yl0.add(1.0 * d);
        if (d == lastd) {
          f++;
        } else {
          xl1.add(1.0 * i);
          yl1.add(1.0 * lastd);
          xl2.add(1.0 * f);
          yl2.add(1.0 * lastd);
          lastd = d;
          f = 1;
        }
      }
    }

    final XYSeriesCollection xysc = new XYSeriesCollection();
    final XYSeries xys0 = ChartTools.toLogLogXYSeries(xl0, yl0, "幂律分布（降序序号--度）");
    final XYSeries xys1 = ChartTools.toLogLogXYSeries(xl1, yl1, "幂律分布（度累计分布--度）");
    final XYSeries xys2 = ChartTools.toLogLogXYSeries(xl2, yl2, "幂律分布（频数--度）");

    xysc.addSeries(xys0);
    xysc.addSeries(xys1);
    xysc.addSeries(xys2);
    return xysc;
  }

  protected static XYSeries toXYSeries(final List<Double> xl,
                                       final List<Double> yl,
                                       final String title) throws Exception {
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
    return xys;
  }
}
