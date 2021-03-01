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
		grid = new TestBoardCell[ROWS][COLS];
		
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		
		// Setting up the grid
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i, j);
			}
		}
		
		// Calculating all adjacency grids in each grid cell
		calcAdjacency();
	}
	
	// calcTargets function
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		// Setting the visited and targets sets to be empty
		visited.clear();
		targets.clear();
		
		// Adding start location to the visited list
		visited.add(startCell);
		
		findAllTargets(startCell, pathlength);
	}
	
	public void findAllTargets(TestBoardCell startCell, int pathlength) {
		Set<TestBoardCell> adjacentCells = startCell.getAdjList();
		for(TestBoardCell adjCell : adjacentCells) {
			if(visited.contains(adjCell) || adjCell.getOccupied() == true) {
				continue;
			}
			
			if(adjCell.getRoom() == true) {
				targets.add(adjCell);
				continue;
			}
			
			visited.add(adjCell);
			
			if(pathlength == 1) targets.add(adjCell);
			else findAllTargets(adjCell, pathlength - 1);
			
			visited.remove(adjCell);
		}
	}
	
	public void calcAdjacency() {
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				if((i - 1) >= 0) {
					grid[i][j].addAdjacency(grid[i-1][j]);
				}
				if((i+1) < ROWS) {
					grid[i][j].addAdjacency(grid[i+1][j]);
				}
				if((j-1) >= 0) {
					grid[i][j].addAdjacency(grid[i][j-1]);
				}
				if((j+1) < COLS) {
					grid[i][j].addAdjacency(grid[i][j+1]);
				}
			}
		}
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
