package provider.model;

/**
 * Represents the owner of a cell in a board given to a Card.
 */
public enum CardOwner {

  ORANGE("C"), CYAN("I"), NONE("X");

  private String c;

  CardOwner(String c) {
    this.c = c;
  }

  @Override
  public String toString() {
    return this.c;
  }
}
