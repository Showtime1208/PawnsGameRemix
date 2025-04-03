package model.card;

public class SimpleInfluence implements Influence {

  private final boolean influence;


  public SimpleInfluence(boolean influence) {
    this.influence = influence;
  }

  @Override
  public boolean getInfluence() {
    return influence;
  }

  @Override
  public SimpleInfluence[][] getInfluenceArray(char[][] grid) {
    if (grid.length != 5) {
      throw new IllegalArgumentException("Grid must have 5 rows.");
    }
    SimpleInfluence[][] influenceGrid = new SimpleInfluence[5][5];
    for (int i = 0; i < 5; i++) {
      if (grid[i].length != 5) {
        throw new IllegalArgumentException("Each row has 5 columns");
      }
      for (int j = 0; j < 5; j++) {
        char c = grid[i][j];
        if (c == 'I') {
          influenceGrid[i][j] = new SimpleInfluence(true);
        }
        if (c == 'C' || c == 'X') {
          influenceGrid[i][j] = new SimpleInfluence(false);
        } else {
          throw new IllegalArgumentException("Invalid character in the inlfuence grid");
        }
      }
    }
    return influenceGrid;
  }
}
