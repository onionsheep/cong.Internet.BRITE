package org.cong.complexNetwork.model;

import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.graph.Node;

public class BA {

	public static Logger	logger	= LogManager.getLogger(BA.class);

	public static double probability(Node i, Node[] nodeArray) {
		double probability = 0;
		int sumOfDegree = 0;
		for (Node node : nodeArray) {
			sumOfDegree += node.getDegree();
		}
		probability = 1.0 * i.getDegree() / sumOfDegree;
		return probability;
	}

	public static void generateEdges(Plane plane, int oneNodeEdge, int nodeCount) {
		double probability = 0.0;
		UndirectedGraph undirectedGraph = plane.getGraph();
		Set<Node> nodes = undirectedGraph.getNodes();
		Node[] nodeArray = nodes.toArray(new Node[0]);
		for (int i = 0; i < nodeCount; i++) {
			Node newNode = plane.randomNodeNoDuplication();
			// 计算节点的概率，并存储在0到1的区间上，只记录上限
			probability = 0.0;
			double[] probabilities = new double[nodeArray.length];
			for (int j = 0; j < nodeArray.length; j++) {
				probability += BA.probability(nodeArray[j], nodeArray);
				probabilities[j] = probability;
			}
			// 添加oneNodeEdge条边，这oneNodeEdge添加边的时候不重新计算原来节点的度，概率
			addEdges(oneNodeEdge, undirectedGraph, nodeArray, probabilities, newNode);

			undirectedGraph.getNodes().add(newNode);
		}
	}

	/**
	 * @param oneNodeEdge
	 * @param undirectedGraph
	 * @param nodeArray
	 * @param newNode
	 * @param nodeProbability
	 *          在新节点和旧节点之间添加oneNodeEdge条边
	 */
	protected static void addEdges(int oneNodeEdge, UndirectedGraph undirectedGraph, Node[] nodeArray, double[] probabilities, Node newNode) {
		double rand;
		Boolean result;
		double probability;
		int m = 0;
		while (m < oneNodeEdge) {
			rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
			probability = 0.0;
			for (int j = 0; j < nodeArray.length; j++) {
				probability = probabilities[j];
				if (rand <= probability) {
					result = undirectedGraph.connect(newNode, nodeArray[j]);
					if (result) {
						m += 1;
					}
					break;
				}
			}
		}
	}
}
