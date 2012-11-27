package org.cong.complexNetwork.graph;

import it.uniroma1.dis.wiserver.gexf4j.core.EdgeType;
import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.Mode;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.GexfImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class BriteGraph extends Graph{
	protected Set<BriteNode>	briteNodes;
	protected Set<BriteEdge>	briteEdges;

	public BriteGraph() {
		briteNodes = new HashSet<>();
		briteEdges = new HashSet<>();
	}

	public Set<BriteNode> getBriteNodes() {
		return briteNodes;
	}

	public Set<BriteEdge> getBriteEdges() {
		return briteEdges;
	}

	public Boolean connect(BriteNode u, BriteNode v) {
		Boolean result = null;
		BriteEdge edge = new BriteEdge(u, v);
		result = this.briteEdges.add(edge);
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
		for(BriteNode node : briteNodes){
			nodeMap.put(node, graph.createNode(node.getId()));
		}
		for(BriteEdge edge : briteEdges){
			BriteNode source = (BriteNode) edge.getSource();
			BriteNode target = (BriteNode) edge.getTarget();
			nodeMap.get(source).connectTo(nodeMap.get(target));
		}
		return gexf;
	}
}
