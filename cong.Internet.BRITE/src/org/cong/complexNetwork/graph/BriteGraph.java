package org.cong.complexNetwork.graph;

import it.uniroma1.dis.wiserver.gexf4j.core.EdgeType;
import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.Mode;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.GexfImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BriteGraph {
	protected Set<BriteNode>	nodes;
	protected Set<BriteEdge>	edges;

	public BriteGraph() {
		nodes = new HashSet<>();
		edges = new HashSet<>();
	}

	public Set<BriteNode> getNodes() {
		return nodes;
	}

	public Set<BriteEdge> getEdges() {
		return edges;
	}

	public Boolean connect(BriteNode u, BriteNode v) {
		Boolean result = null;
		BriteEdge edge = new BriteEdge(u, v);
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
		Map<BriteNode,it.uniroma1.dis.wiserver.gexf4j.core.Node> nodeMap = new HashMap<>();
		for(BriteNode node : nodes){
			nodeMap.put(node, graph.createNode(node.getId()));
		}
		for(BriteEdge edge : edges){
			BriteNode source = (BriteNode) edge.getSource();
			BriteNode target = (BriteNode) edge.getTarget();
			nodeMap.get(source).connectTo(nodeMap.get(target));
		}
		return gexf;
	}
}
