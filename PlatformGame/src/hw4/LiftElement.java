package hw4;

/**
 * An element with two distinctive behaviors. First, it can be set up to move
 * vertically within a fixed set of boundaries. On reaching a boundary, the
 * y-component of its velocity is reversed. Second, it maintains a list of
 * <em>associated</em> elements whose basic motion all occurs relative to the
 * LiftElement.
 * 
 * @author Gavin Nienke
 */
public class LiftElement extends PlatformLiftMechanics {

	/**
	 * List of elements associated with this element. Associated elements are moved
	 * when this element moves.
	 */

	/**
	 * Constructs a new Elevator. Initially the upper and lower boundaries are
	 * <code>Double.NEGATIVE_INFINITY</code> and
	 * <code>Double.POSITIVE_INFINITY</code>, respectively.
	 * 
	 * @param x      x-coordinate of initial position of upper left corner
	 * @param y      y-coordinate of initial position of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public LiftElement(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * 
	 * Adds an associated element to this LiftElement, and sets this object to be
	 * the AttachedElement's base.
	 */
	@Override
	public void update() {
		 // check if element is moving downwards and approaching the bottom boundary
		if (getDeltaY() + getHeight() + getYInt() >= getMax() && getDeltaY() > 0) {
			  // reverse vertical velocity and move to the bottom boundary
			setVelocity(getDeltaX(), -1 * getDeltaY());
			setPosition(getDeltaX() + getXInt(), getMax() - getHeight());
			 // check if element is moving upwards and approaching the top boundary
		} else if (getDeltaY() + getYInt() <= getMin() && getDeltaY() < 0) {
			 // reverse vertical velocity and move to the top boundary
			setVelocity(getDeltaX(), -1 * getDeltaY());
			setPosition(getDeltaX() + getXInt(), getMin());
			  // else, update position normally based on velocity
		} else {
			setPosition(getDeltaX() + getXInt(), getYInt() + getDeltaY());

		}
		super.update();
		// adjust position to maintain consistency with delta values
		setPosition(getXInt() - getDeltaX(), getYInt() - getDeltaY());
	}
}