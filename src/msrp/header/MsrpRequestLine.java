package msrp.header;

import msrp.util.Separators;
/**
 * 
 * @author inam_
 */
public class MsrpRequestLine {
  String method = null;
  String transactionId = null;
  String scheme = null;
  /**
   * Default Constructor - Intializes the Object
   */
  public MsrpRequestLine() {
  }

  /**
   * Parameterized Constructor
   * @param requestLine String
   */
  public MsrpRequestLine(String requestLine) {
    parse(requestLine);
  }

  /**
   * Parametrized Constructor
   * @param method String
   * @param transactionId String
   */
  public MsrpRequestLine(String method, String transactionId) {
    this.transactionId = transactionId;
    this.method = method;
  }

  /**
   * Get the Method
   *
   * @return method string.
   */
  public String getMethod() {
    return method;
  }

  /**
   * Set the Method
   * @param method String
   */
  public void setMethod(String method) {
    this.method = method;
  }

  /**
   * Get the TransactionId
   * @return TransactionId string.
   */
  public String getTransactionId() {
    return transactionId;
  }

  /**
   * Set the TransactionId
   * @param transactionId String
   */
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  /**
   * Encode the Request Line
   * @return String encoded RequestLine Header
   */
  public String encode() {
    StringBuffer requestLine = new StringBuffer();
    requestLine.append("MSRP");
    requestLine.append(Separators.SP);
    if (transactionId != null) {
      requestLine.append(transactionId);
      requestLine.append(Separators.SP);
    }
    if (method != null) {
      requestLine.append(method);
      requestLine.append(Separators.SP);
    }

    return requestLine.toString();
  }

  /**
   * Parses MSRP Request Line
   * @param requestLine String
   */
  private void parse(String requestLine) {

    String[] buffer = requestLine.split(Separators.SP);
    if (buffer.length > 0) {
      scheme = buffer[0];
      transactionId = buffer[1];
      method = buffer[2];
    }
  }

}
