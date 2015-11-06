package gtg_control_subsystem;

import gtg_model_subsystem.MainModel;
import gtg_view_subsystem.PathData;
import java.awt.Point;
public class TestController{
	
	public MainModel modelFacade;
	/**/
	private ViewController viewController;
	private MapEditController mapEditor;
	private PathSearchController pathSearchController;
	private AdminController userChecker;
	private PathData pathData;
	/**/
	public TestController(MainModel mainModel){
			modelFacade = new MainModel();
			viewController = new ViewController();
			mapEditor = new MapEditController();
			pathSearchController = new PathSearchController();
			userChecker = new AdminController();			
	}
	
	public Point validatePoint(String mapName, Point point){
			Point correctedPoint = modelFacade.validatePoint(mapName, point.x, point.y);
			return correctedPoint;
	}
	
	public boolean setStartEndPathPoint(Point point, String pointType, String mapName){
		 if(modelFacade.setStartEndPathPoint(point, pointType, mapName))
			 return true;
		 else
			 return false;
	}
	public PathData getPathData(){
			pathData = new PathData();
			pathData.setStartPoint(new Point(
								   modelFacade.getPath().getStartPoint().getX(), 
								   modelFacade.getPath().getStartPoint().getY()));
			pathData.setEndPoint(new Point(
								 modelFacade.getPath().getEndPoint().getX(), 
								 modelFacade.getPath().getEndPoint().getY()));
			pathData.setWayPoints(modelFacade.convertWayPointsToPoints());
			pathData.setArrayOfMapNames(modelFacade.getArrayOfMapNames());
			return pathData;
	}
	/**/
	public String getMapImage(String mapName){
		String ImageURL ="";
		
		return ImageURL;
	}
	
//	public String[] getMapDate(String mapName){
//		String mapNames[] = {};
//		
//		return mapData;		
//	}
	
	public TargetPntInfo setTaskPnt(int x, int y, String pntType, int scaleLevel, int x_center, int y_center, String mapName){
		TargetPntInfo targetPnt = new TargetPntInfo();
		
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