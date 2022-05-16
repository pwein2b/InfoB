/**
 * Klasse MyList welche mit generischen Typen arbeitet
 * Diese Klasse arbeitet mithilfe der Klasse MyEntry, welche einzelne, generische Listenelemente darstellt.
 *
 * @author Markus Kuehn
 */

package Task1and2;

public class MyList <T> implements Cloneable{

    /**
     * Reference on the first Entry of this List
     */
    private MyEntry<T> begin;

    /**
     * Reference before the actual Entry of this List
     */
    private MyEntry<T> pos;


    /**
     * Konstruktor fuer MyList
     * setzt einen neuen entry als Startposition
     */
    public MyList(){
        this.pos = begin = new MyEntry<T>();
    }


    /**
     *  Returns the actual element of this List.
     *
     * @return the actual element
     * @throws RuntimeException
     * if the last Entry of this List already has been reached.
     *
     * The return statement has to be casted to T for this to work
     */
    public T elem() {
        if (endpos()) {
            throw new RuntimeException("Already at the end of this List");
        }
        return (T)pos.next.o;
    }

    /**
     * Inserts <code>o</code> in this List. It will be placed before the actual
     * element. After insertion the inserted element will become the actual
     * element.
     *
     * @param x
     *           the element to be inserted
     */
    public void add(T x) {
        MyEntry<T> newone = new MyEntry<T>(x, pos.next);

        pos.next = newone;
    }

    /**
     * Diese Methode ueberschreibt die in clone() Methode von Object
     * Wir koennen einfach super.clone aufrufen, da diese beriets in Object Implementiert ist
     * Fuer genaue funktionsweise der clone() Methode siehe Java API von Object
     *
     * @return ein klon der derzeitigen Liste
     */
    public MyList<T> clone(){
        try{

            MyList<T> geklonteListe = (MyList<T>)super.clone();
            geklonteListe.begin = this.begin.clone();
            geklonteListe.pos = geklonteListe.begin;
            return geklonteListe;

        } catch(CloneNotSupportedException a){
            System.out.println("Dieses Objekt kann nicht geklont werden.");
            System.exit(-1);
            //Wird zum kompilieren benoetigt
            return null;
        }

    }

    //The Methods below this line have not been altered from the original List Methods

    /**
     * Deletes the actual element of this List. The element after the actual
     * element will become the new actual element.
     *
     * @throws RuntimeException
     *            if the last Entry of this List already has been reached.
     */
    public void delete() {
        if (endpos()) {
            throw new RuntimeException("Already at the end of this List");
        }
        pos.next = pos.next.next;
    }

    /**
     * Determines if this List is empty or not.
     *
     * @return <code>true</code>, if there are no elements in this List
     */
    public boolean empty() {
        return begin.next == null;
    }

    /**
     * Determines if it is possible to {@link #advance()} in this List. Returns
     * <code>true</code> if the last position of this List has been reached. An
     * {@link #empty()} List will alway deliver <code>true</code>
     *
     * @return <code>true</code> if the last Entry in this List already has been
     *         reached.
     */
    public boolean endpos() { // true, wenn am Ende
        return pos.next == null;
    }

    /**
     * Returns to the beginning of this List.
     */
    public void reset() {
        pos = begin;
    }

    /**
     * Advances one step in this List.
     *
     * @throws RuntimeException
     *            if the last Entry of this List already has been reached.
     */
    public void advance() {
        if (endpos()) {
            throw new RuntimeException("Already at the end of this List");
        }
        pos = pos.next;
    }

}
