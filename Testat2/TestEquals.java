/**
 * Testet die equals()- und hashCode()-Methoden der bereitgestellten Klassen Student  und Person.
 *
 * -- Was tut die Klasse --
 *
 * Folgende Elemente des equals()-Kontraktes werden überprüft:
 *  - Reflexivität
 *  - Transitivität
 *  - Symmetrie
 *  - equals(null) == false
 *
 * Nicht überprüft wird Konstistenz, da die gegebenen Klassen keine Setter exponieren.
 *
 * Folgende Elemente des hashCode()-Kontraktes werden überprüft:
 *  - Objekte, für die equals() true ergibt, geben den gleichen hashCode zurück.
 *
 * Nicht überprüft wird Konsistenz (selber Grund).
 *
 * -- Ergebnisse --
 * Man stellt fest, dass die Testklasse nur Probleme mit der Symmetrie feststellt,
 * was daran liegt, dass Probleme auftauchen, wenn man eine Instanz von Person (und nicht Student)
 * mit einer Instanz von Student vergleicht. In der einen Richtung wird equals() dynamisch gebunden
 * und zu Person.equals() aufgelöst, in der anderen Richtung zu Student.equals(), wobei Person.equals()
 * nicht darauf Rücksicht nimmt, dass das übergebene Objekt Instanz einer Subklasse sein könnte.
 * Möglicher Lösungsweg: in jeder Klasse eine Methode einbauen, die den Namen der Klasse zurückgibt,
 * sodass die equals()-Methode direkt überprüfen kann, ob der beschriebene Fall eintritt.
 *
 * Aus dem selben Grund scheitert auch der hashCode()-Kontrakt, wenn Person.equals() mit einer Student-
 * Instanz aufgerufen wird, kann true zurückgegeben werden, aber die hashCodes werden sich unterscheiden.
 * Lösungsweg: Wie oben.
 *
 * @author Philipp Weinbrenner
 * @version 2022-04-21
 */

class TestEquals {
  private Person p1;
  private Person p2;
  private Person p3;
  private Student s1;
  private Student s2;
  private Student s3;
  private Person[] instances;
  private String[] instanceNames;

  private void initDemoObjects () {
    p1 = new Person("Hans im Glück");
    p2 = new Person("Maria Mustermann");
    p3 = new Person("Hans im Glück");
    s1 = new Student("Hans im Glück", 1234);
    s2 = new Student("Maria Mustermann", 987654);
    s3 = new Student("Hans im Glück", 1342);
    instances = new Person[]{p1, p2, p3, s1, s2, s3};
    instanceNames = new String[]{"p1", "p2", "p3", "s1", "s2", "s3"};
  }

  private void testEqualsReflexive () {
    initDemoObjects();
    printTestHeader("'equals' soll reflexiv sein");
    expectEqual(p1, p1, "p1, p1");
    expectEqual(p2, p2, "p2, p2");
    expectEqual(p3, p3, "p3, p3");
    expectEqual(s1, s1, "s1, s1");
    expectEqual(s2, s2, "s2, s2");
    expectEqual(s3, s3, "s3, s3");
  }

  private void testEqualsSymmetric () {
    initDemoObjects();
    printTestHeader("'equals' soll symmetrisch sein");

    /* Alle Paare unter den Testobjekten durchgehen */
    for (int i = 0; i < instances.length; i++) {
      for (int j = i; j < instances.length; j++) {
        Person a = instances[i];
        Person b = instances[j];
        if (a.equals(b)) {
          expectEqual(b, a, instanceNames[j] + ", " + instanceNames[i]);
        } else {
          expectNotEqual(b, a, instanceNames[j] + ", " + instanceNames[i]);
        }
      }
    }
  }

  private void testEqualsTransitive () {
    initDemoObjects();
    printTestHeader("'equals' soll transitiv sein");

    /* Wir gehen alle Objekte a durch, und suchen ein Objekt b, dass für gleich gehalten wird,
     * und dann ein weiteres c, dass für gleich zu b gehalten wird, und überprüfen, dass a gleich
     * für c gehalten wird. */
    for (int i = 0; i < instances.length; i++) {
      Person a = instances[i];
      for (int j = i; j < instances.length; j++) {
        Person b = instances[j];
        if (!a.equals(b))
          continue;
        for (int k = j; k < instances.length; k++) {
          Person c = instances[k];
          if (!b.equals(c))
            continue;
          expectEqual(a, c, "Wir haben " + instanceNames[i] + " === " + instanceNames[j] + " und " + instanceNames[j] + " === " + instanceNames[k] + ", Test auf Transitivität");
        }
      }
    }
  }

  private void testEqualsNull () {
    initDemoObjects();
    printTestHeader("'equals(null)' soll false ergeben");
    for (int i = 0; i < instances.length; i++) {
      expectNotEqual(instances[i], null, instanceNames[i]);
    }
  }

  private void testHashCode () {
    initDemoObjects();
    printTestHeader("Es soll gelten: wenn a.equals(b) für a != null, dann hashCode(a) == hashCode(b)");

    /* Alle Instanzpaare finden, die gleich gelten, in beiden Richtungen */
    for (int i = 0; i < instances.length; i++) {
      Person a = instances[i];
      for (int j = 0; j < instances.length; j++) {
        Person b = instances[j];
        if (!a.equals(b))
          continue;

        expectHashCodeMatch(a, b, instanceNames[i] + ", " + instanceNames[j]);
      }
    }
  }

  private void printTestHeader(String header) {
    System.out.println();
    System.out.println(header);
    System.out.println("==========");
  }

  private boolean expectNotEqual(Person p1, Person p2, String comment) {
    System.out.print(comment);
    System.out.print(" ... ");
    boolean result = expectNotEqual(p1, p2);
    if (result == true)
      System.out.println("Okay");
    return result;
  }

  private boolean expectEqual(Person p1, Person p2, String comment) {
    System.out.print(comment);
    System.out.print(" ... ");
    boolean result = expectEqual(p1, p2);
    if (result == true)
      System.out.println("Okay");
    return result;
  }

  private boolean expectEqual(Person p1, Person p2) {
    if (!p1.equals(p2)) {
      System.out.println("Test gescheitert, '" + p1.getName() + "' und '" + p2.getName() + "' sollten als gleich betrachtet werden");
      return false;
    }
    return true;
  }

  private boolean expectNotEqual(Person p1, Person p2) {
    if (p1.equals(p2)) {
      System.out.println("Test gescheitert, '" + p1.getName() + "' und '" + p2.getName() + "' sollten als ungleich betrachtet werden");
      return false;
    }
    return true;
  }

  private boolean expectHashCodeMatch(Person p1, Person p2, String comment) {
    System.out.print(comment);
    System.out.print(" ... ");
    boolean result = expectHashCodeMatch(p1, p2);
    if (result == true)
      System.out.println("Okay");
    return result;
  }

  private boolean expectHashCodeMatch(Person p1, Person p2) {
    if (p1.hashCode() != p2.hashCode()) {
      System.out.println("Test gescheitert, '" + p1.getName() + "' und '" + p2.getName() + "' sollten den gleichen HashCode zurück geben");
      return false;
    }
    return true;
  }

  public TestEquals() {
  }

  public void performTests() {
    testEqualsReflexive();
    testEqualsSymmetric();
    testEqualsTransitive();
    testEqualsNull();
    testHashCode();
  }

  public static void main(String[] args) {
    TestEquals t = new TestEquals();
    System.out.println("Beginne mit der Durchführung der Tests ... ");
    t.performTests();
  }
}
