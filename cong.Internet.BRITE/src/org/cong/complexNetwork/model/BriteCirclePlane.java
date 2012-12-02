package org.cong.complexNetwork.model;

import org.cong.complexNetwork.graph.BriteCoordinate;
import org.cong.complexNetwork.graph.BriteNode;

public class BriteCirclePlane extends BritePlane {

	protected double	radius;

	public BriteCirclePlane(int hs, int ls, double radius) {
		super(hs, ls);
		if (2 * radius > ls) {
			radius = ls / 2.0;
		}
		this.radius = radius;
	}

	public BriteCirclePlane(int hs, int ls) {
		this(hs, ls, ls * 0.5);
	}

	@Override
	public BriteNode randomNode() throws Exception {
		BriteNode briteNode = null;
		boolean outCircle = true;
		while (outCircle) {
			briteNode = super.randomNode();
			BriteCoordinate briteCoordinate = briteNode.getBriteCoordinate();
			int x = briteCoordinate.getLx();
			int y = briteCoordinate.getLy();
			x = x - ls / 2;
			y = y - ls / 2;
			if (x * x + y * y < radius * radius) {
				outCircle = false;
			}
		}
		return briteNode;
	}

}
