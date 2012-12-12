package org.cong.complexNetwork.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Node;

public class BriteTangWaxman extends Tang {
  public static Logger logger = LogManager.getLogger(BriteTangWaxman.class);

  private final double alpha;
  private final double beta;
  private final Plane plane;
  private double maxED;

  public BriteTangWaxman(final double epsilon,final double alpha, final double beta, final Plane plane) {
    super(epsilon);
    this.alpha = alpha;
    this.beta = beta;
    this.plane = plane;
    this.maxED = plane.MaxEuclideanDistance();
  }

  @Override
  protected  double probability(final Node n, final Node newNode) {
    double p = 0;
    final double ed = Plane.EuclideanDistanceBetween(n.getCoordinate(),
                                                     newNode.getCoordinate());
    final double pw = this.alpha * Math.exp(-ed / (this.maxED * this.beta));
    p = Math.pow(n.getDegree(), this.epsilon) * pw;
    return p;
  }
  @Override
  protected void reCalculate(){
    this.maxED = this.plane.MaxEuclideanDistance();
  }
}
