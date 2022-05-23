/**
 * Klasse, welche ein Persistentes Array erzeugen kann
 *
 * @author Markus Kuehn
 */

package Task5and6;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;

public class PersistentArray {

    private RandomAccessFile rand;

    private File file;

    /**
     * Konstruktor fuer PersistentArray
     * Benoetigt ein Integer Array und einen namen fuer das Array
     *
     * @param array das Integer Array
     * @param name der Name der dem Integer Array zugewiesen wird
     */
    public PersistentArray(Integer[] array, String name) throws IOException{
        file = new File(name);
        if (!file.exists()){
            file.createNewFile();
        }

        rand = new RandomAccessFile(file, "rw");

        //writing the array via RandomAccessFile
        rand.setLength(0);
        for(int i = 0; i< array.length; i++){
            rand.writeInt(array[i]);
        }

    }

    /**
     * Zweiter Konstruktor fuer PersistentArray
     * Sucht nach einem File mit dem uebergebenen Namen
     *
     * @param name der Name der Datei
     * @throws FileNotFoundException wenn keine Datei mit gleichem Namen gefunden wurde
     */
    public PersistentArray(String name) throws FileNotFoundException{
        file = new File(name);
        if (!file.exists()){
            System.out.println("Es existiert kein File unter dem namen: " + name);
            throw new FileNotFoundException("Error");
        }
        rand = new RandomAccessFile(file, "rw");
    }

    /**
     * Der getter fuer ein Element in unserem Integer Array
     * seek() setzt den file-pointer offset in bytes, deswegen muessen wir i mit 4 multiplizieren
     *
     * @param i
     * @return den Integer welcher an der gefragten Stelle steht
     * @throws IOException
     */
    public Integer getElement(int i) throws IOException {
        rand.seek(i * 4);
        Integer a = rand.readInt();
        return a;

    }

    /**
     *Schreibt ein Integer an die gewuenschte Stelle
     *
     * @param i dei Stelle an welche geschrieben werden soll
     * @param content der Integer welcher in das Array geschrieben werden soll
     * @throws IOException
     */
    public void setElement(int i, Integer content) throws IOException {
        rand.seek(i * 4);
        rand.writeInt(content);
    }

    /**
     * die length() Methode von File gibt die laenge in long zurueck
     * deswegen muessen wir diese noch durch 4 teilen
     *
     * @return
     */
    public int getNumberofEntries() throws IOException {
        long number = rand.length();
        int r = (int) (number / 4);
        return r;
    }

    public void closeArray() throws IOException {
        this.rand.close();
    }
}
