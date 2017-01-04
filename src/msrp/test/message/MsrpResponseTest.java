package msrp.test.message;

import msrp.util.Separators;
import msrp.message.MsrpResponse;
import junit.framework.*;

public class MsrpResponseTest
    extends TestCase {

  public void testMsrpResponse() {
    /**
         MsrpResponse response = new MsrpResponse();
         response.setToPath(new ToPath(new MsrpUrl(
        "msrp://inam@carriertel.com:1223/rede234ff;tcp")));
         response.setFromPath(new FromPath(new MsrpUrl(
        "msrp://inam@carriertel.com:1223/rede234ff;tcp")));
         response.setResponseCode(200);
         response.setResponsePhrase("OK");
         response.setTransactionId("232323");
         System.out.println(response.toString());
     */
    StringBuffer buffer = new StringBuffer("MSRP a786hjs2 200 OK");
    buffer.append(Separators.NEWLINE);
    buffer.append("To-Path: msrp://inam@carriertel.com:9090/a786hjs2;tcp");
    buffer.append(Separators.NEWLINE);
    buffer.append("From-Path: msrp://inam@carriertel.com:9090/a786hjs2;tcp");
    buffer.append(Separators.NEWLINE);
    buffer.append("Byte-Range: 1-16/22");
    buffer.append(Separators.NEWLINE);
    buffer.append("-------a786hjs2$");

    MsrpResponse response2 = new MsrpResponse(buffer.toString());
    System.out.println(response2.toString());

  }
}
