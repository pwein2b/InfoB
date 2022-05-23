/**
 * Klasse, welche PersistentArray testet
 *
 * @author Markus Kuehn
 */

package Task5and6;

import javax.print.attribute.TextSyntax;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Task6 {

    /**
     * Testet die in Aufgabe 5 geforderten Funktionen
     *
     * @param args
     */
    public  static void  main(String args[]){
        Task6 test = new Task6();

        try {
            test.Testread(3);
        } catch( IOException b){
            System.out.println("Exception beim Testen der Read funktion");
        }

        try {
            test.Testwrite(5);
        } catch (IOException e) {
            System.out.println("Exception beim Testen der Write funktion");
        }

        try{
            test.Testlength();
        } catch (IOException e) {
            System.out.println("Fehler beim testen der Laengenfunktion");
        }

        test.Testarray1();
        test.Testarray2();

        try {
            test.Testclosing();
        } catch (IOException e) {
            System.out.println("Exception erfolgreich gefangen: ");
            e.printStackTrace();
        }

    }

    /**
     * Testet die getElement() Funktion von PersistentArray
     *
     * @param stelle die Stelle welche gelesen werden soll
     * @throws IOException
     */
    private void Testread(int stelle) throws IOException {
        System.out.println("Teste Testread...");
        Integer[] x = {1,2,3,4,5,6,7,8,9,10};
        PersistentArray temp = new PersistentArray(x,"array1");
        if(!(temp.getElement(stelle) == x[stelle])){
            System.out.println("Fehler bei Testread");
            throw new IOException();
        }
    }

    /**
     * Testet die setElement Funktion von PersistentArray
     *
     * @param index der Index an welchen geschrieben werden soll
     * @throws IOException
     */
    private void Testwrite(int index) throws IOException {
        Integer[] x = {1,2,3,4,5,6,7,8,9,10};
        System.out.println("Erstelle Array...");
        PersistentArray temp = new PersistentArray(x,"array1");
        temp.setElement(index,7);
        System.out.println();
        if( temp.getElement(index) != 7){
            System.out.println("Fehler bei Testwrite");
            throw new IOException();
        }
    }

    /**
     * Testet die getNumberOfEntries Funktion von PersistentArray
     *
     * @throws IOException
     */
    private void Testlength() throws IOException {
        Integer[] x = {1,2,3,4,5,6,7,8,9,10};
        System.out.println("Erstelle Array...");
        PersistentArray temp = new PersistentArray(x,"array1");
        System.out.println("Laenge des arrays: " + temp.getNumberofEntries());
        if(temp.getNumberofEntries() != x.length){
            System.out.println("Fehler bei Testlength");
            throw new IOException();
        }
    }


    /**
     * Testet ob file array1 auch per namen gefunden werden kann
     */
    private void Testarray1() {
        try {
            PersistentArray a = new PersistentArray("array1");
        } catch (FileNotFoundException e) {
            System.out.println("Array 1 wurde nicht erkannt");
        }
    }

    /**
     * Testet ob file array2 gefunden werden kann
     */
    private void Testarray2(){
        try {
            PersistentArray a = new PersistentArray("array2");
        } catch (FileNotFoundException e) {
            System.out.println("Exception gefangen. Test erfolgreich");
        }
    }

    /**
     * Testet die closeArray Methode von PersistentArray
     *
     * @throws IOException wenn der Stream geschlossen wurde
     */
    private void Testclosing()  throws IOException {
        System.out.println("Teste Testread...");
        Integer[] x = {1,2,3,4,5,6,7,8,9,10};
        PersistentArray temp = new PersistentArray(x,"array1");
        temp.closeArray();
        temp.setElement(2,5);
    }
}
