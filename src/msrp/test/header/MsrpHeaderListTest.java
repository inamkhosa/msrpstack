package msrp.test.header;

import msrp.header.SuccessReport;
import msrp.header.Status;
import msrp.header.ContentType;
import msrp.header.MsrpHeaderList;
import msrp.header.FailureReport;
import msrp.header.MessageID;
import msrp.header.FromPath;
import msrp.header.MsrpHeader;
import msrp.header.ByteRange;
import msrp.header.ToPath;
import msrp.address.MsrpUrl;
import junit.framework.*;

public class MsrpHeaderListTest
    extends TestCase {

  public void testMsrpHeaderList() {

    String fromUrl = "msrp://inam@carriertel.com:8090/9di4ee934wzd;tcp";
    String toUrl = "msrp://inam@hotmail.com:6070/uyy76ge333;tcp";
    MsrpUrl msrpFrom = new MsrpUrl(fromUrl);
    MsrpUrl msrpTo = new MsrpUrl(toUrl);

    MsrpHeaderList headerList = new MsrpHeaderList();
    ToPath toPath = new ToPath(msrpTo);
    MsrpHeader header = toPath;
    headerList.addHeader(header);

    header = new FromPath(msrpFrom);
    headerList.addHeader(header);

    header = new MessageID("1111");
    headerList.addHeader(header);

    header = new ByteRange(1, 25, 25);
    headerList.addHeader(header);

    header = new ContentType("text", "plain");
    headerList.addHeader(header);

    header = new SuccessReport(false);
    headerList.addHeader(header);

    header = new FailureReport("partial");
    headerList.addHeader(header);
    header = new Status();
    headerList.addHeader(header);

    System.out.print(headerList.encodeHeaders());

  }
}
