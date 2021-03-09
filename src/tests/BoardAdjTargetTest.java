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
		Set<BoardCell> testList = board.getAdjList(21, 4);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(20, 8)));
		assertTrue(testList.contains(board.getCell(9,19)));

		// Testing greenhouse with two doors and no secret rooms
		testList = board.getAdjList(22, 14);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(17, 13)));
		assertTrue(testList.contains(board.getCell(17, 14)));

		// Testing the kitchen with two doors and a secret room
		testList = board.getAdjList(9, 19);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(21, 4)));
		assertTrue(testList.contains(board.getCell(11, 17)));
		assertTrue(testList.contains(board.getCell(11, 18)));
	}


	// Testing adjacency on doors (include rooms + walkways)
	// Color is light orange on spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		// Door to Theater
		Set<BoardCell> testList = board.getAdjList(6, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(8, 2)));
		assertTrue(testList.contains(board.getCell(5, 5)));
		assertTrue(testList.contains(board.getCell(6, 6)));

		// Door to Pool
		testList = board.getAdjList(17, 2);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(14, 3)));
		assertTrue(testList.contains(board.getCell(17, 1)));
		assertTrue(testList.contains(board.getCell(17, 3)));

		// Door to Dining Room
		testList = board.getAdjList(2, 15);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 12)));
		assertTrue(testList.contains(board.getCell(1, 15)));
		assertTrue(testList.contains(board.getCell(3, 15)));
		assertTrue(testList.contains(board.getCell(2, 16)));
	}

	// Testing adjacency in different walkways
	// Color is dark orange on spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Bottom of the board, surrounded by room
		Set<BoardCell> testList = board.getAdjList(23, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(23, 6)));

		// Near door but not adjacent to door
		testList = board.getAdjList(5, 4);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(5, 3)));
		assertTrue(testList.contains(board.getCell(4, 4)));
		assertTrue(testList.contains(board.getCell(5, 5)));

		// Adjacent to a door
		testList = board.getAdjList(14, 19);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(13, 19)));
		assertTrue(testList.contains(board.getCell(14, 18)));
		assertTrue(testList.contains(board.getCell(14, 20)));

		// Next to closet/wall
		testList = board.getAdjList(5,21);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(4, 21)));
		assertTrue(testList.contains(board.getCell(6, 21)));
		assertTrue(testList.contains(board.getCell(5, 20)));

	}


	// Testing movement out of the Theater room
	// Colored as light blue on spreadsheet
	@Test
	public void testTargetsInTheater() {
		// Testing targets with a path length of 1
		board.calcTargets(board.getCell(8, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(6, 5)));
		assertTrue(targets.contains(board.getCell(10, 5)));	

		// Testing targets with a path length of 3
		board.calcTargets(board.getCell(8, 2), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(7, 6)));
		assertTrue(targets.contains(board.getCell(9, 6)));	
		assertTrue(targets.contains(board.getCell(11, 4)));
		assertTrue(targets.contains(board.getCell(6, 7)));	

		// Testing targets with a path length of 4
		board.calcTargets(board.getCell(8, 2), 4);
		targets= board.getTargets();
		assertEquals(18, targets.size());
		assertTrue(targets.contains(board.getCell(5, 3)));
		assertTrue(targets.contains(board.getCell(8, 6)));	
		assertTrue(targets.contains(board.getCell(6, 8)));
		assertTrue(targets.contains(board.getCell(11, 7)));
		assertTrue(targets.contains(board.getCell(12, 4)));
	}

	// Testing movement out of the Bedroom
	// Colored as light blue on spreadsheet
	@Test
	public void testTargetsInBedRoom() {
		// Testing targets with a path length of 1
		board.calcTargets(board.getCell(2, 3), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(2, 20)));
		assertTrue(targets.contains(board.getCell(4, 5)));	

		// Testing targets with a path length of 3
		board.calcTargets(board.getCell(2, 3), 3);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(4, 3)));
		assertTrue(targets.contains(board.getCell(4, 7)));	
		assertTrue(targets.contains(board.getCell(3, 6)));
		assertTrue(targets.contains(board.getCell(6, 5)));
		assertTrue(targets.contains(board.getCell(5, 4)));
		assertTrue(targets.contains(board.getCell(2, 20)));

		// Testing targets with a path length of 4
		board.calcTargets(board.getCell(2, 3), 4);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(4, 2)));
		assertTrue(targets.contains(board.getCell(3, 7)));
		assertTrue(targets.contains(board.getCell(5, 5)));
		assertTrue(targets.contains(board.getCell(2, 20)));	
	}

	// Testing cells that are doors
	// Colored as light blue on spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// Testing targets with a path length of 1
		board.calcTargets(board.getCell(14, 20), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(14, 21)));
		assertTrue(targets.contains(board.getCell(14, 19)));	
		assertTrue(targets.contains(board.getCell(13, 20)));
		assertTrue(targets.contains(board.getCell(19, 20)));

		// Testing targets with a path length of 3
		board.calcTargets(board.getCell(14, 20), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(11, 20)));
		assertTrue(targets.contains(board.getCell(12, 19)));
		assertTrue(targets.contains(board.getCell(12, 21)));	
		assertTrue(targets.contains(board.getCell(14, 21)));
		assertTrue(targets.contains(board.getCell(19, 20)));	

		// Testing targets with a path length of 4
		board.calcTargets(board.getCell(14, 20), 4);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(19, 20)));
		assertTrue(targets.contains(board.getCell(11, 19)));
		assertTrue(targets.contains(board.getCell(12, 18)));	
		assertTrue(targets.contains(board.getCell(15, 17)));
		assertTrue(targets.contains(board.getCell(13, 21)));
		assertTrue(targets.contains(board.getCell(19, 20)));
	}

	// Testing regular walkways
	// Colored as light blue in spreadsheet
	@Test
	public void testTargetsInWalkway1() {
		// Testing targets with a path length of 1
		board.calcTargets(board.getCell(22, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(21, 17)));
		assertTrue(targets.contains(board.getCell(23, 17)));
		assertTrue(targets.contains(board.getCell(22, 18)));

		// Testing targets with a path length of 2
		board.calcTargets(board.getCell(22, 17), 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(20, 17)));
		assertTrue(targets.contains(board.getCell(21, 18)));
		assertTrue(targets.contains(board.getCell(23, 18)));	

		// Testing targets with a path length of 3
		board.calcTargets(board.getCell(22, 17), 3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(19, 17)));
		assertTrue(targets.contains(board.getCell(20, 18)));
		assertTrue(targets.contains(board.getCell(22, 18)));	
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
		board.calcTargets(board.getCell(15, 14), 2);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(13, 14)));
		assertTrue(targets.contains(board.getCell(14, 15)));
		assertTrue(targets.contains(board.getCell(15, 16)));	

		// Testing targets with a path length of 4 -- walkway enters the Greenhouse room
		board.calcTargets(board.getCell(15, 14), 3);
		targets= board.getTargets();
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCell(22, 14)));
		assertTrue(targets.contains(board.getCell(12, 14)));
		assertTrue(targets.contains(board.getCell(13, 13)));
		assertTrue(targets.contains(board.getCell(15, 13)));	

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
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(12, 3)));
		assertTrue(targets.contains(board.getCell(11, 4)));
		assertFalse(targets.contains(board.getCell(13, 6)));

		// Room is occupied and the walkway next to the doorway is occupied
		board.getCell(2, 20).setOccupied(true);
		board.getCell(4, 19).setOccupied(true);
		board.calcTargets(board.getCell(4, 18), 1);
		board.getCell(2, 20).setOccupied(false);
		board.getCell(4, 19).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(4, 17)));	
		assertTrue(targets.contains(board.getCell(5, 18)));	
		assertTrue(targets.contains(board.getCell(2, 20)));	
		assertFalse(targets.contains(board.getCell(4, 19)));

		// Leaving a room with a blocked doorway
		board.getCell(15, 6).setOccupied(true);
		board.calcTargets(board.getCell(14, 3), 2);
		board.getCell(15, 6).setOccupied(false);
		
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(17, 1)));
		assertTrue(targets.contains(board.getCell(17, 3)));
		
		// Making sure you can't leave through an occupied doorway
		assertFalse(targets.contains(board.getCell(15, 6)));
		assertFalse(targets.contains(board.getCell(14, 6)));


	}
}
