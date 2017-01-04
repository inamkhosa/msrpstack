package msrp.address;
/**
 * <p> MsrpUrl.Java </p>
 *
 * <p>MSRP URL Parser Class</p>
 * @author Inam
 * @version 1.0
 */
public class MsrpUrl {

  private String scheme;
  private String user;
  private String host;
  private int port;
  private String sessionId;
  private String transport;
  /**
   * Constructor intializes MSRP URL
   * @param url String - contains msrp url string
   */
  public MsrpUrl(String url) {
    parse(url.toLowerCase());
  }

  /**
   * This method returns port number contained in msrp url
   * @return int - contains port number
   */
  public int getPort() {
    return port;
  }

  /**
   * this method returns user part of msrp url string
   * @return String - contains user value
   */
  public String getUser() {
    return user.toLowerCase();
  }

  /**
   * this method returns the host part of msrp url string
   * @return String - contains host value
   */
  public String getHost() {
    return host;
  }

  /**
   * this method returns msrp url scheme part
   * @return String - contains msrp scheme
   */
  public String getScheme() {
    return scheme;
  }

  /**
   * this method return mrsp sessionId part contained in msrp url
   * @return String - contains msrp session value
   */
  public String getSessionId() {
    return sessionId;
  }

  /**
   * this method returns transport part of msrp url
   * @return String - contains transport value
   */
  public String getTransport() {
    return transport;
  }

  /**
   * this method parses msrp url and intialzed the class variable with those values;
   * @param url String - msrp url string
   */
  private void parse(String url) {
    int position = 0;
    String buffer = url;
    position = buffer.indexOf("://");
    //Parse the MSRP Scheme
    scheme = buffer.substring(0, position);
    //Check the scheme checkScheme(scheme);
    position += 3;
    //Check If URL contains User Part
    if (buffer.indexOf("@", position) > 0) {
      user = buffer.substring(position, buffer.indexOf("@"));
      position += user.length() + 1;
    }
    else {
      user = null;
    }
    host = buffer.substring(position, buffer.indexOf("/", position));
    position += host.length() + 1;
    //Check if host part contians port
    if (host.matches(":")) {
      String tempHost = host;
      int index = tempHost.indexOf(":");
      host = tempHost.substring(0, index);
      index += 1;
      port = Integer.parseInt(tempHost.substring(index));
    }
    else {
      port = 0;
    }
    sessionId = buffer.substring(position, buffer.indexOf(";"));
    position += sessionId.length() + 1;
    transport = buffer.substring(position);

  }

  /**
   * toString
   * This method returns the string represenation of MSRP url
   * @return String - contains mrsp url string
   */
  public String toString() {
    StringBuffer str = new StringBuffer(scheme + "://");
    if (user != null && user.length() > 0) {
      str.append(user + "@");
    }
    str.append(host + ":");
    str.append(String.valueOf(port) + "/");
    str.append(sessionId + ";");
    str.append(transport);
    return str.toString();
  }
}
