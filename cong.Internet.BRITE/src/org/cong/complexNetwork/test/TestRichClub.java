package org.cong.complexNetwork.test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cong.complexNetwork.graph.UndirectedGraph;
import org.cong.complexNetwork.model.BriteBAAndWaxman;
import org.cong.complexNetwork.model.BriteCirclePlane;
import org.cong.complexNetwork.model.BriteWaxman;
import org.cong.complexNetwork.util.RichClubUtil;

public class TestRichClub {
  public static Logger logger = LogManager.getLogger(TestRichClub.class);

  public static void main(String[] args) throws Exception {
    BriteCirclePlane bp = new BriteCirclePlane(10, 1000, 100.0);
    bp.addRandomNodes(30);
    BriteWaxman.generateEdges(bp, 0.7, 0.3);
    BriteBAAndWaxman.generateEdges(bp, 5, 970, 0.6, 0.3);
    logger.debug("Nodes " + bp.getBriteGraph().getNodes().size());
    logger.debug("Edges " + bp.getBriteGraph().getEdges().size());
    UndirectedGraph ug = bp.getBriteGraph();
    //RichClubUtil.showRichClubChartByDegree(ug);
    RichClubUtil.showRichClubChartByOrder(ug);
  }


}
