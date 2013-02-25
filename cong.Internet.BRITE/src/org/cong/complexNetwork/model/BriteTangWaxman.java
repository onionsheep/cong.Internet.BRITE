package org.cong.complexNetwork.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.Plane;

public class BriteTangWaxman extends BriteTang {
  protected double     alpha;

  protected double     beta;
  private double       maxED;

  public static Logger logger = LogManager.getLogger(BriteTangWaxman.class);

  public BriteTangWaxman(final Plane plane,
                         final int oneNodeEdge,
                         final int nodeCount,
                         final double epsilon,
                         final double alpha,
                         final double beta) {
    super(plane, oneNodeEdge, nodeCount, epsilon);
    this.alpha = alpha;
    this.beta = beta;
    this.maxED = plane.MaxEuclideanDistance();
  }

  public double getAlpha() {
    return this.alpha;
  }

  public double getBeta() {
    return this.beta;
  }

  public double getMaxED() {
    return this.maxED;
  }

  @Override
  protected double probability(final Node n, final Node newNode) {
    double p = 0;
    final double ed = Plane.EuclideanDistanceBetween(n.getCoordinate(), newNode.getCoordinate());
    final double pw = this.alpha * Math.exp(-ed / (this.maxED * this.beta));
    p = Math.pow(n.getDegree(), this.epsilon) * pw;
    return p;
  }

  @Override
  protected void reCalculate() {
    this.maxED = this.plane.MaxEuclideanDistance();
  }

  public void setAlpha(final double alpha) {
    this.alpha = alpha;
  }

  public void setBeta(final double beta) {
    this.beta = beta;
  }

  /**
   * maxD will be set to 0 by default. otherwise some bug will happened
   * 
   * @param maxED
   */
  public void setMaxED(final double maxED) {
    this.maxED = 0;
  }
}
