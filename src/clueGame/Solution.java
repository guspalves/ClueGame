package clueGame;

public class Solution {
	private Card person;
	private Card room;
	private Card weapon;
	
	public Solution(Card person, Card room, Card weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public boolean isSolution(Card person, Card room, Card weapon) {
		return (this.person.equals(person)) && (this.room.equals(room)) && (this.weapon.equals(weapon));
	}
}
