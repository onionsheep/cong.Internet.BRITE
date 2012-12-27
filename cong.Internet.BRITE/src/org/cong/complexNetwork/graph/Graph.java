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

public class Graph {
  protected Set<Edge>       edges;
  protected Map<Node, Node> nodesMap;
  // 使用Map存储Node,方便检索
  protected boolean         directed;

  public static Logger      logger           = LogManager.getLogger(Graph.class);

  public static int         defaultNodeCount = 5000;
  public static int         defaultEdgeCount = 5000;

  /**
   * 默认构造一个无向图
   */
  public Graph() {
    this(false);
  }

  /**
   * @param directed
   *          true为有向图,false为无向图
   */
  public Graph(boolean directed) {
    this.directed = directed;
    this.edges = new HashSet<>(defaultEdgeCount);
    this.nodesMap = new HashMap<>(defaultNodeCount);
  }

  public Node addNode(final Node node) {
    if (this.nodesMap.containsKey(node)) {
      return null;
    } else {
      return this.nodesMap.put(node, node);
    }
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
    final Edge edge = new Edge(source, target, directed);
    result = this.edges.add(edge);
    if (result) {
      boolean sr = false;
      boolean tr = false;
      if (!directed) {
        tr = target.connectNode(source);
      }
      sr = source.connectNode(target);
      if (!sr || !tr) {
        if (!directed) {
          target.disConnectNode(source);
        }
        source.disConnectNode(target);
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
  public boolean containsNode(final Node node) {
    return this.nodesMap.containsKey(node);
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
      if (!directed) {
        target.disConnectNode(source);
      }
      source.disConnectNode(target);
    }
    return result;
  }

  public Set<Edge> getEdges() {
    return this.edges;
  }

  /**
   * 获取与所给节点equals的图中的节点
   * 
   * @param node
   *          给定节点
   * @return 图中的节点
   */
  public Node getNode(final Node node) {
    return this.nodesMap.get(node);
  }

  public Set<Node> getNodes() {
    return this.nodesMap.keySet();
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
      if (!directed) {
        target.disConnectNode(source);
      }
      source.disConnectNode(target);
    }
    return result;
  }
  
  
  

  /**
   * @return 图中最大度的点的度数
   */
  public int getMaxDegree() {
    int maxD = 0;
    for (final Node n : this.getNodes()) {
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
    for (final Node n : this.getNodes()) {
      if (minD > n.getDegree()) {
        minD = n.getDegree();
      }
    }
    return minD;
  }

  

  /**
   * 把图转换成一个邻接矩阵，图的规模比较小的时候适用
   * 
   * @return 存放邻接矩阵的二维数组
   */
  public double[][] toAdjacentMatrix() {
    final int nodeCount = this.nodesMap.size();
    final double[][] martrix = new double[nodeCount][nodeCount];
    final Node[] nodeArray = this.getNodes().toArray(new Node[0]);
    final Edge[] edgeArray = this.edges.toArray(new Edge[0]);
    // List<Node> nodeList = Arrays.asList(nodeArray);

    for (final Edge e : edgeArray) {
      for (int i = 0; i < nodeArray.length; i++) {
        if (e.getSource().equals(nodeArray[i])) {
          for (int j = 0; j < nodeArray.length; j++) {
            if (e.getTarget().equals(nodeArray[j])) {
              martrix[i][j] = e.getWeight();
              if (!directed) {
                martrix[j][i] = e.getWeight();
              }
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
    final Node[] nodeArray = this.getNodes().toArray(new Node[0]);
    // Edge[] edgeArray = edges.toArray(new Edge[0]);
    final File f = new File(filePath);
    StringBuffer sb;
    int size = 2 * nodeArray.length;
    FileUtils.writeStringToFile(f, "AM = [\n", "UTF-8");
    for (final Node element : nodeArray) {
      sb = new StringBuffer(size);
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
    if (!directed) {
      graph.setDefaultEdgeType(EdgeType.UNDIRECTED);
    } else {
      graph.setDefaultEdgeType(EdgeType.DIRECTED);
    }
    graph.setMode(Mode.STATIC);
    final Map<Node, it.uniroma1.dis.wiserver.gexf4j.core.Node> nodeMap = new HashMap<>();

    for (final Node node : this.getNodes()) {
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
    nl.addAll(this.getNodes());
    int size = nl.size();
    Map<Node, Integer> map = new HashMap<>();
    for (int i = 0; i < size; i++) {
      map.put(nl.get(i), i);
    }
    for (final Edge e : this.edges) {
      final int i = 1 + map.get(e.getSource());// nl.indexOf(e.getSource());
      final int j = 1 + map.get(e.getTarget());// nl.indexOf(e.getTarget());
      s.addElement(i, j, e.getWeight());
      if (!directed) {
        s.addElement(j, i, e.getWeight());
      }
    }
    return s;
  }
  
  /**
   * 转换为一个n*3的二维数组存储的矩阵，第二维度的下标：0行下标1列下标2权重
   * 
   * @return 稀疏矩阵
   */
  public int[][] toSparseArray() {
    final int size = this.edges.size();
    final int[][] s = new int[size][3];
    int k = 0;
    final List<Node> nl = new ArrayList<>();
    nl.addAll(this.getNodes());
    for (final Edge e : this.edges) {
      s[k][0] = 1 + nl.indexOf(e.getTarget());
      s[k][1] = 1 + nl.indexOf(e.getSource());
      s[k][2] = e.getWeight();
      k++;
    }
    return s;
  }
}
