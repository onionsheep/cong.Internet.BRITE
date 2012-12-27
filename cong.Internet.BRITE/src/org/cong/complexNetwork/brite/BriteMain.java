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
import org.cong.complexNetwork.model.BriteBA;
import org.cong.complexNetwork.model.BritePlane;
import org.cong.complexNetwork.model.BriteWaxman;

import com.alibaba.fastjson.JSON;

public class BriteMain {

  public static Logger logger = LogManager.getLogger(BriteMain.class);

  private static void generate(final Configuration configuration) throws Exception {
    final BritePlane britePlane = new BritePlane(configuration.hs, configuration.ls);
    britePlane.addRandomNodes(configuration.waxmanNodeCount);
    BriteMain.logger.info("正在生成Waxman随机图...");
    BriteWaxman.generateEdges(new BriteWaxman(britePlane,
                                              configuration.waxmanAlpha,
                                              configuration.waxmanBeta));

    BriteMain.logger.info("正在生成BA图...");
    BriteBA.generateEdges(new BriteBA(britePlane,
                                      configuration.baOneNodeEdge,
                                      configuration.baNodeCount));
    BriteMain.logger.info("正在把模型转化为GEXF格式...");

    final Gexf gexf = britePlane.getGraph().toGexf();
    BriteMain.logger.debug("正在输出为文件...");
    final StaxGraphWriter graphWriter = new StaxGraphWriter();
    final File f = new File(configuration.outputFileName);
    Writer out;
    try {
      out = new FileWriter(f, false);
      graphWriter.writeToStream(gexf, out, "UTF-8");
      BriteMain.logger.info("文件已保存" + f.getAbsolutePath());
    }
    catch (final IOException e) {
      BriteMain.logger.error("保存文件失败");
      e.printStackTrace();
    }
  }

  public static void main(final String[] args) throws Exception {
    final File file = new File("Configuration.json");
    try {
      final String confStr = FileUtils.readFileToString(file);
      // final Configuration conf = Json.fromJson(Configuration.class, confStr);
      // nutz的json解析
      final Configuration conf = JSON.parseObject(confStr, Configuration.class);
      // fastjson
      BriteMain.generate(conf);
    }
    catch (final IOException e) {
      BriteMain.logger.error("配置文件读取错误，请检查。");
      e.printStackTrace();
    }
  }
}
