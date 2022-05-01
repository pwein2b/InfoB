public class Library {

    private List storage;

    /**
     * Konstruktor von Library
     * Erstellt eine neue Liste, in welcher Objekte gespeichert werden koennen
     */
    public Library(){
        storage = new List();
    }

    /**
     * Fuegt der Library das uebergebene LibraryItem hinzu
     *
     * @param item das Item, welches hinzugefuegt wird
     */
    public void addItem(LibraryItem item){
        this.storage.add(item);
    }

    /**
     * Loescht das uebergebene Element aus der Library
     *
     * @param item das Item welches geloescht werden soll
     */
    public void deleteItem(LibraryItem item){
        storage.reset();
        while (storage.endpos() != true){
            if( storage.elem().equals(item)){
                storage.delete();
            } else{
                storage.advance();
            }
        }
    }

    /**
     * Sucht in der hinter der Library liegenden Liste nach Elementen mit der (zumindest teilweise) gleichen Beschreibung
     * Gibt anschliessend eine Liste mit allen uebereinstimmenden Elementen zurück
     *
     * @param text der text nach welchem gesucht werden soll
     * @return eine Liste mit allen Uebereinstimmungen
     */
    public List search(String text){
        List a = new List();
        storage.reset();
        while(storage.endpos()!= true){
            String temp = ((LibraryItem)storage.elem()).getDescription();
            if(temp.contains(text)) {
                a.add(storage.elem());
            }
            storage.advance();
        }
        return a;
    }
}
