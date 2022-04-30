/**
 * Ein Punkt im n-dimensionalen Raum.
 *
 * Die Dimension des Umgebungsraums n wird durch den Konstruktor festgelegt
 *
 * @author Philipp Weinbrenner
 * @version 2022-04-28
 */

class Point extends Geometry {
	private double[] coordinates;
	private int object_dimensions;

	public Point(double... coordinates) {
    super(coordinates.length);

		this.coordinates = coordinates;
		object_dimensions = coordinates.length;
		if (object_dimensions < 2)
			throw new IllegalArgumentException("Es können nur Objekte im zumindest 2-dimensionalen Raum angelegt werden");
	}

	@Override
	public int dimensions () {
		return object_dimensions;
	}

	@Override
	public double volume() {
		return 0;
	}

	@Override
	public Geometry encapsulate (Geometry other) {
		if (other == null)
			return null;

		if (other instanceof Point)
			return new Volume (this, (Point)other);

		return other.encapsulate(this);
	}

	public double[] getCoordinates () {
		return coordinates;
	}
}
