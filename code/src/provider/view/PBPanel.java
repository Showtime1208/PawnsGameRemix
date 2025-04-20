package provider.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import provider.model.PlayerEnum;
import provider.model.ProviderCard;
import provider.model.ReadonlyPawnsBoardModel;

/**
 * Represents a panel for Pawns Board.
 */
public class PBPanel extends JPanel {

  private final ReadonlyPawnsBoardModel model;
  private final Color playerRedColor = new Color(255, 0, 0, 125);
  private final Color playerBlueColor = new Color(0, 0, 255, 125);
  private final Color itemSelectedColor = new Color(0, 255, 255, 125);
  private final int boardWidth;
  private final int boardHeight;
  private final Font stringFont = new Font("SansSerif", Font.PLAIN, 24);
  private final Font cardFont = new Font("Monospaced", Font.BOLD, 12);
  private final MouseEventsListener listener = new MouseEventsListener();
  private final List<PlayerAction> observers;
  private int cardHighlightX = -1;
  private int cellHighlightY = -1;
  private int cellHighlightX = -1;
  private ProviderCard highlightedProviderCard = null;
  private PlayerEnum playerEnum;
  private int lastHighlightedCellX;
  private int lastHighlightedCellY;
  private boolean isHighlightedCell = false;

  /**
   * Represents a panel for Pawns Board.
   *
   * @param model represents the model passed in to the panel
   */
  public PBPanel(ReadonlyPawnsBoardModel model, PlayerEnum playerEnum) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.playerEnum = playerEnum;
    this.boardWidth = model.getWidth() * 100 + 200;
    this.boardHeight = model.getHeight() * 100;
    this.addMouseListener(listener);
    setFocusable(true);
    this.addNewKeyListener();
    this.observers = new ArrayList<>();
  }

  /**
   * Observer to be added.
   *
   * @param obs observer
   */
  public void addPlayerActionObserver(PlayerAction obs) {
    this.observers.add(obs);
  }

  /**
   * Displays a text field that shows the result of an interaction.
   *
   * @param msg the text displayed by the panel
   */
  public void showMessage(String msg) {
    JOptionPane.showMessageDialog(null, msg);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setFont(stringFont);
    drawEnds(g2d);
    drawScore(g2d);
    drawCells(g2d);
    drawLines(g2d);
    drawHand(g2d);
  }

  private void drawScore(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);
    for (int i = 0; i < this.model.getHeight(); i++) {
      g2d.drawString(this.model.getRowScore(i, PlayerEnum.Red) + "", 50, 50 + i * 100);
      g2d.drawString(this.model.getRowScore(i, PlayerEnum.Blue) + "", this.boardWidth - 50,
          50 + i * 100);
    }
  }

  private void drawEnds(Graphics2D g2d) {
    for (int i = 0; i < this.model.getHeight(); i++) {
      g2d.setColor(Color.GRAY);
      g2d.fillRect(0, (100 * i), 100, 100);
      g2d.fillRect(boardWidth - 100, (100 * i), 100, 100);
      if (this.model.getRowScore(i, PlayerEnum.Red) > this.model.getRowScore(i, PlayerEnum.Blue)) {
        g2d.setColor(playerRedColor);
        g2d.fillOval(25, (100 * i) + 25, 50, 50);
      } else if (this.model.getRowScore(i, PlayerEnum.Red) < this.model.getRowScore(i,
          PlayerEnum.Blue)) {
        g2d.setColor(playerBlueColor);
        g2d.fillOval(boardWidth - 75, (100 * i) + 25, 50, 50);
      }
    }
    g2d.drawLine(0, 0, 0, boardHeight);
    g2d.drawLine(boardWidth, 0, boardWidth, boardHeight);
  }

  private void drawLines(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);
    int mHeight = this.model.getHeight();
    int mWidth = this.model.getWidth();
    for (int i = 0; i <= mHeight; i++) {
      g2d.drawLine(0, 100 * i, boardWidth, 100 * i);
    }
    for (int i = 0; i <= mWidth; i++) {
      g2d.drawLine(100 * i, 0, 100 * i, this.boardHeight);
    }
    for (int i = 0; i <= this.model.getHand(this.model.getTurn()).size(); i++) {
      g2d.drawLine((this.boardWidth / 3) * i, this.model.getHeight() * 100,
          (this.boardWidth / 3) * i, this.model.getHeight() * 100 + 100);
    }
    g2d.drawLine(0, this.boardHeight + 100, this.boardWidth, this.boardHeight + 100);
  }

  private void drawCells(Graphics2D g2d) {
    System.out.println(this.cellHighlightX + " " + this.cellHighlightY);
    for (int j = 0; j < this.model.getHeight(); j++) {
      for (int i = 0; i < this.model.getWidth(); i++) {
        if (this.model.getCellAt(j, i).getCard() != null) {
          this.setPlayerColor(g2d);
          renderCard(g2d, j, i);
        } else if (i == this.cellHighlightX && j == this.cellHighlightY
            && this.isHighlightedCell) {
          g2d.setColor(itemSelectedColor);
          g2d.fillRect(100 * i + 101, 100 * j, 100, 100);
          if (this.model.getCellAt(j, i).getPawns() != 0) {
            g2d.setColor(Color.BLACK);
            g2d.drawString(this.model.getCellAt(j, i).getPawns() + "",
                150 + 100 * i, 50 + 100 * j);
          }
        } else if (this.model.getCellAt(j, i).getPawns() != 0) {
          if (this.model.getCellAt(j, i).getOwner() == PlayerEnum.Red) {
            g2d.setColor(playerRedColor);
          } else {
            g2d.setColor(playerBlueColor);
          }
          g2d.fillRect(100 * i + 101, 100 * j, 100, 100);
          g2d.setColor(Color.BLACK);
          g2d.drawString(this.model.getCellAt(j, i).getPawns() + "",
              150 + 100 * i, 50 + 100 * j);
        } else {
          g2d.setColor(Color.LIGHT_GRAY);
          g2d.fillRect(100 * i + 101, 100 * j, 100, 100);
          g2d.setColor(Color.BLACK);
        }
      }
    }
  }

  // Can possibly be upgraded to render the influence grid in color, for now textual is good.
  private void renderCard(Graphics2D g2d, int i, int j) {
    g2d.setColor(Color.BLACK);
    g2d.setFont(cardFont);
    String[] split = this.model.getCellAt(i, j).getCard().toString().split("\\n");
    for (int n = 0; n < split.length; n++) {
      g2d.drawString(split[n], 101 + 100 * j, 10 + (10 * n) + (100 * i));
    }
    if (this.model.getCellAt(i, j).getOwner() == PlayerEnum.Blue) {
      g2d.setColor(playerBlueColor);
    } else {
      g2d.setColor(playerRedColor);
    }
    g2d.fillRect(100 * j + 101, 100 * i, 100, 100);
    g2d.setFont(this.stringFont);
  }

  private void drawHand(Graphics2D g2d) {
    if (this.highlightedProviderCard != null) {
      System.out.println(this.highlightedProviderCard.getName());
    } else {
      System.out.println("null");
    }
    for (int i = 0; i < this.model.getHand(this.playerEnum).size(); i++) {
      if (this.model.getHand(this.playerEnum).get(i).equals(this.highlightedProviderCard)) {
        g2d.setColor(this.itemSelectedColor);
      } else {
        this.setPlayerColor(g2d);
      }
      g2d.fillRect((this.boardWidth / 3) * i, this.model.getHeight() * 100,
          (this.boardWidth / 3), 100);
      g2d.setColor(Color.BLACK);
      g2d.setFont(cardFont);
      String[] split;
      for (int j = 0; j < this.model.getHand(this.playerEnum).size(); j++) {
        split = this.model.getHand(this.playerEnum).get(j).toString().split("\\n");
        for (int n = 0; n < split.length; n++) {
          g2d.drawString(split[n], (this.boardWidth / 3) * j,
              (this.model.getHeight() * 100) + 10 + (10 * n));
        }
      }
    }
  }

  private void setPlayerColor(Graphics2D g2d) {
    if (this.playerEnum == PlayerEnum.Blue) {
      g2d.setColor(playerBlueColor);
    } else {
      g2d.setColor(playerRedColor);
    }
  }

  private void addNewKeyListener() {
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          if (!PBPanel.this.isHighlightedCell || PBPanel.this.highlightedProviderCard == null) {
            return;
          } else {
            for (PlayerAction obs : PBPanel.this.observers) {
              obs.moveChosen(PBPanel.this.cardHighlightX,
                  PBPanel.this.cellHighlightY, PBPanel.this.cellHighlightX);
            }
            PBPanel.this.highlightedProviderCard = null;
            PBPanel.this.isHighlightedCell = false;
          }
          PBPanel.this.repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
          PBPanel.this.highlightedProviderCard = null;
          PBPanel.this.isHighlightedCell = false;
          PBPanel.this.repaint();
          for (PlayerAction obs : PBPanel.this.observers) {
            obs.passTurn();
          }
        }
      }
    });
  }

  private class MouseEventsListener extends MouseInputAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
      Point point = e.getPoint();
      if (point.getY() > boardHeight && point.getY() < boardHeight + 100
          && point.getX() >= 0 && point.getX() < boardWidth) {
        cardHighlightX = this.getCardXValue((int) point.getX());
        highlightCard();
        System.out.println("Clicked on card in hand slot " + cardHighlightX);
      } else if (point.getX() > 100 && point.getX() < boardWidth - 100
          && point.getY() > 0 && point.getY() < boardHeight) {
        System.out.println("Clicked on cell with coordinates " + point.getX() + "," + point.getY());
        cellHighlightX = this.getXValue((int) point.getX());
        cellHighlightY = this.getYValue((int) point.getY());
        setHighlightCell(cellHighlightX, cellHighlightY);
      } else {
        return;
      }
      PBPanel.this.repaint();
    }

    private void setHighlightCell(int highlightX, int highlightY) {
      if (highlightX < 0 || highlightY < 0) {
        return;
      }
      if (PBPanel.this.model.getCellAt(highlightY, highlightX).getCard()
          != null) {
        return;
      }
      PBPanel.this.cellHighlightX = highlightX;
      PBPanel.this.cellHighlightY = highlightY;
      if (!PBPanel.this.isHighlightedCell) {
        System.out.println("setting highlighted cell to true");
        PBPanel.this.lastHighlightedCellX = highlightX;
        PBPanel.this.lastHighlightedCellY = highlightY;
        PBPanel.this.isHighlightedCell = true;
      } else if (PBPanel.this.lastHighlightedCellX == highlightX
          && PBPanel.this.lastHighlightedCellY == highlightY) {
        System.out.println("setting highlighted cell to false");
        PBPanel.this.isHighlightedCell = false;
      } else {
        System.out.println("doing nothing");
        PBPanel.this.cellHighlightX = PBPanel.this.lastHighlightedCellX;
        PBPanel.this.cellHighlightY = PBPanel.this.lastHighlightedCellY;
      }
    }

    private void highlightCard() {
      if (PBPanel.this.highlightedProviderCard == null) {
        PBPanel.this.highlightedProviderCard = PBPanel.this.model.getHand(
            PBPanel.this.model.getTurn()).get((PBPanel.this.cardHighlightX));
      } else if (PBPanel.this.model.getHand(PBPanel.this.model.getTurn())
          .get((PBPanel.this.cardHighlightX)).equals(
              PBPanel.this.highlightedProviderCard)) {
        PBPanel.this.highlightedProviderCard = null;
      }
    }

    private int getXValue(int x) {
      return (x / (PBPanel.this.boardWidth / (PBPanel.this.model.getWidth() + 2)) - 1);
    }

    private int getYValue(int y) {
      return (y / (PBPanel.this.boardHeight / (PBPanel.this.model.getHeight())));
    }

    private int getCardXValue(int x) {
      return x / (boardWidth / model.getHand(model.getTurn()).size());
    }
  }
}
