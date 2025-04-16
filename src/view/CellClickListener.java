package view;

/**
 * Interface for the CellClickListener. Used as a listener for the board.
 */
public interface CellClickListener {

  /**
   * Responds when the cell is clicked.
   *
   * @param row the row.
   * @param col the col.
   */
  void onCellClicked(int row, int col);
}
