package org.cong.complexNetwork.util;

import java.util.ArrayList;
import java.util.Arrays;
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

import Jama.Matrix;

public class ChartTools {
  public static Logger logger = LogManager.getLogger(ChartTools.class);

  public static void drawChart(final XYDataset xyds, final String title) {
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

  public static XYDataset toLogLogXYDataset(final List<Double> xl,
                                            final List<Double> yl,
                                            final String title) throws Exception {
    final XYSeriesCollection xysc = new XYSeriesCollection();
    final XYSeries xys = toLogLogXYSeries(xl, yl, title);
    xysc.addSeries(xys);
    return xysc;
  }

  protected static XYSeries toLogLogXYSeries(final List<Double> xl,
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
    final XYSeries xys = toXYSeries(xl, yl, title);
    xysc.addSeries(xys);
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

  public static XYDataset toXYDatasetPowerLaw(final UndirectedGraph g) throws Exception {
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
    final XYSeries xys0 = toLogLogXYSeries(xl0, yl0, "幂律分布（降序序号--度）");
    final XYSeries xys1 = toLogLogXYSeries(xl1, yl1, "幂律分布（度累计分布--度）");
    final XYSeries xys2 = toLogLogXYSeries(xl2, yl2, "幂律分布（频数--度）");
    
    xysc.addSeries(xys0);
    xysc.addSeries(xys1);
    xysc.addSeries(xys2);
    return xysc;
  }

  public static XYDataset eigPowerLaw(UndirectedGraph ug) throws Exception{
//    SimpleMatrix m = new SimpleMatrix(ug.toAdjacentMatrix());
//    SimpleEVD<?> evd = m.eig();
//    int ecount = evd.getNumberOfEigenvalues();
//    double[] es = new double[ecount];
//    for(int i = 0; i < ecount; i++){
//      Complex64F c = evd.getEigenvalue(i);
//      es[i] = c.getReal();
//    }
    Matrix ma = new Matrix(ug.toAdjacentMatrix());
    double[] es = ma.eig().getRealEigenvalues();
    int ecount = es.length;
    Arrays.sort(es);
    final List<Double> xl0 = new ArrayList<>();
    final List<Double> yl0 = new ArrayList<>();
    for(int i = 0; i < ecount; i++){
      xl0.add(ecount - i + 0.0);
      yl0.add(es[i]);
    }
    return toXYDataset(xl0,yl0,"幂律分布");
  }
}
