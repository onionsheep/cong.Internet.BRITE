package org.cong.complexNetwork.graph;

import it.uniroma1.dis.wiserver.gexf4j.core.EdgeType;
import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.Mode;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.GexfImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
	
	public boolean connect(Node u, Node v) {
		boolean result = false;
		Edge edge = new Edge(u, v);
		result = this.edges.add(edge);
		if(result){
			u.connectNode(v);
			v.connectNode(u);
		}
		return result;
	}
	public Gexf toGexf(){
		Gexf gexf = new GexfImpl();
		it.uniroma1.dis.wiserver.gexf4j.core.Graph graph = gexf.getGraph();
		graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.STATIC);
		Map<Node,it.uniroma1.dis.wiserver.gexf4j.core.Node> nodeMap = new HashMap<>();
		for(Node node : nodes){
			nodeMap.put(node, graph.createNode(node.getId()));
		}
		for(Edge edge : edges){
			Node source = (BriteNode) edge.getSource();
			Node target = (BriteNode) edge.getTarget();
			nodeMap.get(source).connectTo(nodeMap.get(target));
		}
		return gexf;
	}
	

}
