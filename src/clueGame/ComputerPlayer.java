/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Child class of player for computer player
 */

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	// ComputerPlayer Constructor
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}
	
	public Solution createSuggestion(Card room) {
		// Initializing cards
		Card person = null;
		Card weapon = null;
		
		// Choosing a random person, which is not seen yet
		while(true){
			// Random index
			Random rand = new Random();
			int index = rand.nextInt(deck.size());
			
			// Checking if card is seen and if the card is a room
			if(seen.contains(deck.get(index)) || deck.get(index).getType() == CardType.ROOM) {
				continue;
			}
			
			// Setting the person to be person
			if(deck.get(index).getType() == CardType.PERSON) {
				person = deck.get(index);
				break;
			}
		}
		
		// Choosing a random weapon
		while(true){
			Random rand = new Random();
			int index = rand.nextInt(deck.size());
			
			if(seen.contains(deck.get(index)) || deck.get(index).getType() == CardType.ROOM) {
				continue;
			}
			
			if(deck.get(index).getType() == CardType.WEAPON) {
				weapon = deck.get(index);
				break;
			}
		}
		
		// Returning suggestion
		return new Solution(person, room, weapon);
	}
	
	public BoardCell selectTargets(Set<BoardCell> targets) {
		// Creating copy of targets and storing it in an ArrayList for ease of access
		ArrayList<BoardCell> possibleTargets = new ArrayList<BoardCell>(targets);
		
		// Initializing used variables
		BoardCell fin = null;
		ArrayList<Integer> roomIndex = new ArrayList<Integer>();
		
		// Finding indexes of rooms
		for(int i = 0; i < possibleTargets.size(); i++) {
			if(possibleTargets.get(i).isRoom()) {
				roomIndex.add(i);
			}
		}
		
		// Choosing a random piece to move to and ensuring it's not a room
		Random rand = new Random();
		int cellIndex = rand.nextInt(possibleTargets.size());
		while(possibleTargets.get(cellIndex).isRoom() == true) {
			cellIndex = rand.nextInt(possibleTargets.size());
		}
		
		fin = possibleTargets.get(cellIndex);

		// Checking if piece should be a room instead
		if(roomIndex.size() >= 1) {
			for(int i = 0; i < roomIndex.size(); i++) {
				int index = rand.nextInt(roomIndex.size());
				String name = possibleTargets.get(roomIndex.get(index)).getRoomName();
				
				Card testCard = possibleTargets.get(roomIndex.get(index)).getRoomCard();
				
				if(seen.contains(testCard)) {
					continue;
				} else {
					this.updateSeen(testCard);
					fin = possibleTargets.get(roomIndex.get(index));
				}
				break;
			}
		}
	
		// Returning selected piece
		return fin;
	}
}
