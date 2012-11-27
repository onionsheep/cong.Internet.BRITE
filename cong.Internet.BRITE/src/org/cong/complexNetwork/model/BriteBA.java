package org.cong.complexNetwork.model;

import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;

public class BriteBA {
	
	public static Logger logger = LogManager.getLogger(BriteBA.class);
	
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
		BriteGraph graph = britePlane.getBriteGraph();
		Set<BriteNode> nodes = graph.getBriteNodes();
		for (Integer i = 0; i < nodeCount; i++) {
			BriteNode newNode = britePlane.randomNodeNoDuplication();
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
			graph.getNodes().add(newNode);
		}
	}
}
