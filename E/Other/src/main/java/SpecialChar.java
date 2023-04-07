// This class is a data representation of the json object formatted as:
// { "vertical" : Vertical, "horizontal" : Horizontal }
public class SpecialChar {

  private String horizontal;
  private String vertical;

  // Note: Constructor and Getters/Setters are needed for the org.codehaus.jackson.map.ObjectMapper
  // used in Xtcp
  public SpecialChar() {
  }

  public void setHorizontal(String horizontal) {
    this.horizontal = horizontal;
  }

  public String getHorizontal() {
    return this.horizontal;
  }

  public void setVertical(String vertical) {
    this.vertical = vertical;
  }

  public String getVertical() {
    return this.vertical;
  }

  @Override
  public String toString() {
    String combined = this.horizontal + "_" + this.vertical;
    switch(combined) {
      case "LEFT_UP":
        return "┘";
      case "LEFT_DOWN":
        return "┐";
      case "RIGHT_UP":
        return "└";
      case "RIGHT_DOWN":
        return "┌";
      default:
        throw new IllegalArgumentException("invalid name");
    }
  }
}
