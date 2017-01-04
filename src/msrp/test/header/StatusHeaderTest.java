package msrp.test.header;

import msrp.header.Status;
import junit.framework.*;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class StatusHeaderTest
    extends TestCase {

  public void testStatusHeader() {
    Status status = new Status();
    status.setReason("OK");
    status.setStatusCode(200);
    System.out.println(status.toString());
  }
}
