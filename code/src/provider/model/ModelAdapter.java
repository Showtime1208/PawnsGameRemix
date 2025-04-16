package provider.model;

import java.util.ArrayList;
import java.util.List;
import model.Player;
import model.ReadOnlyBoard;
import model.card.Card;

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
    if (playerEnum == PlayerEnum.Red) {
      return board.getRowScore(player1, row);
    } else {
      return board.getRowScore(player2, row);
    }

  }

  @Override
  public PlayerEnum getWinner() {
    if (!board.isGameOver()) {
      throw new IllegalStateException();
    }
    Player winner =  board.getWinner();
    if (winner == null) {
      return PlayerEnum.None;
    } else if (winner == player1) {
      return PlayerEnum.Red;
    } else return PlayerEnum.Blue;
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
    boolean turn = board.getTurn();
    return turn ? PlayerEnum.Blue : PlayerEnum.Red;
  }

  @Override
  public ProviderCell[][] getBoard() {
    int rowSize =  board.getHeight();
    int colSize =  board.getWidth();
    ProviderCell[][] board = new ProviderCell[rowSize][colSize];
    for (int row = 0; row < rowSize; row++) {
      for (int col = 0; col < colSize; col++) {
        board[row][col] = getCellAt(row, col);
      }
    }
    return board;
  }

  @Override
  public List<ProviderCard> getHand(PlayerEnum playerEnum) {
    List<ProviderCard> result = new ArrayList<>();
    Player player = (playerEnum == PlayerEnum.Red) ? player1 : player2;
    for (Card card : player.getHand()) {
      result.add(new CardAdapter(card));
    }
    return result;
  }

  @Override
  public PlayerEnum getOwnerOf(int row, int col) {
    if (board.getCell(row, col).getCard() != null) {
      Player cardOwner =  board.getCell(row, col).getCard().getOwner();
      if (cardOwner == player1) {
        return PlayerEnum.Red;
      } else  if (cardOwner == player2) {
        return PlayerEnum.Blue;
      }
    }
    if (!board.getCell(row, col).getPawns().isEmpty()) {
      Player pawnOwner = board.getCell(row, col).getPawns().get(0).getOwner();
      return (pawnOwner == player1) ? PlayerEnum.Red : PlayerEnum.Blue;
    }
    return PlayerEnum.None;

  }

  @Override
  public boolean isMoveLegal(int handIdx, int row, int col) {
    if (board.isGameOver()) {
      return false;
    }
    if (row < 0 || row >= board.getHeight() || col < 0 || col >= board.getWidth()) {
      return false;
    }
    if (board.getCell(row, col).getCard() != null) {
      return false;
    }
    PlayerEnum turn = getTurn();
    Player current = (turn == PlayerEnum.Red) ? player1 : player2;
    if (handIdx < 0 || handIdx >= current.getHandSize()) {
      return false;
    }

    if (board.getCell(row, col).getPawns().isEmpty()) {
      return false;
    }
    if (!board.getCell(row, col).getPawns().get(0).getOwner().equals(current)) {
      return false;
    }

    Card chosenCard = current.getHand().get(handIdx);
    return chosenCard.getCost() <= board.getCell(row, col).getPawns().size();
  }

  @Override
  public int getPlayerScore(PlayerEnum playerEnum) {
    Player player = (playerEnum == PlayerEnum.Red) ? player1 : player2;
    return board.getTotalScore(player);
  }
}
