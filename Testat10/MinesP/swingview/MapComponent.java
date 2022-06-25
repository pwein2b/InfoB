/**
 * This component harvests FieldComponents in an appropriate grid, representing an entire game map.
 * If any covered field is clicked, this information is passed on to registered FieldActionListener's.
 *
 * @author Philipp Weinbrenner
 */

package swingview;
import model.MinesField;
import model.Field;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComponent;
import java.util.LinkedList;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MapComponent extends JComponent {
  private MinesField field;
  private int rows;
  private int columns;
  private FieldComponent[][] components;

  private LinkedList<FieldActionListener> listeners = new LinkedList<FieldActionListener>();

  public MapComponent (MinesField field) {
    rows = field.getRows();
    columns = field.getColumns();
    this.field = field;
    components = new FieldComponent[rows][columns];

    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.weightx = c.weighty = 1.0;
    c.fill = GridBagConstraints.BOTH;
    c.insets = new Insets(1, 1, 1, 1);

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        components[i][j] = new FieldComponent(field, i, j);
        c.gridx = i;
        c.gridy = j;
        add(components[i][j], c);
        components[i][j].addMouseListener(new FieldListener(this, i, j));
      }
    }

    setVisible(true);
  }

  /**
   * Again, we need another completely unneccessary subclass because javac won't allow us to simply copy
   * local variables into an anonymous inner class
   * @see MinesField.FieldListener - same problem there
   */
  private class FieldListener implements MouseListener {
    private MapComponent obj;
    private int i, j;
    public FieldListener(MapComponent obj, int i, int j) {
      this.obj = obj; this.i = i; this.j = j;
    }
    @Override
    public void mouseClicked(MouseEvent evt) {
      if (evt.getButton() == MouseEvent.BUTTON1)
        obj.notifyUncover(i, j);
      else
        obj.notifyToggleMark(i, j);
    }
    @Override
    public void mouseEntered(MouseEvent evt){}
    @Override
    public void mouseExited(MouseEvent evt){}
    @Override
    public void mousePressed(MouseEvent evt){}
    @Override
    public void mouseReleased(MouseEvent evt){}
  }

  /**
   * Notify the registered FieldActionListener's, that the user requested that the field at coordinates (row, column) be uncovered.
   */
  protected void notifyUncover(int row, int column) {
    for (FieldActionListener l : listeners)
      l.uncover(row, column);
  }

  protected void notifyToggleMark(int row, int column) {
    for (FieldActionListener l : listeners)
      l.toggle_mark(row, column);
  }

  public void addFieldActionListener (FieldActionListener l) {
    listeners.add(l);
  }

  public void removeFieldActionListener (FieldActionListener l) {
    listeners.remove(l);
  }

  /**
   * An abstract listener to listen for a user request to uncover a field in the MapComponent
   */
  public interface FieldActionListener {
    public abstract void uncover (int row, int column);
    public abstract void toggle_mark (int row, int column);
  }
}
