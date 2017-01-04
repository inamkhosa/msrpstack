package msrp.test.message;

import msrp.util.Separators;
import msrp.message.ResponseParser;
import junit.framework.*;

public class ResponseParserTest
    extends TestCase {
  public void testResponseParser() {
    StringBuffer buffer = new StringBuffer("MSRP a786hjs2 200 OK");
    buffer.append(Separators.NEWLINE);
    buffer.append("To-Path: msrp://inam@carriertel.com:9090/fssf44;tcp");
    buffer.append(Separators.NEWLINE);
    buffer.append("From-Path: msrp://inam@carriertel.com:9090/fssed;tcp");
    buffer.append(Separators.NEWLINE);
    buffer.append("Byte-Range: 1-16/22");
    buffer.append(Separators.NEWLINE);
    buffer.append("-------a786hjs2$");
    ResponseParser parser = new ResponseParser(buffer.toString());
    System.out.println("Byte-Range :" + parser.getByteRange().toString());
    System.out.println("To-Path :" +
                       parser.getToPath().getURLs().get(0).toString());
    System.out.println("From-Path :" +
                       parser.getFromPath().getURLs().get(0).toString());

  }
}
