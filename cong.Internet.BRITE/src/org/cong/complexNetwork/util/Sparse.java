package org.cong.complexNetwork.util;

import java.util.ArrayList;
import java.util.List;

public class Sparse<T> {

  protected List<Integer> xl;
  protected List<Integer> yl;
  protected List<T> vl;

  public Sparse() {
    xl = new ArrayList<>();
    yl = new ArrayList<>();
    vl = new ArrayList<>();
  }

  public void addElement(int x, int y, T value) {
    xl.add(x);
    yl.add(y);
    vl.add(value);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Sparse [xl=");
    builder.append(xl);
    builder.append(", yl=");
    builder.append(yl);
    builder.append(", vl=");
    builder.append(vl);
    builder.append("]");
    return builder.toString();
  }

  public String toMatlabString() {
    StringBuffer sb = new StringBuffer();
    sb.append("xl = [");
    int l = vl.size();
    for (int i = 0; i < l; i++) {
      sb.append(xl.get(i));
      sb.append(", ");
    }
    sb.append("];\n");
    sb.append("yl = [");
    for (int i = 0; i < l; i++) {
      sb.append(yl.get(i));
      sb.append(", ");
    }
    sb.append("];\n");
    sb.append("vl = [");
    for (int i = 0; i < l; i++) {
      sb.append(vl.get(i));
      sb.append(", ");
    }
    sb.append("];\n");
    sb.append("S = sparse(xl, yl, vl);");
    return sb.toString();
  }
  
}
