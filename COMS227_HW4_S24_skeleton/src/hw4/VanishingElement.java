package hw4;

/**
 * An element that does not move. Instead, it is intended to appear on the
 * screen for a fixed number of frames.
 * 
 * @author Gavin Nienke
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class VanishingElement extends SimpleElement {
	
	/**
	 * the number of frames until the element is marked for deletion. 
	 */
	private int timeRemaining;
	
	/**
	 * Constructs a new VanishingElement.
	 * 
	 * @param x           x-coordinate of upper left corner
	 * @param y           y-coordinate of upper left corner
	 * @param width       element's width
	 * @param height      element's height
	 * @param initialLife the number of frames until this element marks itself for
	 *                    deletion
	 */
	public VanishingElement(double x, double y, int width, int height, int initialLife) {
		super(x, y, width, height);
		timeRemaining = initialLife;
	}
	
	/**
	 * counts down until the frame count reaches zero. Once zero, element is marked for deletion. 
	 */
	@Override
	public void update() {
		timeRemaining--;
		if (timeRemaining == 0) {
			markForDeletion();
		}
		super.update();
	}
}
