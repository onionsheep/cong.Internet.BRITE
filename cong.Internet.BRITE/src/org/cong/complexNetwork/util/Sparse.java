package org.cong.complexNetwork.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 稀疏矩阵,元素是（下标x，下标y，元素值）这种结构。暂时只为导出为Matlab稀疏矩阵使用。
 * 
 * @author onion_sheep(onion_sheep@163.com)
 * @param <T>
 *          元素之类型
 */
public class Sparse<T> {

  protected List<T>       vl;
  protected List<Integer> xl;
  protected List<Integer> yl;

  public Sparse() {
    this.xl = new ArrayList<>();
    this.yl = new ArrayList<>();
    this.vl = new ArrayList<>();
  }

  /**
   * 添加一个元素
   * 
   * @param x
   *          下标x
   * @param y
   *          下标y
   * @param value
   *          元素值
   */
  public void addElement(final int x, final int y, final T value) {
    this.xl.add(x);
    this.yl.add(y);
    this.vl.add(value);
  }

  public void toMatlabFile(final String fileName) {

  }

  /**
   * @return Matlab所需要的稀疏矩阵的生成字符串
   */
  public String toMatlabString() {
    final int length = 3 * this.vl.size();
    final StringBuffer sb = new StringBuffer(length);
    sb.append("xl = [");
    final int l = this.vl.size();
    for (int i = 0; i < l; i++) {
      sb.append(this.xl.get(i));
      sb.append(",");
    }
    sb.append("];\n");
    sb.append("yl = [");
    for (int i = 0; i < l; i++) {
      sb.append(this.yl.get(i));
      sb.append(",");
    }
    sb.append("];\n");
    sb.append("vl = [");
    for (int i = 0; i < l; i++) {
      sb.append(this.vl.get(i));
      sb.append(",");
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
