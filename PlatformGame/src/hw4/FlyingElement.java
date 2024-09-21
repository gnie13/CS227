package hw4;

/**
 * Moving element in which the vertical velocity is adjusted each frame by a
 * gravitational constant to simulate gravity. The element can be set to
 * "grounded", meaning gravity will no longer influence its velocity.
 * 
 * @author Gavin Nienke
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class FlyingElement extends MovingElement {

	/**
	 * indicates whether the element is on the ground. 
	 */
	private boolean grounded = true;
	
	/**
	 * gravity constant that affects vertical velocity
	 */
	private double gravity = 0.0;

	/**
	 * Constructs a new FlyingElement. By default it should be grounded, meaning
	 * gravity does not influence its velocity.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public FlyingElement(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * Updates position and adds 
	 * the gravitational constant to 
	 * the y-component of the velocity.
	 */
	@Override
	public void update() {
		super.update();
		if (!grounded) {
			setVelocity(getDeltaX(), getDeltaY() + gravity);
		}
	}

	/**
	 * checks if element is on the ground. 
	 * 
	 * @return grounded. Element is on ground if true, not on ground if false. 
	 */
	public boolean isGrounded() {
		return grounded;
	}

	/**
	 * sets gravity onto the elements movement. 
	 * 
	 * @param gravity is to check if the element is under the influence of gravity. 
	 */
	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	/**
	 *sets the element to grounded. 
	 * 
	 * @param ground is to check if the element is on the ground. 
	 */
	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}
}
