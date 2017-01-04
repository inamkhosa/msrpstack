package msrp.header;

import msrp.address.MsrpUrl;
import java.util.*;

/**
 * 
 * @author inam_
 */
public class ToPath
    extends MsrpHeader {
  List urlList;
  /**
   * Constructor -Intializes the Object.
   * @param msrpURL MsrpUrl
   */
  public ToPath(MsrpUrl msrpURL) {
    urlList = new ArrayList();
    addURL(msrpURL);
  }

  /**
   * Constructor -Intializes the Object
   * @param strToLine String
   */
  public ToPath(String strToLine) {
    parse(strToLine);
  }

  /**
   * Adds ToURL to URL List
   * @param msrpURL MsrpUrl
   */
  public void addURL(MsrpUrl msrpURL) {
    urlList.add(msrpURL);
  }

  /**
   * Get URL List
   * @return List
   */
  public List getURLs() {
    return urlList;
  }

  /**
   * Encodes String
   * @return String
   */
  public String encode() {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < urlList.size(); i++) {
      buffer.append(urlList.get(0).toString());
      if (i > 0) {
        buffer.append("*");
      }
    }
    return buffer.toString();
  }

  /**
   * Parses To Path
   * @param strToLine String
   */
  private void parse(String strToLine) {
    MsrpUrl url = new MsrpUrl(strToLine);
    urlList = new ArrayList();
    addURL(url);
  }

  /**
   * toString Represenation
   * @return String
   */
  public String toString() {
    return encode();
  }

  /**
   * Get Header Name
   * @return String
   */
  public String getHeaderName() {
    return "To-Path";
  }

}
