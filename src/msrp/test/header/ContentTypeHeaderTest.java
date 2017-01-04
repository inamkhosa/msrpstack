package msrp.test.header;

import msrp.header.ContentType;
import junit.framework.*;

public class ContentTypeHeaderTest
    extends TestCase {
  public void testContentType() {
    ContentType content = new ContentType();
    content.setType("text");
    content.setSubType("plain");
    assertEquals("plain", content.getContentType());
    assertEquals("text", content.getContentType());
    System.out.println(content.encode());
  }
}
