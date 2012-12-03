package org.cong.complexNetwork.util;

public class Tools {

  public static int randomInt(int n){
    return java.util.concurrent.ThreadLocalRandom.current().nextInt(n);
  }
  public static double randomDouble(double n){
    return java.util.concurrent.ThreadLocalRandom.current().nextDouble(n);
  }
}
