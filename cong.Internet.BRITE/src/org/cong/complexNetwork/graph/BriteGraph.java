package org.cong.complexNetwork.graph;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.SetUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BriteGraph extends Graph {
	public static Logger			logger	= LogManager.getLogger(BriteGraph.class);
	public static Integer			t1			= 0;
	public static Integer			t2			= 0;

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
		if (u.equals(v)) {
			return false;
		}
		Boolean result = false;
		Boolean r1 = false;
		Boolean r2 = false;
		Edge edge = new Edge(u, v);
		result = this.edges.add(edge);
		if (result) {
			r1 = u.connectNode(v);
			r2 = v.connectNode(u);
			if(!r1.equals(r2)){
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
}
