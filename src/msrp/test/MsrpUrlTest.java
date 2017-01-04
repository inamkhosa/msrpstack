package msrp.test;

import msrp.address.MsrpUrl;
import junit.framework.*;

public class MsrpUrlTest
    extends TestCase {

  public void test() {
    String url = "msrp://inam@biloxi.example.com:12763/kjhd37s2s20w2a;tcp";
    MsrpUrl msrpURL = new MsrpUrl(url);
    assertEquals("msrp", msrpURL.getScheme());
    assertEquals("inam", msrpURL.getUser());
    assertEquals("biloxi.example.com", msrpURL.getHost());
    assertEquals(12763, msrpURL.getPort());
    assertEquals("kjhd37s2s20w2a", msrpURL.getSessionId());
    assertEquals("tcp", msrpURL.getTransport());
  }
}
