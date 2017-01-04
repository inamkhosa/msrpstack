package msrp.stack;

import msrp.message.MsrpRequest;
import msrp.message.MsrpResponse;
import msrp.address.MsrpUrl;
import java.io.*;
import java.net.*;

/**
 * 
 * @author inam_
 */
public class TCPChannel {

  private Socket outSocket;
  private OutputStream outgoingChannel;
  int port;
  private MsrpStack stack;
  private String myAddress;
  private InetAddress peerAddress;
  private int peerPort;
  public static String expMessage = null;
  private String peerProtocol;
  /**
   * Default Constructor
   */
  public TCPChannel() {
  }

  /**
   * Sends msrp request
   * @param message MsrpRequest
   * @return boolean
   */
  public boolean sendRequest(MsrpRequest message) {
    MsrpUrl toURL = null;
    //Check if the Message is Response or Request Message
    toURL = (MsrpUrl) message.getToPath().getURLs().get(0);
    String toAddress = toURL.getHost();
    int toPort = toURL.getPort();
    System.out.println("\n\nRemote Address" + toAddress);
    System.out.println("\n#####Message to Send############\n\n\n");
    System.out.println(message.toString());
    try {
      SocketAddress sockAddr = new InetSocketAddress(toAddress,
          toPort); //new InetSocketAddress(Address, port);
      Logger.logEvent("Sending Message at\t" + sockAddr.toString());
      outSocket = new Socket();
      outSocket.connect(sockAddr);
      outgoingChannel = outSocket.getOutputStream();
      //Convert Message to UTF -8 Encoding
      String strMsg = null;
      strMsg = new String(message.toString().getBytes(), "utf-8");
      outgoingChannel.write(strMsg.getBytes());
      outgoingChannel.flush();
      outgoingChannel.close();
      outSocket.close();
    }
    catch (Exception exp) {
      Logger.logEvent("TCPChannel" + exp.getMessage());
      System.out.println(exp.getMessage());
      expMessage = exp.getMessage();
      return false;
    }
    return true;
  }

  /**
   * Sends msrp response
   * @param message MsrpResponse
   * @return boolean
   */
  public boolean sendResponse(MsrpResponse message) {
    MsrpUrl toURL = null;
    //Check if the Message is Response or Request Message
    toURL = (MsrpUrl) message.getToPath().getURLs().get(0);
    String toAddress = toURL.getHost();
    if (toAddress == null) {
      System.out.println("To Address is Empty");
      return false;
    }
    int toPort = toURL.getPort();
    try {

      SocketAddress sockAddr = new InetSocketAddress(toAddress,
          toPort); //new InetSocketAddress(Address, port);
      Logger.logEvent("Sending Message at\t" + sockAddr.toString());

      outSocket = new Socket();
      outSocket.connect(sockAddr);
      outgoingChannel = outSocket.getOutputStream();
      //Convert Message to UTF -8 Encoding
      String strMsg = null;
      try {
        strMsg = new String(message.toString().getBytes(), "utf-8");
      }
      catch (UnsupportedEncodingException exp) {
        Logger.logEvent("TCPChannel UnsupportedEncodingException:\t" +
                        exp.getMessage());
      }
      outgoingChannel.write(strMsg.getBytes());
      outgoingChannel.flush();
      outgoingChannel.close();
      outSocket.close();
    }
    catch (Exception exp) {
      Logger.logEvent("TCPChannel Exception:\t" +
                      exp.getMessage());
    }
    return true;
  }

}
