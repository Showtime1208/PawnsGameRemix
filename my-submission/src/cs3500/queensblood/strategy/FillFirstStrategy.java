package cs3500.queensblood.strategy;

import cs3500.queensblood.model.*;
import cs3500.queensblood.model.enumsModel.Ownership;

import java.util.List;

public class FillFirstStrategy implements Strategy {

  @Override
  public Move chooseMove(ImmutableGame model, ImmutableParticipant player) {
    Ownership owner = player.getColor();
    List<GameCard> hand = player.getHand();
    int rows = model.getNumRows();
    int cols = model.getNumCols();

    for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
      GameCard card = hand.get(cardIndex);
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          ImmutableTile cell = model.getCell(r, c);
          if (!cell.containsCard() && model.isValidPlacement(card, r, c, owner)) {
            return new Move(cardIndex, r, c);
          }
        }
      }
    }

    return Move.pass();
  }
}
