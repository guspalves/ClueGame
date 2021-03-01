package clueGame;

public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public BoardCell getLabelCell() {
		// TODO Auto-generated method stub
		return null;
	}

	public BoardCell getCenterCell() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

}
