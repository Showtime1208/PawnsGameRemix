package provider.model;

import java.util.List;
import model.Player;
import model.ReadOnlyBoard;

public class ModelAdapter implements ReadonlyPawnsBoardModel {
  private Player player1;
  private Player player2;
  private ReadOnlyBoard board;

  public ModelAdapter(Player player1, Player player2, ReadOnlyBoard board) {
    if (player1 == null || player2 == null ||  board == null) {
      throw new IllegalArgumentException();
    }
    this.player1 = player1;
    this.player2 = player2;
    this.board = board;
  }

  @Override
  public boolean gameOver() {
    return board.isGameOver();
  }

  @Override
  public int getRowScore(int row, PlayerEnum playerEnum) {
    return 0;
  }

  @Override
  public PlayerEnum getWinner() {
    return null;
  }

  @Override
  public int getWidth() {
    return board.getWidth();
  }

  @Override
  public int getHeight() {
    return board.getHeight();
  }

  @Override
  public ProviderCell getCellAt(int row, int col) {
    return new CellAdapter(board.getCell(row, col));
  }

  @Override
  public PlayerEnum getTurn() {
    return null;
  }

  @Override
  public ProviderCell[][] getBoard() {
    return new ProviderCell[0][];
  }

  @Override
  public List<ProviderCard> getHand(PlayerEnum playerEnum) {
    return List.of();
  }

  @Override
  public PlayerEnum getOwnerOf(int row, int col) {
    return null;
  }

  @Override
  public boolean isMoveLegal(int handIdx, int row, int col) {
    return false;
  }

  @Override
  public int getPlayerScore(PlayerEnum playerEnum) {
    return 0;
  }
}
