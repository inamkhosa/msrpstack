package msrp.test.header;

import msrp.header.FromPath;
import msrp.address.MsrpUrl;
import java.util.*;

import junit.framework.*;

public class FromHeaderTest
    extends TestCase {
  public void test() {

    String fromUrl = "msrp://biloxi.example.com:12763/kjhd37s2s20w2a;tcp";
    MsrpUrl msrpURL = new MsrpUrl(fromUrl);
    //Test From Path Header
    FromPath fromHeader = new FromPath(msrpURL);
    List fromList = fromHeader.getURLs();
    assertEquals(fromUrl, fromList.get(0).toString());

  }
}
