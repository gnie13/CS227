package hw3;



import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;
import api.Exit;
import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Wall;

/**
 * Class that models a game.
 * 
 * @author Gavin Nienke
 */
public class LizardGame {
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	private ArrayList<Lizard> lizards;
	private int height;
	private int width;
	private ArrayList<Wall> walls;
	private ArrayList<Exit> exits;
	private Cell[][] grid;

	/**
	 * Constructs a new LizardGame object with given grid dimensions.
	 * 
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public LizardGame(int width, int height) {
		this.width = width;
		this.height = height;

		this.walls = new ArrayList<>();
		this.exits = new ArrayList<>();

		this.grid = new Cell[width][height];
		this.lizards = new ArrayList<Lizard>();

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				this.grid[col][row] = new Cell(col, row);
			}
		}
		resetGrid(width, height);
	}

	/**
	 * Get the grid's width.
	 * 
	 * @return width of the grid
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the grid's height.
	 * 
	 * @return height of the grid
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Adds a wall to the grid.
	 * <p>
	 * Specifically, this method calls placeWall on the Cell object associated with
	 * the wall (see the Wall class for how to get the cell associated with the
	 * wall). This class assumes a cell has already been set on the wall before
	 * being called.
	 * 
	 * @param wall to add
	 */
	public void addWall(Wall wall) {
		Cell cell = wall.getCell();
		cell.placeWall(wall);
	}

	/**
	 * Adds an exit to the grid.
	 * <p>
	 * Specifically, this method calls placeExit on the Cell object associated with
	 * the exit (see the Exit class for how to get the cell associated with the
	 * exit). This class assumes a cell has already been set on the exit before
	 * being called.
	 * 
	 * @param exit to add
	 */
	public void addExit(Exit exit) {
		exit.getCell().placeExit(exit);

	}

	/**
	 * Gets a list of all lizards on the grid. Does not include lizards that have
	 * exited.
	 * 
	 * @return lizards list of lizards
	 */
	public ArrayList<Lizard> getLizards() {
		return lizards;
	}

	/**
	 * Adds the given lizard to the grid.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards.
	 * 
	 * @param lizard to add
	 */
	public void addLizard(Lizard lizard) {
		lizards.add(lizard);
		if (scoreListener != null) {
			scoreListener.updateScore(lizards.size());
		}

	}

	/**
	 * Removes the given lizard from the grid. Be aware that each cell object knows
	 * about a lizard that is placed on top of it. It is expected that this method
	 * updates all cells that the lizard used to be on, so that they now have no
	 * lizard placed on them.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards using
	 * updateScore().
	 * 
	 * @param lizard to remove
	 */
	public void removeLizard(Lizard lizard) {

		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < getWidth(); col++) {
				Cell cell = grid[col][row];
				if (cell.getLizard() == lizard) {
					cell.placeLizard(null);

				}
			}
		}
		lizards.remove(lizard);
		if (scoreListener != null) {
			scoreListener.updateScore(lizards.size());
		}
	}

	// private checkCanMove(int row, int col, Lizard lizard) {

	// return null;

	/**
	 * Gets the cell for the given column and row.
	 * <p>
	 * If the column or row are outside of the boundaries of the grid the method
	 * returns null.
	 * 
	 * @param col column of the cell
	 * @param row of the cell
	 * @return the cell or null
	 */
	public Cell getCell(int col, int row) {
		if (col < 0 || col >= width || row < 0 || row >= height) {
			return null;
		}
		return grid[col][row];
	}

	/**
	 * Gets the cell that is adjacent to (one over from) the given column and row,
	 * when moving in the given direction. For example (1, 4, UP) returns the cell
	 * at (1, 3).
	 * <p>
	 * If the adjacent cell is outside of the boundaries of the grid, the method
	 * returns null.
	 * 
	 * @param col the given column
	 * @param row the given row
	 * @param dir the direction from the given column and row to the adjacent cell
	 * @return the adjacent cell or null
	 */
	public Cell getAdjacentCell(int col, int row, Direction dir) {
		switch (dir) {
		case LEFT:
			col--;
			break;
		case RIGHT:
			col++;
			break;
		case UP:
			row--;
			break;
		case DOWN:
			row++;
			break;
		default:
			return null;
		}
		if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
			return null;
		}
		// return cell a new position
		return grid[col][row];
	}

	/**
	 * Resets the grid. After calling this method the game should have a grid of
	 * size width x height containing all empty cells. Empty means cells with no
	 * walls, exits, etc.
	 * <p>
	 * All lizards should also be removed from the grid.
	 * 
	 * @param width  number of columns of the resized grid
	 * @param height number of rows of the resized grid
	 */
	public void resetGrid(int width, int height) {

		walls.clear();
		exits.clear();
		lizards.clear();

		this.width = width;
		this.height = height;
		this.grid = new Cell[width][height];
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				this.grid[col][row] = new Cell(col, row);
			}
		}
	}

	/**
	 * Returns true if a given cell location (col, row) is available for a lizard to
	 * move into. Specifically the cell cannot contain a wall or a lizard. Any other
	 * type of cell, including an exit is available.
	 * 
	 * @param row of the cell being tested
	 * @param col of the cell being tested
	 * @return true if the cell is available, false otherwise
	 */
	public boolean isAvailable(int col, int row) {
		Cell cell = getCell(col, row);

		return cell != null && cell.getLizard() == null && cell.getWall() == null;// cell is empty, no lizard or wall is
																					// in cell
	}

	/**
	 * Move the lizard specified by its body segment at the given position (col,
	 * row) one cell in the given direction. The entire body of the lizard must move
	 * in a snake like fashion, in other words, each body segment pushes and pulls
	 * the segments it is connected to forward or backward in the path of the
	 * lizard's body. The given direction may result in the lizard moving its body
	 * either forward or backward by one cell.
	 * <p>
	 * The segments of a lizard's body are linked together and movement must always
	 * be "in-line" with the body. It is allowed to implement movement by either
	 * shifting every body segment one cell over or by creating a new head or tail
	 * segment and removing an existing head or tail segment to achieve the same
	 * effect of movement in the forward or backward direction.
	 * <p>
	 * If any segment of the lizard moves over an exit cell, the lizard should be
	 * removed from the grid.
	 * <p>
	 * If there are no lizards left on the grid the player has won the puzzle the
	 * the dialog listener should be used to display (see showDialog) the message
	 * "You win!".
	 * <p>
	 * It is possible that the given direction is not in-line with the body of the
	 * lizard (as described above), in that case this method should do nothing.
	 * <p>
	 * It is possible that the given column and row are outside the bounds of the
	 * grid, in that case this method should do nothing.
	 * <p>
	 * It is possible that there is no lizard at the given column and row, in that
	 * case this method should do nothing.
	 * <p>
	 * It is possible that the lizard is blocked and cannot move in the requested
	 * direction, in that case this method should do nothing.
	 * <p>
	 * <b>Developer's note: You may have noticed that there are a lot of details
	 * that need to be considered when implement this method method. It is highly
	 * recommend to explore how you can use the public API methods of this class,
	 * Grid and Lizard (hint: there are many helpful methods in those classes that
	 * will simplify your logic here) and also create your own private helper
	 * methods. Break the problem into smaller parts are work on each part
	 * individually.</b>
	 * 
	 * @param col the given column of a selected segment
	 * @param row the given row of a selected segment
	 * @param dir the given direction to move the selected segment
	 */
	public void move(int col, int row, Direction dir) {
	    //currentCell is passed into move()
		Cell currentCell = getCell(col, row);
		//checks if empty
	    if (currentCell.getLizard() == null)
	        return;
	    //moving lizard, along with head, tail, and segment that can all be passed in through the currentCell. 
	    Lizard lizOnCell = currentCell.getLizard();
	    BodySegment lizardHead = lizOnCell.getHeadSegment();
	    BodySegment lizardTail = lizOnCell.getTailSegment();
	    BodySegment pickedSegment = lizOnCell.getSegmentAt(currentCell);
	    //array containing all lizard segments. 
	    ArrayList<BodySegment> segments = lizOnCell.getSegments();
	    //head and tail of lizard starting point.
	    Cell headCell = lizardHead.getCell();
	    Cell tailCell = lizardTail.getCell();
	    //checks if the direction of the behind segment equals the current direction
	    Direction directionToSegmentBehind = lizOnCell.getDirectionToSegmentBehind(pickedSegment);
	    //checks if the direction of the ahead segment equals the current direction
	    Direction directionToSegmentAhead = lizOnCell.getDirectionToSegmentAhead(pickedSegment);
	    //rows and cols of the head and tail cell. Helps determine if the adjacent cells are open and can be moved to. 
	    int headCellCol = headCell.getCol();
	    int headCellRow = headCell.getRow();
	    int tailCellCol = tailCell.getCol();
	    int tailCellRow = tailCell.getRow();
	    
	    Cell headCellToMove = getAdjacentCell(headCellCol, headCellRow, dir);
	    Cell tailAdjancentCell = getAdjacentCell(tailCellCol, tailCellRow, dir);
	    //returns true if the head can move forward and the tail can move backward respectively. 
	    boolean canMoveForward = headCellToMove != null && isAvailable(headCellToMove.getCol(), headCellToMove.getRow());
	    boolean canMoveBackward = tailAdjancentCell != null && isAvailable(tailAdjancentCell.getCol(), tailAdjancentCell.getRow());
	    //checks if the currently picked segment is either the lizard's head or tail 
	    //if it is the head segment, it assigns the direction dir to the direction ahead. If it is the tail segment, it assigns the direction dir to the direciton behind.  
	    
	    if (pickedSegment == lizardHead)
	        directionToSegmentAhead = dir;
	    if (pickedSegment == lizardTail)
	        directionToSegmentBehind = dir;
	    // this is the movement of the lizard's body segments through the user pulling the snake towards the direction of its head. 
	    // checks if the lizard makes it to the exit and prints "You Win!" if true. 
	    if (dir == directionToSegmentAhead && canMoveForward) {
	        BodySegment tail = lizOnCell.getTailSegment();
	        tail.getCell().removeLizard();
	        //shifts all segments one in the head direction. 
	        for (int i = 0; i < segments.size() - 1; i++) {
	            Cell cell = segments.get(i + 1).getCell();
	            segments.get(i).setCell(cell);
	        }
	      //puts the lizard head as the very last element of the array list. 
	        BodySegment head = segments.get(segments.size() - 1);
	        head.setCell(headCellToMove);
	        //checks if the user makes it into the exit using the lizards head. 
	        Exit exit = headCellToMove.getExit();
	        if (exit != null) {
	            removeLizard(lizOnCell);
	            if (lizards.isEmpty()) {
	                dialogListener.showDialog("You Win!");
	            }
	        }
	    }
	    //similar to the movement of the lizard's body by pulling towards direction of the head, 
	    //this is used when the user pulls towards the tail. 
	    //prints "You Win!" if the user makes it to an exit. 
	    if (dir == directionToSegmentBehind && canMoveBackward) {
	        BodySegment head = lizOnCell.getHeadSegment();
	        head.getCell().removeLizard();
	        //shifts all segments one in the tail direction. 
	        for (int i = segments.size() - 1; i > 0; i--) {
	            Cell cell = segments.get(i - 1).getCell();
	            segments.get(i).setCell(cell);
	        }
	        //puts the lizard tail as the first element of the array list. 
	        BodySegment tail = segments.get(0);
	        tail.setCell(tailAdjancentCell);
	        //checks if the user makes it to the exit
	        Exit exit = tailAdjancentCell.getExit();
	        if (exit != null) {
	            removeLizard(lizOnCell);
	            if (lizards.isEmpty()) {
	                dialogListener.showDialog("You Win!");
	            }
	        }
	    }
	}

	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}

	@Override
	public String toString() {
		String str = "---------- GRID ----------\n";
		str += "Dimensions:\n";
		str += getWidth() + " " + getHeight() + "\n";
		str += "Layout:\n";
		for (int y = 0; y < getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			for (int x = 0; x < getWidth(); x++) {
				str += getCell(x, y);
			}
		}
		str += "\nLizards:\n";
		for (Lizard l : getLizards()) {
			str += l;
		}
		str += "\n--------------------------\n";
		return str;
	}
}
