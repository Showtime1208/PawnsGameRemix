package cs3500.queensblood.strategy;

import cs3500.queensblood.model.enumsModel.Ownership;
import cs3500.queensblood.model.ImmutableParticipant;
import org.junit.Test;
import static org.junit.Assert.*;

public class FillFirstStrategyTest {

  @Test
  public void testFillFirstFindsFirstLegalMove() {
    StringBuilder log = new StringBuilder();
    MockGame model = new MockGame(log);

    ImmutableParticipant mockPlayer = new ImmutableParticipant() {
      @Override
      public Ownership getColor() {
        return Ownership.RED;
      }

      @Override
      public java.util.List<cs3500.queensblood.model.GameCard> getHand() {
        return model.getHand(this);
      }
    };

    Strategy strategy = new FillFirstStrategy();
    Move move = strategy.chooseMove(model, mockPlayer);

    assertNotNull("Strategy should return a move", move);
    assertFalse("Move should not be pass", move.isPass);
    assertEquals(0, move.cardIndex); // expecting first card
    assertEquals(0, move.row);       // expecting first row
    assertEquals(0, move.col);       // expecting first column

    System.out.println("=== FillFirst Transcript ===");
    System.out.println(log.toString());
  }
}
