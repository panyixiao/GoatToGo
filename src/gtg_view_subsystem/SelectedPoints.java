package gtg_view_subsystem;

public class SelectedPoints {
	private double startX = 0.0;
	private double startY = 0.0;
	private String startMapName = "";
	private double endX = 0.0;
	private double endY = 0.0;
	private String endMapName = "";
	private Boolean isStartSelected = false;
	private Boolean isEndSelected = false;
	public SelectedPoints(){
		this.resetStart();
		this.resetEnd();
	}

	public void resetStart(){
		this.startX = 0.0;
		this.startY = 0.0;
		this.startMapName = "";
		this.isStartSelected = false;
	}

	public void resetEnd(){
		this.endX = 0.0;
		this.endY = 0.0;
		this.endMapName = "";
		this.isEndSelected = false;
	}

	public void setStartLocation(double x, double y, String mapName){
		this.startX = x;
		this.startY = y;
		this.startMapName = mapName;
		this.isStartSelected = true;
	}
	
	public void setEndLocation(double x, double y, String mapName){
		this.endX = x;
		this.endY = y;
		this.endMapName = mapName;
		this.isEndSelected = true;
	}
	
	public double getStartX(){
		return this.startX;
	}
	
	public double getStartY(){
		return this.startY;
	}
	
	public String getStartMapName(){
		return this.startMapName;
	}
	
	public double getEndX(){
		return this.endX;
	}
	
	public double getEndY(){
		return this.endY;
	}
	
	public String getEndMapName(){
		return this.endMapName;
	}
	
	public Boolean arePointsSelected(){
		return this.isStartSelected && this.isEndSelected;
	}
}
