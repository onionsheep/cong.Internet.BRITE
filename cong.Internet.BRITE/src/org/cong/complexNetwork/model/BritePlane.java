package org.cong.complexNetwork.model;

import java.util.HashSet;
import java.util.Set;

import org.cong.complexNetwork.graph.BriteCoordinate;
import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;
import org.cong.complexNetwork.graph.Coordinate;

public class BritePlane {
	protected Integer			hs;
	protected Integer			ls;
	protected BriteGraph	graph;

	public BritePlane(Integer hs, Integer ls) {
		this.hs = hs;
		this.ls = ls;
		this.graph = new BriteGraph();
	}

	public Double EuclideanDistanceBetween(BriteCoordinate u, BriteCoordinate v) {
		Double dis = null;
		Integer ux = u.getHx() + u.getLx() * ls;
		Integer uy = u.getHy() + u.getLy() * ls;
		Integer vx = v.getHx() + v.getLx() * ls;
		Integer vy = v.getHy() + v.getLy() * ls;
		dis = Math.sqrt(Math.pow((ux - vx), 2) + Math.pow((uy - vy), 2));
		return dis;
	}

	public BriteNode randomNode() {
		BriteNode node = null;
		Integer hx = java.util.concurrent.ThreadLocalRandom.current().nextInt(hs);
		Integer hy = java.util.concurrent.ThreadLocalRandom.current().nextInt(hs);
		Integer lx = java.util.concurrent.ThreadLocalRandom.current().nextInt(ls);
		Integer ly = java.util.concurrent.ThreadLocalRandom.current().nextInt(ls);
		BriteCoordinate coordinate = new BriteCoordinate(hx, hy, lx, ly);
		node = new BriteNode(coordinate.toString(), coordinate);
		return node;
	}

	public boolean addRandomNode(){
		BriteNode node = this.randomNode();
		return this.graph.getNodes().add(node);
	}
	
	public void addRandomNodes(Integer count){
		Integer i = 0;
		while(i < count){
			if(this.addRandomNode()){
				i++;
			}
		}
	}
	
	public Double MaxEuclideanDistance() {
		Double maxDis = 0.0;
		Set<BriteNode> nodes = graph.getNodes();
		Set<BriteNode> nodesRemain = new HashSet<>();
		nodesRemain.addAll(nodes);
		for (BriteNode node : nodes) {
			nodesRemain.remove(node);
			for (BriteNode node2 : nodesRemain) {
				Double dis = this.EuclideanDistanceBetween(node.getCoordinate(), node2.getCoordinate());
				if (maxDis < dis) {
					maxDis = dis;
				}
			}
		}
		return maxDis;
	}

	public Double MaxEuclideanDistance2() {
		Double maxDis = 0.0;
		Set<BriteNode> nodes = graph.getNodes();

		Set<Coordinate> nodesCordinates = new HashSet<>();

		for (BriteNode node : nodes) {

		}
		return maxDis;
	}

	public Integer getHs() {
		return hs;
	}

	public Integer getLs() {
		return ls;
	}

	public BriteGraph getGraph() {
		return graph;
	}
	
}
