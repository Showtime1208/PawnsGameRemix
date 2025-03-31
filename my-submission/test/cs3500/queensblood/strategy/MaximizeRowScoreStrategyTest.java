package cs3500.queensblood.strategy;

import cs3500.queensblood.model.enumsModel.Ownership;
import cs3500.queensblood.model.ImmutableParticipant;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaximizeRowScoreStrategyTest {

  @Test
  public void testMaximizeRowScoreStrategyAttemptsRowDominance() {
    StringBuilder log = new StringBuilder();
    MockGame model = new MockGame(log);

    ImmutableParticipant mockPlayer = new ImmutableParticipant() {
      @Override
      public Ownership getColor() {
        return Ownership.BLUE;
      }

      @Override
      public java.util.List<cs3500.queensblood.model.GameCard> getHand() {
        return model.getHand(this);
      }
    };

    Strategy strategy = new MaximizeRowScoreStrategy();
    Move move = strategy.chooseMove(model, mockPlayer);

    assertNotNull("Strategy should return a move", move);
    assertFalse("Move should not be pass", move.isPass);
    assertEquals(0, move.cardIndex); // still first card in dummy hand

    System.out.println("=== MaximizeRowScore Transcript ===");
    System.out.println(log.toString());
  }
}
