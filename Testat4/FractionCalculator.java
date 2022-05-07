/**
 * Eine Bruchrechner für die Kommandozeile.
 *
 * Der Aufruf soll wie folgt aussehen:
 * 	"FractionCalculator" Bruch ("+" | "*" | "-" | "/") Bruch
 * 
 * Bei fehlerhaftem Aufruf, inkorrekter Codierung der Brüche oder Division durch 0 wird auf dem Standardfehlerstream
 * eine Meldung ausgegeben, ansonsten wird das Ergebnis der Rechnung auf die Standardausgabe geschriebem.
 *
 * @author Philipp Weinbrenner
 * @version 2022-05-06
 */

class FractionCalculator {
   /**
    * Schreibt eine Fehlermeldung auf den stderr-Stream und beendet das Programm.
    */
   public static void errorMessage(String message) {
      System.err.println(message);
         System.exit(-1);
   }

   public static void main(String[] args) {
      if (args.length != 3) {
         errorMessage("Es müssen genau 3 Argumente übergeben werden: Bruch (\"+\" | \"*\" | \"+\" | \"/\") Bruch");
      }

      Fraction f1 = null, f2 = null;
      try {
         f1 = Fraction.parseFraction(args[0]);
      } catch (IllegalArgumentException ex) {
         errorMessage("Erstes Argument: Ungültiges Bruchformat! Erwartet: [\"-\"] NatZahl \"/\" NatZahlNichtNull");
      }

      try {
         f2 = Fraction.parseFraction(args[2]);
      } catch (IllegalArgumentException ex) {
         errorMessage("Drittes Argument: Ungültiges Bruchformat! Erwartet: [\"-\"] NatZahl \"/\" NatZahlNichtNull");
      }

      Fraction result = null;
      String operator = args[1];
      if (operator.equals("+"))
         result = f1.add(f2);
      else if (operator.equals("-"))
         result = f1.subtract(f2);
      else if (operator.equals("*"))
         result = f1.multiply(f2);
      else if (operator.equals("/"))
         result = f1.divide(f2);
      // Auf Division durch 0 muss nicht getestet werden, das übernimmt die Fraction-Klasse
      else
         errorMessage("Zweites Argument: Ungültiger Operator '" + operator + "', erwarte '+*, '-', '*', '/'");

      System.out.println(result.toString());
   }
}
