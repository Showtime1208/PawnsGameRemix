package view;

import java.awt.Color;
import model.card.InfluenceKind;

/**
 * Records class keeping the different kinds of color that relates to each Influence.
 */
public final class InfluenceLegend {

  private InfluenceLegend() {
  }

  /**
   * Returns the color that is related to each Influence.
   * @param kind the InfluenceKind.
   * @return what color the Kind is correlated to.
   */
  public static Color colorOf(InfluenceKind kind) {
    Color color;
    switch (kind) {
      case CLAIM:
        color = Color.CYAN;
        break;
      case UPGRADE:
        color = new Color(0, 128, 0);
        break;
      case DEVALUE:
        color = new Color(160, 32, 240);
        break;
      default:
        color = Color.LIGHT_GRAY;
        break;
    }
    return color;
  }

}
