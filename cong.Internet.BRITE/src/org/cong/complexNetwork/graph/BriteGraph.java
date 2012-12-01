package org.cong.complexNetwork.graph;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.SetUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BriteGraph extends UndirectedGraph {
	public static Logger			logger	= LogManager.getLogger(BriteGraph.class);

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
	public boolean connect(Node u, Node v) {
		if (u.equals(v)) {
			return false;
		}
		boolean result = false;
		boolean r1 = false;
		boolean r2 = false;
		Edge edge = new Edge(u, v);
		result = this.edges.add(edge);
		if (result) {
			r1 = u.connectNode(v);
			r2 = v.connectNode(u);
			if(r1 != r2){
				logger.error("发生错误，程序有BUG，请把日志文件发给程序提供者，以便修正，谢谢！");
			}
		}
		return result;
	}

	@Override
	public Gexf toGexf() {
		nodes = SetUtils.typedSet(briteNodes, Node.class);
		return super.toGexf();
	}
	
	@Override
	public int[][] toAdjacentMatrix(){
		nodes = SetUtils.typedSet(briteNodes, Node.class);
		return super.toAdjacentMatrix();
	}
}
