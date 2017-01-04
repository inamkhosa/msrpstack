package msrp.test.stack;

import msrp.stack.MsrpStack;
import msrp.stack.Logger;
import junit.framework.*;

public class StackLoggerTest
    extends TestCase {
  public void testLogger() {
    MsrpStack.setStackLogPath("D:\\CarrierTel\\jain_msrp\\log\\");
    Logger.logEvent("Log Event");
  }
}
