package model.card;

/**
 * Implementation of the basic influence for the game. Holds only a boolean value indicating whether
 * or not it influences the square.
 */
public class SimpleInfluence implements Influence {
  private final InfluenceKind kind;

  /**
   * SimpleInfluence constructor.
   *
   * @param kind the influence.
   */
  public SimpleInfluence(InfluenceKind kind) {

    this.kind = kind;
  }

  @Override
  public boolean getInfluence() {
    return kind != InfluenceKind.NONE;
  }

  @Override
  public InfluenceKind getInfluenceKind() {
    return kind;
  }



}
