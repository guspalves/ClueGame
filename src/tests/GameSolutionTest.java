package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

import clueGame.*;
import org.junit.jupiter.api.Test;

class GameSolutionTest {
	// Static variable for board since only one board is used
	private static Board board;
	private static Card kitchenCard, diningCard, theaterCard, bowlingCard, libraryCard,
	gymCard, poolCard, greenhouseCard, bedroomCard, mustardCard, greenCard, plumCard, peacockCard,
	whiteCard, scarletCard, revolverCard, daggerCard, ropeCard, leadCard, candleCard, wrenchCard;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		// Setting up cards
		kitchenCard = new Card("Kitchen", CardType.ROOM);
		diningCard = new Card("Dining Room", CardType.ROOM);
		theaterCard = new Card("Theater", CardType.ROOM);
		bowlingCard = new Card("Bowling Alley", CardType.ROOM);
		libraryCard = new Card("Library", CardType.ROOM);
		gymCard = new Card("Gym", CardType.ROOM);
		poolCard = new Card("Pool", CardType.ROOM);
		greenhouseCard = new Card("Greenhouse", CardType.ROOM);
		bedroomCard = new Card("Bedroom", CardType.ROOM);
		
		mustardCard = new Card("Colonel Mustard", CardType.PERSON);
		greenCard = new Card("Mr. Green", CardType.PERSON);
		plumCard = new Card("Professor Plum", CardType.PERSON);
		peacockCard = new Card("Mrs. Peacock", CardType.PERSON);
		whiteCard = new Card("Mrs. White", CardType.PERSON);
		scarletCard = new Card("Miss Scarlet", CardType.PERSON);
		
		revolverCard = new Card("Revolver", CardType.WEAPON);
		daggerCard = new Card("Dagger", CardType.WEAPON);
		ropeCard = new Card("Rope", CardType.WEAPON);
		leadCard = new Card("Lead Pipe", CardType.WEAPON);
		candleCard = new Card("Candlestick", CardType.WEAPON);
		wrenchCard = new Card("Wrench", CardType.WEAPON);

	}
	
	@Test
	public void TestCheckAccusation() {
		board.setAnswer(mustardCard, gymCard, revolverCard);
		
		// Checking if it's wrong solution
		assertFalse(board.checkAccusation(new Solution(mustardCard, gymCard, daggerCard)));
		assertFalse(board.checkAccusation(new Solution(mustardCard, poolCard, daggerCard)));
		assertFalse(board.checkAccusation(new Solution(greenCard, gymCard, daggerCard)));

		// Checking if it's right solution
		assertTrue(board.checkAccusation(new Solution(mustardCard, gymCard, revolverCard)));
	}
	
	@Test
	public void TestDisproveSuggestion() {
		
	}
	
	@Test
	public void TestHandleSolution() {
		
	}
}
