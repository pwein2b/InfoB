/**
 * Eine Klasse, die eine beliebige börsennotierte Firma repräsentiert.
 *
 * Veränderte Börsenkurse werden durch den Ticker ausgegeben.
 * Wenn eine Instanz durch den Garbage Collector freigegeben wird, wird eine Mitteilung über
 * die Insolvenz ausgegeben.
 *
 * @author Philipp Weinbrenner
 * @version 2022-04-20
 */

class Company {
	private String name;

	/**
	 * Der Aktienkurs
	 */
	private double quote;

	public Company (String name) {
		this.name = name;
		this.quote = 0.0;
	}

	private void notifyTicker () {
		Ticker t = Ticker.getInstance();
		t.print(name + " " + quote);
	}

	public double getStockPrice () {
		return quote;
	}

	/**
	 * Trage einen veränderten Aktienkurs ein.
	 * Der Kurs wird über den Ticker auf der Standardausgabe ausgegeben
	 */
	public void changeStockPrice (double new_quote) {
		this.quote = new_quote;
		notifyTicker();
	}

	protected void finalize () {
		Ticker t = Ticker.getInstance();
		t.print(name + " is bankrupt");

		super().finalize();
	}
}
