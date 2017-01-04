package msrp.header;
/**
 * 
 * @author inam_
 */
public class ContentType
    extends MsrpHeader {
  String type;
  String subType;
  /**
   *
   */
  public ContentType() {
  }

  /**
   *
   * @param contentType String
   */
  public ContentType(String contentType) {
    parse(contentType);
  }

  /**
   * Construct ContentType Object
   * @param content String
   * @param contentType String
   */
  public ContentType(String type, String subType) {
    this.type = type;
    this.subType = subType;
  }

  /**
   *
   * @param contentType String
   */
  private void parse(String strContents) {
    int index = strContents.indexOf("/");
    type = strContents.substring(0, index);
    index += 1;
    subType = strContents.substring(index, strContents.length());
  }

  /**
   *
   * @return String
   */
  public String getHeaderName() {
    return "Content-Type";
  }

  /**
   * Get the Content Type e.g. text, html , application , message
   * @return String conetentType
   */
  public String getContentType() {
    return type;
  }

  /**
   * Get content Format e.g. plain, xhtml, xml etc.
   * @return String content format
   */
  public String getContentSubType() {
    return subType;
  }

  /**
   * Set the content Type
   * @param contentTye String
   */
  public void setType(String contentType) {
    this.type = contentType;
  }

  /**
   * Set the Content format
   * @param contentFormat String
   */
  public void setSubType(String subType) {
    this.subType = subType;
  }

  /**
   * Encodes Header to MSRP Defined Format
   * @return String
   */
  public String encode() {
    StringBuffer contentHeader = new StringBuffer();
    if (type != null) {
      contentHeader.append(type);
      contentHeader.append("/");
    }
    if (subType != null) {
      contentHeader.append(subType);
    }

    return contentHeader.toString();
  }

  /**
   * toString Represenation of Header
   * @return String
   */
  public String toString() {
    return encode();
  }
}
