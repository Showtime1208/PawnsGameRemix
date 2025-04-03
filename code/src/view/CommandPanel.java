package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A panel that provides a text field and an "Execute" button for entering commands.
 * <p>
 * Valid commands are:
 * <ul>
 *   <li>"c" - confirm the move</li>
 *   <li>"p" - pass the turn</li>
 * </ul>
 * When the button is pressed, the command is sent to the attached CommandListener.
 * </p>
 */
public class CommandPanel extends JPanel {

  private final JTextField field;
  private final JButton button;

  public CommandPanel() {
    setLayout(new FlowLayout());
    add(new JLabel("Enter command (c=confirm, p=pass): "));

    field = new JTextField(10);
    button = new JButton("Execute");

    add(field);
    add(button);
  }

  /**
   * Attaches a CommandListener to handle command input.
   *
   * @param listener the CommandListener implementation.
   */
  public void addCommandListener(CommandListener listener) {
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = field.getText().trim().toLowerCase();
        if (listener != null) {
          listener.processCommand(command);
        }
        field.setText("");
      }
    });
  }
}
