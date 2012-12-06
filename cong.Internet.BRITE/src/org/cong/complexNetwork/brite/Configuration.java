package org.cong.complexNetwork.brite;

public class Configuration {
  public Integer hs;
  public Integer ls;
  public Integer waxmanNodeCount;
  public Double waxmanAlpha;
  public Double waxmanBeta;
  public Integer baNodeCount;
  public Integer baOneNodeEdge;
  public String outputFileName;

  public Integer getBaNodeCount() {
    return baNodeCount;
  }

  public Integer getBaOneNodeEdge() {
    return baOneNodeEdge;
  }

  public Integer getHs() {
    return hs;
  }

  public Integer getLs() {
    return ls;
  }

  public String getOutputFileName() {
    return outputFileName;
  }

  public Double getWaxmanAlpha() {
    return waxmanAlpha;
  }

  public Double getWaxmanBeta() {
    return waxmanBeta;
  }

  public Integer getWaxmanNodeCount() {
    return waxmanNodeCount;
  }

  public void setBaNodeCount(Integer baNodeCount) {
    this.baNodeCount = baNodeCount;
  }

  public void setBaOneNodeEdge(Integer baOneNodeEdge) {
    this.baOneNodeEdge = baOneNodeEdge;
  }

  public void setHs(Integer hs) {
    this.hs = hs;
  }

  public void setLs(Integer ls) {
    this.ls = ls;
  }

  public void setOutputFileName(String outputFileName) {
    this.outputFileName = outputFileName;
  }

  public void setWaxmanAlpha(Double waxmanAlpha) {
    this.waxmanAlpha = waxmanAlpha;
  }

  public void setWaxmanBeta(Double waxmanBeta) {
    this.waxmanBeta = waxmanBeta;
  }

  public void setWaxmanNodeCount(Integer waxmanNodeCount) {
    this.waxmanNodeCount = waxmanNodeCount;
  }

}
