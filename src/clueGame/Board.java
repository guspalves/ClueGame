/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the TestBoard to run JUnit tests
 */

package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import Experiment.TestBoardCell;

public class Board {
	// Instance variables
	private BoardCell[][] grid;
	private int numRows, numColumns;
	private String layoutConfigFile, setupConfigFile;
	private char walkwayChar;
	private char unusedChar;
	private List<BoardCell> totalDoorWays;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private ArrayList<Player> playerArr;
	private ArrayList<Card> weaponArr;
	private ArrayList<Card> roomArr;
	private static Board theInstance = new Board();
	private ArrayList<Card> deck;
	private Solution theAnswer;

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
		playerArr = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		weaponArr = new ArrayList<Card>();
		roomArr = new ArrayList<Card>();
		
		try {
			loadSetupConfig();
			loadLayoutConfig();
			if(!weaponArr.isEmpty() && !roomArr.isEmpty() && !playerArr.isEmpty()) {
				deal();
			}
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
				
				if(!tempString.equals("Room") && !tempString.equals("Space") && !tempString.equals("Player") && !tempString.equals("Weapon")) { 
					throw new BadConfigFormatException("Config File Does Not Have Proper Format");
				}
				
				// Handles reading in rooms and spaces
				if(tempString.equals("Room") || tempString.equals("Space")) {
					if(tempList[1].equals("Walkway")) walkwayChar = tempList[2].charAt(0);
					if(tempList[1].equals("Unused")) unusedChar = tempList[2].charAt(0);
					
					Room room = new Room(tempList[1]);
					Character symbol = tempList[2].charAt(0);
					
					roomMap.put(symbol, room);
					if(tempString.equals("Room")) {
						Card roomCard = new Card(tempList[1], CardType.ROOM);
						deck.add(roomCard);
						roomArr.add(roomCard);
					}
					continue;
				}
				
				// Handles reading in players
				if(tempString.equals("Player")) {
					String name = tempList[1];
					String colorString = tempList[2];
					
					// Initializing color
					Color color = null;
					
					// Switch statement to figure out color
					switch(colorString) {
						case "Black":
							color = Color.black;
							break;
						case "Blue":
							color = Color.blue;
							break;
						case "Yellow":
							color = Color.yellow;
							break;
						case "Red":
							color = Color.red;
							break;
						case "Green":
							color = Color.green;
							break;
						case "Pink":
							color = Color.pink;
							break;
						
						default:
							throw new BadConfigFormatException("Config File Does Not Have Proper Format");
					}
					
					// Figuring out starting location
					int startRow = Integer.parseInt(tempList[4]);
					int startCol = Integer.parseInt(tempList[5]);
					
					// Creating player
					if(tempList[3].equals("Human")) {
						HumanPlayer player = new HumanPlayer(name, color, startRow, startCol);
						playerArr.add(player);
						Card playerCard = new Card(name, CardType.PERSON);
						deck.add(playerCard);
					} else if(tempList[3].equals("Computer"))  {
						ComputerPlayer player = new ComputerPlayer(name, color, startRow, startCol);
						playerArr.add(player);
						Card playerCard = new Card(name, CardType.PERSON);
						deck.add(playerCard);
					} else {
						throw new BadConfigFormatException("Config File Does Not Have Proper Format");
					}
					
					// Going to next iteration
					continue;
				}
				
				if(tempString.equals("Weapon")) {
					String weaponName = tempList[1];
					Card weaponCard = new Card(weaponName, CardType.WEAPON);
					deck.add(weaponCard);
					weaponArr.add(weaponCard);
				}
				
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
					if(initial != walkwayChar && initial != unusedChar) {
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
					switch(secondChar) {
					case '^':
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.UP);
						totalDoorWays.add(grid[i][j]);
						break;
					case 'v':
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.DOWN);
						totalDoorWays.add(grid[i][j]);
						break;
					case '<':
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.LEFT);
						totalDoorWays.add(grid[i][j]);
						break;
					case '>':
						grid[i][j].setIsDoorway(true);
						grid[i][j].setDoorDirection(DoorDirection.RIGHT);
						totalDoorWays.add(grid[i][j]);
						break;
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
				// Obtaining wanted cell
				BoardCell tempCell = grid[i][j];
				
				// Obtaining wanted char
				char initial = tempCell.getInitial();

				// Checks if it's a room		
				if(initial != walkwayChar && initial != unusedChar) {
					Room room = roomMap.get(initial);

					// Adding door ways to adjacency list of room
					ArrayList<BoardCell> doors = room.getDoorWays();
					for(BoardCell door : doors) {
						tempCell.addAdjacency(door);
					}

					// Calculating adjacency if it has a secret passage
					if(room.isHasSecretpassage()) {
						// Adding secret passage to adjacency
						BoardCell temp = room.getPassage();

						// Adding center cell of end destination of secret passage to adjList
						tempCell.addAdjacency(roomMap.get(temp.getSecretPassage()).getCenterCell());
					}

					continue;
				}

				// Calculating adjacency of doorways
				if(tempCell.isDoorway()) {
					tempCell.addAdjacency(roomMap.get(tempCell.getEntryToRoom()).getCenterCell());
				}

				// Making sure it doesn't go outside the grid and adding adjacent walkways
				if((i - 1) >= 0) {
					if(grid[i-1][j].getInitial() == walkwayChar) {
						tempCell.addAdjacency(grid[i-1][j]);
					}
				}
				if((i+1) < numRows) {
					if(grid[i+1][j].getInitial() == walkwayChar) {
						tempCell.addAdjacency(grid[i+1][j]);
					}
				}
				if((j-1) >= 0) {
					if(grid[i][j-1].getInitial() == walkwayChar) {
						tempCell.addAdjacency(grid[i][j-1]);
					}
				}
				if((j+1) < numColumns) {
					if(grid[i][j+1].getInitial() == walkwayChar) {
						tempCell.addAdjacency(grid[i][j+1]);
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
				// Making sure the player can go to room center even if it is occupied
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

	public void deal(){
		// Use random numbers to create theAnswer
		ArrayList<Card> tempDeck = new ArrayList<Card>(deck);
		
		Random random = new Random();
		int roomCardIndex = random.nextInt(roomArr.size());
		int personCardIndex = random.nextInt(playerArr.size() - 1) + roomArr.size();
		int weaponCardIndex = random.nextInt(weaponArr.size() - 2) + playerArr.size() + roomArr.size();
		
		Card roomCard = deck.get(roomCardIndex);
		tempDeck.remove(roomCardIndex);
		Card personCard = deck.get(personCardIndex);
		tempDeck.remove(personCardIndex);
		Card weaponCard = deck.get(weaponCardIndex);
		tempDeck.remove(weaponCardIndex);
		
		// Creating solution
		theAnswer = new Solution(personCard, roomCard, weaponCard);
		
		int playerIndex = 0;
		while(!tempDeck.isEmpty()) {
			int cardDealtIndex = random.nextInt(tempDeck.size());
			
			Card cardDealt = tempDeck.get(cardDealtIndex);
			tempDeck.remove(cardDealtIndex);
			
			playerArr.get(playerIndex).addCard(cardDealt);
			
			playerIndex++;
			
			if(playerIndex >= playerArr.size()) {
				playerIndex = 0;
			}
		}
	}
	
	/*
	 * Getters
	 */
	
	public Solution getTheAnswer() {
		return theAnswer;
	}

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

	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public ArrayList<Player> getPlayerArray() {
		return playerArr;
	}
}