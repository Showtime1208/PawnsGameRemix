package view;

import java.io.IOException;

/**
 * Interface representing a textual view for the PawnsGame board.
 */
public interface TextualView {

  /**
   * Generates the textual representation of the textualView.
   */
  public String toString();

  /**
   * Renders the toString into an appendable.
   *
   * @param out the Appendable that will be rendered.
   * @throws IOException if there is an exception.
   */
  public void render(Appendable out) throws IOException;

}
