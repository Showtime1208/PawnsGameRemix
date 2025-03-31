package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.Ownership;
import java.util.List;

/**
 * Full implementation of the game model logic for the Pawns Board game.
 * This class manages all mutable game state, including turn control, player interaction,
 * card placement, and game progression.
 * Extends {@link AbstractGameStructure} to reuse core observer logic.
 */
public class PawnsGameStructure extends AbstractGameStructure implements Game {

  private boolean gameStarted;
  private boolean lastPlayerPassed;

  /**
   * Initializes a new game model with specified board dimensions, player decks, and hand size.
   * Sets up the board state and prepares each participant for gameplay.
   *
   * @param rows      number of rows in the board
   * @param cols      number of columns in the board (must be odd and >= 3)
   * @param redDeck   list of cards for the RED player
   * @param blueDeck  list of cards for the BLUE player
   * @param handSize  maximum hand size for each player
   */
  public PawnsGameStructure(int rows, int cols, List<GameCard> redDeck, List<GameCard> blueDeck, int handSize) {
    if (cols % 2 == 0 || rows < 1 || cols < 3) {
      throw new IllegalArgumentException("Board dimensions must be rows > 0 and odd cols >= 3");
    }
    if (redDeck.isEmpty() || blueDeck.isEmpty()) {
      throw new IllegalArgumentException("Player decks cannot be empty");
    }

    this.board = new PawnsBoardModel(rows, cols);
    Participant red = new GameParticipant(Ownership.RED, redDeck, handSize);
    Participant blue = new GameParticipant(Ownership.BLUE, blueDeck, handSize);

    this.redPlayer = red;
    this.bluePlayer = blue;
    this.currentPlayer = red;
    this.gameStarted = false;
    this.lastPlayerPassed = false;
  }

  /**
   * Begins the game. Should be called before players take turns.
   */
  @Override
  public void startGame() {
    this.gameStarted = true;
    this.lastPlayerPassed = false;
    System.out.println("Game started!");
  }

  /**
   * Attempts to place a card at a specified location for the current player.
   * If the move is valid, the card is placed, influence applied, and turn ends.
   *
   * @param row  target row on the board
   * @param col  target column on the board
   * @param card the card to play
   * @return true if the card was successfully placed; false otherwise
   */
  @Override
  public boolean placeCard(int row, int col, GameCard card) {
    if (isGameOver()) return false;

    PawnsBoard mutableBoard = (PawnsBoard) this.board;
    if (!mutableBoard.isValidPlacement(row, col, card, currentPlayer.getColor())) return false;

    mutableBoard.placeCard(row, col, card, currentPlayer.getColor());

    Participant player = (Participant) currentPlayer;
    int idx = player.getHand().indexOf(card);
    if (idx != -1) {
      player.removeCard(idx);
    }

    if (!player.getDeck().isEmpty()) {
      player.drawCard();
    }

    lastPlayerPassed = false;
    switchTurn();
    return true;
  }

  /**
   * Skips the current player's turn.
   * If both players pass in succession, the game ends.
   */
  @Override
  public void passTurn() {
    if (lastPlayerPassed) {
      System.out.println("Both players passed. Game over.");
      this.gameStarted = false;
      return;
    }
    lastPlayerPassed = true;
    switchTurn();
  }

  /**
   * Checks whether the game has ended.
   * The game is over if both players passed or no valid moves remain.
   *
   * @return true if the game is complete; false otherwise
   */
  @Override
  public boolean isGameOver() {
    if (!gameStarted) return true;

    if (!hasValidMove((Participant) redPlayer) && !hasValidMove((Participant) bluePlayer)) {
      System.out.println("No valid moves left. Game over.");
      gameStarted = false;
      return true;
    }
    return false;
  }

  /**
   * Gets the current player taking their turn.
   *
   * @return the active participant
   */
  @Override
  public ImmutableParticipant getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Swaps the active player between red and blue.
   */
  private void switchTurn() {
    this.currentPlayer = (currentPlayer == redPlayer) ? bluePlayer : redPlayer;
  }

  /**
   * Checks whether the given participant has at least one legal card placement on the board.
   *
   * @param p the player to check
   * @return true if any valid move exists; false otherwise
   */
  private boolean hasValidMove(Participant p) {
    PawnsBoard board = (PawnsBoard) this.board;
    for (int r = 0; r < board.getHeight(); r++) {
      for (int c = 0; c < board.getWidth(); c++) {
        for (GameCard card : p.getHand()) {
          if (board.isValidPlacement(r, c, card, p.getColor())) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
