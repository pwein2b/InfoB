/**
 * This class represents a single field on a MinesField playing field.
 *
 * A field is described by its current state: It can hold a mine or not, it can be marked or not, and it can be covered or not.
 * By default, a field is not mined, not marked, and covered.
 * Any changes in these properties will be communicated through PropertyChangeEvent's.
 *
 * Since the Game class depends on reacting to a field being uncovered, a field can only be uncovered by calling the appropriate
 * method in the MinesField object it belongs to.
 *
 * Some utility functions for working with fields are in the MinesField class, since they may depend on the surrounding fields.
 *
 * @author Philipp Weinbrenner
 */

package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Field {
  private boolean hasMine;
  private boolean hasMark;
  private boolean covered;
  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  /**
   * Construct a new Field instance.
   */
  Field (boolean mine, boolean mark) {
    this.hasMine = mine;
    this.hasMark = mark;
    this.covered = true;
  }

  /**
   * Construct a Field that is not marked
   */
  Field (boolean mine) {
    this(mine, false);
  }

  /**
   * Construct a Field that is neither marked nor mined.
   */
  Field () {
    this(false, false);
  }

  public boolean getHasMine() {
    return hasMine;
  }

  public boolean getHasMark() {
    return hasMark;
  }

  public boolean getCovered() {
    return covered;
  }

  public void setHasMine(boolean newval) {
    boolean old = hasMine;
    hasMine = newval;
    pcs.firePropertyChange("hasMine", old, newval);
  }

  public void setHasMark(boolean newval) {
    boolean old = hasMark;
    hasMark = newval;
    pcs.firePropertyChange("hasMark", old, newval);
  }

  void setCovered (boolean newval) {
    boolean old = covered;
    covered = newval;
    pcs.firePropertyChange("covered", old, newval);
  }

  /**
   * Attach a PropertyChangeListener for the mine and mark properties.
   */
  public void addPropertyListener (PropertyChangeListener l) {
    pcs.addPropertyChangeListener(l);
  }

  /**
   * Remove a PropertyChangeListener
   */
  public void removePropertyListener (PropertyChangeListener l) {
    pcs.removePropertyChangeListener(l);
  }
}
