package msrp.message;

import msrp.util.Separators;
import msrp.header.MsrpResponseLine;
import msrp.header.FromPath;
import msrp.header.ByteRange;
import msrp.header.ToPath;

/**
 * 
 * @author inam_
 */
public class ResponseParser {
  ToPath toPath = null;
  FromPath fromPath = null;
  MsrpResponseLine responseLine = null;
  ByteRange byteRange = null;
  String body = null;
  /**
   * Default constructor
   * @param response String
   */
  public ResponseParser(String response) {
    parse(response);
  }

  /**
   * Get Response Line
   * @return MsrpResponseLine
   */
  public MsrpResponseLine getResponseLine() {
    return responseLine;
  }

  /**
   * Get To Path Header
   * @return ToPath
   */
  public ToPath getToPath() {
    return toPath;
  }

  /**
   * Get From Path Header
   * @return FromPath
   */
  public FromPath getFromPath() {
    return fromPath;
  }

  /**
   * Get Byte Range Header
   * @return ByteRange
   */
  public ByteRange getByteRange() {
    return byteRange;
  }

  /**
   * get response body
   * @return String
   */
  public String getResponseBody() {
    return body;
  }

  /**
   * parses msrp response
   * @param response String
   */
  private void parse(String response) {

    String[] responseFields = response.split(Separators.NEWLINE);
    StringBuffer buffer = null;
    int fieldIndex = 0;
    responseLine = new MsrpResponseLine(responseFields[fieldIndex++]);
    if (responseFields[fieldIndex].startsWith("To-Path")) {
      buffer = new StringBuffer(responseFields[fieldIndex++]);
      int index = buffer.indexOf(":");
      buffer.delete(0, index + 1);
      toPath = new ToPath(buffer.toString());
      buffer = null;
    }
    if (responseFields[fieldIndex].startsWith("From-Path")) {
      buffer = new StringBuffer(responseFields[fieldIndex++]);
      int index = buffer.indexOf(":");
      buffer.delete(0, index + 1);
      fromPath = new FromPath(buffer.toString());
      buffer = null;
    }
    if (responseFields[fieldIndex].startsWith("Byte-Range")) {
      buffer = new StringBuffer(responseFields[fieldIndex++]);
      int index = buffer.indexOf(":");
      buffer.delete(0, index + 1);
      byteRange = new ByteRange(buffer.toString().trim());
    }

    StringBuffer tempBuffer = new StringBuffer();

    for (int i = fieldIndex; i < responseFields.length; i++) {
      tempBuffer.append(responseFields[i]);
    }
    //Check if message has the body
    String terminator = "-------" + responseLine.getTransactionId() + "$";
    //Check for empty body
    if (tempBuffer.length() == terminator.length()) {
      body = "";
    }
    else {
      int index = tempBuffer.indexOf(terminator);
      tempBuffer.delete(index, tempBuffer.length());
      body = tempBuffer.toString();
    }

  }
}
