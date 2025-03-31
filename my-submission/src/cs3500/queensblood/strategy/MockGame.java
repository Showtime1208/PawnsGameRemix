package cs3500.queensblood.strategy;

import cs3500.queensblood.model.*;
import cs3500.queensblood.model.enumsModel.Ownership;
import cs3500.queensblood.model.enumsModel.InfluenceType;
import cs3500.queensblood.model.ImmutableGrid;
import cs3500.queensblood.model.GameCard;
import java.util.ArrayList;
import java.util.List;

/**
 * A mock model that records method calls for testing strategy behavior.
 */
public class MockGame implements ImmutableGame {
  private final StringBuilder log;

  public MockGame(StringBuilder log) {
    this.log = log;
  }

  @Override
  public int getNumRows() {
    log.append("getNumRows()\n");
    return 3;
  }

  @Override
  public int getNumCols() {
    log.append("getNumCols()\n");
    return 5;
  }

  @Override
  public ImmutableTile getCell(int row, int col) {
    log.append(String.format("getCell(%d, %d)\n", row, col));
    return new ImmutableTile() {
      @Override
      public boolean containsCard() {
        log.append(String.format("hasCard() called on cell (%d, %d)\n", row, col));
        return false;
      }

      @Override
      public Ownership tileOwner() {
        log.append(String.format("getOwner() called on cell (%d, %d)\n", row, col));
        return Ownership.NONE;
      }

      @Override
      public int participantCount() {
        log.append(String.format("getPawnCount() called on cell (%d, %d)\n", row, col));
        return 0;
      }

      @Override
      public GameCard getCard() {
        log.append(String.format("getCard() called on cell (%d, %d)\n", row, col));
        return null; // or return a DummyCard if needed
      }
    };
  }



  @Override
  public List<GameCard> getHand(ImmutableParticipant player) {
    log.append("getHand(...) called\n");
    List<GameCard> hand = new ArrayList<>();
    hand.add(new DummyCard("Card0"));
    hand.add(new DummyCard("Card1"));
    return hand;
  }

  @Override
  public boolean isValidPlacement(GameCard card, int row, int col, Ownership owner) {
    log.append(String.format("isValidPlacement(%s, %d, %d, %s)\n", card.getName(), row, col, owner));
    return true;
  }

  @Override
  public int getRowScore(int row, Ownership owner) {
    log.append(String.format("getRowScore(%d, %s)\n", row, owner));
    return 0;
  }

  @Override
  public int getTotalScore(Ownership owner) {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public Ownership getWinner() {
    return null;
  }

  @Override
  public ImmutableParticipant getCurrentPlayer() {
    return null;
  }

  @Override
  public ImmutableGrid getReadonlyBoard() {
    return null;
  }

  /**
   * A dummy Card for mocking purposes.
   */
  private static class DummyCard implements GameCard {
    private final String name;

    public DummyCard(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public int getCost() {
      return 1;  // mock cost
    }

    @Override
    public int getValueScore() {
      return 5;  // mock score, adjust if needed for testing
    }

    @Override
    public InfluenceType[][] getInfluences() {
      return new InfluenceType[5][5]; // mock empty grid
    }

    @Override
    public String toString() {
      return name;
    }
  }


}
