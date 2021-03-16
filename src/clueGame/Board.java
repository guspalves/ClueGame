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

import Experiment.TestBoardCell;

public class Board {
	// Instance variables
	private BoardCell[][] grid;
	private int numRows, numColumns;
	private String layoutConfigFile, setupConfigFile;
	private List<BoardCell> totalDoorWays;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
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
		// Init for sets and arrays
		totalDoorWays = new ArrayList<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		} finally {
			calculateAdjacency();
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
				
				if(tempString.equals("Room") == false && tempString.equals("Space") == false) { 
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
					if(initial != 'W' && initial != 'X') {
						grid[i][j].setRoom(true);
					} else {
						grid[i][j].setRoom(false);
					}
				} else {
					throw new BadConfigFormatException("Board Layout Refers to Room not in Setup File");
				}

				// Set the special cells
				if (dataStr.length() > 1) {

					char secondChar = dataStr.charAt(1);
					if(roomMap.containsKey(secondChar)) {
						grid[i][j].setIsSecretPassage(true);
						grid[i][j].setSecretPassageChar(secondChar);

						// Setting the secret passage parameters in target Room
						roomMap.get(initial).setHasSecretpassage(true);
						roomMap.get(initial).setSecretPassage(grid[i][j]);
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
						totalDoorWays.add(grid[i][j]);
					} else if (secondChar == 'v') {
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.DOWN);
						totalDoorWays.add(grid[i][j]);
					} else if (secondChar == '<') {
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.LEFT);
						totalDoorWays.add(grid[i][j]);
					} else if (secondChar == '>') {
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.RIGHT);
						totalDoorWays.add(grid[i][j]);
					}
				}
			}
		}
	}

	// Method to determine which door leads to what room
	public void parseDoorways() {
		for(BoardCell door : totalDoorWays) {
			int row = door.getRow();
			int col = door.getCol();
			switch(door.getDoorDirection()) {
			case UP:
				// Adding that doorway to the ArrayList stored in Room
				roomMap.get(grid[row - 1][col].getInitial()).addDoorway(door);
				// Setting the EntryToRoom character in the BoardCell corresponding to the board at that index
				grid[row][col].setEntryToRoom(grid[row - 1][col].getInitial());
				break;
			case DOWN:
				roomMap.get(grid[row + 1][col].getInitial()).addDoorway(door);
				grid[row][col].setEntryToRoom(grid[row + 1][col].getInitial());
				break;
			case RIGHT:
				roomMap.get(grid[row][col + 1].getInitial()).addDoorway(door);
				grid[row][col].setEntryToRoom(grid[row][col + 1].getInitial());
				break;
			case LEFT:
				roomMap.get(grid[row][col - 1].getInitial()).addDoorway(door);
				grid[row][col].setEntryToRoom(grid[row][col - 1].getInitial());
				break;
			}
		}
	}

	public void calculateAdjacency() {
		// Calculating which door ways lead to what rooms
		parseDoorways();

		// Looping through grid
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				char initial = grid[i][j].getInitial();

				// Checks if it's a room		
				if(initial != 'W' && initial != 'X') {
					Room room = roomMap.get(initial);

					// Adding door ways to adjacency list of room
					ArrayList<BoardCell> doors = room.getDoorWays();
					for(BoardCell door : doors) {
						grid[i][j].addAdjacency(door);
					}

					// Calculating adjacency if it has a secret passage
					if(room.isHasSecretpassage()) {
						// Adding secret passage to adjacency
						BoardCell temp = room.getPassage();

						// Adding center cell of end destination of secret passage to adjList
						grid[i][j].addAdjacency(roomMap.get(temp.getSecretPassage()).getCenterCell());
					}

					continue;
				}

				// Calculating adjacency of doorways
				if(grid[i][j].isDoorway()) {
					grid[i][j].addAdjacency(roomMap.get(grid[i][j].getEntryToRoom()).getCenterCell());
					if((i-1) >= 0 && grid[i-1][j].getInitial() == 'W') {
						grid[i][j].addAdjacency(grid[i-1][j]);
					}
					if((i+1) < numRows && grid[i+1][j].getInitial() == 'W') {
						grid[i][j].addAdjacency(grid[i+1][j]);
					}
					if((j-1) >= 0 && grid[i][j-1].getInitial() == 'W') {
						grid[i][j].addAdjacency(grid[i][j-1]);
					}
					if((j+1) < numColumns && grid[i][j+1].getInitial() == 'W') {
						grid[i][j].addAdjacency(grid[i][j+1]);
					}

					continue;
				}

				// Making sure it doesn't go outside the grid and adding adjacent walkways
				if((i - 1) >= 0) {
					if(grid[i-1][j].getInitial() == 'W') {
						grid[i][j].addAdjacency(grid[i-1][j]);
					}
				}
				if((i+1) < numRows) {
					if(grid[i+1][j].getInitial() == 'W') {
						grid[i][j].addAdjacency(grid[i+1][j]);
					}
				}
				if((j-1) >= 0) {
					if(grid[i][j-1].getInitial() == 'W') {
						grid[i][j].addAdjacency(grid[i][j-1]);
					}
				}
				if((j+1) < numColumns) {
					if(grid[i][j+1].getInitial() == 'W') {
						grid[i][j].addAdjacency(grid[i][j+1]);
					}
				}
			}
		}
	}


	public void calcTargets(BoardCell cell, int pathlength) {
		// Setting the visited and targets sets to be empty
		visited.clear();
		targets.clear();

		// Adding start location to the visited list
		visited.add(cell);

		// Calling the findALlTargets function
		findAllTargets(cell, pathlength);
	}

	public void findAllTargets(BoardCell startCell, int pathlength) {
		// Obtaining set of adjacency of startCell
		Set<BoardCell> adjacentCells = startCell.getAdjList();

		// for each loop to go through adjacency set for startCell
		for(BoardCell adjCell : adjacentCells) {
			// check if the cell is occupied or has been visited
			if(visited.contains(adjCell) || adjCell.getIsOccupied() == true) {
				if(roomMap.get(adjCell.getInitial()).getCenterCell() == adjCell && !visited.contains(adjCell)) {
					targets.add(adjCell);
				}

				continue;
			}

			// check if the cell is a room
			if(adjCell.isRoom() == true) {
				targets.add(adjCell);
				continue;
			}

			// Adding adjCell to visited set
			visited.add(adjCell);

			// Recursive call
			if(pathlength == 1) targets.add(adjCell);
			else findAllTargets(adjCell, (pathlength - 1));

			// Removing adjCell from visited list
			visited.remove(adjCell);
		}
	}


	/*
	 * Getters
	 */
	
	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

}
