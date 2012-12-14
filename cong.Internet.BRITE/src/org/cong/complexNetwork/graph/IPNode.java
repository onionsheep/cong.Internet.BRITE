package org.cong.complexNetwork.graph;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class IPNode extends Node {
  protected String ip;
  public static Logger logger = LogManager.getLogger(IPNode.class);

  public IPNode(final String ip) {
    super(ip.hashCode());
    this.ip = ip;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final IPNode other = (IPNode) obj;
    if (this.ip == null) {
      if (other.ip != null) {
        return false;
      }
    } else if (!this.ip.equals(other.ip)) {
      return false;
    }
    return true;
  }

  public String getIp() {
    return this.ip;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = (prime * result) + ((this.ip == null) ? 0 : this.ip.hashCode());
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("IPNode[");
    builder.append(this.ip);
    builder.append("]");
    return builder.toString();
  }

}
