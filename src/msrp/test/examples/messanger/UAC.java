package msrp.test.examples.messanger;

import msrp.stack.MsrpStack;
import javax.sip.*;
import javax.sip.SipListener;
import javax.sip.address.*;
import javax.sip.message.*;
import javax.sip.header.*;
import java.util.Properties;
import java.util.ArrayList;
import java.net.*;
import java.text.ParseException;
/**
 * 
 * @author inam_
 */


public class UAC
    implements SipListener, Runnable {

  SipFactory sipFactory = null;
  private static AddressFactory addressFactory = null;
  private static MessageFactory messageFactory = null;
  private static HeaderFactory headerFactory = null;
  protected SipStack sipStack = null;
  protected SipProvider udpProvider = null;
  protected SipProvider tcpProvider = null;
  private SipProvider sipProvider;

  protected Dialog dialog = null;

  private ListeningPoint tcpListeningPoint;
  private ListeningPoint udpListeningPoint;

  private ContactHeader contactHeader;
  private SipURI requestURI;
  private CSeqHeader cSeqHeader;
  private FromHeader fromHeader;
  private ToHeader toHeader;
  private RouteHeader routeHeader;
  private ArrayList viaHeaders;
  private MaxForwardsHeader maxForwards;
  private Header extensionHeader;
  private String sdpData;
  private UAC listener;
  private Address fromNameAddress;
  private String routeURI = null;
  private Address routeAddress;
  private ContentTypeHeader contentTypeHeader;
  static String peerHostPort = "";
  static String localHost = "";

  public static String expMessage = null;
  //GUI Variables
  MainWindow mainWindow = null;

  public UAC() {
    // init();
  }

  /**
   *
   * @param requestEvent RequestEvent
   */

  @Override
  public void processRequest(RequestEvent requestEvent) {
    Request request = requestEvent.getRequest();
    if (request.getMethod().compareToIgnoreCase(Request.BYE) == 0) {
      System.out.println(
          "\n\n###############################UAC: Got BYE Sending OK\n\n");
      try {
        Response response = messageFactory.createResponse(200, request);
        ServerTransaction st = requestEvent.getServerTransaction();
        st.sendResponse(response);
      }
      catch (ParseException | InvalidArgumentException | SipException ex) {
        System.out.println("UAC: Exception " + ex.getMessage());
      }
    }
  }

  /**
   *
   * @param responseEvent ResponseEvent
   */
  @Override
  public void processResponse(ResponseEvent responseEvent) {

    Response response = responseEvent.getResponse();
    ClientTransaction clientTransaction = responseEvent.getClientTransaction();
    try {

      if ( (response.getStatusCode() == response.OK) &&
          ( (CSeqHeader) response.getHeader(CSeqHeader.NAME)).getMethod().
          equals(Request.INVITE)) {
        if (clientTransaction != null) {
          dialog = clientTransaction.getDialog();
          Request ackRequest = null;
          ackRequest = dialog.createRequest(Request.ACK);
          System.out.println("UAC: Sending ACK");
          dialog.sendAck(ackRequest);
        }
      }
    }
    catch (SipException ex) {
    }

  }

  /**
   *
   * @param timeoutEvent TimeoutEvent
   */
  public void processTimeout(TimeoutEvent timeoutEvent) {
    //Here we will process request timeout event.
    System.out.println("UAC Event Timedout" +
                       timeoutEvent.getClientTransaction().getDialog().
                       getRemoteParty().getURI().toString());
  }

  /**
   *
   * @param cmdSequence int
     * @return 
   */
  public boolean sendInviteRequest(int cmdSequence) {
    try {

      if (headerFactory == null) {
      }
      cSeqHeader = headerFactory.createCSeqHeader(1, Request.INVITE);
      if (sipProvider == null) {
      }
      CallIdHeader callIdHeader = sipProvider.getNewCallId();
      MsrpStack.setMessageId(callIdHeader.getCallId());
      int fromTag = 1000 + cmdSequence;
      // create To Header
      String toSipAddress = MsrpStack.getPeerAddress();
      String toUser = MsrpStack.getRemoteUser();
      String toDisplayName = MsrpStack.getRemoteUser();

      SipURI toAddress =
          addressFactory.createSipURI(toUser, toSipAddress);

      Address toNameAddress = addressFactory.createAddress(toAddress);

      toNameAddress.setDisplayName(toDisplayName);

      toHeader =
          headerFactory.createToHeader(toNameAddress, null);

      // create Request URI
      peerHostPort = MsrpStack.getPeerAddress() + ":" + MsrpStack.getRemotePort();
      requestURI =
          addressFactory.createSipURI(toUser, peerHostPort);

      fromHeader = headerFactory.createFromHeader(fromNameAddress,
                                                  new Integer(fromTag).toString());
      //Add Route Header
      //routeAddress = addressFactory.createAddress("<sip:inam@127.0.0.1:5070>");
      routeURI = MsrpStack.getRoute();
      routeAddress = addressFactory.createAddress(routeURI);
      routeHeader = headerFactory.createRouteHeader(routeAddress);

      String sdpData =
          "v=0"
          + "\r\no=" + MsrpStack.getLocalUser() +
          " 2890844526 2890844527 IN IP4" +
          MsrpStack.getLocalAddress()
          + "\r\ns= -"
          + "\r\nc=IN IP4 " + MsrpStack.getLocalAddress()
          + "\r\nt=0 0"
          + "\r\nm=message 7394 TCP/MSRP *"
          + "\r\na=accept-types: message/cpim text/plain text/html"
          + "\r\na=path:msrp://" + MsrpStack.getLocalAddress() + ":" + "6060/"
          + "2s93i93idj" + ";tcp";

      Request request = messageFactory.createRequest(requestURI,
          Request.INVITE,
          callIdHeader, cSeqHeader, fromHeader, toHeader, viaHeaders,
          maxForwards);
      request.setHeader(contactHeader);
      request.setHeader(routeHeader);
      request.setContent(sdpData, contentTypeHeader);
      System.out.println("UAC: Request To Send : \n" +
                         request.toString());
      ClientTransaction inviteTid = sipProvider.getNewClientTransaction(request);
      inviteTid.sendRequest();
    }
    catch (Exception ex) {
      System.out.println("UAC: Send Transaction Failed:\t" +
                         ex.getMessage());
      expMessage = ex.getMessage();
      //ex.printStackTrace();
      return false;
    }
    return true;
  }

  public void sendBye() {
    try {
      if (dialog.getState() == DialogState.CONFIRMED) {
        Request byeRequest = dialog.createRequest(Request.BYE);
        ClientTransaction tr =
            sipProvider.getNewClientTransaction(byeRequest);
        System.out.println("UAS: sending bye! ");
        dialog.sendRequest(tr);
        System.out.println("Dialog State = " + dialog.getState() + "\n\n" +
                           byeRequest.toString());
      }
    }
    catch (Exception ex) {
      System.out.println("Sending BYE");
    }

  }

  /**
   *
   */
  public void init() {

    sipStack = null;

    sipFactory = SipFactory.getInstance();

    sipFactory.setPathName("gov.nist");

    Properties properties = new Properties();

//    localHost = MsrpStack.getLocalAddress();
    localHost = getLocalAddress();
    if (localHost != null) {
      properties.setProperty("javax.sip.IP_ADDRESS", localHost);
    }

    properties.setProperty("javax.sip.STACK_NAME", "msrpChat");

    properties.setProperty("javax.sip.RETRANSMISSION_FILTER", "off");

    // The following properties are specific to nist-sip
    // and are not necessarily part of any other jain-sip
    // implementation.
    // You can set a max message size for tcp transport to
    // guard against denial of service attack.
    properties.setProperty("gov.nist.javax.sip.MAX_MESSAGE_SIZE", "1048576");

    // Drop the client connection after we are done with the transaction.
    properties.setProperty("gov.nist.javax.sip.CACHE_CLIENT_CONNECTIONS",
                           "false");
    // Set to 0 in your production code for max speed.
    // You need  16 for logging traces. 32 for debug + traces.
    // Your code will limp at 32 but it is best for debugging.
    properties.setProperty("gov.nist.javax.sip.TRACE_LEVEL", "0");

    try {
      // Create SipStack object
      sipStack = sipFactory.createSipStack(properties);
      System.out.println("SIP Stack Created at IP :\t" +
                         sipStack.getIPAddress().toString());
    }
    catch (PeerUnavailableException e) {
      System.out.print(e.getMessage());
    }

    try {
      headerFactory = sipFactory.createHeaderFactory();

      addressFactory = sipFactory.createAddressFactory();

      messageFactory = sipFactory.createMessageFactory();

      // If you want to try TCP transport change the following to
      //String transport = "tcp";
      String transport = "udp";

      /* udp ListeningPoint */
      udpListeningPoint = sipStack.createListeningPoint(5060, "udp");
      udpProvider = sipStack.createSipProvider(udpListeningPoint);
      listener = this;
      udpProvider.addSipListener(listener);
      System.out.println("UAC - Created UDP Listner at 5060");
      /* tcp ListeningPoint */
      tcpListeningPoint = sipStack.createListeningPoint(5060, "tcp");
      tcpProvider = sipStack.createSipProvider(tcpListeningPoint);
      tcpProvider.addSipListener(listener);
      System.out.println("UAC - Created TCP Listner at\t" +
                         tcpProvider.getSipStack().getIPAddress() + ":" + 5060);

      MsgLogger.logEvent("UAC -UDP Listener Started at :\t" +
                         sipStack.getIPAddress().toString() + 5060 + "\r\n" +
                         "\t\tUAC - TCP Listener Started at:\t " +
                         sipStack.getIPAddress().toString() + 5060);

      sipProvider = transport.equalsIgnoreCase("tcp") ?
          udpProvider : tcpProvider;
//      sipProvider = tcpProvider;

      String fromSipAddress = MsrpStack.getLocalAddress();
      String fromName = MsrpStack.getLocalUser();
      String fromDisplayName = MsrpStack.getLocalUser();
//      String toSipAddress = MsrpStack.getPeerAddress();
//      String toUser = MsrpStack.getRemoteUser();
//      String toDisplayName = MsrpStack.getRemoteUser();

      // create >From Header
      SipURI fromAddress =
          addressFactory.createSipURI(fromName, fromSipAddress);

      fromNameAddress = addressFactory.createAddress(fromAddress);

      fromNameAddress.setDisplayName(fromDisplayName);
      /*
            // create To Header
            SipURI toAddress =
                addressFactory.createSipURI(toUser, toSipAddress);

            Address toNameAddress = addressFactory.createAddress(toAddress);

            toNameAddress.setDisplayName(toDisplayName);

            toHeader =
                headerFactory.createToHeader(toNameAddress, null);

            // create Request URI

             requestURI =
          addressFactory.createSipURI(toUser, peerHostPort);
       */
      // Create ViaHeaders
      viaHeaders = new ArrayList();

      int port = sipProvider.getListeningPoint().getPort();

      ViaHeader viaHeader =
          headerFactory.createViaHeader(
              sipStack.getIPAddress(),
              sipProvider.getListeningPoint().getPort(),
              transport,
              null);

      // add via headers
      viaHeaders.add(viaHeader);

      // Create ContentTypeHeader
      contentTypeHeader =
          headerFactory.createContentTypeHeader("application", "sdp");

      // Create a new MaxForwardsHeader
      maxForwards =
          headerFactory.createMaxForwardsHeader(70);

      // Create contact headers
      String host = sipStack.getIPAddress();

      SipURI contactUrl = addressFactory.createSipURI(fromName, host);
      contactUrl.setPort(tcpListeningPoint.getPort());

      // Create the contact name address.
      SipURI contactURI = addressFactory.createSipURI(fromName, host);
      contactURI.setPort(sipProvider.getListeningPoint().getPort());

      Address contactAddress = addressFactory.createAddress(contactURI);

      // Add the contact address.
      contactAddress.setDisplayName(fromName);

      contactHeader =
          headerFactory.createContactHeader(contactAddress);
      mainWindow = new MainWindow();
    }
    catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
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
    System.out.println("UAC Getting Local IP := " + addr.getHostAddress());
    return addr.getHostAddress();
  }

  @Override
  public void run() {
    init();
  }

//Test purpose

  public static void main(String args[]) {
    UAC uac = new UAC();
    uac.init();
    // uac.sendInviteRequest(1234);
    System.out.println(uac.getLocalAddress());
    //uac.sendBye();
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
