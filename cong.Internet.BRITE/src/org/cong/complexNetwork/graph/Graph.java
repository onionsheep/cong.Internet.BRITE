package org.cong.complexNetwork.graph;

import java.util.HashSet;
import java.util.Set;

public class Graph {
	protected Set<Node>	nodes;
	protected Set<Edge>	edges;
	
	public Graph(){
		nodes = new HashSet<>();
		edges = new HashSet<>();
	}
	
	public Set<Node> getNodes() {
		return nodes;
	}
	public Set<Edge> getEdges() {
		return edges;
	}
	
	

}
