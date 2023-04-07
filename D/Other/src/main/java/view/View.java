package view;

import java.util.List;

/**
 * Represents a simple view that draws the given Strings as a grid of tiles.
 */
public interface View {

  /**
   * Draws the given list of Strings as a grid of tiles.
   * @param rowsOfCharacters the list of strings where each character represents a specific tile
   */
  void drawView(List<String> rowsOfCharacters);

}
