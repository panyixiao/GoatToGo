package gtg_view_subsystem;

public class SelectedPoints {
	private int startX = 0;
	private int startY = 0;
	private String startMapName = "";
	private int endX = 0;
	private int endY = 0;
	private String endMapName = "";
	private Boolean isStartSelected = false;
	private Boolean isEndSelected = false;
	public SelectedPoints(){
		this.resetStart();
		this.resetEnd();
	}

	public void resetStart(){
		this.startX = 0;
		this.startY = 0;
		this.startMapName = "";
		this.isStartSelected = false;
	}

	public void resetEnd(){
		this.endX = 0;
		this.endY = 0;
		this.endMapName = "";
		this.isEndSelected = false;
	}

	public void setStartLocation(int x, int y, String mapName){
		this.startX = x;
		this.startY = y;
		this.startMapName = mapName;
		this.isStartSelected = true;
	}
	
	public void setEndLocation(int x, int y, String mapName){
		this.endX = x;
		this.endY = y;
		this.endMapName = mapName;
		this.isEndSelected = true;
	}
	
	public int getStartX(){
		return this.startX;
	}
	
	public int getStartY(){
		return this.startY;
	}
	
	public String getStartMapName(){
		return this.startMapName;
	}
	
	public int getEndX(){
		return this.endX;
	}
	
	public int getEndY(){
		return this.endY;
	}
	
	public String getEndMapName(){
		return this.endMapName;
	}
	
	public Boolean arePointsSelected(){
		return this.isStartSelected && this.isEndSelected;
	}
	
	public Boolean isStartSelected(){
		return this.isStartSelected;
	}
	
	public Boolean isEndSelected(){
		return this.isEndSelected;
	}
}
