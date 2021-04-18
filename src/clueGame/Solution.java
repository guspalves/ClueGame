package clueGame;

public class Solution {
	@Override
	public String toString() {
		return "Solution [person=" + person.getCardName() + ", room=" + room.getCardName() + ", weapon=" + weapon.getCardName() + "]";
	}

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
