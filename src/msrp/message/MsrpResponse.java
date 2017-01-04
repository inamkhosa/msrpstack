package msrp.message;

import msrp.util.Separators;
import msrp.header.MsrpResponseLine;
import msrp.header.MsrpHeaderList;
import msrp.header.Status;
import msrp.header.MessageID;
import msrp.header.FromPath;
import msrp.header.ByteRange;
import msrp.header.ToPath;
/**
 * 
 * @author inam_
 */
public class MsrpResponse
    implements ResponseCode {

  MsrpResponseLine responseLine;
  String body = null;
  ToPath toPath = null;
  FromPath fromPath = null;
  MessageID messageID = null;
  ByteRange byteRange = null;
  Status status = null;
  MsrpHeaderList headers = null;
  String transactionId = null;

  /**
   *Default Constructor
   */
  public MsrpResponse() {
    responseLine = new MsrpResponseLine();
    headers = new MsrpHeaderList();
  }

  /**
   * Parametrized Constructor
   * @param responseLine MsrpResponseLine
   * @param toPath ToPath
   * @param formPath FromPath
   */
  public MsrpResponse(MsrpResponseLine responseLine, ToPath toPath,
                      FromPath formPath) {
    this.responseLine = responseLine;
    this.fromPath = fromPath;
    this.toPath = toPath;
    headers = new MsrpHeaderList();
  }

  /**
   * Parametrized Constructor
   * @param responseLine MsrpResponseLine
   * @param toPath ToPath
   * @param formPath FromPath
   * @param byteRange ByteRange
   */
  public MsrpResponse(MsrpResponseLine responseLine, ToPath toPath,
                      FromPath formPath, ByteRange byteRange) {
    this.responseLine = responseLine;
    this.fromPath = fromPath;
    this.toPath = toPath;
    this.byteRange = byteRange;
    headers = new MsrpHeaderList();
  }

  /**
   * Parametrized Constructor
   * @param msrpBytes String
   */
  public MsrpResponse(String msrpBytes) {
    headers = new MsrpHeaderList();
    parse(msrpBytes);
  }

  /**
   * Parser
   * @param messageToParse String
   */
  private void parse(String messageToParse) {
    ResponseParser parser = new ResponseParser(messageToParse);
    responseLine = parser.getResponseLine();
    toPath = parser.getToPath();
    fromPath = parser.getFromPath();
    byteRange = parser.getByteRange();
    body = parser.getResponseBody();
    transactionId = responseLine.getTransactionId();
  }

  /**
   * Set Resposne Line
   * @param responseLine MsrpResponseLine
   */
  public void setResponseLine(MsrpResponseLine responseLine) {
    this.responseLine = responseLine;
  }

  /**
   * Set Transaction Id
   * @param transactionId int
   */
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  /**
   * Get Transaction Id
   * @return String
   */
  public String getTransactionId() {
    return transactionId;
  }

  /**
   * Set the ToPath Header
   * @param toPath ToPath
   */
  public void setToPath(ToPath toPath) {
    this.toPath = toPath;
  }

  /**
   * Get ToPath Header
   * @return ToPath
   */
  public ToPath getToPath() {
    return toPath;
  }

  /**
   * Set From Path Header
   * @param fromPath FromPath
   */
  public void setFromPath(FromPath fromPath) {
    this.fromPath = fromPath;
  }

  /**
   * Get From Path
   * @return FromPath
   */
  public FromPath getFromPath() {
    return fromPath;
  }

  /**
   * set Byte Range Header
   * @param byteRange ByteRange
   */
  public void setByteRange(ByteRange byteRange) {
    this.byteRange = byteRange;
  }

  /**
   * Get Byte Range Header
   * @return ByteRange
   */
  public ByteRange getByteRange() {
    return byteRange;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * Set Message Id
   * @param messageID MessageID
   */
  public void setMessageID(MessageID messageID) {
    this.messageID = messageID;
  }

  /**
   * Get Message Id
   * @return MessageID
   */
  public MessageID getMessageID() {
    return messageID;
  }

  /**
   * Set Body
   * @param body String
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Set ResponseCode
   * @param code int
   */
  public void setResponseCode(int code) {
    this.responseLine.setResponseCode(code);
  }

  /**
   * Override from ResponseCode
   * @return int
   */
  public int getResponseCode() {
    return responseLine.getResponseCode();
  }

  /**
   * Override from ResponseCode
   * @param reasonPhrase String
   */
  public void setResponsePhrase(String reasonPhrase) {
    this.responseLine.setReasonPhrase(reasonPhrase);
  }

  public String getResponsePhrase(int code) {
    String retVal = "";
    switch (code) {
      case OK:
        retVal = "OK";
        break;
      case UNINTELLIGIBLE:
        retVal = "Unintelligible Request";
        break;
      case ACTIONNOTALLOWED:
        retVal = "Action Not Allowed";
        break;
      case TIMEOUT:
        retVal = "Time Out";
        break;
      case STOP:
        retVal = "Stop Sending Message";
        break;
      case NOTUNDERSTOOD:
        retVal = "Media Type Not Understood";
        break;
      case PARAMOUTOFBOUND:
        retVal = "Requested Parameters Out of Bound";
        break;
      case SESSIONNOTEXIST:
        retVal = "Session Does not Exist";
        break;
      case METHODNOTSUPPORTED:
        retVal = "Method Not Understood";
        break;
      case SESSIONINUSE:
        retVal = "Session Already in Use";
        break;
    }
    return retVal;
  }

  /**
   * Get MSRP Response Line
   * @return MsrpResponseLine
   */
  public MsrpResponseLine getResponseLine() {
    return responseLine;
  }

  /**
   * Encodes to MSRP Defined Format.
   * @return String
   */
  public String encode() {
//    headers = new ArrayList();
    StringBuffer buffer = new StringBuffer();
    headers = null;
    headers = new MsrpHeaderList();
    if (responseLine != null) {
      buffer.append(responseLine.toString());
      buffer.append(Separators.NEWLINE);
    }
    if (toPath != null) {
      headers.addHeader(toPath);
    }
    if (fromPath != null) {
      headers.addHeader(fromPath);
    }
    if (messageID != null) {
      headers.addHeader(messageID);
    }
    if (byteRange != null) {
      headers.addHeader(byteRange);
    }
    if (status != null) {
      headers.addHeader(status);
    }
    buffer.append(headers.encodeHeaders());
    if (body != null && body.length() > 0) {
      buffer.append(body);
      buffer.append(Separators.NEWLINE);
    }
    buffer.append("-------" + getTransactionId() + "$");
    return buffer.toString();

  }

  /**
   * toString reprsenation
   * @return String
   */
  public String toString() {
    return encode();
  }
}
