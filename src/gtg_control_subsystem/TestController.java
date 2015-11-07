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
	

}