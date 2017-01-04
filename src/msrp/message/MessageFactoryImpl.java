package msrp.message;

import msrp.header.MsrpResponseLine;
import msrp.header.SuccessReport;
import msrp.header.Status;
import msrp.header.ContentType;
import msrp.header.FailureReport;
import msrp.header.MessageID;
import msrp.header.FromPath;
import msrp.header.MsrpRequestLine;
import msrp.header.ByteRange;
import msrp.header.ToPath;
import msrp.address.MsrpUrl;
import java.util.*;

import msrp.stack.MsrpStack;

/**
 * 
 * @author inam_
 */
public class MessageFactoryImpl
    implements MsrpMessageFactory {
  /**
   * This Method Creates MSRP Request Given Value
   * @param method String
   * @param toPath ToPath
   * @param fromPath FromPath
   * @param messageID MessageID
   * @param byteRange ByteRange
   * @param contentType ContentType
   * @param successReport SuccessReport
   * @param failureReport FailureReport
   * @param body String
   * @return MsrpRequest
   */
  public MsrpRequest createRequest(String method, ToPath toPath,
                                   FromPath fromPath, MessageID messageID,
                                   ByteRange byteRange, ContentType contentType,
                                   SuccessReport successReport,
                                   FailureReport failureReport,
                                   String body) {
    MsrpRequest newRequest = new MsrpRequest();
    //Prepare Request Line
    MsrpRequestLine requestLine = new MsrpRequestLine();
    requestLine.setMethod(method);
    //have to look into transaction genration
    requestLine.setTransactionId(MsrpStack.getCurrentTransaction());
    newRequest.setRequestLine(requestLine);
    newRequest.setToPath(toPath);
    newRequest.setFromPath(fromPath);
    newRequest.setMessageID(messageID);
    newRequest.setByteRange(byteRange);
    newRequest.setContentType(contentType);
    newRequest.setSuccessReport(successReport);
    newRequest.setFailureReport(failureReport);
    newRequest.setBody(body);
    return newRequest;
  }

  /**
   * This method create MSRP Request when given following values
   * @param method String
   * @param toPath ToPath
   * @param fromPath FromPath
   * @param messageID MessageID
   * @param byteRange ByteRange
   * @param contentType ContentType
   * @param body String
   * @return MsrpRequest
   */
  public MsrpRequest createRequest(String method, ToPath toPath,
                                   FromPath fromPath, MessageID messageID,
                                   ByteRange byteRange, ContentType contentType,
                                   String body) {
    MsrpRequest newRequest = new MsrpRequest();
    //Prepare Request Line
    MsrpRequestLine requestLine = new MsrpRequestLine();
    requestLine.setMethod(method);
    String strId = MsrpStack.getCurrentTransaction();
    if (strId != null) {
      requestLine.setTransactionId(strId);
    }
    else {
      strId = createTransactionId();
      requestLine.setTransactionId(strId);
    }
    newRequest.setRequestLine(requestLine);
    newRequest.setToPath(toPath);
    newRequest.setFromPath(fromPath);
    newRequest.setMessageID(messageID);
    newRequest.setByteRange(byteRange);
    newRequest.setContentType(contentType);
    newRequest.setBody(body);
    return newRequest;
  }

  /**
   * Overloaded Method to Create MSRP request.
   * @param request String
   * @return MsrpRequest
   */
  public MsrpRequest createRequest(String request) {

    MsrpRequest newRequest = new MsrpRequest();
    RequestParser parser = new RequestParser(request);
    newRequest.setRequestLine(parser.getRequestLine());
    newRequest.setToPath(parser.getToPath());
    newRequest.setFromPath(parser.getFromPath());
    newRequest.setMessageID(parser.getMessageID());
    newRequest.setByteRange(parser.getByteRange());
    newRequest.setContentType(parser.getContentType());
    if (parser.getSuccessReport() != null) {
      newRequest.setSuccessReport(parser.getSuccessReport());
    }
    if (parser.getFailureReport() != null) {
      newRequest.setFailureReport(parser.getFailureReport());
    }
    newRequest.setBody(parser.getBody());
    return newRequest;
  }

  /**
   * Creates Reponse when given following params
   * @param statusCode int
   * @param byteRange ByteRange
   * @param status Status
   * @param request MsrpRequest
   * @param body String
   * @return MsrpResponse
   */
  public MsrpResponse createResponse(int statusCode, ByteRange byteRange,
                                     Status status,
                                     MsrpRequest request,
                                     String body) {
    MsrpResponse newResponse = new MsrpResponse();
    //Prepare Response Line

    MsrpResponseLine responseLine = new MsrpResponseLine();
    responseLine.setResponseCode(statusCode);
    responseLine.setReasonPhrase(newResponse.getResponsePhrase(statusCode));
    responseLine.setTransactionId(request.getTransactionId());
    MsrpUrl toUrl = (MsrpUrl) request.getFromPath().getURLs().get(0);
    MsrpUrl fromUrl = (MsrpUrl) request.getToPath().getURLs().get(0);
    newResponse.setResponseLine(responseLine);
    newResponse.setTransactionId(request.getTransactionId());
    newResponse.toPath = new ToPath(toUrl);
    newResponse.fromPath = new FromPath(fromUrl);
    newResponse.setByteRange(byteRange);
    newResponse.setStatus(status);
    newResponse.setBody(body);
    return newResponse;
  }

  /**
   * Overloaded method creates msrp reponse with followin params.
   * @param statusCode int
   * @param request MsrpRequest
   * @param byteRange ByteRange
   * @param body String
   * @return MsrpResponse
   */
  public MsrpResponse createResponse(int statusCode, MsrpRequest request,
                                     ByteRange byteRange,
                                     String body) {

    MsrpResponse newResponse = new MsrpResponse();
    //Prepare Response Line
    MsrpResponseLine responseLine = new MsrpResponseLine();
    responseLine.setResponseCode(statusCode);
    responseLine.setReasonPhrase(newResponse.getResponsePhrase(statusCode));
    responseLine.setTransactionId(request.getTransactionId());
    MsrpUrl toUrl = (MsrpUrl) request.getFromPath().getURLs().get(0);
    MsrpUrl fromUrl = (MsrpUrl) request.getToPath().getURLs().get(0);
    newResponse.setResponseLine(responseLine);
    newResponse.setTransactionId(request.getTransactionId());
    newResponse.toPath = new ToPath(toUrl);
    newResponse.fromPath = new FromPath(fromUrl);
    newResponse.byteRange = byteRange;
    newResponse.setBody(body);
    return newResponse;

  }

  /**
   * creates response with following values as input
   * @param statusCode int
   * @param request MsrpRequest
   * @return MsrpResponse
   */
  public MsrpResponse createResponse(int statusCode, MsrpRequest request) {

    MsrpResponse newResponse = new MsrpResponse();
    //Prepare Response Line
    MsrpResponseLine responseLine = new MsrpResponseLine();
    responseLine.setResponseCode(statusCode);
    responseLine.setReasonPhrase(newResponse.getResponsePhrase(statusCode));
    responseLine.setTransactionId(request.getTransactionId());
    newResponse.setResponseLine(responseLine);
    newResponse.setTransactionId(request.getTransactionId());
    MsrpUrl toUrl = (MsrpUrl) request.getFromPath().getURLs().get(0);
    MsrpUrl fromUrl = (MsrpUrl) request.getToPath().getURLs().get(0);
    newResponse.toPath = new ToPath(toUrl);
    newResponse.fromPath = new FromPath(fromUrl);
    //newResponse.messageID = request.getMessageID();
    newResponse.byteRange = request.getByteRange();
    return newResponse;
  }

  /**
   * Overloaded method
   * @param response String
   * @return MsrpResponse
   */
  public MsrpResponse createResponse(String response) {

    MsrpResponse newResponse = new MsrpResponse(response);
    ResponseParser parser = new ResponseParser(response);

    newResponse.responseLine = parser.getResponseLine();
    newResponse.transactionId = parser.getResponseLine().getTransactionId();
    // Substitute the To and From Path Headers
    MsrpUrl toUrl = (MsrpUrl) parser.getFromPath().getURLs().get(0);
    MsrpUrl fromUrl = (MsrpUrl) parser.getToPath().getURLs().get(0);

    newResponse.toPath = new ToPath(toUrl);
    newResponse.fromPath = new FromPath(fromUrl);
    newResponse.byteRange = parser.getByteRange();
    newResponse.body = parser.getResponseBody();

    return newResponse;
  }

  /**
   * This method creates Transaction Id
   * @return String
   */
  public static String createTransactionId() {
    Random rand = new Random(System.currentTimeMillis());
    long val = rand.nextLong();
    String ret = String.valueOf(val);
    ret = ret.replaceFirst("-", "9");
    return ret;
  }
}
