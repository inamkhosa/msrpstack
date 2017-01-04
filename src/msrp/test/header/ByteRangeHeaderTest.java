package msrp.test.header;

import msrp.header.ByteRange;
import junit.framework.*;

public class ByteRangeHeaderTest
    extends TestCase {
  public ByteRangeHeaderTest() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void test() {
    //Test ByteRange Header
    ByteRange byteRangeHeader = new ByteRange("1-25/25");
    assertEquals(1, byteRangeHeader.getStartIndex());
    assertEquals(25, byteRangeHeader.getEndIndex());
    assertEquals(25, byteRangeHeader.getSize());
    System.out.print(byteRangeHeader.encode());

  }

  private void jbInit() throws Exception {
  }
}
