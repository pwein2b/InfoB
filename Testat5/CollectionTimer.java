/**
 * Eine Testklasse, die die Laufzeiten von add(), remove() und contains() auf Implementationen von Collection testet.
 * Standardmäßig beim Aufruf dieser Klasse wird LinkedList, ArrayList und HashSet getestet.
 * Die Parametrisierung wird zur Einfachheit mit Integer gemacht.
 *
 * Erwartete Ergebnisse:
 *  - LinkedList sollte Zugriff, Einfügen und Löschen in O(n) schaffen.
 *  - ArrayList speichert Elemente ähnlich wie Vector in C++ und sollte daher Zugriffe in O(1) schaffen.
 *    Einfügen ist vermutlich O(n), da ggf. alles umkopiert werden muss.
 *    Contains() benötigt vermutlich O(n), da alles durchgegangen werden muss.
 *    Löschen ist vermutlich O(n), da erst das zu löschende Element gefunden werden muss, und dann alle folgenden umkopiert werden müssen.
 *  - HashSet sollte für große n die getesteten Operationen in O(n) ausführen können, für kleine n in O(1).
 *
 * @author Philipp Weinbrenner
 * @version 2022-05-14
 */

import java.util.Collection;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;

class CollectionTimer<T extends Collection<Integer>> {
  private T collection;

  /**
   * Erstelle einen neuen CollectionTimer für eine Collection-Instanz.
   */
  public CollectionTimer(T collection) {
    this.collection = collection;
  }

  /**
   * Füllt die Collection-Instanz mit n Demo-Objekten.
   * Die Instanz wird vorher nicht ge-cleared.
   * Die Demo-Objekte sind Integer-Instanzen, die die natürlichen Zahlen von 0 bis n darstellen.
   * @param n Anzahl der Demo-Objekte
   */
  private void fillCollection(int n) {
    for (int i = 0; i < n; i++) {
      collection.add(Integer.valueOf(i));
    }
  }

  /**
   * Messen der Zeit, die zum Einfügen von n Elementen benötigt wird.
   * Der Rückgabetyp ist die verstrichene Zeit in Nanosekunden.
   * Man beachte, dass das in Abhängigkeit von Hardware und Betriebssystem
   * nicht notwendigerweise auf Nanosekunden genau ist.
   * Es wird der Mittelwert über 5 Durchgänge gebildet.
   * @param n Anzahl der einzufügenden Elemente
   * @return Durchschnittlich benötigte Zeit in Nanosekunden
   */
  public long timeAdd(int n) {
    long[] time_values = new long[5];

    for (int iteration = 0; iteration < 5; iteration++) {
      collection.clear();

      long start_time = System.nanoTime();
      fillCollection(n);
      time_values[iteration] = System.nanoTime() - start_time;
    }

    long average = 0;
    for (int i = 0; i < 5; i++)
      average += time_values[i];
    average = average / 5;
    return average;
  }

  /**
   * Messen der Zeit, die zum Löschen von n Elementen benötigt wird.
   * Der Rückgabetyp ist die verstrichene Zeit in Nanosekunden.
   * Man beachte, dass das in Abhängigkeit von Hardware und Betriebssystem
   * nicht notwendigerweise auf Nanosekunden genau ist.
   * Es wird der Mittelwert über 5 Durchgänge gebildet.
   * @param n Anzahl der einzufügenden und wieder zu löschenden Elemente Elemente
   * @return Durchschnittlich benötigte Zeit in Nanosekunden
   */
  public long timeRemove(int n) {
    long[] time_values = new long[5];

    for (int iteration = 0; iteration < 5; iteration++) {
      collection.clear();

      fillCollection(n);

      long start_time = System.nanoTime();
      for (int i = 0; i < n; i++) {
        Integer valueToRemove = Integer.valueOf(i);
        collection.remove(valueToRemove);
        // Das zu entfernende Element wird per equals() gesucht, daher funktioniert das
      }
      time_values[iteration] = System.nanoTime() - start_time;
    }

    long average = 0;
    for (int i = 0; i < 5; i++)
      average += time_values[i];
    average = average / 5;
    return average;
  }

  /**
   * Messen der Zeit, die zum Testen benötigt wird, ob ein bestimmtes Element in der Collection vorliegt oder nicht.
   * Der Rückgabetyp ist die verstrichene Zeit in Nanosekunden.
   * Man beachte, dass das in Abhängigkeit von Hardware und Betriebssystem
   * nicht notwendigerweise auf Nanosekunden genau ist.
   *
   * Es werden n Elemente eingefügt und 2*m Tests mit contains() durchgeführt,
   * nämlich m Mal mit Elementen, die nicht in der Collection vorliegen,
   * und m Mal mit solchen, die vorliegen.
   *
   * Es wird der Mittelwert über 5 Durchgänge gebildet.
   *
   * @param n Anzahl der einzufügenden Elemente
   * @param m Hälfte der Anzahl der auszuführenden contains()-Aufrufe
   * @return Durchschnittlich benötigte Zeit in Nanosekunden
   */
  public long timeContains(int n, int m) {
    long[] time_values = new long[5];

    for (int iteration = 0; iteration < 5; iteration++) {
      collection.clear();

      fillCollection(n);

      long start_time = System.nanoTime();
      for (int i = 0; i < m; i++) {
        Integer valueNotPresent = Integer.valueOf(-1 - (i % n));
        Integer valuePresent = Integer.valueOf(i % n);
        collection.contains(valueNotPresent);
        collection.contains(valuePresent);
      }
      time_values[iteration] = System.nanoTime() - start_time;
    }

    long average = 0;
    for (int i = 0; i < 5; i++)
      average += time_values[i];
    average = average / 5;
    return average;
  }

  /**
   * Erzeuge einen String, der eine Ganzzahl mit Trennern zur Lesbarkeit darstellt: Dreiergruppen von Ziffern werden mit einfachen Anführungszeichen abgetrennt.
   */
  public static String decimalFormat(long value) {
    StringBuilder sb = new StringBuilder();
    while(value != 0) {
      long triplet = value % 1000;
      value = (value - triplet) / 1000;
      sb.insert(0, triplet);
      sb.insert(0, "000", 0, 3 - Long.toString(triplet).length());
      if (value != 0)
        sb.insert(0, "'");
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    CollectionTimer[] collections = new CollectionTimer[] {
      new CollectionTimer<LinkedList<Integer>> (new LinkedList<Integer>()),
      new CollectionTimer<ArrayList<Integer>> (new ArrayList<Integer>()),
      new CollectionTimer<HashSet<Integer>> (new HashSet<Integer>())
    };
    String[] collectionNames = new String[] {
      "LinkedList",
      "ArrayList",
      "HashSet"
    };

    /* Tabellenkopf */
    System.out.print(" | # Elemente | ");
    for (int i = 0; i < collections.length; i++)
      System.out.print(collectionNames[i] + " | ");
    System.out.println();

    /* -- Timer für ADD -- */
    System.out.println();
    System.out.println("Zeitmessung: add() - Zeit in ns");

    /* Iteration über Anzahl der Elemente */
    for(int exponent = 2; exponent < 7; exponent++) {
      int n = (int)Math.pow(10, exponent);
      System.out.print(" | 10^ " + exponent);

      for(int i = 0; i < collections.length; i++) {
        long result_ns = collections[i].timeAdd(n);
        System.out.print(" | " + decimalFormat(result_ns));
      }
      System.out.println(" | ");
    }

    /* -- Timer für REMOVE -- */
    System.out.println();
    System.out.println("Zeitmessung: remove() - Zeit in ns");

    /* Iteration über Anzahl der Elemente */
    for(int exponent = 2; exponent < 7; exponent++) {
      int n = (int)Math.pow(10, exponent);
      System.out.print(" | 10^ " + exponent);

      for(int i = 0; i < collections.length; i++) {
        long result_ns = collections[i].timeRemove(n);
        System.out.print(" | " + decimalFormat(result_ns));
      }
      System.out.println(" |");
    }

    /* -- Timer für CONTAINS -- */
    System.out.println();
    System.out.println("Zeitmessung: contains() - Zeit in ns");

    /* Iteration über Anzahl der Elemente */
    for(int exponent = 2; exponent < 7; exponent++) {
      int n = (int)Math.pow(10, exponent);
      int m = (int)Math.pow(10, exponent - 2);
      System.out.print(" | 10^" + exponent + " (10^" + (exponent-2) + " Tests)");

      for(int i = 0; i < collections.length; i++) {
        long result_ns = collections[i].timeContains(n, m);
        System.out.print(" | " + decimalFormat(result_ns));
      }
      System.out.println(" | ");
    }
  }
}
