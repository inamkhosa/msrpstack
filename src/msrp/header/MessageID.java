package msrp.header;
/**
 * 
 * @author inam_
 */
public class MessageID
    extends MsrpHeader {

  public String messageId;
  /**
   *
   */
  public MessageID() {
  }

  /**
   * Constructor- Intializes Object
   * @param messageIdLine String
   */
  public MessageID(String messageIdLine) {
    this.messageId = messageIdLine;
  }

  /**
   * Set MessageId value
   */
  public void setMesssageID(String messageId) {
    this.messageId = messageId;
  }

  /**
   * Return MessageId Value
   * @return String
   */
  public String getMessageID() {
    return messageId;
  }

  /**
   * Encodes to MSRP defined Format.
   * @return String
   */
  public String encode() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(String.valueOf(messageId));
    return buffer.toString();
  }

  /**
   * Get the Header Name
   * @return String
   */
  public String getHeaderName() {
    return "Message-ID";
  }

  /**
   * toString Representation of MSRP Header
   * @return String
   */
  public String toString() {
    return encode();
  }

}
