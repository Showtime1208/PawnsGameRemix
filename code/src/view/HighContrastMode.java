package view;

import java.awt.Color;

/**
 * Manages high contrast mode settings for the Pawns Game. This class provides color schemes for
 * high contrast mode to improve accessibility.
 */
public class HighContrastMode {

  private static final Color PURE_BLACK = Color.BLACK;
  private static final Color PURE_WHITE = Color.WHITE;
  private static final Color PURE_RED = Color.RED;
  private static final Color PURE_CYAN = Color.CYAN;
  private static final Color HIGHLIGHT_YELLOW = Color.YELLOW;

  private boolean isEnabled;

  /**
   * Constructs a new HighContrastMode instance. High contrast mode is disabled by default.
   */
  public HighContrastMode() {
    this.isEnabled = false;
  }

  /**
   * Toggles high contrast mode on/off.
   */
  public void toggle() {
    this.isEnabled = !this.isEnabled;
  }

  /**
   * Checks if high contrast mode is enabled.
   *
   * @return true if high contrast mode is enabled, false otherwise
   */
  public boolean isEnabled() {
    return isEnabled;
  }

  /**
   * Gets the appropriate cell background color based on the current mode.
   *
   * @param isHighlighted whether the cell is highlighted
   * @return the appropriate Color for the cell background
   */
  public Color getCellBackgroundColor(boolean isHighlighted) {
    if (!isEnabled) {
      return isHighlighted ? new Color(200, 200, 100) : Color.WHITE;
    }
    return isHighlighted ? HIGHLIGHT_YELLOW : PURE_BLACK;
  }

  /**
   * Gets the appropriate text color based on the current mode and context.
   *
   * @param isPlayerCell  whether the cell belongs to a player
   * @param isHighlighted whether the cell is highlighted
   * @return the appropriate Color for the text
   */
  public Color getTextColor(boolean isPlayerCell, boolean isHighlighted) {
    if (!isEnabled) {
      return Color.BLACK;
    }
    if (isHighlighted || isPlayerCell) {
      return PURE_BLACK;
    }
    return PURE_WHITE;
  }

  /**
   * Gets the appropriate player color based on the current mode.
   *
   * @param isRed whether the player is red
   * @return the appropriate Color for the player
   */
  public Color getPlayerColor(boolean isRed) {
    if (!isEnabled) {
      return isRed ? new Color(255, 100, 100) : new Color(100, 100, 255);
    }
    return isRed ? PURE_RED : PURE_CYAN;
  }
}