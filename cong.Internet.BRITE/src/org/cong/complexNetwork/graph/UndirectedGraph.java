package org.cong.complexNetwork.graph;

import it.uniroma1.dis.wiserver.gexf4j.core.EdgeType;
import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.Mode;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.GexfImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.util.Sparse;

public class UndirectedGraph {
  protected Set<Node> nodes;
  protected Set<Edge> edges;
  protected Map<Node, Node> nodeMap;
  public static Logger logger = LogManager.getLogger(UndirectedGraph.class);

  public UndirectedGraph() {
    this.nodes = new HashSet<>();
    this.edges = new HashSet<>();
    this.nodeMap = new HashMap<>();
  }

  public boolean addNode(final Node node) {
    return this.nodes.add(node);
  }

  /**
   * 求邻接矩阵的秩，参考《基于大范围模式的互联网拓扑建模》（徐野）这本书上的代码。不知道对不对，经过有限次测试没有发现问题。不理解原理。
   * 
   * @return 邻接矩阵的秩
   */
  public int adjacentMatrixRank() {
    int r = 0;
    for (final Node n : this.nodes) {
      if (n.getDegree() > 0) {
        r++;
      }
    }
    return r;
  }

  /**
   * 连接给定的两个节点为一条边并添加入图
   * 
   * @param source
   *          源节点
   * @param target
   *          目标节点
   * @return 连接并添加成功返回true，否则false
   * @throws Exception
   */
  public boolean connect(final Node source, final Node target) throws Exception {
    if (source.equals(target)) {
      return false;
    }
    boolean result = false;
    final Edge edge = new Edge(source, target);
    result = this.edges.add(edge);
    if (result) {
      final boolean sr = source.connectNode(target);
      final boolean tr = target.connectNode(source);
      if(!sr || !tr){
        source.disConnectNode(target);
        target.disConnectNode(source);
        this.edges.remove(edge);
        throw new Exception("添加边发生异常，节点度可能发生错误.");
      }
    }
    return result;
  }

  /**
   * 检查图中是否含有给定节点
   * 
   * @param n
   *          给定节点
   * @return 含有true，不含有false
   */
  public boolean containsNode(final Node n) {
    return this.nodes.contains(n);
  }

  /**
   * 断开图中本来相连的两个点
   * 
   * @param source
   *          源节点
   * @param target
   *          目标节点
   * @return 断开成功返回true。图中不存在这两个节点之间的连接则返回false
   * @throws Exception
   */
  public boolean disConnect(final Node source, final Node target) throws Exception {
    boolean result = false;
    final Edge edge = new Edge(source, target);
    result = this.removeEdge(edge);
    if (result) {
      source.disConnectNode(target);
      target.disConnectNode(source);
    }
    return result;
  }

  public Set<Edge> getEdges() {
    return this.edges;
  }

  /**
   * @return 图中最大度的点的度数
   */
  public int getMaxDegree() {
    int maxD = 0;
    for (final Node n : this.nodes) {
      if (maxD < n.getDegree()) {
        maxD = n.getDegree();
      }
    }
    return maxD;
  }

  /**
   * @return 图中最小度的点的度数
   */
  public int getMinDegree() {
    int minD = Integer.MAX_VALUE;
    for (final Node n : this.nodes) {
      if (minD > n.getDegree()) {
        minD = n.getDegree();
      }
    }
    return minD;
  }

  /**
   * 获取与所给节点equals的图中的节点，不建议使用，效率低下
   * 
   * @param node
   *          给定节点
   * @return 图中的节点
   */
  public Node getNode(final Node node) {
    Node n0 = null;
    for (final Node n : this.nodes) {
      if (n.equals(node)) {
        n0 = n;
        break;
      }
    }
    return n0;
  }

  public Set<Node> getNodes() {
    return this.nodes;
  }

  /**
   * 从图中删除给定边
   * 
   * @param edge
   *          给定边
   * @return 删除成功返回true，图中不存在给定边或者删除失败返回false
   */
  public boolean removeEdge(final Edge edge) {
    boolean result;
    result = this.edges.remove(edge);
    final Node source = edge.getSource();
    final Node target = edge.getTarget();
    if (result) {
      source.disConnectNode(target);
      target.disConnectNode(source);
    }
    return result;
  }

  /**
   * 把图转换成一个邻接矩阵，图的规模比较小的时候适用
   * 
   * @return 存放邻接矩阵的二维数组
   */
  public double[][] toAdjacentMatrix() {
    final int nodeCount = this.nodes.size();
    final double[][] martrix = new double[nodeCount][nodeCount];
    final Node[] nodeArray = this.nodes.toArray(new Node[0]);
    final Edge[] edgeArray = this.edges.toArray(new Edge[0]);
    // List<Node> nodeList = Arrays.asList(nodeArray);

    for (final Edge e : edgeArray) {
      for (int i = 0; i < nodeArray.length; i++) {
        if (e.getSource().equals(nodeArray[i])) {
          for (int j = 0; j < nodeArray.length; j++) {
            if (e.getTarget().equals(nodeArray[j])) {
              martrix[i][j] = e.getWeight();
              martrix[j][i] = e.getWeight();
            }
          }
        }
      }
    }
    return martrix;
  }

  /**
   * 把图转换成一个邻接矩阵并写入指定文件，图的规模比较小的时候适用
   * 
   * @param filePath
   *          文件路径
   * @throws IOException
   */
  public void toAdjacentMatrixFile(final String filePath) throws IOException {
    final Node[] nodeArray = this.nodes.toArray(new Node[0]);
    // Edge[] edgeArray = edges.toArray(new Edge[0]);
    final File f = new File(filePath);
    StringBuffer sb;
    FileUtils.writeStringToFile(f, "AM = [\n", "UTF-8");
    for (final Node element : nodeArray) {
      sb = new StringBuffer();
      for (final Node element2 : nodeArray) {
        if (element.getConnectedNodes().contains(element2)) {
          sb.append("1,");
        } else {
          sb.append("0,");
        }
      }
      sb.deleteCharAt(sb.length() - 1);
      sb.append("\n");
      FileUtils.writeStringToFile(f, sb.toString(), "UTF-8", true);
    }
    FileUtils.writeStringToFile(f, "]", "UTF-8", true);
  }

  /**
   * 生成GEXF格式的图
   * 
   * @return GEXF格式的图
   */
  public Gexf toGexf() {
    final Gexf gexf = new GexfImpl();
    final it.uniroma1.dis.wiserver.gexf4j.core.Graph graph = gexf.getGraph();
    graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.STATIC);
    final Map<Node, it.uniroma1.dis.wiserver.gexf4j.core.Node> nodeMap = new HashMap<>();

    for (final Node node : this.nodes) {
      nodeMap.put(node, graph.createNode(Long.toHexString(node.getId())));
    }
    for (final Edge edge : this.edges) {
      final Node source = edge.getSource();
      final Node target = edge.getTarget();
      if (source == null) {
        logger.debug("source null");
      }
      nodeMap.get(source).connectTo(nodeMap.get(target));
    }
    return gexf;
  }

  /**
   * 把图转换成一个稀疏矩阵，此处转换为一个元素为Integer型的稀疏矩阵。
   * 
   * @return 稀疏矩阵
   */
  public Sparse<Integer> toSparse() {
    final Sparse<Integer> s = new Sparse<>();
    final List<Node> nl = new ArrayList<>();
    nl.addAll(this.nodes);
    for (final Edge e : this.edges) {
      final int i = 1 + nl.indexOf(e.getSource());
      final int j = 1 + nl.indexOf(e.getTarget());
      s.addElement(i, j, e.getWeight());
      s.addElement(j, i, e.getWeight());
    }
    return s;
  }

}
