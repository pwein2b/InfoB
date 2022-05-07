/**
 * Ein Rechteck im R^2.
 *
 * @author Philipp Weinbrenner
 * @version 2022-04-28
 */

class Rectangle extends Volume {
	public Rectangle(Point p1, Point p2) {
		if (p1.dimensions() != p2.dimensions() || p1.dimensions() != 2)
			throw new IllegalArgumentException("Die Punkte sollen beide im R^2 liegen");

		super(p1, p2);
	}
}
