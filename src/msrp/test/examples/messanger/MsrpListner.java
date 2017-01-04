package msrp.test.examples.messanger;

import msrp.stack.Logger;
import msrp.stack.TCPChannel;
import msrp.message.ResponseCode;
import msrp.message.MsrpRequest;
import msrp.message.MessageFactoryImpl;
import msrp.message.MsrpResponse;
import msrp.message.MsrpMessage;
import java.io.*;
import java.nio.Buffer;
import java.util.*;
import java.net.*;
import java.text.SimpleDateFormat;

/**
 * 
 * @author inam_
 */
public class MsrpListner
    implements Runnable {

  private ServerSocket listenSocket = null;
  private Socket clientSocket = null;
  private InputStream incommingChannel = null;
  TCPChannel tcpChannel = null;
  MessageFactoryImpl messageFactory = null;
  static boolean isActive = false;
  /**
   * Constructor
   */
  public MsrpListner() {
    messageFactory = new MessageFactoryImpl();
    tcpChannel = new TCPChannel();
  }

  /**
   * Listen
   */
  protected void listen() {
    Logger.logEvent("Invokening Msrp Listerner");

    try {
      //String addr="192.168.13.2";
      InetAddress addr = InetAddress.getLocalHost();
      System.out.println(addr.toString());
      listenSocket = new ServerSocket(6060, -1, addr);
      Logger.logEvent("Started Listening on Interface\t" +
                      listenSocket.getLocalSocketAddress().toString());
      System.out.println("Starting Msrp Listener at:\t" +
                         listenSocket.getLocalSocketAddress().toString());
      boolean existFlag = true;
      //Read the Messages
      while (true) {
        //Accept Connection
        clientSocket = listenSocket.accept();
        Logger.logEvent("Message Recieved");
        //Get Inputstream
        incommingChannel = clientSocket.getInputStream();
        //Loop for reading the contents
        int count = incommingChannel.available();
        Logger.logEvent("MessageLength =" + count);
        byte[] buffer = new byte[count + 1];
        incommingChannel.read(buffer);
        String msg = new String(buffer, "utf-8");
        buffer = null;
        processMessage(msg.trim());
      }
    }
    catch (Exception exp) {
      Logger.logEvent(exp.getMessage());
    }
  }

  /**
   *
   * @param strMsg String
   */
  private void processMessage(String strMsg) {
    try {
      MsrpMessage msg = new MsrpMessage(strMsg);
      if (msg.isRequest()) {
        Logger.logEvent("Request Recieved");
        MsrpRequest request = msg.getMsrpRequest();
        if (request != null) {
          Logger.logEvent(request.toString());
          processRequest(request);
        }
      }
      else {
        Logger.logEvent("Response Recieved");
        MsrpResponse response = msg.getMsrpResponse();
        if (response != null) {
          Logger.logEvent(response.toString());
        }
      }
    }
    catch (Exception exp) {
      Logger.logEvent(
          "Exception:##### Location MsrpListner::processMessage() #####\r\n" +
          exp.getMessage());
      exp.printStackTrace();

    }
  }

  /**
   * Process Request
   * @param request MsrpRequest
   */
  public void processRequest(MsrpRequest request) {

    MsrpResponse response = messageFactory.createResponse(ResponseCode.OK,
        request);
    if (MainWindow.chatWindow != null && MainWindow.chatWindow.isSendWindow()) {
      Date dateTime = new Date();
      SimpleDateFormat format = new SimpleDateFormat("H:mm:ss", Locale.ENGLISH);
      MainWindow.chatWindow.addMessageToList("Local[" + format.format(dateTime) +
                                             "]\t:" + request.getBody());
    }
    else if (MainWindow.chatWindow != null &&
             MainWindow.chatWindow.isSendWindow() == false) {
      Date dateTime = new Date();
      SimpleDateFormat format = new SimpleDateFormat("H:mm:ss", Locale.ENGLISH);
      MainWindow.chatWindow.addMessageToList("Remote[" + format.format(dateTime) +
                                             "]\t:" +
                                             request.getBody());
    }
    System.out.println("Remote Message: " + request.getBody());
    System.out.println("\nSending Back Response\n" + response.toString());
    tcpChannel.sendResponse(response);
  }

  private void processResponse(MsrpResponse response) {
    //Return Only if not Error Response
  }

  /**
   *
   */
  public void run() {
    listen();
  }
}
