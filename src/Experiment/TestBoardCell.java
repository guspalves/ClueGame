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
	private int row, col;
	private boolean isRoom, isOccupied;
	Set<TestBoardCell> adjList;
	
	// Constructor
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		isRoom = isOccupied = false;
		
		adjList = new HashSet<TestBoardCell>();
	}
	
	// Adding a cell to adjacency list
	void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	// Getter for adjacency list set
	public Set<TestBoardCell> getAdjList() {
		return adjList;
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
