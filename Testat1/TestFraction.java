public class TestFraction{
	int anzahlFehler = 0;
	int number = 2;
	Fraction a = new Fraction(2,8);
	Fraction z = new Fraction(2,3);
	Fraction y = new Fraction(2,4);

	//Fehlerueberpruefungsmethode
	public void assertBool(boolean toTest, String errMsg){
		if(!toTest){
			System.out.println(errMsg);
			this.anzahlFehler++;
		}
	}
	//Test von Multiplikation mit einem Int
	public void TestM1(){
		System.out.println("Test von Multiplikation mit einem int");
		Fraction b = a.multiply(number);
		System.out.println(b.toString());
		this.assertBool(b.numerator == (a.numerator * number) / b.gcd && b.denominator == a.denominator /b.gcd,"Fehler gefunden in M1");
	}
	//Test von Multiplikation mit einer Fraction
	public void TestM2(){
		System.out.println("Test von Multiplikation mit einer Fraction");
		Fraction c = a.multiply(z);
		System.out.println(c.toString());
		this.assertBool(c.numerator == (a.numerator * z.numerator / c.gcd) && c.denominator == (a.denominator * z.denominator / c.gcd), "Fehler gefunden in M2");
	}
	//Test von Division mit einer Fraction
	public void TestD1(){
		System.out.println("Test von Division mit einer Fraction");
		Fraction d = a.divide(z);
		System.out.println(d.toString());
		this.assertBool(d.numerator == (a.numerator * z.denominator /d.gcd) && d.denominator == (a.denominator * z.numerator / d.gcd), "Fehler gefunden in D1");
	}
	//Test con multiplikation mit einer Liste von Fractions
	public void TestM3(){
		System.out.println("Test von Multiplikation mit einer Liste an Fractions");
		Fraction e = a.multiply(y,z);
		System.out.println(e.toString());
		this.assertBool(e.numerator == (a.numerator * y.numerator * z.numerator / e.gcd) && e.denominator == (a.denominator * y.denominator * z.denominator / e.gcd), "Fehler gefunden in M3");
	}
	//getter fuer die Fehleranzahl
	public int getAnzahlFehler(){
		return this.anzahlFehler;
	}
	public void TestAll(){
		this.TestM1();
		this.TestM2();
		this.TestD1();
		this.TestM3();
	}
	public static void main(String[] args){
		System.out.println("Starte Tests...");
		TestFraction Test = new TestFraction();
		Test.TestAll();
		System.out.println("Anzahl der Fehler: " + Test.getAnzahlFehler());
	}
}
