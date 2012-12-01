package org.cong.complexNetwork.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;
import org.cong.complexNetwork.graph.Node;

public class BriteBAAndWaxman extends BriteBA {

	public static double probability(BriteNode newNode, BriteNode oldNode, BritePlane britePlane, double alpha,
			double beta) {
		double probability = 0.0;
		double probabilityW = 0.0;
		Set<BriteNode> nodes = britePlane.getBriteGraph().getBriteNodes();
		double maxEuclideanDistance = britePlane.MaxEuclideanDistance();
		double sumOfDegree = 0.0;
		for(BriteNode node : nodes){
			probabilityW = productOfProbability(newNode, node, britePlane, alpha, beta, maxEuclideanDistance);
			sumOfDegree += node.getDegree() * probabilityW;
		}
		probability = oldNode.getDegree() * productOfProbability(newNode, oldNode, britePlane, alpha, beta, maxEuclideanDistance) / sumOfDegree;
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
	
	public static void generateEdges(BritePlane britePlane, int oneNodeEdge, int nodeCount, double alpha, double beta){
		double probability = 0.0;
		BriteGraph graph = britePlane.getBriteGraph();
		
		for (int i = 0; i < nodeCount; i++) {
			BriteNode[] nodeArray = graph.getBriteNodes().toArray(new BriteNode[0]);
			BriteNode newNode = britePlane.randomNodeNoDuplication();
			Map<Node, Double> nodeProbability = new HashMap<>();
			//计算节点的概率，并存储在0到1的区间上，只记录上限
			probability = 0.0;
			for(BriteNode oldNode : nodeArray){					
				probability += BriteBAAndWaxman.probability(newNode, oldNode, britePlane, alpha, beta);
				nodeProbability.put(oldNode, probability);
			}
		//添加oneNodeEdge条边，这oneNodeEdge添加边的时候不重新计算原来节点的度，概率
			BriteBA.addEdges(oneNodeEdge, graph, nodeArray, newNode, nodeProbability);

			graph.getNodes().add(newNode);
		}
	}
}
