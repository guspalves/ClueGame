/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the Board for Clue
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.function.BooleanSupplier;
import javax.swing.*;

public class Board extends JPanel implements MouseListener{
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
	private Scanner scan;
	private HumanPlayer humanPlayer;
	private int playerCounter = 0;
	private ArrayList<Character> targetRooms;
	private boolean selectionMade;
	private int errorCounter = 1;
	private Color suggestionDisproveColor;

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
		System.out.println("HELLO");
		// Init for sets and arrays
		totalDoorWays = new ArrayList<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		playerArr = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		weaponArr = new ArrayList<Card>();
		roomArr = new ArrayList<Card>();
		targetRooms = new ArrayList<Character>();

		addMouseListener(this);

		try {
			loadSetupConfig();
			loadLayoutConfig();
			calculateAdjacency();
			deal();
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
			scan = new Scanner(reader);
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
						room.setRoomCard(roomCard);
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
					case "Magenta":
						color = Color.magenta;
						break;
					case "Blue":
						color = Color.cyan;
						break;
					case "Yellow":
						color = Color.yellow;
						break;
					case "Red":
						color = new Color(255, 87, 51);
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
						humanPlayer = player;
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
					continue;
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
						grid[i][j].setRoomName(roomMap.get(initial).getRoomName());
						grid[i][j].setRoomCard(roomMap.get(initial).getRoomCard());
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

	private void deal() throws BadConfigFormatException{
		System.out.println("YO");
		if(weaponArr.isEmpty() || roomArr.isEmpty() || playerArr.isEmpty()) {
			throw new BadConfigFormatException("Full deck not provided");
		}
		// Use random numbers to create theAnswer
		ArrayList<Card> tempDeck = new ArrayList<Card>(deck);

		// Card indices for solution
		Random random = new Random();
		
		int roomCardIndex = random.nextInt(roomArr.size());
		int personCardIndex = random.nextInt(playerArr.size() - 1) + roomArr.size();
		int weaponCardIndex = random.nextInt(weaponArr.size() - 2) + playerArr.size() + roomArr.size();

		// geting cards for solution
		Card roomCard = tempDeck.get(roomCardIndex);
		Card personCard = tempDeck.get(personCardIndex);
		Card weaponCard = tempDeck.get(weaponCardIndex);
		
		tempDeck.remove(weaponCardIndex);
		tempDeck.remove(personCardIndex);	
		tempDeck.remove(roomCardIndex);

		// Creating solution
		theAnswer = new Solution(personCard, roomCard, weaponCard);

		int playerIndex = 0;
		// Deals remaining cards to the players
		while(!tempDeck.isEmpty()) {
			int cardDealtIndex = random.nextInt(tempDeck.size());

			Card cardDealt = tempDeck.get(cardDealtIndex);
			tempDeck.remove(cardDealtIndex);

			playerArr.get(playerIndex).updateHand(cardDealt);

			playerIndex++;

			if(playerIndex >= playerArr.size()) {
				playerIndex = 0;
			}
		}
	}

	public void setAnswer(Card person, Card room, Card weapon) {
		theAnswer = new Solution(person, room, weapon);	
	}

	public boolean checkAccusation(Solution solution) {
		return theAnswer.isSolution(solution);
	}

	public Card handleSuggestion(Solution suggestion) {
		for(Player p : playerArr) {
			if(p.disproveSuggestion(suggestion) != null){
				return p.disproveSuggestion(suggestion);
			}
		}
		return null;
	}

	// Method for testing
	public void setPlayerArr(ArrayList<Player> players) {
		playerArr = players;
	}

	/*
	 * GUI Functions
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int x = 0;
		int y = 0;

		// find width and height of cells
		int width = getWidth() / numColumns;
		int height = getHeight() / numRows;

		// iterate over game board
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				char initial = grid[i][j].getInitial();

				// draw the walkway cells tan
				if(initial == walkwayChar) {
					grid[i][j].draw(g, new Color(221,189,77), Color.black, x, y, width, height);
					x = x + width;

					// draw unused cells black
				} else if(initial == unusedChar) {
					grid[i][j].draw(g, Color.black, Color.black, x, y, width, height);
					x = x + width;

					// draw room cells gray
				} else {
					grid[i][j].draw(g, Color.lightGray, Color.lightGray, x, y, width, height);
					x = x + width;
				}

				if(grid[i][j].isTarget()) {
					grid[i][j].draw(g, Color.yellow, Color.black, x - width, y, width, height);
				}

				for(int k = 0; k < targetRooms.size(); k++) {
					if(grid[i][j].getInitial() == targetRooms.get(k)) {
						grid[i][j].draw(g, Color.yellow, Color.yellow, x - width, y, width, height);
						targets.add(grid[i][j]);
					}
				}

				// Draw doorways
				if(grid[i][j].isDoorway()) {
					switch(grid[i][j].getDoorDirection()) {
					case UP:
						grid[i][j].drawDoor(g, x-width, y, width, 4);
						break;
					case DOWN:
						grid[i][j].drawDoor(g, x-width, y+height-5, width, 4);
						break;
					case RIGHT:
						grid[i][j].drawDoor(g, x-5, y, 4, height);
						break;
					case LEFT:
						grid[i][j].drawDoor(g, x-width, y, 4, height);
						break;
					}

				}
			}
			x = 0;
			y = y + height;
		}

		// Draw players
		for(int i = 0; i < playerArr.size(); i++) {
			Player player = playerArr.get(i);

			// Looping through so if any other players are on the same room, they don't get printed over each other
			for(int j = i+1; j < playerArr.size(); j++) {
				if(playerArr.get(i).getRow() == playerArr.get(j).getRow() && playerArr.get(i).getCol() == playerArr.get(j).getCol()) {
					player.draw(g, player.getCol()*width + 10, player.getRow()*height + 2, width - 4, height - 4);
					break;
				}
			}
			player.draw(g, player.getCol()*width + 2, player.getRow()*height + 2, width - 4, height - 4);
		}

		// Draw room names over the rooms
		for(Map.Entry<Character, Room> entry : roomMap.entrySet()) {
			// Creating temporary room from roomMap
			Room temp = roomMap.get(entry.getKey());

			// Since walkway and usued was added to room, ensuring that they are skipped
			if(temp.getRoomName().equals("Walkway") || temp.getRoomName().equals("Unused")) {
				continue;
			}

			// Getting col, row, and drawing room
			int row = temp.getLabelCell().getRow();
			int col = temp.getLabelCell().getCol();
			temp.draw(g, col*width, row*height);
		}
	}

	//Next button logic
	public void nextPlayerFlow() {	
		// Updating current player
		this.repaint();
		GameControlPanel controlPanel = GameControlPanel.getInstance();
		controlPanel.setGuessResult("", Color.white);
		controlPanel.setGuess("", Color.white);
		

		// Getting current player and updating player
		Player currentPlayer = playerArr.get(playerCounter);

		// Ensuring the user has finished his turn before moving on
		if(currentPlayer instanceof HumanPlayer) {
			if(!((HumanPlayer) currentPlayer).isFinished()){
				// Throwing new error message
				ClueGame game = ClueGame.getInstance();
				game.notFinishedMessage();
				return;
			}
		}

		// Incrementing player
		playerCounter++;

		if(playerCounter >= playerArr.size()) {
			playerCounter = 0;
		}

		// Getting roll value
		Random rand = new Random();
		int roll = rand.nextInt(6)+1;

		// Calculating targets
		// Setting temp BoardCell
		BoardCell cell = getCell(currentPlayer.getRow(), currentPlayer.getCol());
		calcTargets(cell, roll);

		// Setting roll value to display
		controlPanel.setRollValue(roll);

		// Set the name to display
		controlPanel.setPlayerName(currentPlayer.getName(), currentPlayer.getColor());

		// Checking if current player is human for extra functionality
		if(currentPlayer instanceof HumanPlayer) {
			selectionMade = false;
			// Display Targets
			for(BoardCell target : targets) {
				target.setTarget(true);
				if(target.getInitial() != walkwayChar) {
					targetRooms.add(target.getInitial());
				}
			}

			// Updating board
			this.repaint();

			// Flag unfinished
			((HumanPlayer) currentPlayer).setFinished(false);
			playerCounter--;

			return;
		}

		// Setting the previous cell to be unoccupied
		BoardCell tmp = getCell(currentPlayer.getRow(), currentPlayer.getCol());
		tmp.setOccupied(false);

		// Moving computer players to new cell and setting it to be occupied
		BoardCell fin = ((ComputerPlayer) currentPlayer).selectTargets(targets);
		fin.setOccupied(true);
		currentPlayer.setRow(fin.getRow());
		currentPlayer.setCol(fin.getCol());
	}

	// Game Board Listener
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		// Ensuring a choice was already made
		if(selectionMade) {
			return;
		}

		// Checking if it's not possible to move
		if(targets.isEmpty() && !selectionMade) {
			Player currentPlayer = playerArr.get(playerCounter);
			((HumanPlayer) currentPlayer).setFinished(true);
			return;
		}

		// Temp board cell
		BoardCell selected = null;

		// Determining which cell was selected 
		for(BoardCell target : targets) {
			if(target.containsClick(e.getX(), e.getY())) {
				
				// Making it so any click on a room cell will lead to the room center
				if(target.getInitial() != walkwayChar) {
					selected = roomMap.get(target.getInitial()).getCenterCell();
					selectionMade = true;
					Player currentPlayer = playerArr.get(playerCounter);
					((HumanPlayer) currentPlayer).setFinished(true);
					break;
				}
				
				// Choosing new cell
				selected = target;
				selectionMade = true;
				Player currentPlayer = playerArr.get(playerCounter);
				((HumanPlayer) currentPlayer).setFinished(true);
				break;
			}
		}


		// Error message for not selecting a target
		if(!selectionMade) {
			if(errorCounter % 2 == 0) {
				errorCounter++;
				ClueGame.getInstance().notTargetMessage();
				return;
			}
			errorCounter++;
			return;
		}

		// Resetting the targets and repainting the board
		for(BoardCell targets : getTargets()) {
			targets.setTarget(false);
		}
		targetRooms.clear();
		this.repaint();

		// Moving the human player to selected target
		HumanPlayer currentPlayer = (HumanPlayer) playerArr.get(playerCounter);
		BoardCell temp = getCell(currentPlayer.getRow(), currentPlayer.getCol());
		temp.setOccupied(false);
		currentPlayer.setRow(selected.getRow());
		currentPlayer.setCol(selected.getCol());
		temp = getCell(currentPlayer.getRow(), currentPlayer.getCol());
		temp.setOccupied(true);
		if(selected.getInitial() != walkwayChar) {
			ClueGame game = ClueGame.getInstance();
			game.humanPlayerSuggestion(roomMap.get(selected.getInitial()).getRoomName());
			
		}

		// Incrementing and repainting
		playerCounter++;
		this.repaint();
	}

	public void moveSuggestedPlayer(String roomName) {

		ClueGame game = ClueGame.getInstance();
		String suggestedPlayerName = game.getSuggestedPlayer();
		
		for(Player p : playerArr) {
			if(p.getName().equals(suggestedPlayerName)) {
				p.setRow(roomMap.get(roomName.charAt(0)).getCenterCell().getRow());
				p.setCol(roomMap.get(roomName.charAt(0)).getCenterCell().getCol());
				this.repaint();
				break;
			}
		}
	}

	public Card disproveSuggestion() {
		Card disproveCard = null;
		ClueGame game = ClueGame.getInstance();
		String susPlayer = game.getSuggestedPlayer();
		String susRoom = game.getSuggestedRoom();
		String susWeapon = game.getSuggestedWeapon();
		Card susPlayerCard = new Card(susPlayer, CardType.PERSON);
		Card susRoomCard = new Card(susRoom, CardType.ROOM);
		Card susWeaponCard = new Card(susWeapon, CardType.WEAPON);
		
		Solution suggestion = new Solution(susPlayerCard, susRoomCard, susWeaponCard);
		
		int counter = 0;
		
		for(Player p : playerArr) {
			// Ensure player can't disprove his own suggestion
			if(counter == playerCounter - 1) {
				counter++;
				continue;
			}
			
			// Incrementation of counter
			counter = counter++;
			counter = counter % playerArr.size();

			
			disproveCard = p.disproveSuggestion(suggestion);
			if(disproveCard != null) {
				disproveCard.setColor(p.getColor());
				suggestionDisproveColor = p.getColor();
				break;
			}
		}
		
		
		
		return disproveCard;
	}
	
	public void accusationHandling(Solution accusation) {
		ClueGame game = ClueGame.getInstance();
		
		if(theAnswer.isSolution(accusation)) {
			game.winMessage();
		} else {
			game.loseMessage();
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

	public Player getHumanPlayer() {
		return humanPlayer;
	}
	
	public ArrayList<Card> getWeaponArr() {
		return weaponArr;
	}
	
	public ArrayList<Card> getRoomArr() {
		return roomArr;
	}
	
	public boolean isSelectionMade() {
		return selectionMade;
	}
	
	public Color getSuggestionDisproveColor() {
		return suggestionDisproveColor;
	}

}