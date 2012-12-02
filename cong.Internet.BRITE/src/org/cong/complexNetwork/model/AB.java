package org.cong.complexNetwork.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Node;

public class AB {
	public static Logger	logger	= LogManager.getLogger(AB.class);

	public static double probability(Node i, Node[] nodeArray) {
		double probability = 0;
		int sumOfDegree = 0;
		for (Node node : nodeArray) {
			sumOfDegree += (node.getDegree() + 1);
		}
		probability = 1.0 * (i.getDegree() + 1) / sumOfDegree;
		return probability;
	}
}
