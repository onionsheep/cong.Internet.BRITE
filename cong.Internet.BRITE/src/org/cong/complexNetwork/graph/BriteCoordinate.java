package org.cong.complexNetwork.graph;

public class BriteCoordinate extends Coordinate {
	protected Integer		hx;
	protected Integer		hy;
	protected Integer		lx;
	protected Integer		ly;

	private static int	halfSizeOfInteger	= Integer.SIZE / 2;
	private static int	maxInt						= Integer.MAX_VALUE / 2;

	public BriteCoordinate(Integer hx, Integer hy, Integer lx, Integer ly) throws Exception {
		super(hx << halfSizeOfInteger ^ lx, hy << halfSizeOfInteger ^ ly);
		this.hx = hx;
		this.hy = hy;
		this.lx = lx;
		this.ly = ly;
		if(hx >= maxInt ||hy >= maxInt || lx >= maxInt ||lx >= maxInt ){
			throw new Exception("坐标过大");
		}
	}

	public Integer getHx() {
		return hx;
	}

	public void setHx(Integer hx) {
		this.hx = hx;
	}

	public Integer getHy() {
		return hy;
	}

	public void setHy(Integer hy) {
		this.hy = hy;
	}

	public Integer getLx() {
		return lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}

	public Integer getLy() {
		return ly;
	}

	public void setLy(Integer ly) {
		this.ly = ly;
	}

	@Override
	public int hashCode() {
		final int prime = 10007;
		int result = super.hashCode();
		result = prime * result + ((hx == null) ? 0 : hx.hashCode());
		result = prime * result + ((hy == null) ? 0 : hy.hashCode());
		result = prime * result + ((lx == null) ? 0 : lx.hashCode());
		result = prime * result + ((ly == null) ? 0 : ly.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BriteCoordinate other = (BriteCoordinate) obj;
		if (this.toLong() == other.toLong()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BriteCoordinate [");
		builder.append(hx);
		builder.append(".");
		builder.append(hy);
		builder.append(", ");
		builder.append(lx);
		builder.append(".");
		builder.append(ly);
		builder.append("]");
		return builder.toString();
	}
}
