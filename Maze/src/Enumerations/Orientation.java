package Enumerations;

 /**
 * Data representation of the counterclockwise orientation of the Tile.
 * Note: rotate functionality added but never used. Keeping it for potential future use.
 */
public enum Orientation {
  DEGREE0(0),
  DEGREE90(90),
  DEGREE180(180),
  DEGREE270(270);

  private int degrees;


  Orientation(int degrees) {
    this.degrees = degrees;
  }

  /**
   * Get the degrees of the orientation
   * @return an int of the degrees
   */
  public int getDegrees() {
    return this.degrees;
  }

  /**
   * Rotate a tile by 90 degrees the number of times specified.
   * @param times the number of rotations
   * @return the new orientation
   */
  public Orientation rotate(int times) {
    Orientation result = this;
    for(int i=0; i<times;i++) {
      if (result == DEGREE0) {
        result = DEGREE90;
      } else if (result == DEGREE90) {
        result = DEGREE180;
      } else if (result == DEGREE180) {
        result = DEGREE270;
      } else{
        result = DEGREE0;
      }
    }
    return result;
  }

}
