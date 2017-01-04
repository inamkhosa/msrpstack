package msrp.message;

import msrp.header.SuccessReport;
import msrp.header.Status;
import msrp.header.ContentType;
import msrp.header.FailureReport;
import msrp.header.MessageID;
import msrp.header.FromPath;
import msrp.header.ByteRange;
import msrp.header.ToPath;

/**
 * 
 * @author inam_
 */
public interface MsrpMessageFactory {
  /**
   * Genric Message Factory
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
                                   String body);

  /**
   * This method creates MSRP Header
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
                                   String body);

  /**
   * This method creates Request when supplied forllowing params
   * @param request String
   * @return MsrpRequest
   */
  public MsrpRequest createRequest(String request);

  /**
   * Creates MSRP Response
   * @param statusCode int
   * @param toPath ToPath
   * @param fromPath FromPath
   * @param byteRange ByteRange
   * @param body String
   * @return MsrpResponse
   */
  public MsrpResponse createResponse(int statusCode, ByteRange byteRange,
                                     Status status,
                                     MsrpRequest request,
                                     String body);

  /**
   * Creates MSRP Response with following params as input
   * @param statusCode int
   * @param request MsrpRequest
   * @param byteRange ByteRange
   * @param body String
   * @return MsrpResponse
   */
  public MsrpResponse createResponse(int statusCode, MsrpRequest request,
                                     ByteRange byteRange,
                                     String body);

  /**
   * Creates MSRP Response with Following params as input
   * @param statusCode int
   * @param request MsrpRequest
   * @return MsrpResponse
   */

  public MsrpResponse createResponse(int statusCode, MsrpRequest request);

  /**
   * Creates MSRP Response
   * @param response String
   * @return MsrpResponse
   */
  public MsrpResponse createResponse(String response);
}
