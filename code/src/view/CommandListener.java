package view;

/**
 * A functional interface for processing command input.
 * <p>
 * Implement this interface to handle commands entered in the command panel.
 * </p>
 */
public interface CommandListener {

  /**
   * Processes a command entered by the user.
   *
   * @param command the command string (expected to be "c" or "p")
   */
  void processCommand(String command);
}
