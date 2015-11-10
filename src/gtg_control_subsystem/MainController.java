package gtg_control_subsystem;

import java.awt.Point;
import java.awt.geom.Point2D;

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
	
	private ArrayList<Point2D> tempPntList;
	private ArrayList<Point2D> tempEdgeList;
	
	/**/
	public MainController(MainModel mapModel){
		this.mapModel = mapModel;	
		tempPntList = new ArrayList<Point2D>();
		tempEdgeList = new ArrayList<Point2D>();
	}
	
	
	/**/
	public String getMapImage(String mapName){
		String ImageURL ="";
		
		return ImageURL;
	}
	
	public ArrayList<String> getMapList(){
		ArrayList<String> mapData= new ArrayList<String>();
		mapData=mapModel.getArrayOfMapNames();
		/*for (String temp: mapData) {
			System.out.println("Map Name: "+temp);
		}*/
		return mapData;		
	}
	/*
	public ArrayList<String> getMapDate(String mapName){
		ArrayList<String> mapData= new ArrayList<String>();
		mapData=mapModel.getArrayOfMapNames();
		return mapData;		
	}
	*/
	public Point setTaskPnt(Point taskPnt, String pntType, String mapName){
		//TargetPntInfo targetPnt = new TargetPntInfo();
		Point targetPnt = new Point();
		System.out.println("Task Type:" + pntType);
		
		targetPnt = mapModel.validatePoint(mapName, taskPnt.x, taskPnt.y);
		System.out.println("Mapping To point: " + targetPnt.x + ", " + targetPnt.y);
		mapModel.setStartEndPathPoint(targetPnt, pntType, mapName);		
		return targetPnt;
	}
	
	public PathData getPathData(){
		PathData path = new PathData();
		// 
		mapModel.testDij("BH_Basement");
		Path calculateResult = mapModel.getPath();
		mapModel.printPath("BH_Basement");
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
		// For Test
		ArrayList<String> mapNames = new ArrayList<String>();
		mapNames.add("BH_Basement");		
		path.setArrayOfMapNames(mapNames);
		
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
		isAdmin = mapModel.isValidAdmin(userName, passWord);
		if(!isAdmin){
			System.out.println("Sorry You are not Admin!");
			mapModel.printAdmins();
		}
		return isAdmin;
	}
	
	/* Used for create the "MapName.txt" file, 
	 * correspond to a button "Generate Road Map" on the Admin page
	 * Used to save the temporal point graph to file*/
	
	public Boolean createCoordinateGraph(String mapName){
		Boolean success = false;
		
		return success;
	}
	
	public Boolean addPoint(Point2D inputPnt){
		Boolean success = false;
		/*
		 * Create point on temporal the point graph created in MapEditor
		 * 
		 * */
		if(CheckPntExistence(inputPnt,tempPntList)==0){
			tempPntList.add(inputPnt);	
			success = true;
		}
		return success;
	}
	
	public Boolean createEdge(Point2D pnt1, Point2D pnt2){
		Boolean success = true;
		// Check Edge Redundancy
		int PointID_1 = CheckPntExistence(pnt1,tempEdgeList);
		int PointID_2 = CheckPntExistence(pnt2,tempEdgeList);
		if(PointID_1 !=0 && PointID_2!=0 && 
		   Math.abs(PointID_1-PointID_2) == 1){
			success = false;
			return success;
		}
			
		tempEdgeList.add(pnt1);
		tempEdgeList.add(pnt2);		
		return success;
	}
	
	public void clearAllTempData(){
		tempPntList.clear();
		tempEdgeList.clear();
	}
	
	public Boolean deletePoint(Point2D inputPnt){
		Boolean pointDeleted = false;
		if(tempPntList.isEmpty()){
			System.out.println("List is empty, nothing to delete.");
			return pointDeleted;
		}		
		for(int i = 0; i<tempPntList.size(); i++){
			Point2D pnt = tempPntList.get(i);
			double distance = Math.sqrt(Math.pow(pnt.getX() - inputPnt.getX(), 2) + Math.pow(pnt.getY() - inputPnt.getY(), 2));
			if(distance < 20){
				tempPntList.remove(i);
				pointDeleted = true;
				return pointDeleted;
			}
		}		
		return pointDeleted;
	}

	public Point2D pointMapping(Point2D inputPnt){		
		Point2D searchingResult = new Point2D.Double(0,0);
		
		for (Point2D temPnt : tempPntList){
			double d = Math.sqrt(Math.pow(inputPnt.getX() - temPnt.getX(), 2) + 
							     Math.pow(inputPnt.getY() - temPnt.getY(), 2));
			if(d <= 15){
				
				System.out.println("Mapping To Point" + temPnt.getX() + "," + temPnt.getY());
				searchingResult = temPnt;
				return searchingResult;
			}
		}		
		System.out.println("Invalid Input!!");
		return searchingResult;
	}
	
	public Point2D getLastPnt()
	{
		Point2D pnt = new Point2D.Double(0,0);
		if(tempPntList.size()!=0){
			pnt = tempPntList.get(tempPntList.size()-1);
			return pnt;
		}	
		System.out.println("There is no Pnt in the list!");
		return pnt;		
	}
	
	public ArrayList<Point2D> getDisplayPnt(){		
		return tempPntList;
	}
	public ArrayList<Point2D> getDisplayEdge(){
		return tempEdgeList;
	}
	
	
	private int CheckPntExistence(Point2D pnt, ArrayList<Point2D> list){
		int pntID = 0;
		if(list.isEmpty())
			return pntID;
		int toleranceRadius = 15;	// 5 pixels
		for (int i =0;i<list.size();i++){
			Point2D temPnt = list.get(i);
			double d = Math.sqrt(Math.pow(pnt.getX() - temPnt.getX(), 2) + 
							     Math.pow(pnt.getY() - temPnt.getY(), 2));
			
			if(d <= toleranceRadius){
				pntID = i;
				System.out.println("Point Exist!");
				return pntID;
			}
		}
		return pntID;
	}
	

}