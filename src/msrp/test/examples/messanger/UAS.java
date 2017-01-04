package msrp.test.examples.messanger;

import msrp.stack.MsrpStack;
import javax.sip.*;
import javax.sip.address.*;
import javax.sip.message.*;
import javax.sip.header.*;
import javax.sip.SipListener;
import java.util.*;
import java.text.*;
//MSRP Imports
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.UnknownHostException;

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
public class UAS
    implements SipListener, Runnable {

  private MessageFactory messageFactory = null;
  private AddressFactory addressFactory = null;
  private HeaderFactory headerFactory = null;
  private SipStack sipStack = null;
  private Dialog dialog = null;
  protected SipProvider tcpProvider = null;
  protected SipProvider udpProvider = null;
  private SipProvider sipProvider;
  public String myAddress;
  public int myPort = 5070;
  private final boolean sessionEstablished = false;
  MainWindow mainWindow = null;
  String localIP = null;
  @Override
  public void processRequest(RequestEvent requestEvent) {
    Request request = requestEvent.getRequest();

      switch (request.getMethod()) {
          case Request.INVITE:
              processInvite(requestEvent);
              break;
          case Request.BYE:
              processBye(requestEvent);
              break;
          case Request.ACK:
              processAck(requestEvent);
              break;
          default:
              break;
      }

  }

  @Override
  public void processResponse(ResponseEvent responseEvent) {
    Response response = responseEvent.getResponse();

  }

  @Override
  public void processTimeout(TimeoutEvent timeoutEvent) {
    Transaction transaction;
    if (timeoutEvent.isServerTransaction()) {
      transaction = timeoutEvent.getServerTransaction();
    }
    else {
      transaction = timeoutEvent.getClientTransaction();
    }
  }

  public void processInvite(RequestEvent requestEvent) {
    try {
      Request request = requestEvent.getRequest();
      //Change Provider here
      ViaHeader via = (ViaHeader) request.getHeader("Via");
      MsrpStack.setCurrentTransaction(via.getBranch());
      SipProvider sipProvider = (SipProvider) requestEvent.getSource();
      Response response = messageFactory.createResponse(Response.OK, request);
      ToHeader toHeader = (ToHeader) response.getHeader(ToHeader.NAME);
      toHeader.setTag("1234");
      String user = MsrpStack.getLocalUser();
      myAddress = MsrpStack.getLocalAddress();
      Address address =
          addressFactory.createAddress(user + "<sip:" + myAddress + ":" +
                                       myPort +
                                       ">");

      ContactHeader contactHeader = headerFactory.createContactHeader(address);
      response.addHeader(contactHeader);
      ServerTransaction st = requestEvent.getServerTransaction();
      if (st == null) {
        st = sipProvider.getNewServerTransaction(request);
      }
      dialog = st.getDialog();
      System.out.println("UAS : Sending OK Response:\n" + response.toString());
      st.sendResponse(response);
    }
    catch (ParseException | InvalidArgumentException | SipException ex) {
    }

  }

  public void processAck(RequestEvent requestEvent) {
    System.out.println("UAS Recieved ACK\n" +
                       requestEvent.getRequest().toString());
    System.out.println(requestEvent.getRequest().toString());

    // Lets Start the MSRP Session Here
    Request request = requestEvent.getRequest();
    FromHeader to = (FromHeader) request.getHeader("From");
    String addr = to.getAddress().getURI().toString();
    String buffer = addr;
    int index = buffer.indexOf("@");
    addr = buffer.substring(index + 1, buffer.length());
    // "amjad" <sip:amjad@192.168.0.1>
    System.out.println("Address =\t" + addr);
    MsrpStack.setPeerAddress(addr);
    MainWindow.chatWindow = new ChatWindow("Recieved Chat Window");
    MainWindow.chatWindow.init();
    MainWindow.chatWindow.setParent(mainWindow);
    MainWindow.chatWindow.setSendReceieveWindow(false);
    MainWindow.SipSessionEstablished();
  }

  public void sendBye() {
    try {
      if (dialog.getState() == DialogState.CONFIRMED) {
        Request byeRequest = dialog.createRequest(Request.BYE);
        ClientTransaction tr =
            sipProvider.getNewClientTransaction(byeRequest);
        System.out.println("UAS: sending bye! ");
        dialog.sendRequest(tr);
        System.out.println("Dialog State = " + dialog.getState());
      }
    }
    catch (SipException ex) {
      System.exit(0);
    }

  }

  public boolean getSessionEstablished() {
    return sessionEstablished;
  }

  public void processBye(RequestEvent requestEvent) {
    System.out.println(
        "\n\n#################### UAS has Recieved BYE and Sending Back OK\n");
    ServerTransaction serverTransaction = requestEvent.getServerTransaction();
    Dialog dialog = serverTransaction.getDialog();
    try {
      Response response = messageFactory.createResponse(200,
          requestEvent.getRequest());
      serverTransaction.sendResponse(response);
      System.out.println(response.toString());
    }
    catch (ParseException | InvalidArgumentException | SipException exp) {
      System.out.println(exp.getMessage());
    }
  }

  public void processCancel(RequestEvent requestEvent) {
  }

  /**
   *
   * @return String
   */
  public String getLocalAddress() {
    InetAddress addr = null;
    try {
      addr = Inet4Address.getLocalHost();

    }
    catch (UnknownHostException exp) {
      MsgLogger.logEvent(exp.getMessage());
    }
    System.out.println("UAS Getting Local System IP:= " + addr.getHostAddress());
    return addr.getHostAddress();
  }

  public void init() {
    SipFactory sipFactory = SipFactory.getInstance();

    sipFactory.setPathName("gov.nist");

    Properties properties = new Properties();
    //Here Read the Local System IP from Conf File
    //String localAddress =MsrpStack.getLocalAddress();
    String localAddress = getLocalAddress();
    properties.setProperty("javax.sip.IP_ADDRESS", localAddress);

    properties.setProperty("javax.sip.RETRANSMISSION_FILTER", "true");

    properties.setProperty("gov.nist.javax.sip.MAX_MESSAGE_SIZE", "4096");

    properties.setProperty("gov.nist.javax.sip.CACHE_SERVER_CONNECTIONS",
                           "false");

    properties.setProperty("javax.sip.STACK_NAME", "msrpChat");
    try {
      sipStack = sipFactory.createSipStack(properties);
      System.out.println("Created SipStack  With name = " +
                         sipStack.getStackName());
    }

    catch (PeerUnavailableException e) {
    }

    try {
      headerFactory = sipFactory.createHeaderFactory();

      addressFactory = sipFactory.createAddressFactory();

      messageFactory = sipFactory.createMessageFactory();

      //Create UDP Provider
      ListeningPoint lpUdp = sipStack.createListeningPoint(5070, "udp");
      this.udpProvider = sipStack.createSipProvider(lpUdp);
      udpProvider.addSipListener(this);

      System.out.println("UAS - Created UDP Provider at " +
                         udpProvider.getSipStack().getIPAddress() + ":" +
                         5070);

      MsgLogger.logEvent("UAS UDP Listener Created at:\t" +
                         udpProvider.getSipStack().getIPAddress() + ":" +
                         5070);

      //Create TCP Provider
      ListeningPoint lpTcp = sipStack.createListeningPoint(5070, "tcp");
      this.tcpProvider = sipStack.createSipProvider(lpTcp);
      tcpProvider.addSipListener(this);
      sipProvider = udpProvider;
      System.out.println("UAS - Created TCP Provider at " +
                         tcpProvider.getSipStack().getIPAddress() + ":" +
                         5070);
      MsgLogger.logEvent("UAS TCP Listener Created at:\t" +
                         tcpProvider.getSipStack().getIPAddress() + ":" +
                         5070);

      mainWindow = new MainWindow();

    }
    catch (Exception ex) {
      System.out.println(ex.getMessage());
      ex.printStackTrace();
    }

  }

  public void run() {
    init();
  }

  public void setLocalIP(String localIP) {
    this.localIP = localIP;
  }

  //For Test Only

  public static void main(String str[]) {
    UAS listner = new UAS();
    listner.init();
  }

    @Override
    public void processIOException(IOExceptionEvent ioee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent tte) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dte) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
