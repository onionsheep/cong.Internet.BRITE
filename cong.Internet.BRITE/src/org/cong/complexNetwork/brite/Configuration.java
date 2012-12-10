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
    return this.baNodeCount;
  }

  public Integer getBaOneNodeEdge() {
    return this.baOneNodeEdge;
  }

  public Integer getHs() {
    return this.hs;
  }

  public Integer getLs() {
    return this.ls;
  }

  public String getOutputFileName() {
    return this.outputFileName;
  }

  public Double getWaxmanAlpha() {
    return this.waxmanAlpha;
  }

  public Double getWaxmanBeta() {
    return this.waxmanBeta;
  }

  public Integer getWaxmanNodeCount() {
    return this.waxmanNodeCount;
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
