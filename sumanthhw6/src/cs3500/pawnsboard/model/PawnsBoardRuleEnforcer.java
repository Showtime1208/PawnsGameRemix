package cs3500.pawnsboard.model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The class that enforces the rules of a game of PawnsBoard.
 */
public class PawnsBoardRuleEnforcer implements PawnsBoardModel {


  // INVARIANT: All rows are always the same size.
  // INVARIANT: The size of both board trackers are always the same.
  private final CardUse[][] boardCards;
  private final Pawn[][] pawnsBoard;

  private CardUse[] redHand;
  private CardUse[] blueHand;
  private final List<CardUse> redDeck;
  private final List<CardUse> blueDeck;

  private final int numCols;
  private final int numRows;
  protected boolean hasStarted;
  private Player currentPlayer;
  private int passesInARow;
  private int handSize;

  PawnsBoardRuleEnforcer(int numRows, int numCols,
                         List<CardUse> redPlayerDeck, List<CardUse> bluePlayerDeck,
                         Player firstPlayer) {

    if (numRows < 1 || (numCols % 2) == 0) {
      throw new IllegalArgumentException("Invalid size of board.");
    }

    this.numCols = numCols;
    this.numRows = numRows;
    this.currentPlayer = firstPlayer;
    this.hasStarted = false;
    this.passesInARow = 0;
    this.boardCards = new CardUse[numCols][numRows];
    this.pawnsBoard = new Pawn[this.numCols][this.numRows];
    this.handSize = 0;

    // initializes the starting pawns on the board for both players.
    for (int row = 0; row < numRows; row++) {
      pawnsBoard[0][row] = new Pawn(1, Player.RED);
      pawnsBoard[boardCards.length - 1][row] = new Pawn(1, Player.BLUE);
    }
    ensureNotTooManyDuplicates(redPlayerDeck);
    ensureNotTooManyDuplicates(bluePlayerDeck);
    redDeck = redPlayerDeck;
    blueDeck = bluePlayerDeck;
  }

  private static void ensureNotTooManyDuplicates(List<CardUse> deck) {
    HashSet<CardUse> set = new HashSet<>(deck);
    int totalCount = deck.size() - set.size();
    if (totalCount > 2) {
      throw new IllegalArgumentException("Too many duplicates in deck: " + totalCount);
    }
  }


  /**
   * Starts the game with the given deck and hand size. If shuffle is set to true,
   * then the deck is shuffled prior to dealing the hand.
   *
   * <p>Note that modifying the deck given here outside this method should have no effect
   * on the game itself.
   *
   * @throws IllegalStateException    if the game has already been started
   * @throws IllegalArgumentException if the deck is null or contains a null object,
   *                                  if handSize is not positive (i.e. 0 or less),
   *                                  or if the deck does not contain enough cards to fill
   *                                  the board AND fill a starting hand
   */
  @Override
  public void startGame(Player startingPlayer, int handSize) {
    if (hasStarted) {
      throw new IllegalStateException("Game is already started.");
    }

    this.handSize = handSize;
    this.blueHand = new CardUse[handSize];
    this.redHand = new CardUse[handSize];

    for (int handIdx = 0; handIdx < redHand.length; handIdx++) {
      playerDrawCard(redHand, redDeck, handIdx);
    }
    for (int handIdx = 0; handIdx < blueHand.length; handIdx++) {
      playerDrawCard(blueHand, blueDeck, handIdx);
    }
    this.currentPlayer = startingPlayer;

    hasStarted = true;

  }

  @Override
  public Player getCurrentTurn() {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    return currentPlayer;
  }

  @Override
  public int getNumberOfRows() {
    return this.numRows;
  }

  @Override
  public int getNumberOfColumns() {
    return this.numCols;
  }

  /**
   * Returns the card in the indicated position on the board. If there is no card on the board
   * and the position is valid, the method will return null.
   *
   * @param row the row to access
   * @param col the column to access
   * @return the card in the valid position or null if the position has no card
   * @throws IllegalArgumentException if the row and column are not a valid location
   *                                  for a card in the polygonal board
   */
  @Override
  public Pawn getPawnsAt(int row, int col) {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    return pawnsBoard[col][row];
  }

  @Override
  public Player getPlayerAt(int row, int col) {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if (boardCards[col][row] == null) {
      return null;
    }
    return boardCards[col][row].getPlayerOwnedBy();
  }


  @Override
  public Map<Integer, Integer> getRowScores(Player player) {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    Map<Integer, Integer> scores = new HashMap<>();

    for (CardUse[] cardCol : boardCards) {
      int rowIndex = 0;
      int rowScore = 0;
      for (CardUse card : cardCol) {
        if (card == null) {
          rowScore += 0;
          rowIndex++;
        } else if (player == card.getPlayerOwnedBy()) {
          rowScore += card.getValueScore();
          scores.put(rowIndex, rowScore);
          rowIndex++;
        }
      }
    }
    return scores;
  }


  @Override
  public void passCurrentTurn() {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    switch (currentPlayer) {
      case BLUE:
        currentPlayer = Player.RED;
        break;
      case RED:
        currentPlayer = Player.BLUE;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + currentPlayer);
    }
    this.passesInARow++;
  }

  /**
   * Returns a copy of the chosen player's current hand. If their hand is empty, then an empty
   * list is returned.
   *
   * @return a copy of the player's current hand
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public List<CardUse> getHand(Player player) {
    CardUse[] handToCopy;
    List<CardUse> copy = new ArrayList<>();
    switch (player) {
      case BLUE:
        handToCopy = blueHand;
        break;
      case RED:
        handToCopy = redHand;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + player);
    }
    for (CardUse card : handToCopy) {
      copy.add(card);
    }
    return copy;
  }


  /**
   * Returns true if the game is over. The implementation must
   * describe what it means for the game to be over.
   *
   * @return true if the game is over, false otherwise
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public boolean isGameOver() {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    return passesInARow >= 2 || boardIsFull();
  }

  private boolean boardIsFull() {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    for (CardUse[] cardRow : boardCards) {
      for (CardUse card : cardRow) {
        if (card == null) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Places a card from the hand to a given position on the polygonal board and then
   * draws a card from the deck if able.
   *
   * @param cardIdx index of the card in hand to place (0-index based)
   * @param row     row to place the card in (0-index based)
   * @param col     column to place the card in (0-index based)
   * @throws IllegalStateException    if the game has not started or there is a card at the given
   *                                  position
   * @throws IllegalArgumentException if cardIdx is out of bounds of the hand or
   *                                  row and col do not indicate a position on the polygon
   */
  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    if (!hasStarted) {
      throw new IllegalStateException("PokerPolygons has not been started");
    }
    CardUse[] playerHand;
    List<CardUse> playerDeck;
    switch (currentPlayer) {
      case BLUE:
        playerHand = blueHand;
        playerDeck = blueDeck;
        break;
      case RED:
        playerHand = redHand;
        playerDeck = redDeck;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + currentPlayer);
        // This can never be reached because of the enum.
    }

    if (cardIdx < 0 || cardIdx >= playerHand.length) {
      throw new IllegalArgumentException("Card index out of bounds");
    }
    if (row < 0 || row >= this.numRows) {
      throw new IllegalArgumentException("Row index out of bounds");
    }
    if (col < 0 || col >= this.numCols) {
      throw new IllegalArgumentException("Column index out of bounds");
    }

    if (enoughPawns(pawnsBoard[col][row], playerHand[cardIdx])) {
      CardUse desiredCard = playerHand[cardIdx];
      CardUse boardCard = new CardUse(desiredCard.getName(), desiredCard.getCost(),
              desiredCard.getValueScore(), desiredCard.getInfluenceGrid());
      boardCard.setPlayerOwnedBy(currentPlayer);
      boardCards[col][row] = boardCard;
      updatePawns(playerHand[cardIdx], col, row);
      playerHand[cardIdx] = null;
      passesInARow = 0;
      if (playerDeck.isEmpty()) {
        return;
      }
      playerDrawCard(playerHand, playerDeck, cardIdx);
      changeCurrentPlayer();
    } else {
      throw new IllegalArgumentException("Not enough pawns at target location");
    }
  }

  private void changeCurrentPlayer() {
    switch (currentPlayer) {
      case BLUE:
        this.currentPlayer = Player.RED;
        break;
      case RED:
        this.currentPlayer = Player.BLUE;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + currentPlayer);
    }
  }

  @Override
  public Player[][] getCardBoard() {
    Player[][] copy = new Player[this.numCols][this.numRows];
    for (int col = 0; col < this.numCols; col++) {
      for (int row = 0; row < this.numRows; row++) {
        if (boardCards[col][row] != null) {
          CardUse card = boardCards[col][row];
          Player cardCopy = card.getPlayerOwnedBy();
          copy[col][row] = cardCopy;
        }
      }
    }
    return copy;
  }

  // Tried to create a generic method for these copies but using
  // generic arrays in java appears to be a mess.
  @Override
  public Pawn[][] getPawnsBoard() {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    Pawn[][] copy = new Pawn[this.numCols][this.numRows];
    for (int col = 0; col < this.numCols; col++) {
      for (int row = 0; row < this.numRows; row++) {
        if (pawnsBoard[col][row] != null) {
          Pawn pawn = pawnsBoard[col][row];
          Pawn pawnCopy = new Pawn(pawn.getNumPawns(), pawn.getOwner());
          copy[col][row] = pawnCopy;
        }
      }
    }
    return copy;
  }

  @Override
  public Optional<Player> getWinner() {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if (!isGameOver()) {
      throw new IllegalStateException("Game has not ended.");
    }

    Map<Integer, Integer> blueRowScores = getRowScores(Player.BLUE);
    Map<Integer, Integer> redRowScores = getRowScores(Player.RED);

    int blueTotal = 0;
    for (Integer score : blueRowScores.values()) {
      blueTotal += score;
    }
    int redTotal = 0;
    for (Integer score : redRowScores.values()) {
      redTotal += score;
    }
    if (blueTotal > redTotal) {
      return Optional.of(Player.BLUE);
    }
    if (redTotal > blueTotal) {
      return Optional.of(Player.RED);
    }
    return Optional.empty();
  }

  private boolean enoughPawns(Pawn pawnsAtLocation, CardUse cardUse) {
    return pawnsAtLocation.getOwner() == currentPlayer &&
            pawnsAtLocation.getNumPawns() >= cardUse.getCost();
  }


  private void updatePawns(CardUse card, int colLocation, int rowLocation) {
    for (Point2D influenceLocation : translateToBoard(card.getInfluenceGrid())) {
      int yPosition = colLocation + (int) influenceLocation.getY();
      int xPosition = rowLocation + (int) influenceLocation.getX();
      try {
        if (pawnsBoard[yPosition][xPosition] == null) {
          Pawn newPawn = new Pawn(1, currentPlayer);
          pawnsBoard[yPosition][xPosition] = newPawn;
        } else if (currentPlayer != pawnsBoard[yPosition][xPosition].getOwner()) {
          pawnsBoard[yPosition][xPosition].changePlayerOwnedBy();
        } else if (pawnsBoard[yPosition][xPosition].getNumPawns() < 3) {
          pawnsBoard[yPosition][xPosition].
                  setNumPawns(pawnsBoard[yPosition][xPosition].getNumPawns() + 1);
        }
      } catch (IndexOutOfBoundsException ignored) {
        // Nothing changes when it is out of bounds
      }
    }
  }

  private List<Point2D> translateToBoard(boolean[][] influenceGrid) {
    List<Point2D> influenceLocations = new ArrayList<>();
    for (int row = 0; row < influenceGrid.length; row++) {
      for (int col = 0; col < influenceGrid[row].length; col++) {
        if (influenceGrid[row][col]) {
          if (currentPlayer == Player.RED) {
            influenceLocations.add(new Point(row + (row - 2), col + (col - 2)));
          } else if (currentPlayer == Player.BLUE) {
            influenceLocations.add(new Point(row - (row - 2), col - (col - 2)));
          }
        }
      }
    }
    return influenceLocations;
  }


  private void playerDrawCard(CardUse[] currentPlayerHand,
                              List<CardUse> playerDeck, int emptyIndex) {
    if (playerDeck.isEmpty()) {
      return;
    }
    currentPlayerHand[emptyIndex] = playerDeck.remove(0);
  }
}
