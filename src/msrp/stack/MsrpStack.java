package msrp.stack;
/**
 * 
 * @author inam_
 */
public class MsrpStack {
  static int port;
  static int remotePort;
  static String localUser;
  static String remoteUser;
  static String localAddress = null;
  static String peerAddress = null;
  static String msgLogPath = null;
  static String stackLogPath = null;
  static String routeAddress = null;
  private static String currentTransaction;
  private static String messageId;
  /**
   * Get Constructor
   */
  public MsrpStack() {
  }

  /**
   * Set Local User
   */
  public static void setLocalUser(String usr) {
    localUser = usr;
  }

  /**
   * Get the User name
   * @return String
   */
  public static String getLocalUser() {
    return localUser;
  }

  /**
   * Set the remote User address
   * @param toUser String
   */
  public static void setRemoteUser(String toUser) {
    remoteUser = toUser;
  }

  /**
   * Gets the Remote User Address
   * @return String
   */
  public static String getRemoteUser() {
    return remoteUser;
  }

  /**
   * Set Route of the Sip Session
   * @param route String
   */
  public static void setRoute(String route) {
    routeAddress = route;
  }

  /**
   * Get Route of the Sip Session
   * @return String
   */
  public static String getRoute() {
    String temp = "<sip:" + getRemoteUser() + "@" + routeAddress + ">";
    routeAddress = temp;
    return routeAddress;
  }

  /**
   * Set Port
   * @param port int
   */
  public static void setPort(int port) {
    port = port;
  }

  /**
   * Get Local Port
   * @return int
   */
  public static int getLocalPort() {
    return 6060;
  }

  /**
   * Sets Remote Port
   * @param port int
   */
  public static void setRemotePort(int port) {
    remotePort = port;
  }

  /**
   * Get Remote Port
   * @return int
   */
  public static int getRemotePort() {
    return remotePort;
  }

  /**
   * Set Local Address
   * @param address Inet4Address
   */
  public static void setLocalAddress(String address) {
    localAddress = address;
  }

  /**
   * Get Local Address
   * @return InetAddress
   */
  public static String getLocalAddress() {
    return localAddress;
  }

  /**
   * Intialize Stack
   * @return boolean
   */
  public static boolean intialize() {
    return false;
  }

  /**
   * Set Remote Host address
   * @param peerAddress InetAddress
   */
  public static void setPeerAddress(String address) {
    peerAddress = address;
  }

  /**
   * Get Remote Host Address
   * @return InetAddress
   */
  public static String getPeerAddress() {
    return peerAddress;
  }

  /**
   * Sets Message Logger Path
   * @param logPath String
   */
  public static void setMsgLogPath(String logPath) {
    msgLogPath = logPath;
  }

  /**
   * Get Message Logger Path
   * @return String
   */
  public static String getMsgLogPath() {
    return msgLogPath;
  }

  /**
   * Set Stack Log Path
   * @param logPath String
   */
  public static void setStackLogPath(String logPath) {
    stackLogPath = logPath;
  }

  /**
   * Get Stack Logger Path
   * @return String
   */
  public static String getStackLogPath() {
    return stackLogPath;
  }

  /**
   * setCurrentTransaction
   *
   * @param string String
   */
  public static void setCurrentTransaction(String trans) {
    currentTransaction = trans;
  }
  /**
   * String Transaction Id
   * @return String
   */
  public static String getCurrentTransaction() {
    return currentTransaction;
  }

  /**
   * setMessageId
   *
   * @param string String
   */
  public static void setMessageId(String msgId) {
    messageId =msgId;
  }
  /**
   *
   * @return String
   */
  public static String getMessageId(){
    return messageId;
  }
}
