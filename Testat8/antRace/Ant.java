package antRace;

import antRace.AntField.Field;

import java.util.LinkedList;
import java.util.List;

/**
 * An {@code Ant} is created at a specific position of an {@link AntField} with
 * an initial {@code stepCount}. When running an Ant, it will lookup the values
 * on the current and all surrounding {@link Field}
 * (Moore-neighborhood) instances and test if the position is free, i.e. has a
 * value of {@code 0}, or if the value is greater than the {@code stepCount} of
 * this Ant. For both cases, the Ant will set the value of the {@code Field} at
 * the visited position to its own {@code stepCount+1}. After an {@code Ant} has
 * successfully visited one field, it will create new {@code Ant} instances with
 * an incremented {@code stepCount} to visit the other available {@code Field}
 * elements. The Ant will run until it finds no more {@code Field} elements in
 * its neighborhood to be altered.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Ant implements Runnable {



   /**
    * 
    * @param fields
    *           the {@code AntField} on which this {@code Ant} operates
    * @param x
    *           x-axis value of the starting position
    * @param y
    *           y-axis value of the starting position
    * @param stepCount
    *           initial stepCount of this {@code Ant}.
    * 
    * @throws IllegalArgumentException
    *            If the {@code Field} at position {@code x,y} does not exist, or
    *            if its value is < 0
    */
   private AntField fields;

   private int x;

   private int y;

   private int stepCount;


   /**
    * Constructor for the Ant class
    *
    * @param fields
    * @param x
    * @param y
    * @param stepCount
    */
   public Ant(AntField fields, int x, int y, int stepCount) {
   this.fields = fields;
   this.x = x;
   this.y = y;
   this.stepCount = stepCount;
   Field initialfield = fields.getField(x,y);
   if(initialfield == null){
      System.out.println("Error detecting fields");
      throw new NullPointerException();
      }
   initialfield.setValue(this.stepCount);
   }


   /**
    * The run Method determining the behaviour of the ant using Moore Neighbourhood
    *
    */
   public void run() {
      LinkedList<Thread> threadList = new LinkedList<>();
      Field tempfield;
      boolean way;
      int localx = this.x;
      int localy = this.y;

      do{
         way = false;

         //Check all surrounding numbers
         for(int i = localx - 1;i <=  localx + 1; i++){
            for(int j = localy - 1;j <= localy + 1; j++){
               tempfield = fields.getField(i,j);

               if( tempfield != null){
                  synchronized (tempfield){
                     int fieldvalue = tempfield.getValue();

                     if (fieldvalue == AntField.FREE || fieldvalue > this.stepCount + 1){

                        if(!way){
                           tempfield.setValue(stepCount + 1);
                           way = true;
                           this.x = i;
                           this.y = j;
                        } else{
                           Ant nextAnt = new Ant(fields, i, j, stepCount +1 );
                           Thread newthread = new Thread(nextAnt);
                           newthread.start();
                           threadList.add(newthread);

                        }

                     }

                  }

               }

            }

         }
         stepCount++;
      }while(way);

      /**
       * Wait for the other threads using join
       */
      for (Thread t : threadList){
         try {
            t.join();
         } catch (InterruptedException e) {
            System.out.println("A thread was interrupted");
            e.printStackTrace();
         }
      }
   }

}
