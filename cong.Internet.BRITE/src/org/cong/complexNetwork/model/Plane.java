package org.cong.complexNetwork.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Coordinate;
import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.util.EuclideanDistanceUtil;

public class Plane {
  protected int        height;
  protected Graph      ug;
  protected int        width;
  public static Logger logger = LogManager.getLogger(Plane.class);

  private static long distanceSqr(final Coordinate u, final Coordinate v) {
    long dis = 0;
    final int xd = u.getX() - v.getX();
    final int yd = u.getY() - v.getY();
    dis = (1l * xd * xd) + (1l * yd * yd);
    return dis;
  }

  private static long distanceSqr(final Node u, final Node v) {
    return Plane.distanceSqr(u.getCoordinate(), v.getCoordinate());
  }

  public static double EuclideanDistanceBetween(final Coordinate u, final Coordinate v) {
    return Math.sqrt(Plane.distanceSqr(u, v));
  }

  public Plane(final int width, final int height) {
    this.width = width;
    this.height = height;
    this.ug = new Graph();
  }

  public Node addOneRandomNode() throws Exception {
    boolean result = true;
    Node node = null;
    while (result) {
      node = this.randomNode();
      if (this.ug.addNode(node) == null) {
        result = false;
      }
    }
    return node;
  }

  protected Node addRandomNode() throws Exception {
    return this.ug.addNode(this.randomNodeNoDuplication());
  }

  public void addRandomNodes(final int count) throws Exception {
    int i = 0;
    while (i < count) {
      if (this.addRandomNode() != null) {
        i++;
      }
    }
  }

  public Graph getGraph() {
    return this.ug;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  /**
   * O(n*log(n))的算法，先Graham's Scan法求解凸包，然后旋转卡壳法求凸包直径 Graham's
   * Scan法求解凸包维基百科http://en.wikipedia.org/wiki/Graham_scan Graham's
   * Scan法求解凸包中文http://www.cnblogs.com/devymex/archive/2010/08/09/1795392.html
   * 旋转卡壳法英文http://cgm.cs.mcgill.ca/~orm/rotcal.frame.html
   * 旋转卡壳法简要的中文网页http://www.cppblog.com/staryjy/archive/2010/09/25/101412.html
   * 
   * @return 平面中最远两点之间的距离
   */
  public double MaxEuclideanDistance() {
    final Set<Node> nodes = this.ug.getNodes();
    final List<Node> nl = new ArrayList<>();
    nl.addAll(nodes);
    return EuclideanDistanceUtil.maxEd(nl);
  }

  public Node randomNode() throws Exception {
    Node node = null;
    final int x = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.width);
    final int y = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.height);
    final Coordinate coordinate = new Coordinate(x, y);
    node = new Node(coordinate.toLong(), coordinate);
    return node;
  }

  public Node randomNodeNoDuplication() throws Exception {
    boolean result = true;
    Node node = null;
    while (result) {
      node = this.randomNode();
      result = this.ug.containsNode(node);
    }
    return node;
  }

}
