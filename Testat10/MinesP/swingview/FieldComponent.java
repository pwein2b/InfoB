/**
 * A custom JComponent that represents a single model.Field in the view.
 *
 * As long as the field is uncovered, ActionEvent's are forwarded.
 *
 * @author Philipp Weinbrenner
 */

package swingview;
import model.Field;
import model.MinesField;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import javax.swing.*;

public class FieldComponent extends JComponent implements MouseListener {
  /**
   * If the field is covered, display a button:
   */
  private JButton buttonChild;

  /**
   * If the field is uncovered, display a label:
   */
  private JLabel labelChild;

  private LinkedList<MouseListener> actionListeners = new LinkedList<MouseListener>();

  /*
   * The coordinates of the field in the gamefield 
   */
  int row, col;
  private Field field;
  private MinesField gamefield;

  public FieldComponent (MinesField mf, int row, int col) {
    super();
    this.field = mf.getField(row, col);
    this.row = row;
    this.col = col;
    this.gamefield = mf;

    field.addPropertyListener(new PropertyChangeListener () {
      @Override
      public void propertyChange (PropertyChangeEvent evt) {
//        System.out.println(evt.toString());
        if(evt.getPropertyName() == "hasMark") {
          Object val = evt.getNewValue();
          assert val instanceof Boolean;
          setShowMark(((Boolean)val).booleanValue());
        } else if(evt.getPropertyName() == "covered" && evt.getNewValue() == Boolean.valueOf(false)) {
          uncover();
        }
      }
    });

    buttonChild = new JButton();
    add(buttonChild);
    buttonChild.addMouseListener(this);

    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    setSize(new Dimension(10, 10));
    setVisible(true);
  }

  @Override
  public Dimension getPreferredSize () {
    return new Dimension(20, 20);
  }

  /**
   * Receive action events from the button and propagate them
   */
  @Override
  public void mouseClicked (MouseEvent e) {
    for (MouseListener a : actionListeners) {
      a.mouseClicked(e);
    }
  }

  private void setShowMark (boolean show) {
    if(show)
      buttonChild.setLabel("?");
    else
      buttonChild.setLabel("");
  }

  private void uncover () {
    remove(buttonChild);
    String label;
    if (field.getHasMine())
      label = "X";
    else {
      int cnt = gamefield.countAdjacentMines(row, col);
      label = Integer.toString(cnt);
    }

    labelChild = new JLabel(label);
    System.out.println("label " + label);
    add(labelChild);
    System.out.println("Field component at " + row + "," + col + " uncovered");
    validate();
  }

  public void addMouseListener(MouseListener l) {
    actionListeners.add(l);
  }

  public void removeMouseListener(MouseListener l) {
    actionListeners.remove(l);
  }

  /*
   * Unused methods we need to implement for MouseListener
   */
  @Override
  public void mouseEntered(MouseEvent evt){}
  @Override
  public void mouseExited(MouseEvent evt){}
  @Override
  public void mousePressed(MouseEvent evt){}
  @Override
  public void mouseReleased(MouseEvent evt){}
}
