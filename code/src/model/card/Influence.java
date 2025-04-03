package model.card;

public interface Influence {

  /**
   * Returns the boolean value representing Influence.
   *
   * @return whether or not the square should be influenced.
   */
  public boolean getInfluence();

  public SimpleInfluence[][] getInfluenceArray(char[][] grid);

}
