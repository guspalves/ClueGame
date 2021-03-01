 /**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the TestBoard to run JUnit tests
 */

package clueGame;

import java.util.*;

public class Board {
	// Instance variables
	private BoardCell[][] grid;
	private int numRows, numColumns;
	private String layoutConfigFile, setupConfigFile;
	private Map<Character, Room> roomMap;
	
	private static Board theInstance = new Board();
	
	// Board constructor
	private Board() {
		super();
	}
	
	// Getter for instance
	public static Board getInstance() {
		return theInstance;
	}
	
	// Set up for the board
	public void initialize() {
		grid = new BoardCell[numRows][numColumns];
		roomMap = new HashMap<Character, Room>();
	}
	
	// Set the files to load the data from
	public void setConfigFiles(String cvsFile, String txtFile) {
		layoutConfigFile = cvsFile;
		setupConfigFile = txtFile;
		
	}
	
	// Load the setup
	public void loadSetupConfig() {
		
	}

	// Load the layout
	public void loadLayoutConfig() {
		
	}

	// Getter for the room given a char
	public Room getRoom(char c) {
		return new Room();
	}
	
	// Getter for the room given a cell
	public Room getRoom(BoardCell cell) {
		return new Room();
	}

	// getter for the number of rows
	public int getNumRows() {
		return numRows;
	}

	// getter for the number of columns 
	public int getNumColumns() {
		return numColumns;
	}
	
	// Obtaining cell at specific row and col
	public BoardCell getCell(int row, int col) {
		return new BoardCell();
	}
}
