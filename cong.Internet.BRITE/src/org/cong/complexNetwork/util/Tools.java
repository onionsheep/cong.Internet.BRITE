package org.cong.complexNetwork.util;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.StaxGraphWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import cong.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.StringNameNode;
import org.cong.complexNetwork.graph.Node;

public class Tools {
  public static Logger logger = LogManager.getLogger(Tools.class);

  public static double randomDouble(final double n) {
    return java.util.concurrent.ThreadLocalRandom.current().nextDouble(n);
  }

  public static int randomInt(final int n) {
    return java.util.concurrent.ThreadLocalRandom.current().nextInt(n);
  }

  public static Graph readStringNodesFromFile(final String filePath, boolean directed) throws Exception {
    final Graph ug = new Graph(directed);
    try {
      final List<String> lines = FileUtils.readLines(filePath,"UTF-8");
      Tools.logger.debug("read ok");
      for (final String line : lines) {
        final String[] p = line.split(" ");
        if (p.length >= 2) {
          Node n0 = new StringNameNode(p[0]);
          Node n1 = new StringNameNode(p[1]);

          if (ug.containsNode(n0)) {
            n0 = ug.getNode(n0);
          } else {
            ug.addNode(n0);
          }
          if (ug.containsNode(n1)) {
            n1 = ug.getNode(n1);
          } else {
            ug.addNode(n1);
          }
          ug.connect(n0, n1);
        }
      }
    }
    catch (final IOException e) {
      Tools.logger.debug("Read file error");
      e.printStackTrace();
    }
    return ug;
  }

  public static void writeAdjacentMatrixtoFile(final double[][] am, final String filePath)
      throws IOException {
    final StringBuffer sb = new StringBuffer();
    sb.append("AM = [\n");
    for (int i = 0; i < am.length; i++) {
      for (int j = 0; j < am[0].length; j++) {
        sb.append((int) am[i][j]);
        if (j < (am[0].length - 1)) {
          sb.append(",");
        }
      }
      if (i < (am.length - 1)) {
        sb.append("\n");
      }
    }
    sb.append("];");
    final File f = new File(filePath);
    FileUtils.writeStringToFile(f, sb.toString(), false);
  }

  public static void writeGexfFile(final Gexf gexf, final String filePath) {
    final StaxGraphWriter graphWriter = new StaxGraphWriter();
    final File f = new File(filePath);
    Writer out;
    try {
      out = new FileWriter(f, false);
      graphWriter.writeToStream(gexf, out, "UTF-8");
      Tools.logger.info(f.getAbsolutePath());
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
