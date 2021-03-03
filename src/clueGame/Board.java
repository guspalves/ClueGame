 /**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the TestBoard to run JUnit tests
 */

package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
		loadSetupConfig();
		loadLayoutConfig();
	}
	
	// Set the files to load the data from
	public void setConfigFiles(String cvsFile, String txtFile) {
		layoutConfigFile = "data/" + cvsFile;
		setupConfigFile = "data/" + txtFile;
		
	}
	
	// Load the setup
	public void loadSetupConfig() {
		// Reading in txt file and figuring out data
		roomMap = new HashMap<Character, Room>();
		try {
			FileReader reader = new FileReader(setupConfigFile);
			Scanner in = new Scanner(reader);
			while(in.hasNextLine()) {
				String temp = in.nextLine();
				
				if(temp.contains("//") || temp.length() == 0) {
					continue;
				}
				
				String[] tempList = temp.split(", ");
				Room room = new Room(tempList[1]);
				Character symbol = tempList[2].charAt(0);
				roomMap.put(symbol, room);
			}
			
			in.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find layout CVS file");
		}
		
	}

	// Load the layout
	public void loadLayoutConfig() {
		// Reading in cvs file and figuring out data
		List<String[]> data = new ArrayList<String[]>();

		try {
			FileReader reader = new FileReader(layoutConfigFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				String temp = in.nextLine();
				String[] tempList = temp.split(",");
				data.add(tempList);
			}

			in.close();

		} catch (FileNotFoundException e) {
			System.out.println("Unable to find layout CVS file");
		}

		numColumns = data.get(0).length;
		numRows = data.size();

		grid = new BoardCell[numRows][numColumns];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				
				char initial = data.get(i)[j].charAt(0);
				
				grid[i][j] = new BoardCell(i, j, initial);

				if(roomMap.containsKey(initial)) {
					grid[i][j].setRoom(true);
				}
				
				if (data.get(i)[j].length() > 1) {
					char secondChar = data.get(i)[j].charAt(1);
					if (secondChar == '#') {
						grid[i][j].setRoomLabel(true);
					}
					if (secondChar == '*') {
						grid[i][j].setRoomCenter(true);
					}
					
					if(secondChar == '^') {
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.UP);
					} else if (secondChar == 'v') {
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.DOWN);
					} else if (secondChar == '<') {
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.LEFT);
					}else if (secondChar == '>') {
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.RIGHT);
					}
				}
			}
		}
	}

	// Getter for the room given a char
	public Room getRoom(char c) {
		return roomMap.get(c);
	}
	
	// Getter for the room given a cell
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell);
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
		return grid[row][col];
	}
}
