package msrp.message;

import msrp.util.Separators;
import msrp.header.SuccessReport;
import msrp.header.MsrpHeaderList;
import msrp.header.ContentType;
import msrp.header.FailureReport;
import msrp.header.MessageID;
import msrp.header.FromPath;
import msrp.header.MsrpRequestLine;
import msrp.header.ByteRange;
import msrp.header.ToPath;

/**
 * 
 * @author inam_
 */
public class MsrpRequest {

  private MsrpRequestLine requestLine;
  private FromPath fromPath;
  private ToPath toPath;
  private MessageID messageID;
  private ByteRange byteRange;
  private ContentType contentType;
  private SuccessReport successReport;
  private FailureReport failureReport;
  private String body;
  private String method;
  private String transactionId;
  private MsrpHeaderList headers;
  /**
   * Default Constructor
   */
  public MsrpRequest() {
    headers = new MsrpHeaderList();
    setDefaults();
  }

  /**
   * Parametrized Constructor
   * @param msrpBytes String
   */
  public MsrpRequest(String msrpBytes) {
    headers = new MsrpHeaderList();
    parseMsrpBytes(msrpBytes);
  }

  /**
   * Set Request Line
   * @param requestLine MsrpRequestLine
   */
  public void setRequestLine(MsrpRequestLine requestLine) {
    this.requestLine = requestLine;
  }

  /**
   * Set Method Name
   * @param method String
   */
  public void setMethod(String method) {
    this.method = method;
  }

  /**
   * Set From Path
   * @param fromPath FromPath
   */
  public void setFromPath(FromPath fromPath) {
    this.fromPath = fromPath;
  }

  /**
   * Set To Path Header
   * @param toPath ToPath
   */
  public void setToPath(ToPath toPath) {
    this.toPath = toPath;
  }

  /**
   * Set Message ID Header
   * @param messageID MessageID
   */
  public void setMessageID(MessageID messageID) {
    this.messageID = messageID;
  }

  /**
   * Set Byte Range Header
   * @param byteRange ByteRange
   */
  public void setByteRange(ByteRange byteRange) {
    this.byteRange = byteRange;
  }

  /**
   * Set ContentType Header
   * @param contentType ContentType
   */
  public void setContentType(ContentType contentType) {
    this.contentType = contentType;
  }

  /**
   * Set Success Report Header
   * @param successReport SuccessReport
   */
  public void setSuccessReport(SuccessReport successReport) {
    this.successReport = successReport;
  }

  /**
   * Set Failure Report Header
   * @param failureReport FailureReport
   */
  public void setFailureReport(FailureReport failureReport) {
    this.failureReport = failureReport;
  }

  public void setHeaderList(MsrpHeaderList headerList) {
    headers = headerList;
  }

  /**
   * Set Body
   * @param body String
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Set Default Values if any
   */
  public void setDefaults() {
    // set the default values for the message
  }

  /**
   * Get Method Name
   * @return String
   */
  public String getMethod() {
    return requestLine.getMethod();
  }

  /**
   * Get Body
   * @return String
   */
  public String getBody() {
    return body;
  }

  /**
   * Get To Path Header
   * @return MsrpUrl
   */
  public ToPath getToPath() {
    return toPath;
  }

  /**
   * Get From Path Header
   * @return MsrpUrl
   */
  public FromPath getFromPath() {
    return fromPath;
  }

  /**
   * Get Message ID Header
   * @return MessageID
   */
  public MessageID getMessageID() {
    return messageID;
  }

  /**
   * Get Byte Range Header
   * @return ByteRange
   */
  public ByteRange getByteRange() {
    return byteRange;
  }

  /**
   * Get Content Type
   * @return ContentType
   */
  public ContentType getContentType() {
    return contentType;
  }

  /**
   * Get SuccessReport Header
   * @return SuccessReport
   */
  public SuccessReport getSuccessReport() {
    return successReport;
  }

  /**
   * Get Failure Report
   * @return FailureReport
   */
  public FailureReport getFailureReport() {
    return failureReport;
  }

  /**
   * Get Header List
   * @return MsrpRequest
   */
  public MsrpHeaderList getHeaderList() {
    return headers;
  }

  /**
   * Encodes to MSRP Defined Format
   * @return String
   */
  public String encode() {

    StringBuffer buffer = new StringBuffer();
    //Refresh the Headers
    headers = null;
    headers = new MsrpHeaderList();
    if (requestLine != null) {
      buffer.append(requestLine.encode());
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
    if (contentType != null) {
      headers.addHeader(contentType);
    }
    if (successReport != null) {
      headers.addHeader(successReport);
    }
    if (failureReport != null) {
      headers.addHeader(failureReport);
    }

    if (headers != null) {
      buffer.append(headers.encodeHeaders());
      buffer.append(Separators.NEWLINE);
    }
    if (body != null) {
      buffer.append(encodeBody());
    }
    return buffer.toString();
  }

  /**
   * Encodes body to MSRP defined Format
   * @return String
   */
  public String encodeBody() {
    StringBuffer buffer = new StringBuffer(body);
    buffer.append(Separators.NEWLINE);
    buffer.append("-------");
    buffer.append(getTransactionId());
    buffer.append("$");
    return buffer.toString();

  }

  /**
   * Parse MSRP Bytes
   * @param msrpBytes String
   */
  private void parseMsrpBytes(String msrpBytes) {

    RequestParser parser = new RequestParser(msrpBytes);
    requestLine = parser.getRequestLine();
    toPath = parser.getToPath();
    fromPath = parser.getFromPath();
    messageID = parser.getMessageID();
    byteRange = parser.getByteRange();
    contentType = parser.getContentType();
    if (parser.getSuccessReport() != null) {
      successReport = parser.getSuccessReport();
    }
    if (parser.getFailureReport() != null) {
      failureReport = parser.getFailureReport();
    }
    body = parser.getBody();
  }

  /**
   * Set Transaction Id
   * @param transactionId String
   */
  public void setTransactionId(String transactionId) {
    requestLine.setTransactionId(transactionId);
  }

  /**
   * Get Transaction Id
   * @return String
   */
  public String getTransactionId() {
    return requestLine.getTransactionId();
  }

  /**
   * Convert to the String representation
   * @return String
   */
  public String toString() {
    return encode();
  }
}
