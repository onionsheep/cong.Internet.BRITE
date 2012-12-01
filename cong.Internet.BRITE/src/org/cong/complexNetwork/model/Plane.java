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
	protected int width;
	protected int height;
	
	public Plane(int width, int height) {
		this.width = width;
		this.height = height;
		this.graph = new Graph();
	}
	
	public double EuclideanDistanceBetween(Coordinate u, Coordinate v) {
		double dis = 0;
		int ux = u.getX();
		int uy = u.getY();
		int vx = v.getX();
		int vy = v.getY();
		dis = Math.sqrt(Math.pow((ux - vx), 2) + Math.pow((uy - vy), 2));
		return dis;
	}
	

	public Node randomNode() {
		Node node = null;
		logger.debug(1);
		int x = java.util.concurrent.ThreadLocalRandom.current().nextInt(width);
		int y = java.util.concurrent.ThreadLocalRandom.current().nextInt(height);
		Coordinate coordinate = new Coordinate(x, y);
		node = new Node(coordinate.toString(), coordinate);
		return node;
	}
	
	public Node randomNodeNoDuplication(){
		boolean result = true;
		Node node = null;
		while (result) {
			node = this.randomNode();
			
			result = this.graph.getNodes().contains(node);
		}
		return node;
	}
	
	public Node addOneRandomNode(){
		boolean result = true;
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
	
	public void addRandomNodes(int count) {
		int i = 0;
		while (i < count) {
			if (this.addRandomNode()) {
				i++;
			}
		}
	}
	
	public double MaxEuclideanDistance() {
		double maxDis = 0.0;
		Set<Node> nodes = graph.getNodes();
		Set<Node> nodesRemain = new HashSet<>();
		nodesRemain.addAll(nodes);
		for (Node node : nodes) {
			nodesRemain.remove(node);
			for (Node node2 : nodesRemain) {
				double dis = this.EuclideanDistanceBetween(node.getCoordinate(), node2.getCoordinate());
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	
}
