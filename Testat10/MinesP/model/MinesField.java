/**
 * This class represents the playing field for the minesweeper instance.
 * Each field is represented by a Field object.
 *
 * The object listens for changes in all of its fields, for the properties they expose through PropertyChangeSupport;
 * events are passed on through the Observable pattern. The java.util.Observable pattern is deprecated and not implemented.
 *
 * @author Philipp Weinbrenner
 */

package model;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;

public class MinesField {
  protected int rows;
  protected int columns;
  private Field[][] fields;
  private LinkedList<MinesFieldListener> listeners;
  private boolean autoUncover = false;

  /**
   * Support property change listening on this object.
   * Not directly used in this class, but useful for subclasses. PropertyChangeEvents are passed on using
   * the MinesFieldListener interface.
   */
  protected PropertyChangeSupport pcs;

  /**
   * Create a new MinesField of the given dimension; all fields are initialized as empty.
   */
  public MinesField (int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    listeners = new LinkedList<MinesFieldListener>();

    this.fields = new Field[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        fields[i][j] = new Field();
        fields[i][j].addPropertyListener(new FieldListener(this, i, j));
/*        fields[i][j].addPropertyListener(new PropertyChangeListener () {
        // this is what we actually want to do, but java does not allow it. See FieldListener below.
          @Override
          public void propertyChange (PropertyChangeEvent evt) {
            notifyAllListeners(I, J, evt);
          }
        });*/
      }
    }

    pcs = new PropertyChangeSupport(this);
    pcs.addPropertyChangeListener(new PropertyChangeListener () {
      @Override
      public void propertyChange (PropertyChangeEvent evt) {
        notifyAllListeners(evt);
      }
    });
  }

  /**
   * Due to some hardly documented javac error (inner classes may not access non-final local veriables),
   * we need the following class to listen to our fields:
   * @see MapComponent.FieldListener - same problem over there
   */
  private class FieldListener implements PropertyChangeListener {
    private int i, j;
    private MinesField object;

    public FieldListener (MinesField obj, int i, int j) {
      this.i = i;
      this.j = j;
      this.object = obj;
    }

    @Override
    public void propertyChange (PropertyChangeEvent evt) {
      object.notifyAllListeners(i, j, evt);
    }
  }

  public Field getField (int row_index, int column_index) {
    return fields[row_index][column_index];
  }

  /**
   * Notify all registered listeners that a property on a certain field has changed.
   * @param i Row index of the field
   * @param j Column index of the field
   * @param evt The PropertyChangeEvent
   */
  private void notifyAllListeners(int i, int j, PropertyChangeEvent evt) {
    for(MinesFieldListener l : listeners) {
      l.update(i, j, evt);
    }
  }

  /**
   * Notify all registered listeners that a property on the MinesField object has changed.
   * @param evt the PropertyChangeEvent
   */
  private void notifyAllListeners(PropertyChangeEvent evt) {
    for(MinesFieldListener l : listeners) {
      l.update(evt);
    }
  }

  /**
   * Count the adjacent mines.
   * This utility function finds all the fields directly/diagonally adjacent to a given field and counts how many of them are mined.
   */
  public int countAdjacentMines (int row_index, int column_index) {
    int cnt = 0;
    for(int rowvar = -1; rowvar <= 1; rowvar++) {
      for(int colvar = -1; colvar <= 1; colvar++) {
        if (!onField(row_index + rowvar, column_index + colvar))
          continue;

        if (getField(row_index + rowvar, column_index + colvar).getHasMine())
          cnt++;
      }
    }

    return cnt;
  }

  public int countMarkedFields () {
    int cnt = 0;
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < columns; j++) {
        if (getField(i, j).getHasMark())
          cnt++;
      }
    }

    return cnt;
  }

  public int countMines () {
    int cnt = 0;
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < columns; j++) {
        if (getField(i, j).getHasMine())
          cnt++;
      }
    }

    return cnt;
  }

  public int countUncoveredFields () {
    int cnt = 0;
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < columns; j++) {
        if (!getField(i, j).getCovered())
          cnt++;
      }
    }

    return cnt;
  }

  /**
   * This method changes the covered property of the field at given position.
   * The property cannot be changed directly at that class, because the Game subclass depends on overriding this method.
   * Use autoUncover() to automatically uncover other fields as well.
   */
  public void uncover (int row_index, int column_index) {
    Field f = getField(row_index, column_index);
    if (f.getCovered() == false)
      return;
    System.out.println("Uncover at " + row_index + "," + column_index);
    f.setCovered(false);

    /* don't autoUncover further if this field already is surrounded by a mine */
    boolean noAutoUncover = (countAdjacentMines(row_index, column_index) != 0);
    if (autoUncover && !noAutoUncover) {
      for(int rowvar = -1; rowvar <= 1; rowvar++) {
        for(int colvar = -1; colvar <= 1; colvar++) {
          if (!onField(row_index + rowvar, column_index + colvar))
            continue;
//          if (0 == countAdjacentMines(row_index + rowvar, column_index + colvar))
          uncover(row_index + rowvar, column_index + colvar);
        }
      }
    }
  }

  /**
   * Auto uncover is disabled by default.
   * If auto uncover is enabled with this method, for each uncovered field, all adjacent fields with mine count zero
   * are also uncovered recursively.
   */
  public void setAutoUncover (boolean value) {
    autoUncover = value;
  }

  /**
   * Determine wether the supplied coordinates represent a field on the MinesField or not.
   */
  public boolean onField(int row, int column) {
    return (row >= 0 && row < rows && column >= 0 && column < columns);
  }

  /**
   3 Attach a new MinesFieldListener to this field.
   */
  public void attachListener (MinesFieldListener listener) {
    listeners.add(listener);
  }

  /**
   * Detach a MinesFieldListener to this field.
   */
  public void detachListener (MinesFieldListener listener) {
    listeners.remove(listener);
  }

  public int getRows () {
    return rows;
  }

  public int getColumns () {
    return columns;
  }

  /**
   * An abstract class for observing changes on a MinesField.
   */
  public interface MinesFieldListener {
    /**
     * Receive an update for the field at row index i, column index j.
     */
    public abstract void update (int i, int j, PropertyChangeEvent evt);

    /**
     * Receive an update for the MinesField object
     */
    public abstract void update (PropertyChangeEvent evt);
  }
}
