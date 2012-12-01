package org.cong.complexNetwork.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.BriteGraph;
import org.cong.complexNetwork.graph.BriteNode;
import org.cong.complexNetwork.graph.Node;

public class BriteBA extends BA{
	
	public static Logger logger = LogManager.getLogger(BriteBA.class);
	
	public static double probability(BriteNode i, BriteNode[] nodeArray) {
		double probability = 0;
		int sumOfDegree = 0;
		for(BriteNode node : nodeArray){
			sumOfDegree += node.getDegree();
		}
		probability = 1.0 * i.getDegree() / sumOfDegree;
		return probability;
	}
	
	

	public static void generateEdges(BritePlane britePlane, int oneNodeEdge, int nodeCount){
		double probability = 0.0;
		BriteGraph graph = britePlane.getBriteGraph();
		
		for (int i = 0; i < nodeCount; i++) {
			BriteNode[] nodeArray = graph.getBriteNodes().toArray(new BriteNode[0]);
			BriteNode newNode = britePlane.randomNodeNoDuplication();
			
			Map<Node, Double> nodeProbability = new HashMap<>();
			//计算节点的概率，并存储在0到1的区间上，只记录上限
			probability = 0.0;
			for(BriteNode oldNode : nodeArray){					
				probability += BriteBA.probability(oldNode, nodeArray);
				nodeProbability.put(oldNode, probability);
			}
			//添加oneNodeEdge条边，这oneNodeEdge添加边的时候不重新计算原来节点的度，概率
			addEdges(oneNodeEdge, graph, nodeArray, newNode, nodeProbability);
			graph.getNodes().add(newNode);
		}
	}



	/**
	 * @param oneNodeEdge
	 * @param graph
	 * @param nodeArray
	 * @param newNode
	 * @param nodeProbability
	 * 在新节点和旧节点之间添加oneNodeEdge条边
	 */
	protected static void addEdges(int oneNodeEdge, BriteGraph graph, BriteNode[] nodeArray, BriteNode newNode,
			Map<Node, Double> nodeProbability) {
		double rand;
		Boolean result;
		double probability;
		int m = 0;
		while(m < oneNodeEdge){
			rand = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
			probability = 0.0;
			for(BriteNode oldNode : nodeArray){					
				probability = nodeProbability.get(oldNode);
				if (rand <= probability) {
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
