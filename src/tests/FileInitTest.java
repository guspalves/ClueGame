/* @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the FileInitTests to run JUnit tests to ensure Board is being initialized correctly
 */

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

class FileInitTest {
	// Constants for rows and cols
	public static final int ROWS = 27;
	public static final int COLS = 25;

	// Static variable for board since only one board is used
	private static Board board;

	// Setup for the board that runs before all tests are completed
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}

	// Testing to see if the correct labels and names are correlated
	@Test
	public void testRoomLabels() {
		assertEquals("Kitchen", board.getRoom('K').getRoomName() );
		assertEquals("Theater", board.getRoom('T').getRoomName() );
		assertEquals("Bowling Alley", board.getRoom('B').getRoomName() );
		assertEquals("Gym", board.getRoom('G').getRoomName() );
		assertEquals("Pool", board.getRoom('P').getRoomName() );
		assertEquals("Greenhouse", board.getRoom('H').getRoomName() );
		assertEquals("Bedroom", board.getRoom('R').getRoomName() );
	}
	
	@Test
	public void testBoardDimensions() {
		// Testing number of rows and cols
		assertEquals(ROWS, board.getNumRows());
		assertEquals(COLS, board.getNumColumns());
	}
	
	// Testing that each DoorDirection is correct
	@Test
	public void FourDoorDirections() {
		BoardCell cell = board.getCell(7, 6);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		cell = board.getCell(5, 12);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		cell = board.getCell(16, 19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		cell = board.getCell(18, 14);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		// Making sure that a walkway does not mean a door
		cell = board.getCell(18, 13);
		assertFalse(cell.isDoorway());
	}
	
	// Testing the number of Doorways in the board
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(19, numDoors);
	}
	
	@Test
	public void testRooms() {
		// testing a generic room location in the Library
		BoardCell cell = board.getCell( 1, 20);
		Room room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getRoomName(), "Library" ) ;
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() ) ;
		assertFalse( cell.isDoorway()) ;

		// Testing a label cell in the Bowling Alley
		cell = board.getCell(19, 21);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getRoomName(), "Bowling Alley" ) ;
		assertTrue( cell.isLabel() );
		assertTrue( room.getLabelCell() == cell );
		
		// Testing the center cell in the Gym
		cell = board.getCell(22, 5);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getRoomName(), "Gym" ) ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );
		
		// Testing a secret passage that connects the Bedroom to the Library
		cell = board.getCell(1, 9);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getRoomName(), "Bedroom" ) ;
		assertTrue( cell.getSecretPassage() == 'L' );
		
		// Testing the walkways in the board
		cell = board.getCell(16, 9);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getRoomName(), "Walkway" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
		// Testing an unused space in the board
		cell = board.getCell(10, 12);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getRoomName(), "Unused" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
	}
}
