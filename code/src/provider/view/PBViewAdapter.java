package provider.view;

import javax.swing.SwingUtilities;
import model.Board;
import model.Player;
import view.PawnsBoardViewInterface;
import view.PawnsBoardViewListener;

/**
 * Adapter class for the view. Adapts the PBGUIView interface so that it conforms to the
 * PawnsBoardViewInterface, in order to create compatibility with the customer controller and
 * model.
 */
public class PBViewAdapter implements PawnsBoardViewInterface {

  private final PBGUIView providerView;

  /**
   * Construcotr for the view adapter.
   *
   * @param providerView the PBGUIView that will be adapted.
   */
  public PBViewAdapter(PBGUIView providerView) {
    this.providerView = providerView;
  }

  @Override
  public void setViewListener(PawnsBoardViewListener listener) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void refreshBoard(Board board, Player player) {
    SwingUtilities.invokeLater(providerView::refresh);
  }

  @Override
  public void highlightCell(int row, int col) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void highlightCard(int cardId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void displayMessage(String message) {
    providerView.showMessage(message);
  }

  @Override
  public void makeVisible() {
    providerView.makeVisible();
  }
}
