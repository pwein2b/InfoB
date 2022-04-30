/**
 * Testklasse für die Geometry-Klassen.
 *
 * @author Philipp Weinbrenner
 * @version 2022-04-30
 */

class TestGeometry {
  public static void printHeader(String h) {
    System.out.println();
    System.out.println(h);
    System.out.println("====");
  }

  public static boolean expectEqual (double v1, double v2, String comment) {
    if (v1 == v2) {
      System.out.println(" ... ok");
      return true;
    }

    System.out.println(" Test gescheitert - " + comment + " - Es gilt " + v1 + " != " + v2);
    return false;
  }

  /**
   * Erwarte gleiches Vuolmen zweier Körper.
   *
   * Abkürzung für expectEqual() mit zwei Doubles, wo man vorher das Volumen erst nehmen muss
   */
  public static boolean expectEqual (Geometry g, double v2, String comment) {
    return expectEqual(g.volume(), v2, comment);
  }

  public static boolean expectEqual (Geometry g1, Geometry g2, String comment) {
    return expectEqual(g1.volume(), g2.volume(), comment);
  }

  public static void main(String args[]) {
    printHeader("Punkte haben kein Volumen");
    expectEqual(new Point(0,1,2,3).volume(), 0, "Punkt in 4D");
    expectEqual(new Point(13, 5).volume(), 0, "Punkt in 2D");

    printHeader("Volumen einiger Quader sollte 1 sein:");
    expectEqual(new Rectangle(new Point(0, 0), new Point(1, 1)).volume(), 1.0, "Einheitsquadrat 2D");
    expectEqual(new Rectangle(new Point(-0.5, -0.5), new Point(0.5, 0.5)).volume(), 1.0, "verschobenes Einheitsquadrat 2D");
    expectEqual(new Volume(new Point(0, 0, 0), new Point(1, 1, 1)), 1.0, "Einheitsquader im R^3");
    expectEqual(new Volume(new Point(0.5, -0.5, 2), new Point(1.5, 0.5, 1)), 1.0, "verschobener Einheitsquader im R^3");

    printHeader("Umfassung von Punkten, erwartetes Volumen 1");
    expectEqual((new Point(0, 0)).encapsulate(new Point(1, 1)), 1.0, "Umfassung zum Einheitsquadrat 2D");
    expectEqual((new Point(0.5, 1, 0)).encapsulate(new Point(-0.5, 2, 1)), 1.0, "Umfassung zum verschobenen Einheitsquadrat 3D");

    printHeader("Umfassung von Punkt und Rechteck, erwartetes Volumen 2");
    Geometry baseRect = new Rectangle(new Point(0, 0), new Point(1, 1));
    expectEqual(baseRect.encapsulate(new Point(0, 2)), 2.0, "R^2");
    expectEqual(baseRect.encapsulate(new Point(1, 2)), 2.0, "R^2");
    expectEqual((new Point(0, 2)).encapsulate(baseRect), 2.0, "R^2 - symmetrisch");

    Geometry baseRect3 = new Volume(new Point(1, 1, 1), new Point(2, 2, 2));
    expectEqual(baseRect3.encapsulate(new Point(3, 2, 2)), 2.0, "R^3");
    expectEqual((new Point(3, 2, 2)).encapsulate(baseRect3), 2.0, "R^3 - symmetrisch");

    printHeader("Keine Änderung erwartet bei Umfassung eines Volumens um einen Punkt innerhalb desselben");
    Geometry vol = new Volume(new Point(1, 1, 1), new Point(3, 3, 3));
    expectEqual(vol.volume(), vol.encapsulate(new Point(2, 2, 2)).volume(), "Fall im R^3");

    printHeader("Das Volumen der Umfassung eines Punktes mit sich selbst soll verschwinden");
    Geometry point = new Point(1,2,3,4,5);
    expectEqual(point.encapsulate(point).volume(), 0.0, "");
  }
}
