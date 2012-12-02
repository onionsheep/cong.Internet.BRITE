package org.cong.complexNetwork.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.BriteCoordinate;
import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;

public class BritePlane extends Plane {

	public static Logger	logger	= LogManager.getLogger(BritePlane.class);
	protected int			hs;
	protected int			ls;
	protected BriteGraph	briteGraph;

	public BritePlane(int hs, int ls) {
		super(hs * ls, hs * ls);
		this.hs = hs;
		this.ls = ls;
		this.briteGraph = new BriteGraph();
		this.undirectedGraph = this.briteGraph;
	}

	public double EuclideanDistanceBetween(BriteCoordinate u, BriteCoordinate v) {
		double dis = 0.0;
		int ux = u.getHx() + u.getLx() * ls;
		int uy = u.getHy() + u.getLy() * ls;
		int vx = v.getHx() + v.getLx() * ls;
		int vy = v.getHy() + v.getLy() * ls;
		dis = Math.sqrt(Math.pow((ux - vx), 2) + Math.pow((uy - vy), 2));
		return dis;
	}

	public BriteNode randomNode() throws Exception {
		BriteNode node = null;
		int hx = java.util.concurrent.ThreadLocalRandom.current().nextInt(hs);
		int hy = java.util.concurrent.ThreadLocalRandom.current().nextInt(hs);
		int lx = java.util.concurrent.ThreadLocalRandom.current().nextInt(ls);
		int ly = java.util.concurrent.ThreadLocalRandom.current().nextInt(ls);
		BriteCoordinate briteCoordinate = new BriteCoordinate(hx, hy, lx, ly);
		node = new BriteNode(briteCoordinate);
		return node;
	}

	public BriteNode randomNodeNoDuplication() throws Exception {
		boolean result = true;
		BriteNode node = null;
		while (result) {
			node = this.randomNode();
			result = this.briteGraph.getBriteNodes().contains(node);
		}
		return node;
	}

	public BriteNode addOneRandomNode() throws Exception {
		boolean result = true;
		BriteNode node = null;
		while (result) {
			node = this.randomNodeNoDuplication();
			result = !this.briteGraph.getBriteNodes().add(node);
		}
		return node;
	}

	public boolean addRandomNode() throws Exception {
		return this.briteGraph.getBriteNodes().add(randomNodeNoDuplication());
	}

	public void addRandomNodes(int count) throws Exception {
		int i = 0;
		while (i < count) {
			if (this.addRandomNode()) {
				i++;
			}
		}
	}

	public double MaxEuclideanDistance() {
		double maxDis = 0.0;
		Set<BriteNode> nodes = briteGraph.getBriteNodes();
		Set<BriteNode> nodesRemain = new HashSet<>();
		nodesRemain.addAll(nodes);
		for (BriteNode node : nodes) {
			nodesRemain.remove(node);
			for (BriteNode node2 : nodesRemain) {
				double dis = this.EuclideanDistanceBetween(node.getBriteCoordinate(), node2.getBriteCoordinate());
				if (maxDis < dis) {
					maxDis = dis;
				}
			}
		}
		return maxDis;
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

	public int getHs() {
		return hs;
	}

	public int getLs() {
		return ls;
	}

	public BriteGraph getBriteGraph() {
		return briteGraph;
	}

}
