Pawns Board Game - HW6 Submission
==================================

Author: Alejandro Campa-Maldonado
Course: CS3500
Assignment: HW6 - Pawns on the World Stage (Part 2)

Overview:
---------
This project expands the Pawns Board game from HW5 to include:
- A complete GUI using Java Swing
- Support for basic computer strategies
- A more robust model structure using immutable interfaces

The game allows two players (Red and Blue) to place cards on a board, apply influence based on 5x5 influence grids, and score points row-by-row.

How to Compile & Run:
---------------------
1. Compile:
   > javac -d out -sourcepath src src/PawnsBoardMain.java

2. Run:
   > java -cp out PawnsBoardMain

3. Run (JAR):
   After building the `.jar` through IntelliJ artifacts:
   > java -jar PawnsBoardGame.jar

Controls:
---------
🖱 Mouse:
- Click on a hand card to select it
- Click on a board cell to select it
- Click again to deselect
- Selection is highlighted visually

⌨️ Keyboard:
- Press `Enter` to confirm a move
- Press `P` to pass the turn

Required Screenshots:
---------------------
1. Start of the game (5x7)
2. Red's turn with selected card and tile
3. Blue's turn with mirrored card
4. Mid-game with multiple cards played

Changes for Part 2:
-------------------
✅ Refactored model into immutable and mutable layers:
   - `ImmutableGame`, `ImmutableGrid`, `ImmutableTile`, etc.

✅ Implemented a GUI view:
   - `BoardPanel` draws board state
   - `HandPanel` displays hand cards
   - `PawnsBoardFrame` coordinates layout
   - Fully resizable and interactive

✅ Event Handling:
   - Mouse clicks handled through listeners
   - Cell/card highlighting
   - Keyboard input for confirm/pass

✅ Strategy Implementations:
   - `FillFirstStrategy` – Picks the first legal move
   - `MaximizeRowScoreStrategy` – Chooses a move that helps win a row

✅ Testing:
   - Unit tests using mocks
   - Two transcripts:
     • strategy-transcript-first.txt
     • strategy-transcript-score.txt

✅ View Behavior:
   - Does not mutate the model directly
   - Handles invalid clicks and input safely
   - Adjusts to window resizing gracefully

Directory Structure:
--------------------
- src/          → Game source files (model, view, controller, enums)
- test/         → Unit tests and strategy tests
- docs/         → Contains deck.config used for game setup
- out/          → Build output
- screenshots/  → 4 required screenshots
- strategy-transcript-first.txt
- strategy-transcript-score.txt
- README.txt    → This file
- *.jar         → Final runnable JAR (required for submission)

Notes:
------
- All MVC components are clearly separated
- View is read-only and interacts through a controller
- Strategy results are verifiable through mock transcripts
- Fully follows assignment specs
