package msrp.message;

import msrp.util.Separators;
import msrp.header.SuccessReport;
import msrp.header.ContentType;
import msrp.header.FailureReport;
import msrp.header.MessageID;
import msrp.header.FromPath;
import msrp.header.MsrpRequestLine;
import msrp.header.ByteRange;
import msrp.header.ToPath;
import msrp.address.MsrpUrl;

/**
 * 
 * @author inam_
 */

public class RequestParser {

  FromPath fromPath = null;
  ToPath toPath = null;
  MessageID messageID = null;
  ByteRange byteRange = null;
  ContentType contentType = null;
  SuccessReport successReport = null;
  FailureReport failureReport = null;
  String body = null;
  MsrpRequestLine requestLine = null;
  /**
   * Default Constructor
   * @param request String
   */
  public RequestParser(String request) {
    parse(request);
  }

  /**
   * This method parses an MSRP Request
   * @param request String
   */
  private void parse(String request) {

    String[] requestFields = request.split(Separators.NEWLINE);
    int fieldIndex = 0;
    StringBuffer headerValue = null;

    if (requestFields.length > 0) {
      //Request Line
      headerValue = new StringBuffer(requestFields[fieldIndex++].trim());
      requestLine = new MsrpRequestLine(headerValue.toString());
      //Check if the From-Path Header
      if (request.matches("To-Path")) {
        headerValue = new StringBuffer(requestFields[fieldIndex++].trim());
        int index = headerValue.indexOf(":");
        headerValue.delete(0, index + 1);
        MsrpUrl fromUrl = new MsrpUrl(headerValue.toString());
        toPath = new ToPath(fromUrl);
        headerValue = null;
      }
      if (request.matches("From-Path")) {
        headerValue = new StringBuffer(requestFields[fieldIndex++].trim());
        int index = headerValue.indexOf(":");
        headerValue.delete(0, index + 1);
        MsrpUrl fromUrl = new MsrpUrl(headerValue.toString());
        fromPath = new FromPath(fromUrl);
        headerValue = null;
      }
      if (request.matches("Message-ID")) {
        headerValue = new StringBuffer(requestFields[fieldIndex++].trim());
        int index = headerValue.indexOf(":");
        headerValue.delete(0, index + 1);
        String id = headerValue.toString();
        messageID = new MessageID(id);
        headerValue = null;
      }
      if (request.matches("Byte-Range")) {
        headerValue = new StringBuffer(requestFields[fieldIndex++].trim());
        int index = headerValue.indexOf(":");
        headerValue.delete(0, index + 1);
        byteRange = new ByteRange(headerValue.toString());
        headerValue = null;
      }
      if (request.matches("Content-Type")) {
        headerValue = new StringBuffer(requestFields[fieldIndex++].trim());
        int index = headerValue.indexOf(":");
        headerValue.delete(0, index + 1);
        contentType = new ContentType(headerValue.toString());
        headerValue = null;
      }
      if (request.matches("Success-Report")) {
        headerValue = new StringBuffer(requestFields[fieldIndex++].trim());
        int index = headerValue.indexOf(":");
        headerValue.delete(0, index + 1);
        String val = headerValue.toString();
        if (val.compareToIgnoreCase("yes") == 0) {
          successReport = new SuccessReport(true);
        }
        else {
          successReport = new SuccessReport(false);
        }

        headerValue = null;
      }
      if (request.matches("Failure-Report")) {
        headerValue = new StringBuffer(requestFields[fieldIndex++].trim());
        int index = headerValue.indexOf(":");
        headerValue.delete(0, index + 1);
        failureReport = new FailureReport(headerValue.toString());
        headerValue = null;
      }

      StringBuffer Contents = new StringBuffer();

      for (int i = fieldIndex; i < requestFields.length; i++) {
        Contents.append(requestFields[i]);
      }

      String terminator = "-------" + requestLine.getTransactionId() + "$";
      int position = Contents.indexOf(terminator);
      Contents.delete(position, Contents.length());
      body = new String(Contents.toString());
    }

  }

  /**
   * Get ToPath
   * @return ToPath
   */
  public ToPath getToPath() {
    return toPath;
  }

  /**
   * Get From Path
   * @return FromPath
   */
  public FromPath getFromPath() {
    return fromPath;
  }

  /**
   * get message Id
   * @return MessageID
   */
  public MessageID getMessageID() {
    return messageID;
  }

  /**
   * get byte range
   * @return ByteRange
   */
  public ByteRange getByteRange() {
    return byteRange;
  }

  /**
   * get content type
   * @return ContentType
   */
  public ContentType getContentType() {
    return contentType;
  }

  /**
   * get success report
   * @return SuccessReport
   */
  public SuccessReport getSuccessReport() {
    return successReport;
  }

  /**
   *
   * @return FailureReport
   */
  public FailureReport getFailureReport() {
    return failureReport;
  }

  /**
   * get body
   * @return String
   */
  public String getBody() {
    return body;
  }

  /**
   * get response line
   */
  public MsrpRequestLine getRequestLine() {
    return requestLine;
  }

}
