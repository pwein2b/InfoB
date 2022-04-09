/**
 * Eine Klasse, welche die Klasse Fraction benutzt und deren Methoden Testet.
 *
 * Diese Klasse Testet automatisch anhand von 3 Vorgefertigten Fractions die in der Klasse Fraction enthaltenden Methoden
 *
 * @author Markus Kuehn
 * @version 2022-08-04
 */

public class TestFraction{

    /**
     * Erstellung einer Nummer und drei Fractions zu Testzwecken
     */
    int anzahlFehler = 0;
    int number = 2;
    Fraction a = new Fraction(2,8);
    Fraction z = new Fraction(2,3);
    Fraction y = new Fraction(2,4);

    /**
     *
     * @param toTest Ausdruck, welcher auf Korrektheit getestet werden soll
     * @param errMsg Error Message welche bei Fehlschlag herausgegeben wird
     */
    public void assertBool(boolean toTest, String errMsg){
        if(!toTest){
            System.out.println(errMsg);
            this.anzahlFehler++;
        }
    }

    /**
     * Test der Multiplikation mit einem int
     */
    private void TestM1(){
        System.out.println("Test von Multiplikation mit einem int");
        Fraction b = a.multiply(number);
        System.out.println(b.toString());
        this.assertBool(b.numerator == (a.numerator * number) / b.gcd && b.denominator == a.denominator /b.gcd,"Fehler gefunden in M1");
    }

    /**
     * Test der Multiplikation mit einer Fraction
     */
    private void TestM2(){
        System.out.println("Test von Multiplikation mit einer Fraction");
        Fraction c = a.multiply(z);
        System.out.println(c.toString());
        this.assertBool(c.numerator == (a.numerator * z.numerator / c.gcd) && c.denominator == (a.denominator * z.denominator / c.gcd), "Fehler gefunden in M2");
    }

    /**
     * Test der Division mit einer Fraction
     */
    private void TestD1(){
        System.out.println("Test von Division mit einer Fraction");
        Fraction d = a.divide(z);
        System.out.println(d.toString());
        this.assertBool(d.numerator == (a.numerator * z.denominator /d.gcd) && d.denominator == (a.denominator * z.numerator / d.gcd), "Fehler gefunden in D1");
    }

    /**
     * Test der Multiplikation mit einer Liste an Fractions
     */
    private void TestM3(){
        System.out.println("Test von Multiplikation mit einer Liste an Fractions");
        Fraction e = a.multiply(y,z);
        System.out.println(e.toString());
        this.assertBool(e.numerator == (a.numerator * y.numerator * z.numerator / e.gcd) && e.denominator == (a.denominator * y.denominator * z.denominator / e.gcd), "Fehler gefunden in M3");
    }

    /**
     * Getter für die Anzahl der Fehler
     *
     * @return Anzahl der Fehler
     */
    public int getAnzahlFehler(){
        return this.anzahlFehler;
    }
    private void TestAll(){
        this.TestM1();
        this.TestM2();
        this.TestD1();
        this.TestM3();
    }

    /**
     * Main Methode
     * Gibt informationen zu Programmstart aus
     * @param args
     */
    public static void main(String[] args){
        System.out.println("Starte Tests...");
        TestFraction Test = new TestFraction();
        Test.TestAll();
        System.out.println("Anzahl der Fehler: " + Test.getAnzahlFehler());
    }
}