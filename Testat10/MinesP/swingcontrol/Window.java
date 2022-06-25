/**
 * The main window for a game of Minesweeper
 */

package swingcontrol;
import model.Game;
import model.MinesField;
import swingview.CountComponent;
import swingview.MapComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import java.beans.PropertyChangeEvent;

public class Window extends JFrame implements MinesField.MinesFieldListener, MapComponent.FieldActionListener {
  private Game map;
  private MapComponent mapComponent;

  /**
   * Prepare a new window with a game map of the given dimension and minecount TODO
   */
  public Window (int rows, int cols, int minecount) {
    super();

    map = new Game(rows, cols, minecount);
    map.setAutoUncover(true);
    map.attachListener(this);

    add(new CountComponent(map));

    mapComponent = new MapComponent(map);
    mapComponent.addFieldActionListener(this);
    add(mapComponent);

    setTitle("Minesweeper");
    setSize(500, 500);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    setVisible(true);
  }

  /*
   * Implement MinesFieldListener
   */
  @Override
  public void update (int i, int j, PropertyChangeEvent evt) {
    // only listen for game state changes. do nothing
  }
 
  @Override
  public void update (PropertyChangeEvent evt) {
    if (evt.getPropertyName() != "gameState")
      return;

    if(evt.getNewValue() == Game.GameState.WON)
      JOptionPane.showMessageDialog(this, "You Win!");
    else
      JOptionPane.showMessageDialog(this, "You Lose!");
    mapComponent.setEnabled(false);
  }

  /*
   * Implement FieldActionListener
   */
  @Override
  public void uncover (int row, int column) {
    map.uncover(row, column);
  }

  @Override
  public void toggle_mark (int row, int col) {
    boolean old = map.getField(row, col).getHasMark();
    map.getField(row, col).setHasMark(!old);
  }
}
