package msrp.test.examples.messanger;

import msrp.stack.MsrpStack;
import java.util.Date;
import java.io.Serializable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;

/**
 * 
 * @author inam_
 */
public class MsgLogger
    extends Object implements Serializable {

  public String logFileName = null;
  public String path = null;
  /**
   *
   * @param event Object
   */
  public static void logEvent(Object event) {
    //Take the path from the configuration
//    String path = "D:\\CarrierTel\\jain_msrp\\log\\", logFileName = "log.txt";
    String path = MsrpStack.getMsgLogPath(), logFileName = "messagelog.txt";
    String fullPath = path + logFileName;
    File file = new File(fullPath);
    file.setLastModified(System.currentTimeMillis());
    try {
      FileOutputStream log = new FileOutputStream(file, true);
      String msg = (String) event;
      log.write("\r\n#################################\r\n".getBytes());
      Date time = new Date();
      String timeStamp = "\r\nLog Time: ";
      log.write(timeStamp.getBytes());
      log.write(time.toString().getBytes());
      log.write("\r\n".getBytes());
      log.write("Log Event:\t".getBytes());
      log.write(msg.getBytes());
      log.flush();
      log.close();
    }
    catch (FileNotFoundException fileNotFoundExp) {
      System.out.println("MsgLogger: File not found exception");
    }
    catch (IOException ioExp) {
      System.out.println("IO Exception" + ioExp.getMessage());
      System.exit(0);
    }
  }
}
