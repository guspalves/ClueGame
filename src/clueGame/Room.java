package clueGame;

public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room() {
		super();
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public BoardCell getLabelCell() {
		// TODO Auto-generated method stub
		return labelCell;
	}

	public BoardCell getCenterCell() {
		// TODO Auto-generated method stub
		return centerCell;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

}
