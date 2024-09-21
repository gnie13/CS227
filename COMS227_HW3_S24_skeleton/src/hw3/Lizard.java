package hw3;

import static api.Direction.*;

import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;

/**
 * Represents a Lizard as a collection of body segments.
 * @author Gavin Nienke
 */
public class Lizard {

	private ArrayList<BodySegment> segments;// initializes the lizard segments

	/**
	 * Constructs a Lizard object.
	 */
	public Lizard() {
		segments = new ArrayList<>(); // creates the array list
	}

	/**
	 * Sets the segments of the lizard. Segments should be ordered from tail to
	 * head.
	 * 
	 * @param segments list of segments ordered from tail to head
	 */
	public void setSegments(ArrayList<BodySegment> segments) {
		this.segments = segments; // sets segment list
	}

	/**
	 * Gets the segments of the lizard. Segments are ordered from tail to head.
	 * 
	 * @return a list of segments ordered from tail to head
	 */
	public ArrayList<BodySegment> getSegments() {

		return segments; // gets segment list
	}

	/**
	 * Gets the head segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the head segment
	 */
	public BodySegment getHeadSegment() {
		if (segments == null) {
			return null;
		} else {
			return segments.get(segments.size() - 1); // returns last lizard segment
		}
	}

	/**
	 * Gets the tail segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the tail segment
	 */
	public BodySegment getTailSegment() {
		if (segments == null) {
			return null;
		} else {
			return segments.get(0); // returns last segment of list
		}
	}

	/**
	 * Gets the segment that is located at a given cell or null if there is no
	 * segment at that cell.
	 * 
	 * @param cell to look for lizard
	 * @return the segment that is on the cell or null if there is none
	 */
	public BodySegment getSegmentAt(Cell cell) {
		for (int i = 0; i < segments.size(); i++) {// iterates until it matches number of segments.
			if (cell != null && segments.get(i).getCell().equals(cell)) {// if a cell exists, segment exists
				return segments.get(i);
			}
		}
		return null;
	}

	/**
	 * Get the segment that is in front of (closer to the head segment than) the
	 * given segment. Returns null if there is no segment ahead.
	 * 
	 * @param segment the starting segment
	 * @return the segment in front of the given segment or null
	 */
	public BodySegment getSegmentAhead(BodySegment segment) {
		int index = segments.indexOf(segment);
		if (index >= 0 && index < segments.size() - 1) {// checks if the segment exists and isnt a tail segment.
			return segments.get(index + 1);// finds segment behind
		}
		return null;// failed to find segment behind, or if it was the tail.
	}
	/**
	 * Get the segment that is behind (closer to the tail segment than) the given
	 * segment. Returns null if there is not segment behind.
	 * 
	 * @param segment the starting segment
	 * @return the segment behind of the given segment or null
	 */
	public BodySegment getSegmentBehind(BodySegment segment) {
		int index = segments.indexOf(segment); // finds index of a segment at a given point
		if (index > 0) {// checks if the segment exists and isnt a tail segment.
			return segments.get(index - 1);// finds segment behind
		}
		return null;// failed to find segment behind, or if it was the tail.
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment ahead (in front of) of it. Returns null if there is no segment ahead
	 * of the given segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment ahead of the given segment or null
	 */
	public Direction getDirectionToSegmentAhead(BodySegment segment) {
		BodySegment segmentAhead = getSegmentAhead(segment); // gets segment ahead
		if (segmentAhead != null) {
			Cell currentCell = segment.getCell();
			Cell aheadCell = segmentAhead.getCell();

			if (currentCell.getCol() > aheadCell.getCol()) {
				return LEFT;
			} else if (currentCell.getCol() < aheadCell.getCol()) {
				return RIGHT;
			} else if (currentCell.getRow() < aheadCell.getRow()) {
				return DOWN;
			} else if (currentCell.getRow() > aheadCell.getRow()) {
				return UP;
			}
		}
		return null;
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment behind it. Returns null if there is no segment behind of the given
	 * segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment behind of the given segment or null
	 */
	public Direction getDirectionToSegmentBehind(BodySegment segment) {// identical code to previous method but replaces
																		// aheadSegment with behindSegment
		BodySegment segmentBehind = getSegmentBehind(segment); // gets segment ahead
		if (segmentBehind != null) {
			Cell currentCell = segment.getCell();
			Cell behindCell = segmentBehind.getCell();

			if (currentCell.getCol() > behindCell.getCol()) {
				return LEFT;
			} else if (currentCell.getCol() < behindCell.getCol()) {
				return RIGHT;
			} else if (currentCell.getRow() < behindCell.getRow()) {
				return DOWN;
			} else if (currentCell.getRow() > behindCell.getRow()) {
				return UP;
			}
		}
		return null;
	}

	/**
	 * Gets the direction in which the head segment is pointing. This is the
	 * direction formed by going from the segment behind the head segment to the
	 * head segment. A lizard that does not have more than one segment has no
	 * defined head direction and returns null.
	 * 
	 * @return the direction in which the head segment is pointing or null
	 */
	public Direction getHeadDirection() {
		if (segments.size() < 2) {
			return null;
		}
		return getDirectionToSegmentAhead(segments.get(segments.size() - 2));
	}

	/**
	 * Gets the direction in which the tail segment is pointing. This is the
	 * direction formed by going from the segment ahead of the tail segment to the
	 * tail segment. A lizard that does not have more than one segment has no
	 * defined tail direction and returns null.
	 * 
	 * @return the direction in which the tail segment is pointing or null
	 */
	public Direction getTailDirection() {
		if (segments.size() < 2) {
			return null;
		}
		return getDirectionToSegmentBehind(segments.get(1));
	}

	@Override
	public String toString() {
		String result = "";
		for (BodySegment seg : getSegments()) {
			result += seg + " ";
		}
		return result;
	}
}
