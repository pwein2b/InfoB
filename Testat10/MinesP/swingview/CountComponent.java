/**
 * A JComponent that displays the number of mines that still have to be marked
 *
 * @author Philipp Weinbrenner
 */

package swingview;
import model.Field;
import model.MinesField;
import model.Game;
import javax.swing.JLabel;
import java.beans.PropertyChangeEvent;

public class CountComponent extends JLabel {
  private MinesField gamefield;

  public CountComponent (MinesField f) {
    super("Welcome!");
    gamefield = f;
    gamefield.attachListener(new MinesField.MinesFieldListener () {
      @Override
      public void update (int i, int j, PropertyChangeEvent evt) {
        int mines = gamefield.countMines();
        int marks = gamefield.countMarkedFields();
        setText(marks + " marks for " + mines + " mines");
      }

      @Override
      public void update (PropertyChangeEvent evt) {
        if (evt.getPropertyName() != "gameState")
          return;

        if(evt.getNewValue() == Game.GameState.WON)
          setText("You Win!");
        else
          setText("You Lose!");
      }
    });
  }
}
