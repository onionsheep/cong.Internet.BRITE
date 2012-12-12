package org.cong.complexNetwork.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Node;

public class BriteBAWaxman extends BA {
  public static Logger logger = LogManager.getLogger(BriteBAWaxman.class);

  private final double alpha;
  private final double beta;
  private final Plane plane;
  private double maxED;
  public BriteBAWaxman(final double alpha, final double beta, final Plane plane) {
    super();
    this.alpha = alpha;
    this.beta = beta;
    this.plane = plane;
    this.maxED = plane.MaxEuclideanDistance();
  }

  public double getAlpha() {
    return this.alpha;
  }

  public double getBeta() {
    return this.beta;
  }

  @Override
  protected  double probability(final Node n, final Node newNode) {
    double p = 0;
    final double ed = Plane.EuclideanDistanceBetween(n.getCoordinate(),
                                                     newNode.getCoordinate());
    final double pw = this.alpha * Math.exp(-ed / (this.maxED * this.beta));
    p = n.getDegree() * pw;
    return p;
  }
  @Override
  protected void reCalculate(){
    this.maxED = this.plane.MaxEuclideanDistance();
  }
}
