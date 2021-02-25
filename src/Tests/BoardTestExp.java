package Tests;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Experiment.TestBoard;
import Experiment.TestBoardCell;

class BoardTestExp {
	
	// Instance variable board
	TestBoard board;
	
	// Setting up the game board before each test
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	
	
	// Method to test the Adjacency list of the top left corner (location [0][0])
	@Test
	public void testAdjacencyTopLeft() {
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> adjTestList = cell.getAdjList();
		Assert.assertTrue(adjTestList.contains(board.getCell(0, 1)));
		Assert.assertTrue(adjTestList.contains(board.getCell(1,  0)));
		Assert.assertEquals(2, adjTestList.size());
	}
	
	// Method to test the Adjacency list of bottom right corner (location [3][3])
	@Test
	public void testAdjacencyBottomRight() {
		TestBoardCell cell = board.getCell(3, 3);
		Set<TestBoardCell> adjTestList = cell.getAdjList();
		Assert.assertTrue(adjTestList.contains(board.getCell(2, 3)));
		Assert.assertTrue(adjTestList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, adjTestList.size());
	}

}
