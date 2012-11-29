package org.cong.complexNetwork.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;
import org.cong.complexNetwork.graph.Node;

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
			Map<Node, Double> nodeProbability = new HashMap<>();
			//计算节点的概率，并存储在0到1的区间上，只记录上限
			probability = 0.0;
			for(BriteNode oldNode : nodes){					
				probability += probability(oldNode, nodes);
				nodeProbability.put(oldNode, probability);
			}
			
			while(m < oneNodeEdge){
				rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
				probability = 0.0;
				for(BriteNode oldNode : nodes){					
					probability = nodeProbability.get(oldNode);
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
