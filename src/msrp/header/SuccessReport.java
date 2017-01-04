package msrp.header;

/**
 * 
 * @author inam_
 */
public class SuccessReport
    extends MsrpHeader {

  boolean isSuccessReportRequired;
  /**
   * Constructor : Intialzes SuccessReport Header;
   */
  public SuccessReport() {
    this.isSuccessReportRequired = false;
  }

  /**
   * Parametrized Constructor
   * @param isSuccessReportRequired boolean
   */
  public SuccessReport(boolean isSuccessReportRequired) {
    this.isSuccessReportRequired = isSuccessReportRequired;
  }

  /**
   * Set Success Report Header Field
   * @param isSuccessReportRequired boolean
   */
  public void setSuccessReport(boolean isSuccessReportRequired) {
    this.isSuccessReportRequired = isSuccessReportRequired;
  }

  /**
   * Encodes to MSRP
   * @return String
   */
  public String encode() {
    if (this.isSuccessReportRequired) {
      return "yes";
    }
    else {
      return "no";
    }

  }

  /**
   * Get Header Name
   * @return String
   */
  public String getHeaderName() {
    return "Success-Report";
  }

  /**
   * toString Represenation
   * @return String
   */
  public String toString() {
    return encode();
  }

}
