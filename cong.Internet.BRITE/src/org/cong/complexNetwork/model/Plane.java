package org.cong.complexNetwork.model;

import java.util.HashSet;
import java.util.Set;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Coordinate;
import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.Node;

public class Plane {
	public static Logger			logger	= LogManager.getLogger(Plane.class);
	protected Graph	graph;
	protected Integer width;
	protected Integer height;
	
	public Plane(Integer width, Integer height) {
		this.width = width;
		this.height = height;
		this.graph = new Graph();
	}
	
	public Double EuclideanDistanceBetween(Coordinate u, Coordinate v) {
		Double dis = null;
		Integer ux = u.getX();
		Integer uy = u.getY();
		Integer vx = v.getX();
		Integer vy = v.getY();
		dis = Math.sqrt(Math.pow((ux - vx), 2) + Math.pow((uy - vy), 2));
		return dis;
	}
	

	public Node randomNode() {
		Node node = null;
		logger.debug(1);
		Integer x = java.util.concurrent.ThreadLocalRandom.current().nextInt(width);
		Integer y = java.util.concurrent.ThreadLocalRandom.current().nextInt(height);
		Coordinate coordinate = new Coordinate(x, y);
		node = new Node(coordinate.toString(), coordinate);
		return node;
	}
	
	public Node randomNodeNoDuplication(){
		Boolean result = true;
		Node node = null;
		while (result) {
			node = this.randomNode();
			
			result = this.graph.getNodes().contains(node);
		}
		return node;
	}
	
	public Node addOneRandomNode(){
		Boolean result = true;
		Node node = null;
		while(result){
			node = this.randomNode();
			result = !this.graph.getNodes().add(node);
		}
		return node;
	}
	public boolean addRandomNode(){
		return this.graph.getNodes().add(randomNodeNoDuplication());
	}
	
	public void addRandomNodes(Integer count) {
		Integer i = 0;
		while (i < count) {
			if (this.addRandomNode()) {
				i++;
			}
		}
	}
	
	public Double MaxEuclideanDistance() {
		Double maxDis = 0.0;
		Set<Node> nodes = graph.getNodes();
		Set<Node> nodesRemain = new HashSet<>();
		nodesRemain.addAll(nodes);
		for (Node node : nodes) {
			nodesRemain.remove(node);
			for (Node node2 : nodesRemain) {
				Double dis = this.EuclideanDistanceBetween(node.getCoordinate(), node2.getCoordinate());
				if (maxDis < dis) {
					maxDis = dis;
				}
			}
		}
		return maxDis;
	}

	public Graph getGraph() {
		return graph;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	
}
