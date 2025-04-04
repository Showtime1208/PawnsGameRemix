package strategy;

import model.Board;
import model.Player;

public interface Strategy {

  public Move getMove(Board board, Player player);


}
