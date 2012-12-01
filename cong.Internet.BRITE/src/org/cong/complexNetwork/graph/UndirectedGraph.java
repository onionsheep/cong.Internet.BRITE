package org.cong.complexNetwork.graph;

import it.uniroma1.dis.wiserver.gexf4j.core.EdgeType;
import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.Mode;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.GexfImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UndirectedGraph {
	protected Set<Node>	nodes;
	protected Set<Edge>	edges;
	
	public UndirectedGraph(){
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
	
	public double[][] toAdjacentMatrix(){
		int nodeCount = nodes.size();
		double[][] martrix = new double[nodeCount][nodeCount];
		Node[] nodeArray = nodes.toArray(new Node[0]);
		Edge[] edgeArray = edges.toArray(new Edge[0]);
		//List<Node> nodeList = Arrays.asList(nodeArray);
		
		for(Edge e : edgeArray){
			for(int i =0; i< nodeArray.length;i++){
				if(e.getSource().equals(nodeArray[i])){
					for(int j = 0; j < nodeArray.length; j++){
						if(e.getTarget().equals(nodeArray[j])){
							martrix[i][j] = e.getWeight();
							martrix[j][i] = e.getWeight();
						}
					}
				}
			}
		}
		return martrix;
	}

}
