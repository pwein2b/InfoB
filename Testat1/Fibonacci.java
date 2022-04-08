/**
 * Eine Klasse, welche Fibonacci-Zahlen im long-Format berechnet.
 *
 * @author Philipp Weinbrenner
 * @version 2022-04-08
 */

class Fibonacci {
	private int current_index;
	private long pre_previous_fib;
	private long previous_fib;

	public Fibonacci() {
		current_index = 2;
		pre_previous_fib = 0;
		previous_fib = 1;
	}

	/**
	 * Gibt die nächste Fibonacci-Zahl zurück.
	 *
	 * Es wird jeweils die i-te Fibonacci-Zahl zurückgegeben, wobei i der Wert von getCurrentIndex ist.
	 *
	 * @throws ArithmeticException - wir rechnen mit long-Werten, aber auch der Speicher ist endlich.
	 * Wenn es also zum Überlauf kommt, wird die Exception geworfen.
	 */
	public long next() throws ArithmeticException {
		current_index += 1;

		long next_value = Math.addExact(previous_fib, pre_previous_fib);
		// Long.addExact kann die ArithmeticException werfen
		pre_previous_fib = previous_fib;
		previous_fib = next_value;

		return next_value;
	}

	/**
	 * Gibt den Index der nächsten auszugebenden Fibonacci-Zahl zurück.
	 *
	 * Wenn i der Rückgabe-Wert dieser Methode ist, so wird der nächste Aufruf von next() die i-te Fibonacci-Zahl
	 * zurückgeben.
	 */
	public int getCurrentIndex() {
		return current_index;
	}
}
