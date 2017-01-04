package msrp.header;

/**
 * 
 * @author inam_
 */
public class FailureReport
    extends MsrpHeader {
  String failureReportValue;
  /**
   * Constructor Intializes an Object
   */
  public FailureReport() {
  }

  /**
   * Parameterized Constructor
   * @param isFailureReportRequired boolean
   */
  public FailureReport(String value) {
    this.failureReportValue = value;
  }

  /**
   * Set Failure Report Flags
   * @param isFailureReportRequired boolean
   */
  public void setFailureReport(String value) {
    this.failureReportValue = value;
  }

  /**
   * Get Failure Report Value
   * @return String
   */
  public String getFailureReport() {
    return failureReportValue;
  }

  /**
   * Get Header Name as defined in MSRP
   * @return String
   */
  public String getHeaderName() {
    return "Failure-Report";
  }

  /**
   * toString Represenation of MSRP String
   * @return String
   */
  public String toString() {
    return failureReportValue;
  }

}
