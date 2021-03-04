 /**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the Room class
 */

package clueGame;

public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	// Room default constructor
	public Room(String name) {
		super();
		this.name = name;
	}

	// getter for room name
	public String getRoomName() {
		return name;
	}

	// getter for the label cell
	public BoardCell getLabelCell() {
		return labelCell;
	}

	// getter for center cell
	public BoardCell getCenterCell() {
		return centerCell;
	}

	// setter for label cell
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	// setter for center cell
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

}
