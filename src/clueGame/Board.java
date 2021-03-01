 /* @author Gustavo Alves
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
	
	private Board() {
		super();
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		// TODO Auto-generated method stub
		grid = new BoardCell[numRows][numColumns];
		roomMap = new HashMap<Character, Room>();
	}
	
	public void setConfigFiles(String cvsFile, String txtFile) {
		// TODO Auto-generated method stub
		layoutConfigFile = cvsFile;
		setupConfigFile = txtFile;
		
	}

	public void loadSetupConfig() {
		// TODO Auto-generated method stub
		
	}

	public void loadLayoutConfig() {
		// TODO Auto-generated method stub
		
	}

	public Room getRoom(char c) {
		// TODO Auto-generated method stub
		return new Room();
	}
	
	public Room getRoom(BoardCell cell) {
		// TODO Auto-generated method stub
		return new Room();
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}
	
	// Obtaining cell at specific row and col
	public BoardCell getCell(int row, int col) {
		return new BoardCell();
	}
}
