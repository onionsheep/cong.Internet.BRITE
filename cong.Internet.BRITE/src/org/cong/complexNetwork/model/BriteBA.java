package org.cong.complexNetwork.model;

import java.util.Set;

import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;

public class BriteBA {
	public static Double probability(BriteNode i, Set<BriteNode> nodes) {
		Double probability = null;
		Integer sumOfDegree = 0;
		for(BriteNode node : nodes){
			sumOfDegree += node.getDegree();
		}
		probability = 1.0 * i.getDegree() / sumOfDegree;
		return probability;
	}

	public static void generateEdges(BritePlane britePlane, Integer oneNodeEdge, Integer nodeCount){
		Double rand = 0.0;
		Boolean result;
		Double probability = 0.0;
		BriteGraph graph = britePlane.getGraph();
		Set<BriteNode> nodes = graph.getNodes();
		for (Integer i = 0; i < nodeCount; i++) {
			BriteNode newNode = britePlane.addOneRandomNode();
			Integer m = 0;
			while(m < oneNodeEdge){
				rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
				probability = 0.0;
				for(BriteNode oldNode : nodes){
					probability += probability(oldNode, nodes);
					if (rand <= probability) {
						result = false;
						result = graph.connect(newNode, oldNode);
						if(result){
							m += 1;
						}
						break;
					}
				}
			}
		}
	}
}
