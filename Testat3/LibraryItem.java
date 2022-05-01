/**
 * Diese abstrakte Klasse implementiert die in Aufgabe 3.3 gefragten Methoden
 * Ausserdem wird die abstrakte Methode getDescription definiert, welche in BluRay und Book jeweils implementiert sind
 *
 * @author Markus Kuehn
 */
public abstract class LibraryItem {

    private boolean isBorrowed;

    /**
     * Konstruktor von LibraryItem
     */
    public LibraryItem(){
        isBorrowed = false;
    }

    /**
     * Getter fuer die isBorrowed Variable
     *
     * @return den derzeitigen Status von isBorrowed
     */
    public boolean isBorrowed(){
        return isBorrowed;
    }

    /**
     * Setzt isBorrowed auf den uebergebenen Wert
     *
     * @param isBorrowed Wert auf welchen isBorrowed gesetzt wird
     */
    public void setBorrowed( boolean isBorrowed){
        this.isBorrowed = isBorrowed;
    }

    /**
     * Diese Methode ist abstract, ihre genaue Implementation ist in BluRay oder Book gegeben
     *
     * @return einen String, genaue Implementation ist hier noch nicht gegeben
     */
    public abstract String getDescription();
}
