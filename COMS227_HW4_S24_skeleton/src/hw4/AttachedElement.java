package hw4;

import api.AbstractElement;

/**
 * An attached element is one that is associated with another "base" element
 * such as a PlatformElement or a LiftElement. Specifically, the attached
 * element's movement is determined by the movement of the base element, the
 * element always remains a fixed distance away.
 * 
 * @author Gavin Nienke
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class AttachedElement extends SimpleElement {
	
	/**
	 * The amount to add to the base object's x-coordinate to calculate this
	 * element's x-coordinate.
	 */
	private int offset;
	
	/**
	 * The amount to subtract from the base object's y-coordinate to calculate this
	 * element's y-coordinate.
	 */
	private int hover;
	
	/**
	 * The base object that this element is attached to.
	 */
	private AbstractElement base;

	/**
	 * Constructs a new AttachedElement. Before being added to an associated "base"
	 * element such as a PlatformElement or LiftElement, the x and y coordinates are
	 * initialized to zero. When the base object is set (not in this constructor),
	 * the x-coordinate will be calculated as the base object's x-coordinate, plus
	 * the given offset, and
	 * 
	 * @param width  element's width
	 * @param height element's height
	 * @param offset when added to a base object, this amount will be added to the
	 *               other object's x-coordinate to calculate this element's
	 *               x-coordinate
	 * @param hover  when added to a base object, this element's y-coordinate is the
	 *               other object's y-coordinate, minus this element's height, minus
	 *               the hover amount
	 */
	public AttachedElement(int width, int height, int offset, int hover) {
		super(0, 0, width, height);
		this.offset = offset;
		this.hover = hover;
	}

	/**
	 * Set's this element's base element.
	 **/
	public void setBase(AbstractElement b) {
		setPosition(offset + b.getXReal(), b.getYReal() - hover - super.getHeight());
		base = b;
	}

	/**
	 * Updates this element's position to remain stationary relative to the parent
	 * element (provided that a parent has been set).
	 **/
	@Override
	public void update() {
		if (base != null) { //checks to see if a base exists
			super.setPosition(offset + base.getXReal(), base.getYReal() - hover - super.getHeight());		
			super.update();
		}
	}
	
}
