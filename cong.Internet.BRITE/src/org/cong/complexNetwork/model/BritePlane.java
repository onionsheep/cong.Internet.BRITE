package org.cong.complexNetwork.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.BriteCoordinate;
import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;

public class BritePlane extends Plane {

  public static Logger logger = LogManager.getLogger(BritePlane.class);
  protected int hs;
  protected int ls;
  protected BriteGraph briteGraph;

  public BritePlane(int hs, int ls) {
    super(hs * ls, hs * ls);
    this.hs = hs;
    this.ls = ls;
    this.briteGraph = new BriteGraph();
    this.undirectedGraph = this.briteGraph;
  }

  @Override
  public BriteNode addOneRandomNode() throws Exception {
    boolean result = true;
    BriteNode node = null;
    while (result) {
      node = this.randomNodeNoDuplication();
      result = !this.briteGraph.addNode(node);
      //result = !this.briteGraph.getBriteNodes().add(node);
    }
    return node;
  }

  @Override
  public boolean addRandomNode() throws Exception {
    return this.briteGraph.addNode(this.randomNodeNoDuplication());
    //return this.briteGraph.getBriteNodes().add(this.randomNodeNoDuplication());
  }

  @Override
  public void addRandomNodes(int count) throws Exception {
    int i = 0;
    while (i < count) {
      if (this.addRandomNode()) {
        i++;
      }
    }
  }

  public double EuclideanDistanceBetween(BriteCoordinate u, BriteCoordinate v) {
    double dis = 0.0;
    final int ux = u.getHx() + (u.getLx() * this.ls);
    final int uy = u.getHy() + (u.getLy() * this.ls);
    final int vx = v.getHx() + (v.getLx() * this.ls);
    final int vy = v.getHy() + (v.getLy() * this.ls);
    dis = Math.sqrt(Math.pow((ux - vx), 2) + Math.pow((uy - vy), 2));
    return dis;
  }

  public BriteGraph getBriteGraph() {
    return this.briteGraph;
  }

  public int getHs() {
    return this.hs;
  }

  public int getLs() {
    return this.ls;
  }

  // TODO: write a better function to calculate the max distance
  // public double MaxEuclideanDistance2() {
  // double maxDis = 0.0;
  // Set<BriteNode> nodes = undirectedGraph.getNodes();
  // Set<Coordinate> nodesCordinates = new HashSet<>();
  // for (BriteNode node : nodes) {
  //
  // }
  // return maxDis;
  // }

  @Override
  public double MaxEuclideanDistance() {
    double maxDis = 0.0;
    final Set<BriteNode> nodes = this.briteGraph.getBriteNodes();
    final Set<BriteNode> nodesRemain = new HashSet<>();
    nodesRemain.addAll(nodes);
    for (final BriteNode node : nodes) {
      nodesRemain.remove(node);
      for (final BriteNode node2 : nodesRemain) {
        final double dis = this.EuclideanDistanceBetween(node.getBriteCoordinate(),
                                                         node2.getBriteCoordinate());
        if (maxDis < dis) {
          maxDis = dis;
        }
      }
    }
    return maxDis;
  }

  @Override
  public BriteNode randomNode() throws Exception {
    BriteNode node = null;
    final int hx = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.hs);
    final int hy = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.hs);
    final int lx = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.ls);
    final int ly = java.util.concurrent.ThreadLocalRandom.current().nextInt(this.ls);
    final BriteCoordinate briteCoordinate = new BriteCoordinate(hx, hy, lx, ly);
    node = new BriteNode(briteCoordinate);
    return node;
  }

  @Override
  public BriteNode randomNodeNoDuplication() throws Exception {
    boolean result = true;
    BriteNode node = null;
    while (result) {
      node = this.randomNode();
      result = this.briteGraph.getBriteNodes().contains(node);
    }
    return node;
  }

}
