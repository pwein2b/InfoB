/**
 * A MinesField subclass that implements additional functionality that may be useful for implementing game-play.
 *
 * The field is initialized empty, but when the first Field is uncovered, the others are randomly filled with mines.
 *
 * This class notices when the user loses by uncovering a mined field or wins when uncovering all non-mined fields; therefore,
 * the property gameState should be listened to through a MinesFieldListener.
 *
 * @author Philipp Weinbrenner
 */

package model;
import java.beans.PropertyChangeEvent;

public class Game extends MinesField implements MinesField.MinesFieldListener {
  /**
   * This enum is used to describe the state of gameplay.
   */
  public enum GameState {
    RUNNING,
    WON,
    LOST
  }

  private int minecount;
  private boolean initialized = false;
  private GameState gameState = GameState.RUNNING;

  public Game (int rows, int columns, int minecount) {
    super(rows, columns);
    this.minecount = minecount;
    attachListener(this);
  }

  @Override
  public void uncover(int r, int c) {
    if (!initialized) {
      /* Randomly fill the field with mines, sparing out the field to be uncovered. */
      int mines_to_fill = minecount;
      int remaining_fields = rows * columns - 2;
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
          if (i == r - 1 || i == r + 1)
            continue;

          remaining_fields -= 1;

          if (i == r && j == c)
            continue;

          /* probability to place the mine, s. t. we fill exactly as many mines as desired */
          double p = ((double)mines_to_fill)/((double)remaining_fields);
          if(Math.random() <= p) {
            mines_to_fill--;
            getField(i, j).setHasMine(true);
            System.out.println("Mine placed at " + i + "," + j);
          }
        }
      }

      initialized = true;
    }

    super.uncover(r, c);

    if (getField(r, c).getHasMine())
      setGameState(GameState.LOST);
  }

  @Override
  public void update (int i, int j, PropertyChangeEvent evt) {
      System.out.println("# fields: " + rows * columns);
      System.out.println("# marks: " + countMarkedFields());
      System.out.println("# uncovered: " + countUncoveredFields());
    if (countMarkedFields() + countUncoveredFields() == (rows * columns)) {
      setGameState(GameState.WON);
    }
  }

  @Override
  public void update (PropertyChangeEvent evt) {
  }

  protected void setGameState (GameState newval) {
    GameState old = gameState;
    gameState = newval;
    pcs.firePropertyChange("gameState", old, newval);
  }
}
