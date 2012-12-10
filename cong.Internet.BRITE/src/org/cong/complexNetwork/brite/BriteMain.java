package org.cong.complexNetwork.brite;

import it.uniroma1.dis.wiserver.gexf4j.core.Gexf;
import it.uniroma1.dis.wiserver.gexf4j.core.impl.StaxGraphWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.model.BA;
import org.cong.complexNetwork.model.BritePlane;
import org.cong.complexNetwork.model.BriteWaxman;
import org.nutz.json.Json;

public class BriteMain {

  public static Logger logger = LogManager.getLogger(BriteMain.class);

  private static void generate(Configuration configuration) throws Exception {
    final BritePlane britePlane = new BritePlane(configuration.hs, configuration.ls);
    britePlane.addRandomNodes(configuration.waxmanNodeCount);
    logger.info("正在生成Waxman随机图...");
    BriteWaxman.generateEdges(britePlane, configuration.waxmanAlpha, configuration.waxmanBeta);

    logger.info("正在生成BA图...");
    BA.generateEdges(britePlane, configuration.baOneNodeEdge, configuration.baNodeCount);
    logger.info("正在把模型转化为GEXF格式...");

    final Gexf gexf = britePlane.getGraph().toGexf();
    logger.debug("正在输出为文件...");
    final StaxGraphWriter graphWriter = new StaxGraphWriter();
    final File f = new File(configuration.outputFileName);
    Writer out;
    try {
      out = new FileWriter(f, false);
      graphWriter.writeToStream(gexf, out, "UTF-8");
      logger.info("文件已保存" + f.getAbsolutePath());
    }
    catch (final IOException e) {
      logger.error("保存文件失败");
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {
    final File file = new File("Configuration.json");
    try {
      final String confStr = FileUtils.readFileToString(file);
      final Configuration conf = Json.fromJson(Configuration.class, confStr);
      // nutz的json解析
      // Configuration conf = JSON.parseObject(confStr, Configuration.class);
      // fastjson
      generate(conf);
    }
    catch (final IOException e) {
      logger.error("配置文件读取错误，请检查。");
      e.printStackTrace();
    }
  }
}
