/**
 * Ein rechteckiger Körper im mehrdimensionalen Raum.
 *
 * Der Körper ist durch zwei Punkte festgelegt, welche ihn aufspannen.
 *
 * @author Philipp Weinbrenner
 * @version 2022-04-28
 */

import java.util.Arrays;

class Volume extends Geometry {
	private Point p1;
	private Point p2;
	private double object_volume;

  /**
   * Dimension des Umgebungsraums.
   * Die Instanzvariable dimensions in der Superklasse Geometry tut eigentlich dasselbe, ist aber
   * private und nicht protected.
   */
	private int object_dimensions;

	public Volume (Point p1, Point p2) {
    super(p1.dimensions());
		if (p1.dimensions() != p2.dimensions())
			throw new IllegalArgumentException("Die übergebenen Punkte müssen im gleichen Umgebungsraum liegen!");

		this.p1 = p1;
		this.p2 = p2;
		this.object_dimensions = p1.dimensions();

		double[] coord1 = p1.getCoordinates();
		double[] coord2 = p2.getCoordinates();
		object_volume = 1;
		for (int i = 0; i < object_dimensions; i++) {
			object_volume *= Math.abs(coord1[i] - coord2[i]);
		}
	}

	@Override
	public int dimensions () {
		return object_dimensions;
	}

	@Override
	public double volume() {
		return object_volume;
	}

	@Override
	public Geometry encapsulate (Geometry other) {
		if (other == null)
			return null;

		if (other.dimensions() != object_dimensions)
			throw new IllegalArgumentException("Die übergebenen Objekte sollen im gleichen Umgebungsraum liegen");

		/* In jeder Dimension die minimale und maximale Koordinate auswählen, und daraus zwei Punkte
		 * auswählen, die ein neues Volume aufspannen */
		double[] min_dim = new double[object_dimensions];
		double[] max_dim = new double[object_dimensions];
		
		if (other instanceof Point) {
			for (int i = 0; i < object_dimensions; i++) {
				min_dim[i] = min(((Point)other).getCoordinates()[i], p1.getCoordinates()[i], p2.getCoordinates()[i]);
				max_dim[i] = max(((Point)other).getCoordinates()[i], p1.getCoordinates()[i], p2.getCoordinates()[i]);
			}
		} else if (other instanceof Volume) {
			for (int i = 0; i < object_dimensions; i++) {
				min_dim[i] = min(((Volume)other).getPoint1().getCoordinates()[i], ((Volume)other).getPoint2().getCoordinates()[i], p1.getCoordinates()[i], p2.getCoordinates()[i]);
				max_dim[i] = max(((Volume)other).getPoint1().getCoordinates()[i], ((Volume)other).getPoint2().getCoordinates()[i], p1.getCoordinates()[i], p2.getCoordinates()[i]);
			}
		} else {
			throw new IllegalArgumentException("Unbekannte Geometry-Subklasse");
		}

		return new Volume(new Point(min_dim), new Point(max_dim));
	}

	public Point getPoint1 () {
		return p1;
	}

	public Point getPoint2 () {
		return p2;
	}
	
  /**
   * Berechne das Minimum von einem Array von double-Werten.
   *
   * Math.min gibt leider nur den direkten Vergleich zweier Werte her!
   * Klarer Mangel in der Standardbibliothek.
   * Wir bedienen uns hier einer Rekursion, um das Problem zu lösen.
   */
	private double min (double... values) {
    if (values.length == 1)
      return values[0];
    else if (values.length == 2)
			return Math.min(values[0], values[1]);
		else
			return min(values[0], min(Arrays.copyOfRange(values, 1, values.length)));
	}

  /**
   * Berechne das Maximum eines Double-Arrays.
   * Vergleiche min().
   */
	private double max (double... values) {
    if (values.length == 1)
      return values[0];
    else if (values.length == 2)
			return Math.max(values[0], values[1]);
		else
			return max(values[0], max(Arrays.copyOfRange(values, 1, values.length)));
	}
}
