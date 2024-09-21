package hw4;

/**
 * An element in which the <code>update</code> method updates the position each
 * frame according to a <em>velocity</em> vector (deltaX, deltaY). The units are
 * assumed to be "pixels per frame".
 * 
 * @author Gavin Nienke
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class MovingElement extends SimpleElement{
	
	/**
	 * velocity in the x direction
	 */
	private double deltaX;
	
	/**
	 * velocity in the y direction
	 */
	private double deltaY;

	/**
	 * Constructs a MovingElement with a default velocity of zero in both
	 * directions.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  object's width
	 * @param height object's height
	 */
	public MovingElement(double x, double y, int width, int height) {
		super(x, y, width, height);
		this.deltaX = 0;
		this.deltaY = 0;
	}
	
	/**
	 * Returns the x-component of the velocity.
	 * 
	 * @return x-component of the velocity
	 */
	public double getDeltaX() {
		return deltaX;
	}
	
	/**
	 * Returns the y-component of the velocity.
	 * 
	 * @return y-component of the velocity
	 */
	public double getDeltaY() {
		return deltaY;
	}

	/**
	 * Updates the x-component and y-component of the velocity.
	 * Then it updates this element's state for a new frame.
	 */
	@Override
	public void update() {
		setPosition(getXReal()+ deltaX, getYReal() + deltaY);
		super.update();
	}

	/**
	 * Sets the x-component and y-component of the velocity.
	 * 
	 * @param deltaX x-component of the velocity
	 * @param deltaY y-component of the velocity
	 */
	public void setVelocity(double deltaX, double deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

}
