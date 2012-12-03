package org.cong.complexNetwork.util;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.StaxGraphWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class Tools {
  public static Logger logger = LogManager.getLogger(Tools.class);

  public static int randomInt(int n) {
    return java.util.concurrent.ThreadLocalRandom.current().nextInt(n);
  }

  public static double randomDouble(double n) {
    return java.util.concurrent.ThreadLocalRandom.current().nextDouble(n);
  }

  public static void writeGexfFile(Gexf gexf, String filePath) {
    StaxGraphWriter graphWriter = new StaxGraphWriter();
    File f = new File(filePath);
    Writer out;
    try {
      out = new FileWriter(f, false);
      graphWriter.writeToStream(gexf, out, "UTF-8");
      logger.info(f.getAbsolutePath());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
