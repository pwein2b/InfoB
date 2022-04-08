/**
 * Eine Klasse, welche die Klasse Fibonacci benutzt, um eine Wertetabelle der
 * Fibonacci-Folge auszugeben.
 *
 * Diese Klasse definiert eine main-Methode. Diese verlangt ein Argument, eine natürliche Zahl,
 * die angibt, bis zu welchem Index die Fibonacci-Zahlen berechnet werden sollen.
 *
 * @author Philipp Weinbrennner
 * @version 2022-08-04
 */

class FibonacciPrint {
	private int first_column_width;
	private int second_column_width;
  
	/**
	 * Ein printf-kompatibler Format-String für ja eine Zeile der Tabelle.
	 * Es sollten zwei '%l'-Elemente vorkommen, eins für die erste, eins für die zweite Spalte.
	 * Äquivalent funktioniert das Attribute row_format_string, allerdings kommen dort '%s'-Elemente vor,
	 * für den Tabellenkopf.
	 */ 
	private String row_format_long;
	private String row_format_string;

	/**
	 * Erzeuge eine neue FibonacciPrint-Klasse.
	 *
	 * @param max_fib_index Das höchste i, für welches f_i ausgegeben werden soll.
	 * 			Dieser Parameter wird zur Berechnung der Spaltenbreiten verwendet.
	 */
	public FibonacciPrint (int max_fib_index) {
		/* Wir möchten berechnen, wie breit die Tabellenspalten sein müssen.
		 *
		 * Eine einfache Induktion liefert die Abschätzung f_i < 2^i für alle i > 3.
		 * Weiterhin ist log10(2) ca. 0,3 und ceil(log10(x)) ist die Anzahl der Stellen von x.
		 * Daher reichen für die Breite der zweiten Spalte der Tabelle log10(2^no_values)
		 * = ca. 0.3 * no_values Spalten.
		 * Außerdem muss die Kopfzeile hineinpassen.
		 */
		String first_col_header = " n ";
		String second_col_header = " f(n) ";
		first_column_width = Math.max((int)Math.ceil(Math.log10(max_fib_index)), first_col_header.length());
		second_column_width = Math.max((int)Math.ceil(0.3 * max_fib_index), second_col_header.length());

		/* Um die passenden Spaltenbreiten hinzubekommen, benötigen wir entsprechende printf-Format-Strings;
		 * diese hängen aber davon von der Tabellenbreite ab. Daher generieren wir hier die Format-Strings.
		 */
		row_format_long = "|%" + first_column_width + "d|%" + second_column_width + "d|\n";
		row_format_string = "|%" + first_column_width + "s|%" + second_column_width + "s|\n";
	}

	/**
	 * Drucke eine Tabellenzeile.
	 *
	 * Der Methode sind die beiden Werte; die im Konstruktor angegebenen
     * Spaltenbreiten werden beachtet.
	 * 
	 * @param value1 Die Ganzzahl, die in Spalte 1 gedruckt werden soll.
	 * @param value2 Die Ganzzahl, die in Spalte 2 gedruckt werden soll.
	 */
	private void printRow (long value1, long value2) {
		System.out.printf(row_format_long, value1, value2);
	}

	/**
	 * Drucke eine Tabellenzeile.
	 *
	 * Funktioniert wie printRow(int, int), allerdings mit Strings.
	 */
	private void printRow (String value1, String value2) {
		System.out.printf(row_format_string, value1, value2);
	}

	/**
	 * Drucke mehrmals den selben String hintereinander.
	 *
	 * @param string Die Zeichenkette, die verwendet werden soll.
	 * @param count Die Anzahl der Wiederholungen.
	 */
	private void printRepeatString (String string, int count) {
		for (int i = 0; i < count; i++) {
			System.out.print(string);
		}
	}

	/**
	 * Drucke eine Trennzeile.
	 *
	 * Die Trennzeile sieht wie folgt aus: '+-----+----+', wobei die '+' jeweils an den Spaltengrenzen
	 * stehen.
	 */
	private void printSeparatorRow () {
		System.out.print("+");
		printRepeatString("-", first_column_width);
		System.out.print("+");
		printRepeatString("-", second_column_width);
		System.out.print("+\n");
	}
	
	/**
	 * Drucke die Tabelle mit den Werten bis zur vorgesehenen oberen Grenze.
	 *
	 * Es findet keine Überprüfung statt, ob die im Konstruktor gewählte Tabellenbreite hiermit übereinstimmt.
	 *
	 * @param max_index Die höchste Fibonacci-Zahl, die gedruckt werden soll.
	 */
	public void printValues (int max_index) {
		Fibonacci number_generator = new Fibonacci();
      
		/* Kopfzeile: */
		printRow(" n ", " f(n) ");

		/* Trennzeile: */
		printSeparatorRow();

		/* Werte - nach Aufgabenstellung fängt die Fibonacci-Klasse erst bei f_2 an. Die ersten
		 * Werte geben wir manuell aus. */
		printRow(0, 0);
		if(max_index == 0)
			return;

		printRow(1, 1);
		if (max_index == 1)
			return;

		for(int i = 2; i <= max_index; i++) {
			try {
				printRow(i, number_generator.next());
			} catch (ArithmeticException x) {
				System.out.println("Fehler: Arithmetischer Überlauf bei Berechnung von Fibonacci-Zahl f_" + i);
				return;
			}
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Fehler: Genau ein Argument erwartet (natürliche Zahl)");
			System.exit(1);
		}

		/* Wie viele Werte sollen berechnet werden (NumberFormatException wird nicht behandelt): */
		int no_values = Integer.parseInt(args[0]);

		/* Entsprechende Klasse erzeugen und loslegen */
		FibonacciPrint fp = new FibonacciPrint(no_values);
		fp.printValues(no_values);
	}
}
