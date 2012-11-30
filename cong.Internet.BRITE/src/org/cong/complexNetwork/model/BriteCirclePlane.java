package org.cong.complexNetwork.model;

import org.cong.complexNetwork.graph.BriteCoordinate;
import org.cong.complexNetwork.graph.BriteNode;

public class BriteCirclePlane extends BritePlane {

	public BriteCirclePlane(Integer hs, Integer ls) {
		super(hs, ls);

	}

	@Override
	public BriteNode randomNode() {
		BriteNode briteNode = null;
		Boolean outCircle = true;
		while (outCircle) {
			briteNode = super.randomNode();
			BriteCoordinate briteCoordinate = briteNode.getBriteCoordinate();
			Integer x = briteCoordinate.getLx();
			Integer y = briteCoordinate.getLy();
			x = x - ls / 2;
			y = y - ls / 2;
			if (x * x + y * y < ls * ls / 4) {
				outCircle = false;
			}
		}
		return briteNode;
	}

}
