package msrp.message;

import msrp.util.Separators;

/**
 * 
 * @author inam_
 */
public class MsrpMessage {
  MsrpRequest request = null;
  MsrpResponse response = null;
  boolean isRequest;
  boolean isResponse;
  String message = null;
  /**
   * Constructor
   * @param utf8String String
   */
  public MsrpMessage(String utf8String) {
    message = utf8String;
    checkMessage(message);
  }

  /**
   * Check to see if Message id Request or Response
   * @param utf8String String
   */
  public void checkMessage(String utf8String) {
    String msrpStartLine, fields[] = utf8String.trim().split(Separators.NEWLINE);
    if (fields.length > 0) {
      msrpStartLine = fields[0];
      String attribs[] = msrpStartLine.split(Separators.SP);
      if (attribs.length >= 3) {
        if (attribs[2].compareTo("SEND") == 0 ||
            attribs[2].compareTo("REPORT") == 0 ||
            attribs[2].compareTo("VISIT") == 0) {
          isRequest = true;
          isResponse = false;
        }
        else {
          isResponse = true;
          isRequest = false;
        }
      }
    }
  }

  /**
   * Returns True if message is Request
   * @return boolean
   */
  public boolean isRequest() {
    return isRequest;
  }

  /**
   * Returns true if Message is Response
   * @return boolean
   */
  public boolean isResponse() {
    return isResponse;
  }

  /**
   * Get MSRP Request.
   * @return MsrpRequest
   */
  public MsrpRequest getMsrpRequest() {
    if (isRequest()) {
      return new MsrpRequest(message);
    }
    else {
      return null;
    }
  }

  /**
   * Get MSRP Response
   * @return MsrpResponse
   */
  public MsrpResponse getMsrpResponse() {
    if (isResponse()) {
      return new MsrpResponse(message);
    }
    else {
      return null;
    }
  }

}
