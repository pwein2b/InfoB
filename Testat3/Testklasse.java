import java.util.ListIterator;

/**
 * Testklasse fuer die Funktionen von Library
 * Die einzelnen Tests sind in Methoden ausgelagert
 *
 * @author Markus Kuehn
 */
public class Testklasse {

    public static void main(String[] args){
        Testklasse Test = new Testklasse();
        Test.TestSearch();
        Test.TestAdd();
        Test.TestDelete();


    }

    /**
     *Funktion, welche die search() Methode von Library testet
     */
    private void TestSearch() {
        System.out.println("Teste addItem Funktion");
        Library lib = new Library();
        BluRay blu1 = new BluRay("Interstellar ","Christopher Nolan");
        Book book1 = new Book("1984 ","George Orwell" );
        lib.addItem(blu1);
        lib.addItem(book1);
        System.out.println("Teste suche nach einem spezifischen Element");
        List tempList = lib.search("Name des Buches:1984 Autor:George Orwell");
        tempList.reset();
        while(!tempList.endpos()){
            LibraryItem a = (LibraryItem)tempList.elem();
            if(!a.getDescription().equals("Name des Buches:1984 Autor:George Orwell")){
                System.out.println("Fehler beim suchen eines spezifischen elements");
            }
            tempList.advance();
        }
        System.out.println("Test abgeschlossen");
        //Test fuer mehrere Elemente beginnt hier
        System.out.println("Teste suche nach mehreren Elementen");
        tempList = lib.search("l");
        tempList.reset();
        while(!tempList.endpos()){
            LibraryItem a = (LibraryItem)tempList.elem();
            if(!a.getDescription().contains("l")){
                System.out.println("Fehler beim suchen eines spezifischen elements");
            }
            tempList.advance();
        }
        System.out.println("Test abgeschlossen");
    }


    /**
     * Funktion, welche die AddItem() Methode von Library testet
     *
     */
    private void TestAdd() {
        System.out.println("Teste addItem Funktion");
        Library lib = new Library();
        List tempList = lib.search("Interstellar");
        tempList.reset();
        try {
            System.out.println(tempList.elem());
        } catch (RuntimeException a){
            System.out.println("Liste ist leer, exception abgefangen");
        }
        System.out.println("Fuege ein Element hinzu");
        BluRay blu1 = new BluRay("Interstellar ","Christopher Nolan");
        lib.addItem(blu1);
        tempList = lib.search("Interstellar");
        tempList.reset();
        if(tempList.elem() == null){
            System.out.println("Fehler beim Hinzufuegen eines Elements");
        } else{
            System.out.println("Keine Fehler entdeckt");
        }
    }

    /**
     * Funktion, welche die deleteItem() Methode von Library testet
     * Fuegt zwei LibraryItems in die
     *
     *
     */
    private void TestDelete(){

        System.out.println("Teste DeleteItem Funktion");
        System.out.println("Erstelle neue Library...");
        Library lib = new Library();
        BluRay blu1 = new BluRay("Interstellar ","Christopher Nolan");
        Book book1 = new Book("1984 ","George Orwell" );
        lib.addItem(blu1);
        lib.addItem(book1);
        System.out.println("Added " + blu1.getDescription() + " and: " + book1.getDescription());
        System.out.println("Deleting 1984...");
        lib.deleteItem(book1);
        List tempList = lib.search("1984");
        tempList.reset();
        while(!tempList.endpos()){
            LibraryItem a = (LibraryItem)tempList.elem();
            if(a.getDescription().equals("Name des Buches:1984 Autor:George Orwell")){
                System.out.println("Fehler bei der Ueberpruefung des Loesch-testes");
            }
            tempList.advance();
        }
        System.out.println("Tests abgeschlossen");
    }
}
