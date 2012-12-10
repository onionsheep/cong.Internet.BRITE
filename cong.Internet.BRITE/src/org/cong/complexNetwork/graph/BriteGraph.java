package org.cong.complexNetwork.graph;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BriteGraph extends UndirectedGraph {
  public static Logger logger = LogManager.getLogger(BriteGraph.class);

  //  protected Set<BriteNode> briteNodes;
  //
  //  public BriteGraph() {
  //    super();
  //    this.briteNodes = new HashSet<>();
  //  }


  @Override
  public boolean connect(Node u, Node v) throws Exception {
    if (u.equals(v)) {
      return false;
    }
    boolean result = false;
    boolean r1 = false;
    boolean r2 = false;
    final Edge edge = new Edge(u, v);
    result = this.edges.add(edge);
    if (result) {
      r1 = u.connectNode(v);
      r2 = v.connectNode(u);
      if (r1 != r2) {
        logger.error("发生错误，程序有BUG，请把日志文件发给程序提供者，以便修正，谢谢！");
      }
    }
    return result;
  }

  //  public Set<BriteNode> getBriteNodes() {
  //    return this.briteNodes;
  //  }
  //
  //  @Override
  //  public Set<Node> getNodes() {
  //    super.nodes = SetUtils.typedSet(this.briteNodes, Node.class);
  //    return super.getNodes();
  //  }
  //
  //  @Override
  //  public double[][] toAdjacentMatrix() {
  //    this.nodes = SetUtils.typedSet(this.briteNodes, Node.class);
  //    return super.toAdjacentMatrix();
  //  }
  //
  //  @Override
  //  public Gexf toGexf() {
  //    this.nodes = SetUtils.typedSet(this.briteNodes, Node.class);
  //    return super.toGexf();
  //  }
}
