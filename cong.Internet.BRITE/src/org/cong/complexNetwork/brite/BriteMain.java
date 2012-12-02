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
	
	public static void main(String[] args) throws Exception {
		File file = new File("Configuration.json");
		try {
			String confStr = FileUtils.readFileToString(file);
			//Configuration conf = Json.fromJson(Configuration.class, confStr);  //nutz的json解析
			Configuration conf = JSON.parseObject(confStr, Configuration.class);
			generate(conf);
		} catch (IOException e) {
			logger.error("配置文件读取错误，请检查。");
			e.printStackTrace();
		}
	}

	private static void generate(Configuration configuration) throws Exception{
		BritePlane britePlane = new BritePlane(configuration.hs, configuration.ls);
		britePlane.addRandomNodes(configuration.waxmanNodeCount);
		logger.info("正在生成Waxman随机图...");
		BriteWaxman.generateEdges(britePlane, configuration.waxmanAlpha, configuration.waxmanBeta);

		logger.info("正在生成BA图...");
		BriteBA.generateEdges(britePlane, configuration.baOneNodeEdge, configuration.baNodeCount);
		logger.info("正在把模型转化为GEXF格式...");
		
		Gexf gexf = britePlane.getBriteGraph().toGexf();
		logger.debug("正在输出为文件...");
		StaxGraphWriter graphWriter = new StaxGraphWriter();
		File f = new File(configuration.outputFileName);
		Writer out;
		try {
			out = new FileWriter(f, false);
			graphWriter.writeToStream(gexf, out, "UTF-8");
			logger.info("文件已保存" + f.getAbsolutePath());
		} catch (IOException e) {
			logger.error("保存文件失败");
			e.printStackTrace();
		}
	}
}
