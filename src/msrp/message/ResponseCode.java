package msrp.message;

/**
 * 
 * @author inam_
 */
public interface ResponseCode {
  /**
   * Sets the Response Code for the MSRP Response
   */
  public void setResponseCode(int code);

  /**
   * Set the Response Phrase Describing the Response Code
   */
  public void setResponsePhrase(String reasonPhrase);

  /**
   * Returs Response Code
   * @return int
   */
  public int getResponseCode();

  /**
   * Returns Response Phrase
   * @return String
   */
  public String getResponsePhrase(int code);

  /**
   * The 200 response code indicates a successful transaction
   */
  public static final int OK = 200;
  /**
   * A 400 response indicates a request was unintelligible
   */
  public static final int UNINTELLIGIBLE = 400;
  /**
   * A 403 response indicates the attempted action is not allowed
   */
  public static final int ACTIONNOTALLOWED = 403;
  /**
   * A 408 response indicates that a downstream transaction did not
   * complete in the alloted time
   */
  public static final int TIMEOUT = 408;
  /**
   * A 413 response indicates that the receiver wishes the sender to stop
   * sending the particular message
   */
  public static final int STOP = 413;
  /**
   * A 415 response indicates the SEND request contained a media type that
   * is not understood by the receiver
   */
  public static final int NOTUNDERSTOOD = 415;
  /**
   * A 423 response indicates that one of the requested parameters is out
   * of bounds
   */
  public static final int PARAMOUTOFBOUND = 423;
  /**
   * A 481 response indicates that the indicated session does not exist.
   */
  public static final int SESSIONNOTEXIST = 481;
  /**
   * A 501 response indicates that the recipient does not understand the
   * request method
   */
  public static final int METHODNOTSUPPORTED = 501;
  /**
   * A 506 response indicates that a request arrived on a session which is
   * already bound to another network connection.
   */
  public static final int SESSIONINUSE = 506;
}
