package hw4;

import java.awt.Rectangle;

import api.AbstractElement;

/*
  - SimpleElement extends AbstractElement - 
 	 - MovingElement & VanishingElement extends SimpleElement - 
 		 - FlyingElement & FollowerElement & PlatformLiftMechanics extends MovingElement - 
 			 - PlatformElement & LiftElement extends PlatformLiftMechanics - 
  
 
/**
 * Minimal concrete extension of AbstractElement. The <code>update</code> method
 * in this implementation just increments the frame count.
 * 
 * @author Gavin Nienke
 */
// TODO: This class must directly or indirectly extend AbstractElement
public class SimpleElement extends AbstractElement {

	/**
	 * Number of times that update has been called. 
	 */
	private int frameCount = 0;
	/**
	 * indicates that the element was marked for deletion.
	 */
	private boolean marked = false;
	/**
	 * Width of the rectangle.
	 */
	private int width;
	
	/**
	 * Height of the rectangle.
	 */
	private int height;
	
	/**
	 * x-coordinate of the elements' rectangle (starts at top left corner).
	 */
	private double x;
	
	/**
	 * y-coordinate of the elements' rectangle (also starts at top left corner).
	 */
	private double y;

	/**
	 * Constructs a new SimpleElement.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public SimpleElement(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns the horizontal coordinate of the upper-left corner of this object's
	 * bounding rectangle, rounded to the nearest integer.
	 * 
	 * @return x-coordinate of the upper-left corner of this object's bounding
	 */
	public int getXInt() {
		int intX = (int) Math.round(x);
		return intX;
	}

	/**
	 * Returns the vertical coordinate of the upper-left corner of this object's
	 * bounding rectangle, rounded to the nearest integer.
	 * 
	 * @return y-coordinate of the upper-left corner of this object's bounding rectangle
	 */
	public int getYInt() {
		int intY = (int) Math.round(y);
		return intY;
	}

	/**
	 * Returns the width of this object's bounding rectangle.
	 * 
	 * @return width of this object's bounding rectangle
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of this object's bounding rectangle.
	 * 
	 * @return height of this object's bounding rectangle
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the bounding rectangle for this object as an instance of
	 * java.awt.Rectangle.
	 * 
	 * @return bounding rectangle for this object
	 */
	public Rectangle getRect() {
		return new Rectangle(getXInt(), getYInt(), width, height);
	}

	/**
	 * Returns the x-coordinate's exact value as a double.
	 * 
	 * @return x-coordinate of the upper-left corner of this object's bounding rectangle
	 */
	@Override
	public double getXReal() {
		return x;
	}

	/**
	 * Returns the y-coordinate's exact value as a double.
	 * 
	 * @return y-coordinate of the upper-left corner of this object's bounding rectangle
	 */
	@Override
	public double getYReal() {
		return y;
	}

	/**
	 * Sets the position of this element.
	 * 
	 * @param newX new x-coordinate of the upper-left corner
	 * @param newY new y-coordinate of the upper-left corner
	 */
	@Override
	public void setPosition(double newX, double newY) {
		x = newX;
		y = newY;
	}

	/**
	 * 	Updates this object's attributes for the next frame.
	 */
	@Override
	public void update() {
		frameCount++;
	}

	/**
	 * Returns the number of times that update() has been invoked for this object.
	 * 
	 * @return the number of times that update() has been invoked for this object
	 */
	@Override
	public int getFrameCount() {
		return frameCount;
	}

	/**
	 * Returns true if this element has been marked for deletion.
	 * 
	 * @return true if this element has been marked for deletion
	 */
	@Override
	public boolean isMarked() {
		return marked;
	}

	/**
	 * Marks element for deletion.
	 * 
	 * @return true if this element has been marked for deletion
	 */
	@Override
	public void markForDeletion() {
		marked = true;
	}

	/**
	 * Determines whether this element's bounding rectangle overlaps the given
	 * element's bounding rectangle.
	 * 
	 * @param other the other element
	 */
	@Override
	public boolean collides(AbstractElement other) {
		return getRect().intersects(other.getRect());
	}
}
