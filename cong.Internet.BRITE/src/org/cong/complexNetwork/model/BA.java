package org.cong.complexNetwork.model;

import java.util.Set;

import org.cong.complexNetwork.graph.BriteNode;

public class BA {

	public static Double probability(BriteNode i, Set<BriteNode> nodes) {
		Double probability = null;
		Integer sumOfDegree = 0;
		for (BriteNode node : nodes) {
			sumOfDegree += node.getDegree() + 1;
		}
		probability = 1.0 * (i.getDegree() + 1) / sumOfDegree;
		return probability;
	}
	
	
}
