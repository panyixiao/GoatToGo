package gtg_control_subsystem;

import gtg_model_subsystem.MainModel;
/* Import Classes from model*/
import gtg_model_subsystem.Node;;


public class MainController{
	/**/
	public gtg_model_subsystem.MainModel model;
	
	/**/
	private ViewController viewController;
	private MapEditController mapEditor;
	private PathSearchController pathSearchController;
	private AdminController userChecker;
	/**/
	
	// Controller Constructor
	public MainController(MainModel MapData){
		this.model = MapData;
	}
	
	//  
	public String getMapImage(String mapName){
		String ImageURL ="";
		// ImageURL = model.getMapImage(mapName);
		return ImageURL; // and MapName
	}
	
	public String[] getMapData(String mapName){
		String mapData[] = {};
		
		return mapData;
	}
	
	public TargetPntInfo setTaskPnt(int x, int y, String taskType, String mapName){
		TargetPntInfo targetPnt = new TargetPntInfo();
		// Do point Validation() in Model
		return targetPnt;
	}
	
	/* need the package from modelsubsystem
	 * 
	 * public MultilayerPath getDirections(){
	 * 
	 * MultilayerPath path = new MultilayerPath();
	 * 
	 * return path;
	 * }
	 * 
	 * */
	public Boolean adminQualification(String userName, String passWord){
		Boolean isAdmin = false;
		
		return isAdmin;
	}
	
	/* Used for create the "MapName.txt" file, 
	 * correspond to a button "Generate Road Map" on the Admin page
	 * Used to save the temporal point graph to file*/
	public Boolean createCoordinateGraph(String mapName){
		Boolean success = false;
		
		return success;
	}
	
	public Boolean createPoint(int x, int y){
		Boolean success = false;
		/*
		 * Create point on temporal the point graph created in MapEditor
		 * */		
		return success;
	}
	
	/* We might consider about using the Point structure from model subsystem*/
	public Boolean createEdge(int pnt1_x, int pnt1_y, int pnt2_x, int pnt2_y){
		Boolean success = false;
		
		return success;
	}
}