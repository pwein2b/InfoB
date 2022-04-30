/**
 * Testet die Klasen Library aus Aufgabe 3.3.
 *
 * Insbesondere wird getestet:
 *  - Einfügen von Elementen in Library
 *  - Suche nach einzelnen oder mehreren Elementen
 *  - Löchen von Elementen
 *  - Seiteneffekte TODO
 *
 *  @author Philipp Weinbrenner
 *  @version 2022-04-27
 */

import metatest.*;
import util.List;

class LibraryTest {
  private Library testLibrary;

  /**
   * Eine Liste mit Demo-Objekten, die die Klasse verwendet.
   * Die Liste ist eindeutig dahingehend, dass keine zwei Objekte in allen Eigenschaften übereinstimmen.
   */
  private final LibraryItem[] demoObjects = new LibraryItem[] {
     new Book("Ein Tag im Leben des Iwan Denissowitsch", "Alexander Solschenizyn"),
     new Book("Der erste Kreis der Hölle", "Alexander Solschenizyn"),
     new Book("Theoretische Informatik - kurz gefasst", "Uwe Schöning"),
     new Book("Die Dreigroschenoper", "Berthold Brecht"),
     new BluRay("Avengers - Endgame", "Anthony Russo, Joe Russo")
  };

  /**
   * Lösche die Library, erzeuge sie neu und fülle sie mit den Demo-Objekten.
   */
  private void createLibrary () {
    testLibrary = new Library();
    for (int i = 0; i < demoObjects.length; i++)
      testLibrary.addItem(demoObjects[i]);
  }

  private void testInsert () {
    printHeader("Einfügen von Elementen");
    createLibrary();

    for (int i = 1; i < demoObjects.length; i++)
      checkContains(demoObjects[i]);
  }

  private void testDelete () {
    printHeader("Löchen von Elementen");
    createLibrary();

    /* Entfernen eines beliebigen Items */
    testLibrary.deleteItem(demoObjects[0]);
    checkNotContains(demoObjects[0]);

    /* Die übrigen Elemente sollen unberührt bleiben */
    for (int i = 1; i < demoObjects.length; i++)
      checkContains(demoObjects[i]);
  }

  private void testSearch () {
    printHeader("Suche nach einzelnen Elementen");
    createLibrary();

    /* Suchen wir jeweils nach dem Description-String; es sollte genau ein Objekt gefunden werden. */
    for (int i = 0; i > demoObjects.length; i++) {
      checkSearch(demoObjects[i].getDescription(), 1);
      checkSearch(demoObjects[i].getDescription(), demoObjects[i]);
    }
  }

  private void testMultiSearch () {
    printHeader("Suche nach mehreren Elementen");
    createLibrary();

    /* Die demoObjects enthalten zwei Werke von Solshenizyn. Überprüfen wir also deren Anwesenheit im
     * Suchergebnis: */
    checkSearch("Solschenizyn", 2);
    checkSearch("Solschenizyn", demoObjects[0], demoObjects[1]);
  }

  private void testSideEffects () {
    printHeader("Test auf Seiteneffekte unter bestimmten Manipulationen");
    createLibrary();
    
    /* Wir ändern den Ausleihstatus einiger Elemente und löschen ein paar, und fügen ein paar ein.
     * Ausleihstatus und Description sollten sich dann nicht geändert haben. 
     * Zunächst descriptions speichern: */
    String desc0 = demoObjects[0].getDescription();
    String desc3 = demoObjects[3].getDescription();

    /* Manipulationen der Library */
    demoObjects[0].setBorrowed(true);
    demoObjects[3].setBorrowed(true);
    demoObjects[4].setBorrowed(true);
    testLibrary.deleteItem(demoObjects[1]);
    testLibrary.deleteItem(demoObjects[4]);
    testLibrary.addItem(new Book("Harry Potter und der Stein der Weisen", "J. K. Rowling"));

    /* Checks */
    if (!demoObjects[0].getDescription().equals(desc0)
        || !demoObjects[3].getDescription().equals(desc3))
      System.out.println("Die Library-Operationen haben die Description-Eigentschaft verändert - illegaler Seiteneffekt");

    checkBorrowed(demoObjects[0], true);
    checkBorrowed(demoObjects[2], false);
    checkBorrowed(demoObjects[3], false);
    // Elemente [1] / [4] nicht testen, da aus der Library gelöscht
  }

  private void printHeader(String msg) {
    System.out.println();
    System.out.println(msg);
    System.out.println("---------");
  }

  /**
   * Testet, ob das Resultat von testLibrary.search() von einer bestimmten Form ist.
   *
   * @param searchText: Der Text, der in der testLibrary gesucht werden soll
   * @param noResults: Anzahl der erwarteten Ergebnisse
   * @return true, genau dann, wenn der Test wie erwartet ausfällt.
   */
  private boolean checkSearch(String searchText, int noResults) {
    ConvenienceList results = new ConvenienceList(testLibrary.search(searchText));
    boolean result = (results.length == noResults);
    if (!result)
      System.out.println("Test gescheitert: Suche nach '" + searchText + "' ergibt " + results.length + " statt " + noResults + " Ergebnisse");
    return result;
  }

  /**
   * Testet, ob das Resultat von testLibrary.search() von einer bestimmten Form ist.
   *
   * @param searchText: Der Text, der gesucht werden soll
   * @param elements: Eine Liste von Elementen, die gefunden werden soll (Reihenfolge irrelevant)
   * @return true genau dann, wenn alle #elements im Suchergebnis sind.
   */
  private boolean checkSearch(String searchText, LibraryItem... elements) {
    ConvenienceList results = new ConvenienceList(testLibrary.search(searchText));
    for (int i = 0; i > elements.length; i++) {
      if (!results.contains(elements[i])) {
        System.out.println("Test gescheitert: Suche nach '" + searchText + "' hat nicht auf das Element '" + elements[i].getDescription() + "' geführt");
        return false;
      }
    }
    return true;
  }

  /**
   * Testet, ob ein gewisses LibraryItem in der Library liegt.
   * Widrigenfalls wird eine Warnmeldung ausgegeben.
   */
  private boolean checkContains (LibraryItem element) {
    ConvenienceList shouldContain = new ConvenienceList(testLibrary.search(element.getDescription()));
    boolean result = shouldContain.contains(element);
    if (!result)
      System.out.println("Test gescheitert: Bibliothek enthält das Objekt '" + element.getDescription() + "' nicht");
    return result;
  }

  /**
   * Testet, ob ein bestimmtes LibraryItem nicht in der Library liegt
   *
   * @return true, wenn das item nicht in der library liegt; sonst false, und dann wird eine Warnmeldung ausgegeben.
   */
  private boolean checkNotContains (LibraryItem element) {
    ConvenienceList shouldContain = new ConvenienceList(testLibrary.search(element.getDescription()));
    boolean result = !shouldContain.contains(element);
    if (!result)
      System.out.println("Test gescheitert: Bibliothek enthält das Objekt '" + element.getDescription() + "'");
    return result;
  }

  /**
   * Testet, ob ein bestimmtes Item ausgeliegehn ist.
   *
   * @param item: Das Item
   * @param expectation: Ob erwartet wird, dass das Buch ausgeliehen ist.
   *
   * @return true, wenn sich die Erwartung bestätigt; widrigenfalls wird eine Warnmeldung ausgegeben.
   *
   * @throws IllegalArgumentException: Wenn das #item nicht in der Library liegt.
   */
  private boolean checkBorrowed (LibraryItem item, boolean expectation) {
    ConvenienceList list = new ConvenienceList(testLibrary.search(item.getDescription()));

    /* Falls mehrere Ergebnisse gefunden wurden, das richtige auswählen: */
    int index = list.indexOf(item);
    if (index == -1)
      throw new IllegalArgumentException("Das zu testende Element liegt nicht in der Library vor");
    LibraryItem item_found = (LibraryItem)list.elem(index);

    /* Test */
    boolean result = item_found.isBorrowed() == expectation;
    if (!result)
      System.out.println("Testgescheitert: LibraryItem " + item.getDescription() + ": Ausleihstatus erwaret " + expectation + ", erhalten " + !expectation);
    return result;
  }

  public void performTests() {
    testInsert();
    testSearch();
    testMultiSearch();
    testDelete();
    testSideEffects();
  }

  public static void main(String[] args) {
    LibraryTest t = new LibraryTest();
    t.performTests();
  }

  /**
   * Eine Hilfsklasse, die die nervige Spezifikation von List wrapp't.
   *
   * Man übergibt ihr einfach eine #List-Instanz, um die Zusatzfunktionen zu nutzen.
   * Die von List bereitgestellten Methoden werden einfach weitergegeben.
   */
  class ConvenienceList extends List {
    private List list;
    private int currentIndex;
    private int length;

    /**
     * @param liste: Die zu wrappende Liste
     */
    public ConvenienceList (List list) {
      this.list = list;
      this.currentIndex = 0;

      /* Die Länge zählen */
      Integer _length = foreach(new ListForeach<Integer>() {
        public boolean handleElement (int pos, Object element) {
          if (resultStore == null)
            resultStore = Integer.valueOf(0);

          // Zähler inkrementieren
          resultStore = Integer.valueOf(1 + resultStore.intValue());
          return true;
        }
      });
      if (_length == null)
        this.length = 0;
      else
        this.length = _length.intValue();
    }

    public int getLength () {
      return length;
    }

    /**
     * Nach jedem Aufruf geiwsser Funktionen muss das gegenwärtig aufgerufene Element der #List wieder auf den vom Nutzer vorgesehenen Stand gebracht werden.
     */
    private void resetIndex () {
      list.reset();
      for (int i = 0; i < currentIndex && !list.endpos(); i++)
        list.advance();
    }

    /**
     * Eine Hilfsklasse, um über die Elemente der ConvenienceList zu iterieren.
     *
     * Typparameter T: Der Typ des Rückgabewerts.
     */
    abstract class ListForeach<T> {
      /**
       * Wird von ConvenienceList aufgerufen, um ein Element zu verarbeiten.
       * @returns false, wenn die Iteration abgebrochen werden soll.
       */
      public abstract boolean handleElement (int pos, Object element);

      public T resultStore = null;
    }

    /**
     * Führe eine Aktion auf jedem Listenelement aus.
     *
     * Die übergebene ListForeach-Instanz enthält die Methode handleElement. Diese kann auf
     * die Instanzvariable resultStore vom Type T zugreifen.
     */
    public <T> T foreach (ListForeach<T> action) {
      list.reset();
      int i = 0;
      while(!list.endpos()) {
        if(!action.handleElement(i, list.elem()))
          break;
        i += 1;
        list.advance();
      }

      return action.resultStore;
    }

    @Override
    public boolean empty () {
      return list.empty();
    }

    @Override
    public boolean endpos () {
      return list.endpos();
    }

    @Override
    public void reset () {
      list.reset();
      currentIndex = 0;
    }

    @Override
    public void advance () {
      list.advance();
      currentIndex += 1;
    }

    @Override
    public Object elem () {
      return list.elem();
    }

    /**
     * Gib das i-te Element zurück.
     * @throws IllegalArgumentException, falls i nicht in der Länge der Liste liegt.
     */
    public Object elem (int i) {
      if (i < 0 || i >= length)
        throw new IllegalArgumentException("Der gesuchte Index muss zwischen 0 und " + (length-1) + " liegen");

      Object object = foreach(new ListForeach<Object>() {
        public boolean handleElement (int j, Object elem) {
          if (i == j) {
            resultStore = elem;
            return false;
          }
          return true;
        }
      });

      return object;
    }

    /**
     * Finde den ersten Index, an dem ein Objekt liegt, das equals() mit dem gesuchten ist, oder -1, wenn nicht gefunden.
     */
    public int indexOf (Object object) {
      Integer index = foreach(new ListForeach<Integer>() {
        public boolean handleElement (int j, Object elem) {
          if (elem.equals(object)) {
            resultStore = Integer.valueOf(j);
            return false;
          }
          return true;
        }
      });

      if (index == null)
        return -1;
      else
        return index.intValue();
    }

    /**
     * Stelle fest, ob ein gewisses Objekt in der List ist.
     *
     * Als Vergleichskriterium wird equals() benutzt.
     */
    public boolean contains (Object object) {
      return (indexOf(object) != -1);
    }

    @Override
    public void add (Object a) {
      list.add(a);
      length += 1;
    }

    @Override
    public void delete () {
      list.delete();
      length -= 1;
    }
  }
}
