package view;

import java.awt.Color;
import model.card.InfluenceKind;

public final class InfluenceLegend {
  private InfluenceLegend() {}

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
