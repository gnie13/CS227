package hw4;

import java.util.ArrayList;

import api.AbstractElement;

//@author Gavin Nienke

public class PlatformLiftMechanics extends MovingElement {

	/**
	 * ArrayList to store the elements that are associated with this element
	 */
	private ArrayList<AbstractElement> elementList = new ArrayList<AbstractElement>();

	/**
	 * lower and right bounds of the element
	 */
	private double max;

	/**
	 * upper and left bounds of the element
	 */
	private double min;

	/**
	 * Constructs a new LiftPlatformElement with the given x and y coordinates,
	 * width, and height.
	 * 
	 * @param x      x-coordinate of the element
	 * @param y      y-coordinate of the element
	 * @param width  width of the element
	 * @param height height of the element
	 */
	public PlatformLiftMechanics(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * Adds an element to the elementList of elements that are associated with this
	 * element
	 * 
	 * @param attached is the element that is added to the elementList.
	 */
	public void addAssociated(AttachedElement attached) {
		elementList.add(attached);
		attached.setBase(this);
	}

	/**
	 * Adds an associated element to this element, and sets this object to be the
	 * FollowerElement's base.
	 * 
	 * @param follower element to be added to the elementList
	 */
	public void addAssociated(FollowerElement follower) {
		elementList.add(follower);
		follower.setBounds(getXInt(), getXInt() + getWidth());
		follower.setBase(this);
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
	 * Sets the bounds of the element
	 * 
	 * @param min left boundary
	 * @param max right boundary
	 */
	public void setBounds(double min, double max) {
		this.min = min;
		this.max = max;
		updateFollowerElementBounds();
	}
	
	 // Helper method to update bounds of FollowerElements
    private void updateFollowerElementBounds() {
        for (AbstractElement element : elementList) {
            if (element instanceof FollowerElement) {
                ((FollowerElement) element).setBounds(min, max);
            }
        }
    }

	/**
	 * deleted the marked associated element.
	 */
	public void deleteMarkedAssociated() {
		elementList.clear();
	}

	/**
	 * Returns the elementList of elements that are associated with this element
	 * 
	 * @return elementList of elements associated with this element
	 */
	public ArrayList<AbstractElement> getAssociated() {
		return elementList;
	}

	/**
	 * Updates each element in the elementList of associated elements and increments
	 * frameCount by calling super.update
	 */
	@Override
	public void update() {
		elementList.forEach(AbstractElement::update);
		super.update();
	}
}
