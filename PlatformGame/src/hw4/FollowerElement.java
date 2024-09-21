package hw4;

import api.AbstractElement;

/**
 * A follower element is one that is associated with another "base" element such
 * as a PlatformElement or LiftElement. Specifically, the follower element's
 * movement is determined by the movement of the base element, when the base
 * move up 10 pixels, the follower moves up 10 pixels. However, the follower may
 * not always be at a fixed location relative to the base. When the horizontal
 * velocity of the follower is set to a non-zero value, the follower will
 * oscillate between the left and right edges of the PlatformElement or
 * LiftElement it is associated with.
 * 
 * @author Gavin Nienke
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class FollowerElement extends MovingElement {

	/**
	 * when added to a base, 
	 * this amount will be added to the bases's x-coordinate
	 * to calculate this element's initial x-coordinate
	 */
	private int initialOffset;

	/**
	 * The left min boundary of the follower element.
	 */
	private double min;

	/**
	 * The right max boundary of the follower element.
	 */
	private double max;

	/**
	 * The base element of this follower element.
	 */
	private AbstractElement base;

	/**
	 * Constructs a new FollowerElement. Before being added to a "base" element such
	 * as a PlatformElement or LiftElement, the x and y coordinates are zero. When a
	 * base element is set, the initial x-coordinate becomes the base's
	 * x-coordinate, plus the given offset, and the y-coordinate becomes the base's
	 * y-coordinate, minus this element's height.
	 * 
	 * @param width         element's width
	 * @param height        element's height
	 * @param initialOffset when added to a base, this amount will be added to the
	 *                      bases's x-coordinate to calculate this element's initial
	 *                      x-coordinate
	 */
	public FollowerElement(int width, int height, int initialOffset) {
		super(0, 0, width, height);
		this.initialOffset = initialOffset;
	}

	/**
	 * Returns the right boundary for this Follower's movement.
	 * 
	 * @return rightmost boundary
	 */
	public double getMax() {
		return max;
	}

	/**
	 * Returns the left boundary for this Follower's movement.
	 * 
	 * @return leftmost boundary
	 */
	public double getMin() {
		return min;
	}

	/**
	 * Sets the base element for this follower element. The initial x-coordinate
	 * becomes the base's x-coordinate, plus the given offset, and the y-coordinate
	 * becomes the base's y-coordinate, minus this element's height.
	 * 
	 * @param base, the base element
	 */
	public void setBase(AbstractElement base) {
		this.base = base;
		setPosition(initialOffset + base.getXInt(), base.getYInt() - getHeight());
	}

	/**
	 * Sets the bounds for this follower element.
	 * 
	 * @param min the left movement boundary
	 * @param max the right movement boundary
	 */
	public void setBounds(double min, double max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * Updates this element's position to move horizontally according to its
	 * velocity (possibly zero) relative to the parent object, and remain "resting"
	 * on the parent object (provided that a parent has been set).
	 */
	public void update() {
		
		super.update();
		  // Adjust position based on velocity
		setPosition(getXInt() - getDeltaX(), getYInt() - getDeltaY());
		 // Set bounds based on the base element's position and width
		setBounds(base.getXInt(), base.getXInt() + base.getWidth());
		 // Check if element is approaching the right boundary
		if (getXInt() + getDeltaX() + getWidth() + ((PlatformLiftMechanics) base).getDeltaX() >= max) {
			 // Move to the right boundary and reverse horizontal velocity
			setPosition(max - getWidth(), base.getYReal() - getHeight());
			setVelocity(getDeltaX() * -1, getDeltaY());
			 // Check if element is approaching the left boundary
		} else if (getXInt() + getDeltaX() - initialOffset + ((PlatformLiftMechanics) base).getDeltaX() <= min) {
			// Move to the left boundary and reverse horizontal velocity
			setPosition(min + initialOffset, base.getYReal() - getHeight());
			setVelocity(getDeltaX() * -1, getDeltaY());
			 // Otherwise move horizontally with the base element's horizontal movement
		} else {
			setPosition(getXInt() + getDeltaX() + ((PlatformLiftMechanics) base).getDeltaX(),
					base.getYReal() - getHeight());
		}
	}
}
