package hw3;

import api.BodySegment;
import api.Exit;
import api.Wall;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utility class with static methods for loading game files.
 * 
 * @author Gavin Nienke
 */
public class GameFileUtil {

	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 */
	public static void load(String filePath, LizardGame game) {
		try {
		Scanner scanner = new Scanner(new File(filePath));

		
		String dimensionsLine = scanner.nextLine();
		    String[] dimensions = dimensionsLine.split("x");
		    int boardWidth = Integer.parseInt(dimensions[0].trim());
		    int boardHeight = Integer.parseInt(dimensions[1].trim());
		    game.resetGrid(boardWidth, boardHeight);

		    int rowIndex = 0;//scans position of lizard
		    while (scanner.hasNextLine()) {
		        String line = scanner.nextLine();
		        if (line.startsWith("L")) {
		            String[] segments = line.substring(2).trim().split(" ");
		            ArrayList<BodySegment> segmentList = new ArrayList<>();
		            Lizard lizard = new Lizard();
		            
		            for (String segment : segments) {
		                String[] parts = segment.split(",");
		                if (parts.length == 2) {
		                    int columnIndex = Integer.parseInt(parts[0].trim());
		                    int cellRow = Integer.parseInt(parts[1].trim());
		                    BodySegment bodySegment = new BodySegment(lizard, game.getCell(columnIndex, cellRow));
		                    segmentList.add(bodySegment);
		                }
		            }

		            lizard.setSegments(segmentList);
		            game.addLizard(lizard);
		        } else {
		            for (int columnIndex = 0; columnIndex < boardWidth; columnIndex++) {
		                char c = line.charAt(columnIndex);
		                if (c == 'W') {
		                    Wall wall = new Wall(game.getCell(columnIndex, rowIndex));
		                    game.addWall(wall);
		                } else if (c == 'E') {
		                    Exit exit = new Exit(game.getCell(columnIndex, rowIndex));
		                    game.addExit(exit);
		                }
		            }
		        }
		        rowIndex++;
		    }

		   
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
	}
}