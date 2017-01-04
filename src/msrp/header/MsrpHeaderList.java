package msrp.header;

import msrp.util.Separators;
import java.util.*;
/**
 * 
 * @author inam_
 */
public class MsrpHeaderList
    extends ArrayList {

  /**
   * Constructor - Intializes
   */
  public MsrpHeaderList() {
  }

  /**
   * Adds header to the List
   * @param header MsrpHeader
   */
  public void addHeader(MsrpHeader header) {
    this.add(header);
  }

  /**
   * Encodes the Headers
   * @return String
   */
  public String encodeHeaders() {

    StringBuffer buffer = new StringBuffer();

    for (int i = 0; i < this.size(); i++) {
      MsrpHeader header = (MsrpHeader) get(i);
      buffer.append(header.getHeaderName() + Separators.COLON + Separators.SP +
                    header.toString());
      buffer.append(Separators.NEWLINE);
    }
    String strTemp = null;
    try {
      strTemp = new String(buffer.toString().getBytes(), "utf-8");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return strTemp;
  }
}
