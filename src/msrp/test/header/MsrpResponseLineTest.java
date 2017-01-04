package msrp.test.header;

import msrp.header.MsrpResponseLine;
import junit.framework.*;

public class MsrpResponseLineTest
    extends TestCase {
  public void testResponseLine() {
    MsrpResponseLine responseLine = new MsrpResponseLine();
    responseLine.setResponseCode(444);
    responseLine.setTransactionId("qw2221dw2d3");
    responseLine.setReasonPhrase("Bad Request");
    System.out.print(responseLine.encode());
  }
}
