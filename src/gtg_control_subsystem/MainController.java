package gtg_control_subsystem;

import java.awt.Point;

import gtg_model_subsystem.MainModel;
import gtg_model_subsystem.Node;
import gtg_model_subsystem.Path;

import gtg_view_subsystem.PathData;

import java.util.List;
import java.util.ArrayList;


public class MainController{
	
	public MainModel mapModel;
	/**/
	private ViewController viewController;
	private MapEditController mapEditor;
	private PathSearchController pathSearchController;
	private AdminController userChecker;
	
	/*This is only for Tuesday show*/
	private int StartID;
	private int EndID;
	
	/**/
	public MainController(MainModel mapModel){
		this.mapModel = mapModel;		
	}
	
	
	/**/
	public String getMapImage(String mapName){
		String ImageURL ="";
		
		return ImageURL;
	}
	
	public String[] getMapDate(String mapName){
		String mapData[] = {};
		
		return mapData;		
	}
	
	public Point setTaskPnt(Point taskPnt, String pntType, String mapName){
		//TargetPntInfo targetPnt = new TargetPntInfo();
		Point targetPnt = new Point();
		System.out.println("Task Type:" + pntType);
		
		targetPnt = mapModel.validatePoint(mapName, taskPnt.x, taskPnt.y);
		System.out.println("Mapping To point: " + targetPnt.x + targetPnt.y);
		mapModel.setStartEndPathPoint(targetPnt, pntType, mapName);		
		return targetPnt;
	}
	
	public PathData getPathData(){
		PathData path = new PathData();
		Path calculateResult = mapModel.getPath();
		
		if(calculateResult.getWayPoints().isEmpty()){
			System.out.println("WayPoint list is Empty, Display failed!");
			return path;
		}
		// Set StartPnt
		Point TempPnt = new Point();
		Node TempNode =  calculateResult.getStartPoint();
		TempPnt.x = TempNode.getX();
		TempPnt.y = TempNode.getY();		
		path.setStartPoint(TempPnt);
		// Set EndPnt
		TempNode = calculateResult.getEndPoint();
		TempPnt.x = TempNode.getX();
		TempPnt.y = TempNode.getY();
		path.setEndPoint(TempPnt);
		ArrayList<Point> displayWayPnts = mapModel.convertWayPointsToPoints();
		path.setWayPoints(displayWayPnts);	
		
		return path;
	}
	
	/*public PathData testCalculation(Point start, Point end, String mapName){
		
		Point startNode=mapData.validatePoint(mapName, start.x, start.y);
		Point endNode=mapData.validatePoint(mapName, end.x, end.y);
		mapData.runJDijkstra(startNode.getID(), endNode.getID());
		Map testMap=mapData.getTestMap();
		List<Node> wayPnt=testMap.getPath().getWayPoints();
		
		Point newStart=new Point(startNode.getX(), startNode.getY());
		Point newEnd=new Point(endNode.getX(), endNode.getY());
		PathData path = new PathData();
		path.setStartPoint(newStart);
		path.setEndPoint(newEnd);
		
		ArrayList<String> tempMapNames = new ArrayList<String>();
		tempMapNames.add(mapName);
		path.setArrayOfMapNames(tempMapNames);
		
		ArrayList<Point> tempPoints = new ArrayList<Point>();
		for (Node n:wayPnt){
			tempPoints.add(new Point(n.getX(),n.getY()));
		}
		path.setArrayOfPoints(tempPoints);
		
		return path;
	} */
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
	
	private ArrayList<Point> tempPntList;
	
	public Boolean createCoordinateGraph(String mapName){
		Boolean success = false;
		
		return success;
	}
	
	public Boolean addPoint(Point inputPnt){
		Boolean success = false;
		/*
		 * Create point on temporal the point graph created in MapEditor
		 * */
		if(!CheckPntExistence(inputPnt,tempPntList)){
			tempPntList.add(inputPnt);	
			success = true;
		}
		return success;
	}
	
	public ArrayList<Point> getDisplayPnt(){
		ArrayList<Point> display_Pnts = tempPntList;		
		return display_Pnts;
	}
	
	private Boolean CheckPntExistence(Point pnt, ArrayList<Point> list){
		Boolean pnt_Exist = false;
		if(list.isEmpty())
			return pnt_Exist;
		int toleranceRadius = 5;	// 5 pixels
		for (Point temPnt : list){
			if(Math.abs(pnt.x - temPnt.x) <= toleranceRadius && 
			   Math.abs(pnt.y - temPnt.y) <= toleranceRadius)
				pnt_Exist = true;
		}
		return pnt_Exist;
	}
	
	/* We might consider about using the Point structure from model subsystem*/
	public Boolean createEdge(int pnt1_x, int pnt1_y, int pnt2_x, int pnt2_y){
		Boolean success = false;
		
		return success;
	}
}