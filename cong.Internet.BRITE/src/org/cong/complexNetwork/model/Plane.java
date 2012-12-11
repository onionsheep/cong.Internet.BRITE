package org.cong.complexNetwork.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Coordinate;
import org.cong.complexNetwork.graph.Node;
import org.cong.complexNetwork.graph.UndirectedGraph;

public class Plane {
  public static Logger logger = LogManager.getLogger(Plane.class);
  protected UndirectedGraph ug;
  protected int width;
  protected int height;

  public Plane(int width, int height) {
    this.width = width;
    this.height = height;
    this.ug = new UndirectedGraph();
  }

  public Node addOneRandomNode() throws Exception {
    boolean result = true;
    Node node = null;
    while (result) {
      node = this.randomNode();
      result = !this.ug.addNode(node);
    }
    return node;
  }

  protected boolean addRandomNode() throws Exception {
    return this.ug.addNode(this.randomNodeNoDuplication());
  }

  public void addRandomNodes(int count) throws Exception {
    int i = 0;
    while (i < count) {
      if (this.addRandomNode()) {
        i++;
      }
    }
  }

  public double EuclideanDistanceBetween(Coordinate u, Coordinate v) {
    double dis = 0;
    final int xd = u.getX() - v.getX();
    final int yd = u.getY() - v.getY();
    dis = Math.sqrt((xd * xd) + (yd * yd));
    return dis;
  }

  public UndirectedGraph getGraph() {
    return this.ug;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  // TODO : 搞一个更高效的算法求最远距离
  // http://blog.csdn.net/zmlcool/article/details/6727351
  public double MaxEuclideanDistance() {
    double maxDis = 0.0;
    final Set<Node> nodes = this.ug.getNodes();
    final Set<Node> nodesRemain = new HashSet<>();
    nodesRemain.addAll(nodes);
    for (final Node node : nodes) {
      nodesRemain.remove(node);
      for (final Node node2 : nodesRemain) {
        final double dis = this.EuclideanDistanceBetween(node.getCoordinate(),
                                                         node2.getCoordinate());
        if (maxDis < dis) {
          maxDis = dis;
        }
      }
    }
    return maxDis;
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
