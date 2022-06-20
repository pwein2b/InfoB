/**
 * Test Class for Task 9.1
 *
 *
 */

import java.io.*;

public class Test {

    public static void main(String args[]){
        Heap tree = new Heap();
        tree.insert(11);
        tree.insert(8);
        tree.insert(13);
        tree.insert(5);
        tree.insert(78);
        tree.insert(35);


        try(ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream("Heap.ser")) ) {
            a.writeObject(tree);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(ObjectInputStream b = new ObjectInputStream(new FileInputStream("Heap.ser"))) {
            Heap newheap = (Heap) b.readObject();
            newheap.print();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
