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
	
	public boolean isSolution(Solution s) {
		return (this.person.equals(s.person)) && (this.room.equals(s.room)) && (this.weapon.equals(s.weapon));
	}
}
