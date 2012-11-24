package org.cong.complexNetwork.graph;

public class BriteCoordinate {
	protected Integer	hx;
	protected Integer	hy;
	protected Integer	lx;
	protected Integer	ly;
	public BriteCoordinate(Integer hx, Integer hy, Integer lx, Integer ly) {
		this.hx = hx;
		this.hy = hy;
		this.lx = lx;
		this.ly = ly;
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
		final int prime = 31;
		int result = 1;
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BriteCoordinate other = (BriteCoordinate) obj;
		if (hx == null) {
			if (other.hx != null)
				return false;
		} else if (!hx.equals(other.hx))
			return false;
		if (hy == null) {
			if (other.hy != null)
				return false;
		} else if (!hy.equals(other.hy))
			return false;
		if (lx == null) {
			if (other.lx != null)
				return false;
		} else if (!lx.equals(other.lx))
			return false;
		if (ly == null) {
			if (other.ly != null)
				return false;
		} else if (!ly.equals(other.ly))
			return false;
		return true;
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