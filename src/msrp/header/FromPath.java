package msrp.header;
/**
 * 
 * @author inam_
 */
import msrp.address.MsrpUrl;
import java.util.*;


/**
 * <p> From Path Header</p>
 * <p>Description: This Class Represents From Header as Defined in MSRP </p>
 * @author not attributable
 * @version 1.0
 */
public class FromPath
    extends MsrpHeader {

  List urlList;
  /**
   * Constructor
   * @param msrpURL MsrpUrl
   */
  public FromPath(MsrpUrl msrpURL) {
    urlList = new ArrayList();
    addURL(msrpURL);
  }

  /**
   * Parametrized Constructor
   * @param strFromLine String
   */
  public FromPath(String strFromLine) {
    parse(strFromLine);
  }

  /**
   * Parses FromHeader Field
   * @param strToLine String
   */
  private void parse(String strFromLine) {
    MsrpUrl url = new MsrpUrl(strFromLine.trim());
    urlList = new ArrayList();
    addURL(url);
  }

  /**
   * Adds MSRP From URL To the List
   * @param msrpURL MsrpUrl
   */
  public void addURL(MsrpUrl msrpURL) {
    urlList.add(msrpURL);
  }

  /**
   * Get the URL List
   * @return List
   */
  public List getURLs() {
    return urlList;
  }

  /**
   * Encodes to MSRP Defined Format
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
   * toString Represenation of From-Path Header
   * @return String
   */
  public String toString() {
    return encode();
  }

  /**
   * Get the Header Name
   * @return String
   */
  public String getHeaderName() {
    return "From-Path";
  }
}
