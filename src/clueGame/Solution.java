/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Class for the Solution data type
 */
package clueGame;

public class Solution {
	private Card person;
	private Card room;
	private Card weapon;
	
	// Constructor
	public Solution(Card person, Card room, Card weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	// Testing if combination of cards create solution
	public boolean isSolution(Card person, Card room, Card weapon) {
		return (this.person.equals(person)) && (this.room.equals(room)) && (this.weapon.equals(weapon));
	}
	
	// Testing if a solution is the same as another solution
	public boolean isSolution(Solution s) {
		return (this.person.equals(s.person)) && (this.room.equals(s.room)) && (this.weapon.equals(s.weapon));
	}
	
	public Card getPerson() {
		return person;
	}

	public Card getRoom() {
		return room;
	}

	public Card getWeapon() {
		return weapon;
	}
}
