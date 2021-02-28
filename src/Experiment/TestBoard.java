/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the TestBoard to run JUnit tests
 */

package Experiment;

import java.util.*;

public class TestBoard {
	// Instance variables
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	// Constructor
	public TestBoard() {
		// Initializing targets and grid
		targets = new HashSet<TestBoardCell>();
		grid = new TestBoardCell[ROWS][COLS];
		
		// Setting up the grid
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i, j);
			}
		}
	}
	
	// calcTargets function
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		return;
	}
	
	// Returning set of targets
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	// Obtaining cell at specific row and col
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
