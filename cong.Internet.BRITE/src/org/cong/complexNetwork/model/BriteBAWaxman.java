package org.cong.complexNetwork.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Node;

public class BriteBAWaxman extends BriteBA {
  protected final double alpha;

  protected final double beta;
  protected double       maxED;
  public static Logger   logger = LogManager.getLogger(BriteBAWaxman.class);

  public BriteBAWaxman(final Plane plane,
                       final int oneNodeEdge,
                       final int nodeCount,
                       final double alpha,
                       final double beta) {
    super(plane, oneNodeEdge, nodeCount);
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
    p = n.getDegree() * pw;
    return p;
  }

  @Override
  protected void reCalculate() {
    this.maxED = this.plane.MaxEuclideanDistance();
  }

  public void setMaxED(final double maxED) {
    this.maxED = maxED;
  }
}
