package org.cong.complexNetwork.test;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.StaxGraphWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.BriteNode;
import org.cong.complexNetwork.model.BriteBA;
import org.cong.complexNetwork.model.BriteBAAndWaxman;
import org.cong.complexNetwork.model.BritePlane;
import org.cong.complexNetwork.model.BriteWaxman;

public class Test {

public static Logger logger = LogManager.getLogger(Test.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BritePlane bp = new BritePlane(100000, 100000);
		bp.addRandomNodes(100);
		BriteWaxman.generateEdges(bp, 0.6, 0.3);
		
		logger.debug("Nodes " + bp.getBriteGraph().getNodes().size());
		logger.debug("Edges " + bp.getBriteGraph().getEdges().size());
		
		//BriteBA.generateEdges(bp, 5, 900);
		BriteBAAndWaxman.generateEdges(bp, 5, 900);
		//BriteTang.generateEdges(bp, 5, 900, 0.1);
		//BA.generateEdges(bp, 5, 10);
		//Tang.generateEdges(bp, 5, 900, 0.0);
		logger.debug("Nodes " + bp.getBriteGraph().getNodes().size());
		logger.debug("Edges " + bp.getBriteGraph().getEdges().size());
		
		Set<BriteNode> nodes = bp.getBriteGraph().getBriteNodes();
		Integer dSum0 = 0;
		Integer dSum1 = 0;
		for (BriteNode n : nodes) {
			dSum0 += n.getDegree();
			dSum1 += n.getConnectedNodes().size(); 
		}
		
		logger.debug("Sum of Degree0 " + dSum0);
		logger.debug("Sum of Degree1 " + dSum1);
		
		Gexf gexf = bp.getBriteGraph().toGexf();
		logger.debug("generating file...");
		StaxGraphWriter graphWriter = new StaxGraphWriter();
		File f = new File("test.gexf");
		Writer out;
		try {
			out = new FileWriter(f, false);
			graphWriter.writeToStream(gexf, out, "UTF-8");
			logger.info(f.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
