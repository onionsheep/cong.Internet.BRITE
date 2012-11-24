package org.cong.complexNetwork.test;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.StaxGraphWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.cong.complexNetwork.model.BritePlane;
import org.cong.complexNetwork.model.BriteWaxman;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testWaxman();
	}

	private static void testWaxman() {
		BritePlane bp = new BritePlane(10000, 10000);
		bp.addRandomNodes(1000);
		BriteWaxman.generateEdges(bp, 0.1, 0.5);
		
		System.out.println(bp.getGraph().getEdges().size());
		Gexf gexf = bp.getGraph().toGexf();
		
		StaxGraphWriter graphWriter = new StaxGraphWriter();
		File f = new File("test_waxman.gexf");
		Writer out;
		try {
			out =  new FileWriter(f, false);
			graphWriter.writeToStream(gexf, out, "UTF-8");
			System.out.println(f.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
