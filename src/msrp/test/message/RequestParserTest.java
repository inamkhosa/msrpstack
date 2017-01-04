package msrp.test.message;

import msrp.message.RequestParser;
import gov.nist.core.*;
import junit.framework.*;

public class RequestParserTest
    extends TestCase {

  public void testRequestParser() {
    StringBuffer str = new StringBuffer("MSRP a786hjs2 SEND" +
                                        Separators.NEWLINE);
    str.append("To-Path: msrp://biloxi.example.com:12763/kjhd37s2s20w2a;tcp" +
               Separators.NEWLINE);
    str.append("From-Path: msrp://atlanta.example.com:7654/jshA7weztas;tcp" +
               Separators.NEWLINE);
    str.append("Message-ID:87652491" + Separators.NEWLINE);
    str.append("Byte-Range:1-25/25" + Separators.NEWLINE);
    str.append("Content-Type:text/plain" + Separators.NEWLINE);
    str.append("This is message Line 1" + Separators.RETURN);
    str.append("This is message Line 2" + "?" + Separators.RETURN);
    str.append("-------a786hjs2$");

    RequestParser parser = new RequestParser(str.toString());
    System.out.println("To-Path =" +
                       parser.getToPath().getURLs().get(0).toString());
    System.out.println("From-Path =" +
                       parser.getFromPath().getURLs().get(0).toString());
    System.out.println("Message-ID = " + parser.getMessageID().getMessageID());

    System.out.println("Byte-Range Start Byte= " +
                       parser.getByteRange().getStartIndex() +
                       "\nEnd Byte =" + parser.getByteRange().getEndIndex() +
                       "\nSize =" + parser.getByteRange().getSize());
    System.out.println("Content Type = " +
                       parser.getContentType().getContentType() +
                       "\n Content Format = " +
                       parser.getContentType().getContentSubType());
    System.out.println("Message Body =" + parser.getBody());

  }

}
