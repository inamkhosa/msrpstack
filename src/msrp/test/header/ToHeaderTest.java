package msrp.test.header;

import msrp.header.ToPath;
import msrp.address.MsrpUrl;
import java.util.*;

import junit.framework.*;

public class ToHeaderTest
    extends TestCase {

  public void test() {

    String fromUrl = "msrp://abc.xyz.com:12763/kjhd37s2s20w2a;tcp";
    MsrpUrl msrpURL = new MsrpUrl(fromUrl);
    //Test From Path Header
    ToPath toHeader = new ToPath(msrpURL);
    List toList = toHeader.getURLs();
    assertEquals(fromUrl, toList.get(0).toString());

  }

}
