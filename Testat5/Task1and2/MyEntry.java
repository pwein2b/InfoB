/**
 * Generic Entry for Task
 *Da diese Klasse das Interface Clonable implementiert wird keine exception geworfen, wenn clone() aufgerufen wird
 *
 * @author Markus Kuehn
 */
package Task1and2;

public class MyEntry<T> implements Cloneable{

    protected T o;

    protected MyEntry<T> next;


    /**
     * Verkettete Konstruktoren
     * Setzen jeweils Inhalt des Entrys und setzen die next Variable auf den nächsten eintrag (soweit vorhanden)
     */
    public MyEntry(){
        this(null,null);
    }

    public MyEntry(T a){
        this(a,null);
    }

    public MyEntry(T a, MyEntry<T> b){
        this.o = a;
        this.next = b;
    }

    /**
     * Diese Methode ueberschreibt die in clone() Methode von Object
     * Wir koennen einfach super.clone aufrufen, da diese beriets in Object Implementiert ist
     * Fuer genaue funktionsweise der clone() Methode siehe Java API von Object
     *
     */
    public MyEntry<T> clone(){
        MyEntry<T> a;
        try{

            a = (MyEntry<T>) super.clone();
            if(next != null){
                a.next = next.clone();
            }
            return a;
        } catch (CloneNotSupportedException b){
            System.out.println("Dieses Objekt kann nicht geklont werden.");
            System.exit(-1);
            //Wird zum kompilieren benoetigt
            return null;
        }

    }
}
