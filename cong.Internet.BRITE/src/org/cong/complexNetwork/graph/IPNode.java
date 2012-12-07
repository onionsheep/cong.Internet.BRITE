package org.cong.complexNetwork.graph;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class IPNode extends Node {
  public static Logger logger = LogManager.getLogger(IPNode.class);
  protected String ip;
  public IPNode(String ip) {
    super(ip.hashCode());
    this.ip = ip;
  }
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((ip == null) ? 0 : ip.hashCode());
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
    IPNode other = (IPNode) obj;
    if (ip == null) {
      if (other.ip != null)
        return false;
    } else if (!ip.equals(other.ip))
      return false;
    return true;
  }
  public String getIp() {
    return ip;
  }
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("IPNode[");
    builder.append(ip);
    builder.append("]");
    return builder.toString();
  }

  
}
