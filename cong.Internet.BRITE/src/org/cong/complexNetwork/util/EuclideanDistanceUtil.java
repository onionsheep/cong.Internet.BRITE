package org.cong.complexNetwork.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.cong.complexNetwork.graph.Coordinate;
import org.cong.complexNetwork.graph.Node;

public class EuclideanDistanceUtil {

  /**
   * 比较器compare，left.y < right.y 返回1。若y相等，left.x < right.x 返回1。相等返回0.其余返回-1
   */
  public static Comparator<Node> cmp = new Comparator<Node>() {
                                       @Override
                                       public int compare(final Node u, final Node v) {
                                         final Coordinate left = u.getCoordinate();
                                         final Coordinate right = v.getCoordinate();
                                         if (((left.getY() < right.getY()) || ((left.getY() == right.getY()) && (left.getX() < right.getX())))) {
                                           return 1;
                                         } else if (left.equals(right)) {
                                           return 0;
                                         } else {
                                           return -1;
                                         }
                                       }
                                     };

  /**
   * 求两个向量的内积
   * 
   * @param pre
   *          上一个点
   * @param cur
   *          当前点
   * @param next
   *          将要选择的点
   * @return
   */
  public static int CrossProduct(final Node pre, final Node cur, final Node next) {
    final int x1 = cur.getCoordinate().getX() - pre.getCoordinate().getX();
    final int y1 = cur.getCoordinate().getY() - pre.getCoordinate().getY();
    final int x2 = cur.getCoordinate().getX() - next.getCoordinate().getX();
    final int y2 = cur.getCoordinate().getY() - next.getCoordinate().getY();
    return ((x1 * x2) + (y1 * y2)); // <0是满足凸包的点
  }

  /**
   * 求两点之间的距离的平方
   * 
   * @param u
   * @param v
   * @return 两点之间的距离的平方
   */
  public static long Distance(final Node u, final Node v) {
    long dis = 0;
    final int xd = u.getCoordinate().getX() - v.getCoordinate().getX();
    final int yd = u.getCoordinate().getY() - v.getCoordinate().getY();
    dis = (1l * xd * xd) + (1l * yd * yd);
    return dis;
  }

  /**
   * Graham's Scan法求解凸包
   * 
   * @param nl
   * @param result
   */
  public static List<Node> GetConvexHull(final List<Node> nl) {
    final List<Node> result = new ArrayList<>();

    // 按照y进行排序，y相等则按照x。由小到大。
    Collections.sort(nl, EuclideanDistanceUtil.cmp);
    final int size = nl.size();
    if (size < 3) {
      result.addAll(nl);
    } else {
      result.add(nl.get(0));
      result.add(nl.get(1));
      result.add(nl.get(2));
      int top = 2;
      for (int i = 3; i < size; i++) {
        while ((top > 0)
               && (EuclideanDistanceUtil.CrossProduct(result.get(top - 1),
                                                      result.get(top),
                                                      nl.get(i)) >= 0)) {
          result.remove(result.size() - 1);
          top--;
        }
        result.add(nl.get(i));

        top++;
      }
    }
    return result;
  }

  public static long max(final long i, final long j) {
    return i > j ? i : j;
  }

  public static double maxEd(final List<Node> nodes) {
    final List<Node> convexHull = EuclideanDistanceUtil.GetConvexHull(nodes);
    final long d = EuclideanDistanceUtil.RotatingCalipers(convexHull);
    return Math.sqrt(d);
  }

  /**
   * 旋转卡壳算法求凸包直径
   * 
   * @param nl
   * @return
   */
  public static long RotatingCalipers(final List<Node> nl) {
    int j = 1;
    final int n = nl.size();
    long maxLength = 0;// 存储最大值
    nl.add(nl.get(0));
    for (int i = 0; i < n; i++) {
      while (EuclideanDistanceUtil.CrossProduct(nl.get(i + 1), nl.get(j + 1), nl.get(i)) > EuclideanDistanceUtil.CrossProduct(nl.get(i + 1),
                                                                                                                              nl.get(j),
                                                                                                                              nl.get(i))) {
        j = (j + 1) % n;
      }
      maxLength = EuclideanDistanceUtil.max(maxLength,
                                            EuclideanDistanceUtil.max(EuclideanDistanceUtil.Distance(nl.get(i),
                                                                                                     nl.get(j)),
                                                                      EuclideanDistanceUtil.Distance(nl.get(i + 1),
                                                                                                     nl.get(j + 1))));
    }
    return maxLength;
  }

}
