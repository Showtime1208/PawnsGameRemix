Pawns Board
Overview
This codebase implements Pawns Board, a two-playerEnum providerCard/board game inspired by a simplified variant of the Queen’s Blood game.
Two players (Red and Blue) place cards on a rectangular board; each providerCard’s placement exerts influence on neighboring cells, adding or converting pawns.
The game ends once both players pass consecutively. Finally, each row’s providerCard values are tallied to determine the row winner,
and total scores are computed across all rows to find the overall winner or a tie.

Key points of gameplay:

Each playerEnum is assigned one of two colors: Red or Blue.
A board with given rows and columns (columns must be odd, rows > 0).
Each playerEnum has a deck read from a configuration file, containing cards with:
A name (e.g., “Security”).
A cost (1–3 pawns).
A value (positive integer).
A 5×5 influence grid, which indicates how the providerCard affects surrounding cells when placed.
Each cell on the board may hold up to 3 pawns or exactly 1 providerCard. A providerCard’s ownership never changes, but pawns may flip ownership as a result of influence.
Red always goes first, drawing a providerCard if any remain in the deck, then placing a providerCard or passing.
This repository includes:

A model describing the data (cards, pawns, board) and the rules/enforcement.
A view (currently textual) that renders the state of the board after each move.
A (future) controller or “user-playerEnum” piece to manage interactive or AI-driven gameplay.

Quick Start:
Below is a quick example showing how you might start a game in a main method or test:


public class PawnsGame {
public static void main(String[] args) {
// 1. Create Players
DeckReader reader = new DeckReader();
String path = "code" + File.separator + "docs" + File.separator + "deck.config";
List<Card> cardList = reader.readDeck(path);
Player player1 = new SimplePlayer(3, true);
player1.setDeck(cardList);
Player player2 = new SimplePlayer(3, false);
player2.setDeck(reader.readDeckReverse(path));
Board board = new GameBoard(3, 5);
TextualView textualView = new SimpleTextualView(board);
try {
board.startGame(player1, player2);
view.render(System.out);
} catch (IOException e) {
e.printStackTrace();
}
board.placeCard(redPlayer, 0, 0, 0);
// Then render again, etc.

    // Additional moves could be placed here
    // ...
}
}

Key Components
Model
Board Interface (model.Board)`
Defines operations for starting the game, placing a providerCard, passing a turn, checking game-over status, and retrieving row or total scores.

GameBoard (an example)
A concrete implementation of Board that holds a 2D grid of Cells, enforces turn-based play, and applies the influence rules once a providerCard is placed.

Card
Represents a single providerCard, having:

getName(): name of the providerCard
getCost(): integer cost (1–3)
getValue(): integer value (score contributed by the providerCard)
getInfluenceArray(): a 5×5 array describing influence squares (with C in the central cell).
Pawn
A simple data object holding isRed (true → Red, false → Blue). Pawns can flip ownership if influenced by an opposing playerEnum’s providerCard.

Influence

Influence interface & SimpleInfluence
A small abstraction indicating which squares of the 5×5 grid apply influence. Each Influence has getInfluence() that returns true or false.
View
TextualView interface
A minimal rendering interface that declares toString() and render(Appendable out).
SimpleTextualView
A concrete class that translates the board’s state into a textual grid.
_ (underscore) for an empty cell
1, 2, or 3 for a cell containing that many pawns
R or B for a cell containing a Red-owned or Blue-owned providerCard
Row-scores displayed to the left and right of each row
(Future expansions might include a GUI-based view, but for now we only rely on textual output.)



Key Subcomponents
DeckReader (in model.providerCard package)

Responsible for parsing deck configuration files.
Ensures each providerCard is read correctly:

CARD_NAME COST VALUE
ROW_0
ROW_1
ROW_2
ROW_3
ROW_4
Validates the 5×5 grid has exactly one C in the center and only X or I in other cells.
GameCard

Concrete implementation of Card, holding the providerCard’s name, cost, value, and a 5×5 matrix of Influence objects.
Ensures the cost is between 1–3 and value > 0.
Throws exceptions if invalid data is provided.
GameBoard

Holds a 2D array of GameCell.
Enforces game rules (max 3 pawns in a cell, ownership changes, preventing duplicates, etc.).
Provides methods for placing cards, applying influence, and scoring rows.
Source Organization

The docs for the images are in the code/docs/images folder.

New classes for the view are the following:

1. CellClickListener -> Purpose: An interface for responding to clicks on a board cell.
2. CommandListener -> Purpose: A functional interface for processing textual commands entered by the user.
3. CommandPanel -> Purpose: A small Swing panel containing a text field and an "Execute" button.
   Users can type commands like "c" or "p" and press the button to trigger the CommandListener.
4. HandActionListener -> Purpose: An interface for reacting to user actions in the playerEnum’s hand, such as selecting a providerCard or confirming a play.
5. HandDisplayPanel -> Purpose: A specialized Swing panel responsible for rendering a playerEnum’s hand visually (cards, influence grids, highlights).
6. PawnsBoardGame -> Purpose: A main view class(JFrame) that orchestrates the overall GUI layout.
7. PawnsBoardViewInterface -> Purpose: An interface that outlines what the board view must support, such as highlighting cells, repainting, etc.
8. PawnsBoardViewListener -> Purpose: An interface for controllers to implement, so they can respond to board events.
9. PlayerHandView -> Purpose: A high-level container for displaying the playerEnum’s hand and a command panel.
10. ScorePanel -> Purpose: A panel to display scores, turn information, or other game metrics.

Strategies for Pawns Game
We provide three computer-play strategies for the Pawns Board game:

FillFirstStrategy
Logic: Scans the playerEnum’s hand in order (index 0 onward) and checks
board cells from top-left to bottom-right. The first legal placement it finds is chosen.

Tie-Breaking: Not needed, because it stops at the first valid cell.

MaximizeRowScoreStrategy
Logic: Identifies rows where the playerEnum is currently losing or tied,
in ascending row order. For each such row, it checks each providerCard and cell in that row. As soon as it finds a move that lifts its row-score above (or ties) the opponent’s, it takes it.

Tie-Breaking: Favors the earliest row, then the earliest cell in that row.
Once a valid move that meets or exceeds the opponent’s row-score is found, it stops.

BoardControlStrategy
Logic: For every possible legal move, it simulates placing the providerCard
and counts the total number of cells that the playerEnum would own afterward. The move yielding the greatest ownership is chosen.

Tie-Breaking: If multiple moves yield the same best ownership count,
it picks the uppermost row, then the leftmost column, then the smallest providerCard index in the playerEnum’s hand.

Usage:
Each strategy implements a common Strategy interface with a
getMove(Board board, Player playerEnum) method. If no move is legal under
that strategy’s logic, it returns a “pass” move (row = -1, col = -1, pass = true).

These strategies can be selectively attached to either playerEnum to provide
varying AI behaviors in your Pawns Board game.

Testing:
Unit tests for each method in Cell, Board, etc. (especially verifying influence logic).
Integration tests verifying that a full sequence of actions produces the correct final board state.
Manual tests using PawnsBoard.main(...), observing console output from the textual view.
You can find relevant tests in test/ directories, each focusing on different aspects of the model or view.

Updates for pt. 3:

Controller
The game now includes a fully implemented controller system that connects playerEnum actions to the game:

PawnsGameController
- Handles both human and AI players
- Manages turn-based gameplay
- Processes playerEnum commands (place providerCard, pass turn)
- Connects view events to model actions
- Ensures valid moves and proper turn order
- Displays game status and error messages to players