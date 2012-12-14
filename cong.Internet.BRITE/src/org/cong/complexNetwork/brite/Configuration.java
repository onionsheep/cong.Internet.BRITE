package org.cong.complexNetwork.brite;

public class Configuration {
  public Integer baNodeCount;
  public Integer baOneNodeEdge;
  public Integer hs;
  public Integer ls;
  public String outputFileName;
  public Double waxmanAlpha;
  public Double waxmanBeta;
  public Integer waxmanNodeCount;

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

  public void setBaNodeCount(final Integer baNodeCount) {
    this.baNodeCount = baNodeCount;
  }

  public void setBaOneNodeEdge(final Integer baOneNodeEdge) {
    this.baOneNodeEdge = baOneNodeEdge;
  }

  public void setHs(final Integer hs) {
    this.hs = hs;
  }

  public void setLs(final Integer ls) {
    this.ls = ls;
  }

  public void setOutputFileName(final String outputFileName) {
    this.outputFileName = outputFileName;
  }

  public void setWaxmanAlpha(final Double waxmanAlpha) {
    this.waxmanAlpha = waxmanAlpha;
  }

  public void setWaxmanBeta(final Double waxmanBeta) {
    this.waxmanBeta = waxmanBeta;
  }

  public void setWaxmanNodeCount(final Integer waxmanNodeCount) {
    this.waxmanNodeCount = waxmanNodeCount;
  }

}
