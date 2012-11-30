package org.cong.complexNetwork.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.Node;

public class BA {

	public static Logger	logger	= LogManager.getLogger(BA.class);

	public static Double probability(Node i, Set<Node> nodes) {
		Double probability = null;
		Integer sumOfDegree = 0;
		for (Node node : nodes) {
			sumOfDegree += node.getDegree();
		}
		probability = 1.0 * i.getDegree() / sumOfDegree;
		return probability;
	}

	public static void generateEdges(Plane plane, Integer oneNodeEdge, Integer nodeCount) {
		Double rand = 0.0;
		Boolean result = false;
		Double probability = 0.0;
		Graph graph = plane.getGraph();
		Set<Node> nodes = graph.getNodes();
		for (Integer i = 0; i < nodeCount; i++) {
			
			Node newNode = plane.randomNodeNoDuplication();
			
			Integer m = 0;
			Map<Node, Double> nodeProbability = new HashMap<>();
			
			// 计算节点的概率，并存储在0到1的区间上，只记录上限
			probability = 0.0;
			logger.debug(i);
			logger.debug(nodes.size());
			for (Node oldNode : nodes) {
				
				probability += probability(oldNode, nodes);
				nodeProbability.put(oldNode, probability);
				
			}
			// 添加m条边，这m添加边的时候不重新计算原来节点的度，概率
			while (m < oneNodeEdge) {
				
				rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
				probability = 0.0;
				for (Node oldNode : nodes) {
					probability = nodeProbability.get(oldNode);
					
					if (rand <= probability) {
						result = graph.connect(newNode, oldNode);
						
						if (result) {
							
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
