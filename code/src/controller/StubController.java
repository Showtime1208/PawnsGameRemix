package controller;

/**
 * A stub controller for testing and debugging GUI interactions.
 */
public class StubController implements Controller {

  /**
   * Represents the constructor of the stub controller.
   */
  public StubController() {
    // No iniitilization required for this constructor.
  }

  @Override
  public void handleCellClick(int row, int col) {
    System.out.println("Cell clicked at coordinates: (" + row + ", " + col + ")");
  }

  @Override
  public void handleCardClick(int cardIndex) {
    System.out.println("Card selected with index: " + cardIndex);
  }

  @Override
  public void handlePassTurn() {
    System.out.println("Pass turn action triggered.");
  }

  @Override
  public void handleTurn() {

  }

  @Override
  public void processCommand(String command) {
    command = command.trim().toLowerCase();
    if ("c".equals(command)) {
      System.out.println("Confirm move action triggered (command: c).");
    } else if ("p".equals(command)) {
      System.out.println("Pass action triggered (command: p).");
    } else {
      System.out.println("No valid command entered.");
    }
  }

  @Override
  public void processAICommand() {

  }

}

