package msrp.test.message;

import msrp.message.MsrpRequest;
import msrp.message.MessageFactoryImpl;
import msrp.message.MsrpResponse;
import msrp.header.SuccessReport;
import msrp.header.Status;
import msrp.header.ContentType;
import msrp.header.FailureReport;
import msrp.header.MessageID;
import msrp.header.FromPath;
import msrp.header.ByteRange;
import msrp.header.ToPath;
import gov.nist.core.*;
import junit.framework.*;

public class MessageFactoryImplTest
    extends TestCase {

  public void testFactory() {

    MessageFactoryImpl impl = new MessageFactoryImpl();
    String method = "SEND";
    ToPath toPath = new ToPath("msrp://inam@carriertel.com:8090/xyz;tcp");
    FromPath fromPath = new FromPath("msrp://inam@yahoo.com:9090/xyz;tcp");
    MessageID messageID = new MessageID("1234");
    ByteRange byteRange = new ByteRange(1, 5, 5);
    ContentType contentType = new ContentType("text", "plain");
    SuccessReport successReport = new SuccessReport(true);
    FailureReport failureReport = new FailureReport("partial");
    String body = "hi , how are you ---message 1?";
    MsrpRequest req1 = impl.createRequest(method, toPath, fromPath, messageID,
                                          byteRange,
                                          contentType, successReport,
                                          failureReport, body);
    System.out.println("#################Request# 1#####################\n" +
                       req1.toString());

    MsrpRequest req2 = impl.createRequest(method, toPath, fromPath, messageID,
                                          byteRange, contentType, body);
    req2.setBody("this is message 2");
    System.out.println("#################Request# 2#####################\n" +
                       req2.toString());
    System.out.println("#################Request# 2#####################\n" +
                       req2.toString());

    // Construtors Message 3 Test

    StringBuffer str = new StringBuffer("MSRP a786hjs2 SEND" +
                                        Separators.NEWLINE);
    str.append("To-Path: msrp://biloxi.example.com:12763/kjhd37s2s20w2a;tcp" +
               Separators.NEWLINE);
    str.append("From-Path: msrp://atlanta.example.com:7654/jshA7weztas;tcp" +
               Separators.NEWLINE);
    str.append("Message-ID:87652491" + Separators.NEWLINE);
    str.append("Byte-Range:1-25/25" + Separators.NEWLINE);
    str.append("Content-Type:text/plain" + Separators.NEWLINE);
    str.append("Success-Report: yes" + Separators.NEWLINE);
    str.append("Failure-Report: no" + Separators.NEWLINE);
    str.append("This is message 3" + "?" + Separators.RETURN);
    str.append("-------a786hjs2$");

    MsrpRequest req3 = impl.createRequest(str.toString());
    System.out.println("#################Request# 3#####################\n" +
                       req3.toString());

    System.out.println(
        "****************************Responses****************************\n");
    MsrpResponse res1 = impl.createResponse(200, new ByteRange(1, 2, 2),
                                            new Status(200, "OK"), req1,
                                            "Response Body");
    System.out.println("#################Response# 1#####################\n" +
                       res1.toString());
    MsrpResponse res2 = impl.createResponse(403, req2, new ByteRange(1, 2, 3),
                                            "");
    System.out.println("##################Response# 2######################\n" +
                       res2.toString());
    MsrpResponse res3 = impl.createResponse(400, req2);
    System.out.println("##################Response# 3 ####################\n" +
                       res3.toString());
  }
}
