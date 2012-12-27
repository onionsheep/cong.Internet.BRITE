package org.cong.complexNetwork.graph;

public class UrlNode extends Node {
  protected String url;

  public UrlNode(String url) {
    super(url.hashCode());
    this.url = url;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((url == null) ? 0 : url.hashCode());
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
    UrlNode other = (UrlNode) obj;
    if (url == null) {
      if (other.url != null)
        return false;
    } else if (!url.equals(other.url))
      return false;
    return true;
  }

  public String getUrl() {
    return url;
  }

}
