package org.cong.complexNetwork.util;

import java.util.ArrayList;
import java.util.List;

public class Sparse<T> {

  protected List<Integer> xl;
  protected List<Integer> yl;
  protected List<T> vl;

  public Sparse() {
    this.xl = new ArrayList<>();
    this.yl = new ArrayList<>();
    this.vl = new ArrayList<>();
  }

  public void addElement(int x, int y, T value) {
    this.xl.add(x);
    this.yl.add(y);
    this.vl.add(value);
  }

  public String toMatlabString() {
    final StringBuffer sb = new StringBuffer();
    sb.append("xl = [");
    final int l = this.vl.size();
    for (int i = 0; i < l; i++) {
      sb.append(this.xl.get(i));
      sb.append(", ");
    }
    sb.append("];\n");
    sb.append("yl = [");
    for (int i = 0; i < l; i++) {
      sb.append(this.yl.get(i));
      sb.append(", ");
    }
    sb.append("];\n");
    sb.append("vl = [");
    for (int i = 0; i < l; i++) {
      sb.append(this.vl.get(i));
      sb.append(", ");
    }
    sb.append("];\n");
    sb.append("S = sparse(xl, yl, vl);");
    return sb.toString();
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("Sparse [xl=");
    builder.append(this.xl);
    builder.append(", yl=");
    builder.append(this.yl);
    builder.append(", vl=");
    builder.append(this.vl);
    builder.append("]");
    return builder.toString();
  }

}
