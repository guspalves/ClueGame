/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Testing to ensure algorithms for targets and adjacency are working properly.
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {

	private static Board board;
	
	// Setting up the board before running any tests
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		board.initialize();
	}

	// Testing movement inside of rooms
	// Color is light orange on spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// Testing gym which has a single door and a secret room
		Set<BoardCell> testList = board.getAdjList(22, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(21, 9)));
		assertTrue(testList.contains(board.getCell(10,20)));

		// Testing greenhouse with two doors and no secret rooms
		testList = board.getAdjList(23, 15);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(18, 14)));
		assertTrue(testList.contains(board.getCell(18, 15)));

		// Testing the kitchen with two doors and a secret room
		testList = board.getAdjList(10, 20);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(22, 5)));
		assertTrue(testList.contains(board.getCell(12, 18)));
		assertTrue(testList.contains(board.getCell(12, 19)));
	}


	// Testing adjacency on doors (include rooms + walkways)
	// Color is light orange on spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		// Door to Theater
		Set<BoardCell> testList = board.getAdjList(7, 6);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(9, 3)));
		assertTrue(testList.contains(board.getCell(6, 6)));
		assertTrue(testList.contains(board.getCell(7, 7)));

		// Door to Pool
		testList = board.getAdjList(18, 3);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(15, 4)));
		assertTrue(testList.contains(board.getCell(18, 2)));
		assertTrue(testList.contains(board.getCell(18, 4)));

		// Door to Dining Room
		testList = board.getAdjList(3, 16);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(3, 13)));
		assertTrue(testList.contains(board.getCell(2, 16)));
		assertTrue(testList.contains(board.getCell(4, 16)));
		assertTrue(testList.contains(board.getCell(3, 17)));
	}

	// Testing adjacency in different walkways
	// Color is dark orange on spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Bottom of the board, surrounded by room
		Set<BoardCell> testList = board.getAdjList(24, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(24, 7)));

		// Near door but not adjacent to door
		testList = board.getAdjList(6, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(6, 4)));
		assertTrue(testList.contains(board.getCell(5, 5)));
		assertTrue(testList.contains(board.getCell(6, 6)));

		// Adjacent to a door
		testList = board.getAdjList(15, 20);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(14, 20)));
		assertTrue(testList.contains(board.getCell(15, 19)));
		assertTrue(testList.contains(board.getCell(15, 21)));

		// Next to closet/wall
		testList = board.getAdjList(6,22);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(5, 22)));
		assertTrue(testList.contains(board.getCell(7, 22)));
		assertTrue(testList.contains(board.getCell(6, 21)));

	}


	// Testing movement out of the Theater room
	// Colored as light blue on spreadsheet
	@Test
	public void testTargetsInTheater() {
		// Testing targets with a path length of 1
		board.calcTargets(board.getCell(9, 3), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(7, 6)));
		assertTrue(targets.contains(board.getCell(11, 6)));	

		// Testing targets with a path length of 3
		board.calcTargets(board.getCell(9, 3), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(10, 7)));	
		assertTrue(targets.contains(board.getCell(12, 5)));
		assertTrue(targets.contains(board.getCell(7, 8)));	

		// Testing targets with a path length of 4
		board.calcTargets(board.getCell(9, 3), 4);
		targets= board.getTargets();
		assertEquals(18, targets.size());
		assertTrue(targets.contains(board.getCell(6, 4)));
		assertTrue(targets.contains(board.getCell(9, 7)));	
		assertTrue(targets.contains(board.getCell(7, 9)));
		assertTrue(targets.contains(board.getCell(12, 8)));
		assertTrue(targets.contains(board.getCell(13, 5)));
	}

	// Testing movement out of the Bedroom
	// Colored as light blue on spreadsheet
	@Test
	public void testTargetsInBedRoom() {
		// Testing targets with a path length of 1
		board.calcTargets(board.getCell(3, 4), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(3, 21)));
		assertTrue(targets.contains(board.getCell(5, 6)));	

		// Testing targets with a path length of 3
		board.calcTargets(board.getCell(3, 4), 3);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(5, 4)));
		assertTrue(targets.contains(board.getCell(5, 8)));	
		assertTrue(targets.contains(board.getCell(4, 7)));
		assertTrue(targets.contains(board.getCell(7, 6)));
		assertTrue(targets.contains(board.getCell(6, 5)));
		assertFalse(targets.contains(board.getCell(2, 21)));

		// Testing targets with a path length of 4
		board.calcTargets(board.getCell(3, 4), 4);
		targets= board.getTargets();
		assertEquals(20, targets.size());
		assertTrue(targets.contains(board.getCell(5, 3)));
		assertTrue(targets.contains(board.getCell(4, 8)));
		assertTrue(targets.contains(board.getCell(6, 6)));
		assertTrue(targets.contains(board.getCell(3, 21)));	
	}

	// Testing cells that are doors
	// Colored as light blue on spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// Testing targets with a path length of 1
		board.calcTargets(board.getCell(15, 21), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(15, 22)));
		assertTrue(targets.contains(board.getCell(15, 20)));	
		assertTrue(targets.contains(board.getCell(14, 21)));
		assertTrue(targets.contains(board.getCell(20, 21)));

		// Testing targets with a path length of 3
		board.calcTargets(board.getCell(15, 21), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(12, 21)));
		assertTrue(targets.contains(board.getCell(13, 20)));
		assertTrue(targets.contains(board.getCell(13, 22)));	
		assertTrue(targets.contains(board.getCell(15, 22)));
		assertTrue(targets.contains(board.getCell(20, 21)));	

		// Testing targets with a path length of 4
		board.calcTargets(board.getCell(15, 21), 4);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(20, 21)));
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(13, 19)));	
		assertTrue(targets.contains(board.getCell(16, 18)));
		assertTrue(targets.contains(board.getCell(14, 22)));
		assertTrue(targets.contains(board.getCell(20, 21)));
	}

	// Testing regular walkways
	// Colored as light blue in spreadsheet
	@Test
	public void testTargetsInWalkway1() {
		// Testing targets with a path length of 1
		board.calcTargets(board.getCell(23, 18), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(22, 18)));
		assertTrue(targets.contains(board.getCell(24, 18)));
		assertTrue(targets.contains(board.getCell(23, 19)));

		// Testing targets with a path length of 2
		board.calcTargets(board.getCell(23, 18), 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(21, 18)));
		assertTrue(targets.contains(board.getCell(22, 19)));
		assertTrue(targets.contains(board.getCell(24, 19)));	

		// Testing targets with a path length of 3
		board.calcTargets(board.getCell(23, 18), 3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(20, 18)));
		assertTrue(targets.contains(board.getCell(21, 19)));
		assertTrue(targets.contains(board.getCell(23, 19)));	
	}

	
	@Test
	public void testTargetsInWalkway2() {
		// Testing targets with a path length of 1
		board.calcTargets(board.getCell(15, 14), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(15, 15)));
		assertTrue(targets.contains(board.getCell(15, 13)));
		assertTrue(targets.contains(board.getCell(14, 14)));	
		assertTrue(targets.contains(board.getCell(16, 14)));	

		// Testing targets with a path length of 2
		board.calcTargets(board.getCell(16, 15), 2);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(14, 15)));
		assertTrue(targets.contains(board.getCell(15, 16)));
		assertTrue(targets.contains(board.getCell(16, 17)));	

		// Testing targets with a path length of 4 -- walkway enters the Greenhouse room
		board.calcTargets(board.getCell(16, 15), 3);
		targets= board.getTargets();
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCell(23, 15)));
		assertTrue(targets.contains(board.getCell(13, 15)));
		assertTrue(targets.contains(board.getCell(14, 14)));
		assertTrue(targets.contains(board.getCell(16, 14)));	

	}

	// Testing movement with cells being occupied
	// Colored as red on spreadsheet
	@Test
	public void testTargetsOccupied() {
		// Testing target with cell near being occupied
		board.getCell(13, 6).setOccupied(true);
		board.calcTargets(board.getCell(12, 5), 2);
		board.getCell(13, 6).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 3)));
		assertFalse(targets.contains(board.getCell(11, 4)));
		assertFalse(targets.contains(board.getCell(13, 6)));

		// Room is occupied and the walkway next to the doorway is occupied
		board.getCell(3, 21).setOccupied(true);
		board.getCell(5, 20).setOccupied(true);
		board.calcTargets(board.getCell(5, 19), 1);
		board.getCell(3, 21).setOccupied(false);
		board.getCell(5, 20).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(5, 18)));	
		assertTrue(targets.contains(board.getCell(6, 19)));	
		assertTrue(targets.contains(board.getCell(3, 21)));	
		assertFalse(targets.contains(board.getCell(5, 20)));

		// Leaving a room with a blocked doorway
		board.getCell(16, 7).setOccupied(true);
		board.calcTargets(board.getCell(15, 4), 2);
		board.getCell(16, 7).setOccupied(false);
		
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(18, 2)));
		assertTrue(targets.contains(board.getCell(18, 4)));
		
		// Making sure you can't leave through an occupied doorway
		assertFalse(targets.contains(board.getCell(16, 7)));
		assertFalse(targets.contains(board.getCell(15, 7)));


	}
}
