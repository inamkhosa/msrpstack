package msrp.test.examples.messanger;
import java.io.*;
import java.io.Serializable;

/**
 * 
 * @author inam_
 */
public class Configuration
    extends Object implements Serializable {
  File file =null;
  FileInputStream inputStream = null;
  FileOutputStream outputStream  =null;
  /**
   * Configuration
   */
  public Configuration (){
    file = new File("");
    try{
      inputStream = new FileInputStream(file);
      outputStream = new FileOutputStream(file,true);
    }catch(Exception exp){
      System.out.println("Configuration: " + exp.toString());
    }
  }
  /**
   *
   */
  public void parse(){
  }
  /**
   *
   * @param entry String
   */
  public void writeEntry(String entryName, String value){

    try {
      //Write Entry name
      outputStream.write(entryName.getBytes());
      outputStream.write("=".getBytes());
      outputStream.write(value.getBytes());
      outputStream.write("\r\n".getBytes());
    }
    catch (IOException ex) {
    }
  }
  public void readEntry(String entryName){
  }

}
