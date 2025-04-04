package model;

import java.util.ArrayList;
import java.util.List;
import model.card.Card;
import model.card.Influence;
import model.card.Pawn;
import view.TurnListener;

/**
 * Implementation of the Board interface. Implements all game logic.
 */
public class GameBoard implements Board {

  private final int rowSize;
  private final int colSize;
  // INVARIANTS:
  /*
  THE ROW SIZE MUST BE GREATER THAN ZERO, AND THE COLUMN SIZE MUST BOTH BE
  GREATER THAN ONE AND ODD.
  Our model ensures that by making sure that the rowSize and colSize are
  final when initialized.
   */
  private Player player1;
  private Player player2;
  private boolean gameStart;
  private boolean turn;
  private boolean passTurn;
  private boolean gameOver;
  private List<TurnListener> listeners;

  private Cell[][] board;

  /**
   * Constructor for the GameBoard class.
   *
   * @param rowSize the size of the rows.
   * @param colSize the size of the columns.
   * @throws IllegalArgumentException if the row or columns are invalid.
   */
  public GameBoard(int rowSize, int colSize) {
    if (rowSize <= 0 || colSize <= 1 || colSize % 2 == 0) {
      throw new IllegalArgumentException("Invalid row or column size");
    }
    this.board = new Cell[rowSize][colSize];
    for (int row = 0; row < rowSize; row++) {
      for (int col = 0; col < colSize; col++) {
        this.board[row][col] = new GameCell();
      }
    }
    this.rowSize = rowSize;
    this.colSize = colSize;
    this.gameStart = false;
    this.passTurn = false;
    this.turn = true;
    this.listeners = new ArrayList<>();

  }

  @Override
  public Cell getCell(int row, int col) {
    if (isInBounds(row, col)) {
      return board[row][col];
    } else {
      throw new IllegalArgumentException("Invalid row or column");
    }
  }

  @Override
  public void startGame(Player player1, Player player2) {
    if (player1.getDeck().size() < rowSize * colSize ||
        player2.getDeck().size() < rowSize * colSize) {
      throw new IllegalArgumentException("Decks are too small for specified dimensions.");
    }
    if (player1.getHandSize() != player2.getHandSize()) {
      throw new IllegalStateException("Players must have same size decks.");
    }
    if (gameStart) {
      throw new IllegalStateException("Game is already started.");
    }
    if (player1 == null || player2 == null) {
      throw new IllegalArgumentException("Players can't be null.");
    }
    this.gameStart = true;
    this.gameOver = false;
    this.player1 = player1;
    this.player2 = player2;
    for (int i = 0; i < player1.getHandSize(); i++) {
      player1.drawFromDeckToHand();
      player2.drawFromDeckToHand();
    }
    for (int row = 0; row < rowSize; row++) {
      this.board[row][0].addPawn(player1);
      this.board[row][colSize - 1].addPawn(player2);
    }
  }

  @Override
  public void placeCard(Player player, int handIdx, int row, int col) {
    if (player == null) {
      throw new IllegalArgumentException("Player is null.");
    }
    if (!isInBounds(row, col)) {
      throw new IllegalStateException("Out of bounds inputs.");
    }
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    if (player.getHand().size() < player.getHandSize() && !player.getDeck().isEmpty()) {
      player.drawFromDeckToHand();
    }
    if (this.turn != player.getIsRed()) {
      throw new IllegalStateException("Not your  turn.");
    }
    if (this.turn != player.getIsRed()) {
      throw new IllegalStateException("Not your  turn.");
    }
    if (handIdx >= player1.getHandSize() || handIdx < 0) {
      throw new IllegalArgumentException("Invalid hand index.");
    }
    Card card = player.getHand().get(handIdx);
    if (board[row][col].getCard() != null) {
      throw new IllegalArgumentException("Card already exists.");
    }
    if (board[row][col].getPawns().isEmpty()) {
      throw new IllegalArgumentException("No such pawn.");
    }
    if (board[row][col].getPawns().get(0).getOwner() != player) {
      throw new IllegalArgumentException("Pawns are not your color.");
    }
    if (board[row][col].getPawns().size() < card.getCost()) {
      throw new IllegalArgumentException("Card cost exceeded.");
    }
    board[row][col].setCard(card);
    player.getHand().remove(handIdx);
    applyInfluence(player, card, row, col);
    if (passTurn) {
      passTurn = false;
    }
    this.turn = !this.turn;
    notifyTurnListeners();
  }

  private void applyInfluence(Player player, Card card, int placedRow, int placedCol) {
    Influence[][] grid = card.getInfluenceArray();
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        boolean influence = grid[row][col].getInfluence();
        if (!influence) {
          continue;
        }
        int rowOffset = row - 2;
        int colOffset = col - 2;
        int targetRow = placedRow + rowOffset;
        int targetCol = placedCol + colOffset;
        if (!isInBounds(targetRow, targetCol)) {
          continue;
        }
        Cell targetCell = board[targetRow][targetCol];
        if (targetCell.getCard() != null) {
          continue;
        }
        List<Pawn> pawns = targetCell.getPawns();
        if (pawns.isEmpty()) {
          targetCell.addPawn(player);
        } else {
          if (pawns.get(0).getOwner() == player) {
            if (pawns.size() < 3) {
              targetCell.addPawn(player);
            }
          } else {
            for (Pawn pawn : pawns) {
              pawn.setOwner(player);
            }
          }
        }
      }
    }
  }

  private boolean isInBounds(int row, int col) {
    return (row >= 0 && row < rowSize && col >= 0 && col < colSize);
  }

  @Override
  public boolean isGameOver() {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    return gameOver;
  }

  @Override
  public int getRowScore(Player player, int row) {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player is null.");
    }
    if (row < 0 || row >= rowSize) {
      throw new IllegalArgumentException("Invalid row index.");
    }
    int rowScore = 0;
    for (int col = 0; col < colSize; col++) {
      Cell cell = board[row][col];
      Card card = cell.getCard();
      if (card != null) {
        if (card.getOwner().equals(player)) {
          rowScore += card.getValue();
        }
      }
    }
    return rowScore;
  }

  @Override
  public int getTotalScore(Player player) {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player is null.");
    }
    int total = 0;
    Player red = this.player1;
    Player blue = this.player2;
    for (int row = 0; row < rowSize; row++) {
      int redScore = getRowScore(red, row);
      int blueScore = getRowScore(blue, row);
      if (redScore > blueScore) {
        if (player == red) {
          total += redScore;
        }
      } else if (blueScore > redScore) {
        if (player == blue) {
          total += blueScore;
        }
      } else {
        continue;
      }
    }
    return total;
  }

  @Override
  public void passTurn(Player player) {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player is null.");
    }
    if (player.getHand().size() < player.getHandSize() && !player.getDeck().isEmpty()) {
      player.drawFromDeckToHand();
    }
    if (this.turn != player.getIsRed()) {
      throw new IllegalStateException("Not your  turn.");
    }
    this.turn = !this.turn;
    notifyTurnListeners();
    if (this.passTurn) {
      this.gameOver = true;
    } else {
      this.passTurn = true;
    }
  }

  @Override
  public void addTurnListener(TurnListener listener) {
    this.listeners.add(listener);
  }

  @Override
  public void removeTurnListener(TurnListener listener) {
    this.listeners.remove(listener);
  }

  private void notifyTurnListeners() {
    for (TurnListener listener : this.listeners) {
      listener.onTurnChange(turn);
    }
  }

  // returns number of rows
  @Override
  public int getWidth() {
    return colSize;
  }

  @Override
  public int getHeight() {
    return rowSize;
  }

  @Override
  public Player getP1() {
    if (!gameStart || player1 == null) {
      throw new IllegalStateException("Game has not started.");
    }
    return player1;
  }

  @Override
  public Player getP2() {
    if (!gameStart || player2 == null) {
      throw new IllegalStateException("Game has not started.");
    }
    return player2;
  }


  @Override
  public boolean getTurn() {
    return turn;
  }

  @Override
  public Cell[][] getCopy() {
    Cell[][] copy = new Cell[rowSize][colSize];
    for (int row = 0; row < rowSize; row++) {
      for (int col = 0; col < colSize; col++) {
        Cell cell = board[row][col];
        copy[row][col] = cell;
      }
    }
    return copy;
  }

  @Override
  public Player getWinner() {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    if (!gameOver) {
      throw new IllegalStateException("Game is not over.");
    }
    int getP1Score = getTotalScore(player1);
    int getP2Score = getTotalScore(player2);
    if (getP1Score > getP2Score) {
      return player1;
    } else if (getP2Score > getP1Score) {
      return player2;
    }
    //NULLPOINTER
    else {
      return null;
    }
  }

  @Override
  public List<Pawn> getPawnsAt(int row, int col) {
    if (row < 0 || row >= rowSize || col < 0 || col >= colSize) {
      throw new IndexOutOfBoundsException("Invalid cell indices");
    }

    List<Pawn> pawns = board[row][col].getPawns();
    return pawns != null ? pawns : new ArrayList<>();
  }

  @Override
  public Card getCardAt(int row, int col) {
    if (row < 0 || row >= rowSize || col < 0 || col >= colSize) {
      throw new IndexOutOfBoundsException("Invalid cell indices");
    }
    return board[row][col].getCard(); // Correct
  }


}
