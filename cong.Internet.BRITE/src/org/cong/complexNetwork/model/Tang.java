package org.cong.complexNetwork.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.Node;

public class Tang {

	public static Double probability(Node i, Set<Node> nodes, Double epsilon) {
		Double probability = null;
		double sumOfDegree = 0.0;
		for (Node node : nodes) {
			sumOfDegree += Math.pow(node.getDegree(), 1 + epsilon);
		}
		probability = Math.pow(i.getDegree(), 1 + epsilon) / sumOfDegree;
		return probability;
	}

	public static void generateEdges(Plane plane, Integer oneNodeEdge, Integer nodeCount, Double epsilon) {
		Boolean result = false;
		Graph graph = plane.getGraph();
		Set<Node> nodes = graph.getNodes();
		for (Integer i = 0; i < nodeCount; i++) {
			// 计算节点的概率，并存储在0到1的区间上，只记录上限
			Map<Node, Double> nodeProbability = generateNodesProbability(epsilon, nodes);
			//新节点
			Node newNode = plane.randomNodeNoDuplication();
			//在原来的图中找一个节点，与新节点相连
			result = addAndConnectNewNode(graph, nodes, nodeProbability, newNode);
			if (result) {
				graph.getNodes().add(newNode);
			} else {
				i -= 1;
			}
			//添加剩余的oneNodeEdge-1条边
			Integer m = oneNodeEdge - 1;
			for(Integer j = 0; j < m; j++){
				Node u = getNodeByProbablity(nodes,nodeProbability);
				Node v = getNodeByProbablity(nodes,nodeProbability);
				result = graph.connect(u, v);
				if(!result){
					j -= 1;
				}
			}
		}
	}

	private static Node getNodeByProbablity(Set<Node> nodes, Map<Node, Double> nodeProbability){
		Node n = null;
		Double rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
		for (Node node : nodes) {
			Double probability = nodeProbability.get(node);
			if (rand <= probability) {
				n = node;
				break;
			}
		}
		return n;
	}
	
	private static Boolean addAndConnectNewNode(Graph graph, Set<Node> nodes, Map<Node, Double> nodeProbability,
			Node newNode) {
		Double probability;
		Double rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
		probability = 0.0;
		Boolean result = false;
		for (Node oldNode : nodes) {
			probability = nodeProbability.get(oldNode);
			if (rand <= probability) {
				result = graph.connect(newNode, oldNode);
				break;
			}
		}
		return result;
	}

	private static Map<Node, Double> generateNodesProbability(Double epsilon, Set<Node> nodes) {
		double probability;
		Map<Node, Double> nodeProbability = new HashMap<>();			
		probability = 0.0;
		for (Node oldNode : nodes) {
			probability += probability(oldNode, nodes, epsilon);
			nodeProbability.put(oldNode, probability);
		}
		return nodeProbability;
	}
}
