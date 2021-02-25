/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the TestBoardCell to run JUnit tests on Clue game board.
 */

package Experiment;

import java.util.*;

public class TestBoardCell {
	// Instance variables
	private int row;
	private int col;
	private boolean isRoom;
	private boolean isOccupied;
	private Set<TestBoardCell> adjacencyList;
	
	// Constructor
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		
		// Initializing set
		adjacencyList = new HashSet<TestBoardCell>();
	}
	
	// Adding a cell to adjacency list
	void addAdjacency(TestBoardCell cell) {
		adjacencyList.add(cell);
	}
	
	// Getter for adjacency list set
	public Set<TestBoardCell> getAdjList() {
		return adjacencyList;
	}

	// Setter and Getter for isRoom
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean getRoom() {
		return isRoom;
	}
	
	// Setter and Getter for isOccuppied
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
}
