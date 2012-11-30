package org.cong.complexNetwork.model;

import java.util.Set;

import org.cong.complexNetwork.graph.BriteNode;

public class BriteBAAndWaxman extends BriteBA {

	public static Double probability(BriteNode newNode, BriteNode oldNode, BritePlane britePlane, Double alpha,
			Double beta) {
		Double probability = null;
		Double probabilityW = null;
		Set<BriteNode> nodes = britePlane.getBriteGraph().getBriteNodes();
		Double maxEuclideanDistance = britePlane.MaxEuclideanDistance();
		Double sumOfDegree = 0.0;
		for(BriteNode node : nodes){
			probabilityW = productOfProbability(newNode, node, britePlane, alpha, beta, maxEuclideanDistance);
			sumOfDegree += node.getDegree() * probabilityW;
		}
		probability = oldNode.getDegree() * productOfProbability(newNode, oldNode, britePlane, alpha, beta, maxEuclideanDistance) / sumOfDegree;
		return probability;
	}

	private static Double productOfProbability(BriteNode newNode, BriteNode oldNode, BritePlane britePlane, Double alpha,
			Double beta, Double maxEuclideanDistance) {
		Double probabilityW;
		Double euclideanDistance;
		euclideanDistance = britePlane.EuclideanDistanceBetween(newNode.getBriteCoordinate(), oldNode.getBriteCoordinate());
		probabilityW = alpha * Math.exp(-euclideanDistance / (beta * maxEuclideanDistance));
		return probabilityW;
	}
}
