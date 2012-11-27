package org.cong.complexNetwork.graph;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.SetUtils;



public class BriteGraph extends Graph{
	protected Set<BriteNode>	briteNodes;

	public BriteGraph() {
		super();
		briteNodes = new HashSet<>();
	}

	public Set<BriteNode> getBriteNodes() {
		return briteNodes;
	}

	@Override
	public Set<Node> getNodes() {
		return SetUtils.typedSet(briteNodes, Node.class);
	}
	@Override
	public Boolean connect(Node u, Node v) {
		Boolean result = null;
		Edge edge = new Edge(u, v);
		result = this.edges.add(edge);
		if (result) {
			u.connectNode(v);
			v.connectNode(u);
		}
		return result;
	}

	@Override
	public Gexf toGexf() {
		nodes = SetUtils.typedSet(briteNodes, Node.class);
		return super.toGexf();
	}
}
