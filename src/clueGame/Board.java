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
		try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	// Set the files to load the data from
	public void setConfigFiles(String cvsFile, String txtFile) {
		layoutConfigFile = "data/" + cvsFile;
		setupConfigFile = "data/" + txtFile;
		
	}
	
	// Load the setup
	public void loadSetupConfig() throws BadConfigFormatException{
		// Reading in txt file and figuring out data
		roomMap = new HashMap<Character, Room>();
	
		try {
			FileReader reader = new FileReader(setupConfigFile);
			Scanner scan = new Scanner(reader);
			while(scan.hasNextLine()) {
				String temp = scan.nextLine();
				
				if(temp.contains("//") || temp.length() == 0) {
					continue;
				}
				
				String[] tempList = temp.split(", ");
				String tempString = tempList[0];
				String roomString = "Room";
				String spaceString = "Space";
				if(tempString.equals(roomString) == false && tempString.equals(spaceString) == false) { 
					throw new BadConfigFormatException("Config File Does Not Have Proper Format");
				}
				
				Room room = new Room(tempList[1]);
				Character symbol = tempList[2].charAt(0);
				roomMap.put(symbol, room);
			}
			
			scan.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find layout CVS file");
		}
		
	}

	// Load the layout
	public void loadLayoutConfig() throws BadConfigFormatException{
		// Reading in cvs file and figuring out data
		List<String[]> data = new ArrayList<String[]>();

		// read data from csv and store in a array list of strings by splitting on each ','
		try {
			FileReader reader = new FileReader(layoutConfigFile);
			Scanner scan = new Scanner(reader);
			while (scan.hasNextLine()) {
				String temp = scan.nextLine();
				String[] tempList = temp.split(",");
				data.add(tempList);
			}

			scan.close();

		} catch (FileNotFoundException e) {
			System.out.println("Unable to find layout CVS file");
		}
		
		// Set the number of rows
		numRows = data.size();
		
		// Testing to make sure every row has the same number of columns
		for(int i = 0; i < numRows - 1; i++){
			if(data.get(i).length != data.get(i+1).length) {
				throw new BadConfigFormatException("Not all columns are the same size");
			}
		}

		// Set the number of columns
		numColumns = data.get(0).length;

		// Create the grid with the number of rows and columns from the input file
		grid = new BoardCell[numRows][numColumns];

		// Set the characters in the board for the rooms, walkways, and special cells
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				
				String dataStr = data.get(i)[j];
				char initial = dataStr.charAt(0);
				
				grid[i][j] = new BoardCell(i, j, initial);
				

				if(roomMap.containsKey(initial)) {
					grid[i][j].setRoom(true);
				} else {
					throw new BadConfigFormatException("Board Layout Refers to Room not in Setup File");
				}
				
				// Set the special cells
				if (dataStr.length() > 1) {
					
					char secondChar = dataStr.charAt(1);
					if(roomMap.containsKey(secondChar)) {
						grid[i][j].setIsSecretPassage(true);
						grid[i][j].setSecretPassageChar(secondChar);
					}
					if (secondChar == '#') {
						roomMap.get(initial).setLabelCell(grid[i][j]);
						grid[i][j].setRoomLabel(true);
					}
					if (secondChar == '*') {
						roomMap.get(initial).setCenterCell(grid[i][j]);
						grid[i][j].setRoomCenter(true);
					}
					
					// Set doorways
					if(secondChar == '^') {
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.UP);
					} else if (secondChar == 'v') {
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.DOWN);
					} else if (secondChar == '<') {
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.LEFT);
					} else if (secondChar == '>') {
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
		return roomMap.get(cell.getInitial());
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
