package org.cong.complexNetwork.model;

import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;

public class BriteBAAndWaxman extends BriteBA {

	public static double probability(BriteNode newNode, BriteNode oldNode, BritePlane britePlane, double alpha,
			double beta) {
		double probability = 0.0;
		double probabilityW = 0.0;
		BriteNode[] nodeArray = britePlane.getBriteGraph().getBriteNodes().toArray(new BriteNode[0]);
		double maxEuclideanDistance = britePlane.MaxEuclideanDistance();
		double sumOfDegree = 0.0;
		for (BriteNode node : nodeArray) {
			probabilityW = productOfProbability(newNode, node, britePlane, alpha, beta, maxEuclideanDistance);
			sumOfDegree += node.getDegree() * probabilityW;
		}
		probability = oldNode.getDegree()
				* productOfProbability(newNode, oldNode, britePlane, alpha, beta, maxEuclideanDistance) / sumOfDegree;
		return probability;
	}

	private static double productOfProbability(BriteNode newNode, BriteNode oldNode, BritePlane britePlane, double alpha,
			double beta, double maxEuclideanDistance) {
		double probabilityW;
		double euclideanDistance;
		euclideanDistance = britePlane.EuclideanDistanceBetween(newNode.getBriteCoordinate(), oldNode.getBriteCoordinate());
		probabilityW = alpha * Math.exp(-euclideanDistance / (beta * maxEuclideanDistance));
		return probabilityW;
	}

	public static void generateEdges(BritePlane britePlane, int oneNodeEdge, int nodeCount, double alpha, double beta) throws Exception {
		double probability = 0.0;
		BriteGraph graph = britePlane.getBriteGraph();

		for (int i = 0; i < nodeCount; i++) {
			BriteNode[] nodeArray = graph.getBriteNodes().toArray(new BriteNode[0]);
			BriteNode newNode = britePlane.randomNodeNoDuplication();
			//Map<Node, Double> nodeProbability = new HashMap<>();
			// 计算节点的概率，并存储在0到1的区间上，只记录上限
			probability = 0.0;
			double[] probabilities = new double[nodeArray.length];
			for (int j = 0; j < nodeArray.length; j++) {
				probability += BriteBA.probability(nodeArray[j], nodeArray);
				probabilities[j] = probability;
			}

			// 添加oneNodeEdge条边，这oneNodeEdge添加边的时候不重新计算原来节点的度，概率
			addEdges(oneNodeEdge, graph, nodeArray, probabilities, newNode);

			graph.getNodes().add(newNode);
		}
	}
}
