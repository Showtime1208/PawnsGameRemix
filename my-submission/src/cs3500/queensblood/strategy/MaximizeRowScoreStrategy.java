package cs3500.queensblood.strategy;

import cs3500.queensblood.model.*;
import cs3500.queensblood.model.enumsModel.Ownership;

import java.util.List;

public class MaximizeRowScoreStrategy implements Strategy {

  @Override
  public Move chooseMove(ImmutableGame model, ImmutableParticipant player) {
    Ownership myOwner = player.getColor();  // ✅ fixed here
    Ownership opponent = (myOwner == Ownership.RED) ? Ownership.BLUE : Ownership.RED;

    List<GameCard> hand = player.getHand();  // also valid from ReadonlyPlayer
    int rows = model.getNumRows();
    int cols = model.getNumCols();

    for (int r = 0; r < rows; r++) {
      int playerScore = model.getRowScore(r, myOwner);
      int opponentScore = model.getRowScore(r, opponent);

      if (playerScore <= opponentScore) {
        for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
          GameCard card = hand.get(cardIndex);
          for (int c = 0; c < cols; c++) {
            ImmutableTile cell = model.getCell(r, c);
            if (!cell.containsCard() && model.isValidPlacement(card, r, c, myOwner)) {
              int simulated = playerScore + 1; // mock score bump
              if (simulated > opponentScore) {
                return new Move(cardIndex, r, c);
              }
            }
          }
        }
      }
    }

    return Move.pass();
  }
}
