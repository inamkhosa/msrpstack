package msrp.test.message;

import msrp.message.MsrpRequest;
import msrp.message.MsrpResponse;
import msrp.message.MsrpMessage;
import gov.nist.core.*;
import junit.framework.*;

public class MsrpMessageTest
    extends TestCase {
  public void testMsrpMessage() {
    StringBuffer str = new StringBuffer("MSRP a786hjs2 SEND" +
                                        Separators.NEWLINE);
    str.append("To-Path: msrp://biloxi.example.com:12763/kjhd37s2s20w2a;tcp" +
               Separators.NEWLINE);
    str.append("From-Path: msrp://atlanta.example.com:7654/jshA7weztas;tcp" +
               Separators.NEWLINE);
    str.append("Message-ID:87652491" + Separators.NEWLINE);
    str.append("Byte-Range:1-25/25" + Separators.NEWLINE);
    str.append("Content-Type:text/plain" + Separators.NEWLINE);
    str.append("This is message Line 1" + Separators.RETURN);
    str.append("This is message Line 2" + "?" + Separators.RETURN);
    str.append("-------a786hjs2$");

    MsrpMessage message = new MsrpMessage(str.toString());
    System.out.println(message.isRequest());
    ///////////////////////////////////////////////////////////
    StringBuffer buffer = new StringBuffer("MSRP a786hjs2 200 OK");
    buffer.append(Separators.NEWLINE);
    buffer.append("To-Path: msrp://inam@carriertel.com:9090/fssf44;tcp");
    buffer.append(Separators.NEWLINE);
    buffer.append("From-Path: msrp://inam@carriertel.com:9090/fssed;tcp");
    buffer.append(Separators.NEWLINE);
    buffer.append("Byte-Range: 1-16/22");
    buffer.append(Separators.NEWLINE);
    buffer.append("-------a786hjs2$");
    MsrpMessage message2 = new MsrpMessage(buffer.toString());
    System.out.println(message2.isRequest());

    MsrpRequest req = message.getMsrpRequest();
    System.out.println(req.toString());

    MsrpResponse res = message2.getMsrpResponse();
    System.out.println(res.toString());
  }
}
