package msrp.stack;

import java.io.*;
import java.util.*;

/**
 * 
 * @author inam_
 */
public class Logger
    extends Object implements Serializable {

  public String logFileName = null;
  public String path = null;
  /**
   * Log an Event
   * @param event Object
   */
  public static void logEvent(Object event) {
    //Take the path from the configuration
//    String path = "D:\\CarrierTel\\jain_msrp\\log\\", logFileName = "log.txt";
    String path = MsrpStack.getStackLogPath(), logFileName = "log.txt";
    String fullPath = path + logFileName;
    File file = new File(fullPath);
    file.setLastModified(System.currentTimeMillis());
    try {
      FileOutputStream log = new FileOutputStream(file, true);
      String msg = (String) event;
      log.write("\r\n#################################\r\n".getBytes());
      Date time = new Date();
      String timeStamp = "\r\nTime of Log:\t";
      log.write(timeStamp.getBytes());
      log.write(time.toString().getBytes());
      log.write("\r\n".getBytes());
      log.write("Message:\t".getBytes());
      log.write(msg.getBytes());
      log.flush();
      log.close();
    }
    catch (FileNotFoundException fileNotFoundExp) {
      System.out.println("File not found exception");
    }
    catch (IOException ioExp) {
      System.out.println("IO Exception" + ioExp.getMessage());
      System.exit(0);
    }
  }
}
