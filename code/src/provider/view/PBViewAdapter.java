package provider.view;

import javax.swing.SwingUtilities;
import model.Board;
import view.PawnsBoardViewInterface;
import view.PawnsBoardViewListener;

public class PBViewAdapter implements PawnsBoardViewInterface {

  private final PBGUIView providerView;

  public PBViewAdapter(PBGUIView providerView) {
    this.providerView = providerView;
  }

  @Override
  public void setViewListener(PawnsBoardViewListener listener) {

  }

  @Override
  public void refreshBoard(Board board) {
    SwingUtilities.invokeLater(providerView::refresh);
  }

  @Override
  public void highlightCell(int row, int col) {

  }

  @Override
  public void highlightCard(int cardId) {

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
