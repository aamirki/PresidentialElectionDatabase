package model;

/**
 * Represents race/ethnicity for some jurisdiction using the categories established by
 * the United States Census.
 */
public class DemographicData {

  private int whitePop;
  private int blackPop;
  private int asianPop;
  private int nativePop;
  private int otherPop;
  private int totalPop;
  private int hispanicPop;

  /**
   * Initializes this DemographicData with race data.
   *
   * @param whitePop the population of white people in this jurisdiction
   * @param blackPop the population of black people in this jurisdiction
   * @param asianPop the population of asian people in this jurisdiction
   * @param nativePop the population of native americans/hawaiians/alaskans in this jurisdiction
   * @param otherPop the population of people in this jurisdiction who identify as another race
   * @param totalPop the total population of this jurisdiction
   */
  public DemographicData(int whitePop, int blackPop, int asianPop,
                         int nativePop, int otherPop, int totalPop) {
    this.whitePop = whitePop;
    this.blackPop = blackPop;
    this.asianPop = asianPop;
    this.nativePop = nativePop;
    this.otherPop = otherPop;
    this.totalPop = totalPop;
  }

  /**
   * Sets the ethnic populations of this jurisdiction.
   *
   * @param hispanicPop the population of Hispanic people in this jurisdiction
   */
  public void setEthnicity(int hispanicPop) {
    this.hispanicPop = hispanicPop;
  }

  /**
   * Retrieves the percentage of people in this jurisdiction that identify as being, at least
   * partially, white.
   *
   * @return the white percentage in this jurisdiction
   */
  public float getWhitePercentage() {
    return (float) whitePop / totalPop * 100;
  }

  /**
   * Retrieves the percentage of people in this jurisdiction that identify as being, at least
   * partially, black.
   *
   * @return the black percentage in this jurisdiction
   */
  public float getBlackPercentage() {
    return (float) blackPop / totalPop * 100;
  }

  /**
   * Retrieves the percentage of people in this jurisdiction that identify as being, at least
   * partially, asian.
   *
   * @return the asian percentage in this jurisdiction
   */
  public float getAsianPercentage() {
    return (float) asianPop / totalPop * 100;
  }

  /**
   * Retrieves the percentage of people in this jurisdiction that identify as being, at least
   * partially, of a race that is not white, black, asian, Native American, Native Hawaiian,
   * or Native Alaskan.
   *
   * @return the other race percentage in this jurisdiction
   */
  public float getOtherPercentage() {
    return (float) otherPop / totalPop * 100;
  }

  /**
   * Retrieves the percentage of people in this jurisdiction that identify as being, at least
   * partially: Native American, Native Hawaiian, or Native Alaskan.
   *
   * @return the native percentage in this jurisdiction
   */
  public float getNativePercentage() {
    return (float) nativePop / totalPop * 100;
  }

  /**
   * Retrieves the percentage of people in this jurisdiction that identify as being, at least
   * partially, Hispanic - of any race.
   *
   * @return the hispanic percentage in this jurisdiction
   */
  public float getHispanicPercentage() {
    return (float) hispanicPop / totalPop * 100;
  }

}