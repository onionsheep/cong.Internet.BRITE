package org.cong.complexNetwork.util;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.StaxGraphWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.Graph;
import org.cong.complexNetwork.graph.IPNode;
import org.cong.complexNetwork.graph.Node;

public class Tools {
  public static Logger logger = LogManager.getLogger(Tools.class);

  public static double randomDouble(final double n) {
    return java.util.concurrent.ThreadLocalRandom.current().nextDouble(n);
  }

  public static int randomInt(final int n) {
    return java.util.concurrent.ThreadLocalRandom.current().nextInt(n);
  }

  public static Graph readIPNodesFromLnkFile(final String filePath) throws Exception {
    final Graph ug = new Graph();
    final File f = new File(filePath);
    final Map<Node, Node> nnmap = new HashMap<>();
    try {
      final List<String> lines = FileUtils.readLines(f, "UTF-8");
      logger.debug("read ok");
      for (final String line : lines) {
        final String[] p = line.split(" ");
        if (p.length >= 2) {
          Node n0 = new IPNode(p[0]);
          final Node n1 = new IPNode(p[1]);
          /*
           * HashMap.put:添加成功返回null，key已存在则返回原来的value
           */
          if (nnmap.containsKey(n0)) {
            n0 = nnmap.get(n0);
          } else {
            nnmap.put(n0, n0);
          }
          if (nnmap.containsKey(n1)) {
            n0 = nnmap.get(n1);
          } else {
            nnmap.put(n1, n1);
          }
          ug.connect(n0, n1);
        }
      }
      ug.getNodes().addAll(nnmap.values());
    }
    catch (final IOException e) {
      logger.debug("Read file error");
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
    FileUtils.writeStringToFile(f, sb.toString(), "UTF-8");
  }

  public static void writeGexfFile(final Gexf gexf, final String filePath) {
    final StaxGraphWriter graphWriter = new StaxGraphWriter();
    final File f = new File(filePath);
    Writer out;
    try {
      out = new FileWriter(f, false);
      graphWriter.writeToStream(gexf, out, "UTF-8");
      logger.info(f.getAbsolutePath());
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
