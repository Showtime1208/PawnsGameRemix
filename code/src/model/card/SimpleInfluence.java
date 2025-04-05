package model.card;

/**
 * Implementation of the basic influence for the game. Holds only a boolean value indicating whether
 * or not it influences the square.
 */
public class SimpleInfluence implements Influence {

  private final boolean influence;

  /**
   * SimpleInfluence constructor.
   *
   * @param influence the influence.
   */
  public SimpleInfluence(boolean influence) {
    this.influence = influence;
  }

  @Override
  public boolean getInfluence() {
    return influence;
  }


}
