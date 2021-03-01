/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the BoardCell class
 */

package clueGame;

import java.util.*;

public class BoardCell {
	// Instance variables
	private int row, col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel, roomCenter;
	private char secretPassage;
	Set<BoardCell> adjList;
	
	// Constructor
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		
		adjList = new HashSet<BoardCell>();
	}
	
	// Adding a cell to adjacency list
	void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	/*
	 * Getters
	 */

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return doorDirection;
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// Getter for adjacency list set
	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	/*
	 * Setters
	 */
	
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
}

