package msrp.header;

import msrp.util.Separators;
/**
 * 
 * @author inam_
 */
public class MsrpResponseLine {

  String transactionId;
  int responseCode;
  String reason;
  String scheme;
  /**
   * Default Constructor.
   */
  public MsrpResponseLine() {
  }

  /**
   * Parametrized Constructor
   * @param responseLine String
   */
  public MsrpResponseLine(String responseLine) {
    parse(responseLine);
  }

  /**
   * Set Transaction Id
   * @param transactionId int
   */
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  /**
   * Set the Response Code
   * @param code int
   */
  public void setResponseCode(int code) {
    this.responseCode = code;
  }

  /**
   * Get the Response Code
   * @return int
   */
  public int getResponseCode() {
    return responseCode;
  }

  /**
   * Get the Transaction Id
   * @return int
   */
  public String getTransactionId() {
    return transactionId;
  }

  /**
   * Set Response Phrase
   * @param comments String
   */
  public void setReasonPhrase(String reason) {
    this.reason = reason;
  }

  /**
   * Get the Response Phrase
   * @return String
   */
  public String getReasonPhrase() {
    return reason;
  }

  /**
   * Parse the Response Line
   * @param responseLine String
   */
  private void parse(String responseLine) {
    String[] responseLineFields = responseLine.split(Separators.SP);
    scheme = responseLineFields[0];
    transactionId = responseLineFields[1];
    responseCode = Integer.parseInt(responseLineFields[2]);
    reason = responseLineFields[3];
  }

  public String getScheme() {
    return scheme;
  }

  /**
   *
   * @return String
   */
  public String encode() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("MSRP");
    buffer.append(Separators.SP);
    if (transactionId != null) {
      buffer.append(transactionId);
      buffer.append(Separators.SP);
    }
    if (responseCode > 0) {
      buffer.append(String.valueOf(responseCode));
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
   *
   * @return String
   */
  public String toString() {
    return encode();
  }
}
