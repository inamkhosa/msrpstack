package msrp.header;
/**
 * 
 * @author inam_
 */
public class ByteRange
    extends MsrpHeader {

  int startByte;
  int endByte;
  int size;
  /**
   * Default Constructor
   */
  public ByteRange() {
  }

  /**
   * Constructs ByteRange Header Object
   * @param startIndex int
   * @param endIndex int
   * @param size int
   */
  public ByteRange(int startIndex, int endIndex, int size) {
    this.startByte = startIndex;
    this.endByte = endIndex;
    this.size = size;
  }

  /**
   * Constructs ByteRange Header Object
   * @param byteRangeHeader String
   */
  public ByteRange(String byteRangeHeader) {
    parse(byteRangeHeader);
  }

  /**
   * Override from MsrpHeader
   * @return String
   */
  public String getHeaderName() {
    return "Byte-Range";
  }

  /**
   * Get start byte index
   * @return int
   */
  public int getStartIndex() {
    return startByte;
  }

  /**
   * Get end byte index
   * @return int
   */
  public int getEndIndex() {
    return endByte;
  }

  /**
   * Get the size of the message chunk
   * @return int
   */
  public int getSize() {
    return size;
  }

  /**
   * Set starting byte index for message chunk
   * @param startIndex int
   */
  public void setStartIndex(int startIndex) {
    this.startByte = startIndex;
  }

  /**
   * Set ending byte index for message chunk
   * @param endIndex int
   */
  public void setEndIndex(int endIndex) {
    this.endByte = endIndex;
  }

  /**
   * Set the size of message chunk
   * @param size int
   */
  public void setSize(int size) {
    this.size = size;
  }

  /**
   * Parses the ByteRange String
   * @param byteRangeHeader String
   */
  private void parse(String byteRangeHeader) {
    StringBuffer buffer = new StringBuffer(byteRangeHeader.trim());
    int index = buffer.indexOf("-");
    startByte = Integer.parseInt(buffer.substring(0, index));
    index += 1;
    endByte = Integer.parseInt(buffer.substring(index, buffer.indexOf("/")));
    index += buffer.substring(index, buffer.indexOf("/")).length() + 1;
    size = Integer.parseInt(buffer.substring(index));
  }

  /**
   * Encodes byte range header
   * @return String
   */
  public String encode() {
    StringBuffer byteRangeHeader = new StringBuffer();
    byteRangeHeader.append(String.valueOf(startByte));
    byteRangeHeader.append("-");
    if (endByte > 0) {
      byteRangeHeader.append(String.valueOf(endByte));
      byteRangeHeader.append("/");
    }
    if (size > 0) {
      byteRangeHeader.append(String.valueOf(size));
    }

    return byteRangeHeader.toString();
  }

  /**
   * ToString Represenation of Message
   * @return String
   */
  public String toString() {
    return encode();
  }
}
