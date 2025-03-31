package cs3500.queensblood.strategy;

import cs3500.queensblood.model.ImmutableGame;
import cs3500.queensblood.model.ImmutableParticipant;

public interface Strategy {
  Move chooseMove(ImmutableGame model, ImmutableParticipant player);
}
