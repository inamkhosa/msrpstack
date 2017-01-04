package msrp.header;

import msrp.util.Separators;

/**
 * 
 * @author inam_
 */
public class Status
    extends MsrpHeader {

  String namespace = "000";
  int statusCode;
  String reason;
  /**
   * Default Constructor
   */
  public Status() {
  }

  /**
   * Parametrized Constructor
   * @param namespace String
   * @param statusCode int
   * @param reason String
   */
  public Status(String namespace, int statusCode, String reason) {
    this.namespace = namespace;
    this.statusCode = statusCode;
    this.reason = reason;
  }

  /**
   * Parametrized Constructor
   * @param statusCode int
   * @param reason String
   */
  public Status(int statusCode, String reason) {
    this.namespace = "000";
    this.statusCode = statusCode;
    this.reason = reason;
  }

  /**
   * Set the Namespace Value
   * @param namespace int
   */
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  /**
   * Get the Namespace Value
   * @return int
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Set the Status Code
   * @param code int
   */
  public void setStatusCode(int code) {
    this.statusCode = code;
  }

  /**
   * Get Status Code
   * @return int
   */
  public int getStatusCode() {
    return statusCode;
  }

  /**
   * Set the Reason Phrase
   * @param reason String
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

  /**
   * Get Reason Phrase
   * @return String
   */
  public String getReason() {
    return reason;
  }

  /**
   * Get Header Name
   * @return String
   */
  public String getHeaderName() {
    return "Status";
  }

  /**
   * Encodes Satus Header
   * @return String
   */
  public String encode() {

    StringBuffer buffer = new StringBuffer();
    if (namespace != null) {
      buffer.append(String.valueOf(namespace));
      buffer.append(Separators.SP);
    }
    if (statusCode > 0) {
      buffer.append(String.valueOf(statusCode));
      if (reason != null) {
        buffer.append(Separators.SP);
      }
    }
    if (reason != null) {
      buffer.append(reason);
    }

    return buffer.toString();
  }

  /**
   * toString Represenation
   * @return String
   */
  public String toString() {
    return encode();
  }
}
