package msrp.test.message;

import msrp.message.MsrpRequest;
import msrp.message.MsrpMethod;
import msrp.header.ContentType;
import msrp.header.MessageID;
import msrp.header.FromPath;
import msrp.header.MsrpRequestLine;
import msrp.header.ByteRange;
import msrp.header.ToPath;
import msrp.address.MsrpUrl;
import junit.framework.*;

public class MsrpRequestTest
    extends TestCase {

  public void testMsrpRequest() {

    String body = "AoA Are you There?";
    MsrpUrl from = new MsrpUrl(
        "msrp://biloxi.example.com:12763/kjhd37s2s20w2a;tcp");
    MsrpUrl to = new MsrpUrl("msrp://atlanta.example.com:7654/jshA7weztas;tcp");

    MsrpRequest request = new MsrpRequest();
    MsrpRequestLine requestLine = new MsrpRequestLine("SEND", "34fdgfd445");
    request.setRequestLine(requestLine);
    FromPath fromPath = new FromPath(from);
    ToPath toPath = new ToPath(to);
    MessageID messageID = new MessageID("11111");
    ByteRange byteRange = new ByteRange("1-25/25");
    ContentType contentType = new ContentType("text", "plain");
    request.setFromPath(fromPath);
    request.setToPath(toPath);
    request.setMessageID(messageID);
    request.setByteRange(byteRange);
    request.setContentType(contentType);
    request.setMethod(MsrpMethod.REPORT);
    request.setBody(body);

    System.out.println(request.toString());

  }

}
