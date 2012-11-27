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
		logger.debug("Nodes " + bp.getBriteGraph().getBriteNodes().size());
		logger.debug("Edges " + bp.getBriteGraph().getBriteEdges().size());
		
		BriteBA.generateEdges(bp, 5, 100);
		logger.debug("Nodes " + bp.getBriteGraph().getBriteNodes().size());
		logger.debug("Edges " + bp.getBriteGraph().getBriteEdges().size());
		
		Set<BriteNode> nodes = bp.getBriteGraph().getBriteNodes();
		Integer dSum = 0;
		for (BriteNode n : nodes) {
			dSum += n.getDegree();
		}

		//System.out.println(dSum);
		logger.debug("Sum of Degree " + dSum);
		
		Gexf gexf = bp.getBriteGraph().toGexf();
		logger.debug("generating file...");
		StaxGraphWriter graphWriter = new StaxGraphWriter();
		File f = new File("test_waxman.gexf");
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
