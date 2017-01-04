package msrp.test.header;

import msrp.message.MsrpMethod;
import msrp.header.MsrpRequestLine;
import junit.framework.*;

public class RequestLineTest
    extends TestCase {
  public void testRequestLine() {

    MsrpRequestLine requestLine = new MsrpRequestLine(MsrpMethod.SEND,
        "a786hjs2");
    assertEquals(MsrpMethod.SEND, requestLine.getMethod());
    assertEquals("a786hjs2", requestLine.getTransactionId());
    MsrpRequestLine line = new MsrpRequestLine("MSRP REPORT 12345");
    assertEquals("REPORT", line.getMethod());
    assertEquals("12345", line.getTransactionId());

  }
}
