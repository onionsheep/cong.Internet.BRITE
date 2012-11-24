package org.cong.complexNetwork.model;

import java.util.HashSet;
import java.util.Set;

import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;

public class BriteWaxman {

	public static Double probability(Double alpha, Double beta, Double euclideanDistance, Double maxEuclideanDistance) {
		Double probability = null;
		probability = alpha * Math.exp(-euclideanDistance / (beta * maxEuclideanDistance));
		return probability;
	}

	public static void generateEdges(BritePlane britePlane, Double alpha, Double beta) {
		Double rand = 0.0;
		Double probability = 0.0;
		Double euclideanDistance = null;
		Double maxEuclideanDistance = britePlane.MaxEuclideanDistance();
		BriteGraph graph = britePlane.getGraph();
		Set<BriteNode> nodes = graph.getNodes();
		Set<BriteNode> nodesRemain = new HashSet<>();
		nodesRemain.addAll(nodes);
		for (BriteNode u : nodes) {
			nodesRemain.remove(u);
			for (BriteNode v : nodesRemain) {
				rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
				euclideanDistance = britePlane.EuclideanDistanceBetween(u.getCoordinate(), v.getCoordinate());
				probability = probability(alpha, beta, euclideanDistance, maxEuclideanDistance);
				if (rand <= probability) {
					graph.connect(u, v);
				}
			}
		}
	}
}
