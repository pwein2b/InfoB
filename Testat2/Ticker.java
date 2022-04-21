/**
 * Ticker - ein Singleton, welches Strings auf der Standardausgabe ausgibt
 * Die einzige Instanz (Singleton) kann mit der Klassenmethode getInstance() abgerufen werden.
 *
 * Die auszugebenden Strings sollen nacheinander mit print() übergeben werden.
 * 
 * @author Philipp Weinbrenner
 * @version 2022-04-02
 */

class Ticker {
	static Ticker instance = null;
	static Ticker getInstance() {
		if (instance == null)
			instance = new Ticker();
		return instance;
	}

	private Ticker () {
	}

	/**
	 * print:
	 *
	 * Gibt den übergebenen String auf der Standardausgabe (im Ticker-Stil) aus.
	 * Leerzeichen etc. am Anfang und Ende des Strings und Zeilenumbrüche bleiben unbeachtet und die
	 * Ausgaben werden durch '+++' voneinander getrennt.
	 *
	 * @param info: Der nach obigem Schema auszugebende String.
	 */
	public void print (String info) {
		System.out.print("+++" + info.strip().replaceAll("\\n", "");
	}
}
