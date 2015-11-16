package gtg_control_subsystem;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;

import gtg_model_subsystem.MainModel;
import gtg_model_subsystem.Node;
import gtg_model_subsystem.Edge;
import gtg_model_subsystem.Path;

import gtg_view_subsystem.PathData;

import java.util.List;
import java.util.ArrayList;


public class MainController{
	
	/*Added by neha. Yixio this is the temporary list to store map names and map urls. This is done to check
	integration between the controller and the view subsystem.*/
	
	private ArrayList<String> listofMaps = new ArrayList<String>();
	private ArrayList<String> urlsofMaps = new ArrayList<String>();

	
	public MainModel mapModel;
	/**/
	private ViewController viewController;
	private MapEditController mapEditor;
	private PathSearchController pathSearchController;
	private AdminController userChecker;
	
	/*This is only for Tuesday show*/
	private int StartID;
	private int EndID;
	
	private ArrayList<Point2D> tempPntList  = new ArrayList<Point2D>();
	private ArrayList<Point2D> tempEdgeList = new ArrayList<Point2D>();
	
	/**/
	public MainController(MainModel mapModel){
		this.mapModel = mapModel;	
	}
	
	
	public ArrayList<String> getMapDate(String mapName){
		ArrayList<String> mapData= new ArrayList<String>();
		mapData=mapModel.getArrayOfMapNames();
		return mapData;
	}
	/* Added by neha
	 * This method should fetch the map url from the model and return the url to the view subsystem.
	 * */
	public String getMapURL(String mapName){
		String mapurl = "";
		int index = listofMaps.indexOf(mapName);
		mapurl = urlsofMaps.get(index);
		return mapurl;
	}
	
	/* Added by Neha
	 * For integration the method return me the hardcoded arraylist.
	 * Once a method from model is available the method should return me an arrayList of mapnames.
	 * The input parameter mapName will help us to use the same method in the map page
	 * i.e if mapName is admin means the list should conatins all the map names
	 * if mapName is campus then the list will contain all building names
	 * if mapName is building then the list will contain all the floor names of the building and the campus map also.
	 * You will have to implement the switch case.
	 */
	public ArrayList<String> getMapList(String mapName){
		ArrayList<String> mapData= new ArrayList<String>();
		//mapData=mapModel.getArrayOfMapNames(mapName);
		
		// For test
		if(mapName == "Campus_Map"){
			mapData.clear();
			mapData.add("Boynton_Hall");
		}
		if(mapName == "Boyton_Hall"){
			mapData.clear();
			mapData.add("BH_Basement");
			mapData.add("BH_FirstFloor");
			mapData.add("BH_SecondFloor");
			mapData.add("BH_ThirdFloor");			
		}
		
		mapData = listofMaps;
		return mapData;		
	}

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
		Point TempStartPnt = new Point();
		Node TempNode =  calculateResult.getStartPoint();
		TempStartPnt.x = TempNode.getX();
		TempStartPnt.y = TempNode.getY();		
		path.setStartPoint(TempStartPnt);
		// Set EndPnt
		TempNode = calculateResult.getEndPoint();
		Point TempEndPnt = new Point();
		TempEndPnt.x = TempNode.getX();
		TempEndPnt.y = TempNode.getY();
		path.setEndPoint(TempEndPnt);
		ArrayList<Point> displayWayPnts = mapModel.convertWayPointsToPoints();
		path.setWayPoints(displayWayPnts);
		// For Test
		ArrayList<String> mapNames = new ArrayList<String>();
		mapNames.add("BH_Basement");		
		path.setArrayOfMapNames(mapNames);
		
		return path;
	}
	
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
	
	public Boolean LoadingPntsAndEdges(){
		List<Node> currentNode = mapModel.getNodeList();
		List<Edge> currentEdge = mapModel.getEdgeList();
		if(currentNode.isEmpty()||currentEdge.isEmpty()){
			System.out.println("Current Node/Edge List is empty");
			return false;
		}		
		tempPntList = transferNodeToPnt2D(currentNode);
		tempEdgeList = transferEdgeToPnt2D(currentEdge);		
		return true;
	}
	
	private ArrayList<Point2D> transferNodeToPnt2D(List<Node> targetList){
		ArrayList<Point2D> pntList = new ArrayList<Point2D>();
		for(Node nd:targetList){
			Point2D pnt = new Point2D.Double(nd.getX(),nd.getY());
			pntList.add(pnt);			
		}
		return pntList;
	}
	private ArrayList<Point2D> transferEdgeToPnt2D(List<Edge> targetList){
		ArrayList<Point2D> edgeList = new ArrayList<Point2D>();
		for(Edge eg:targetList){
			Point2D pnt_1 = new Point2D.Double(eg.getSource().getX(),eg.getSource().getY());
			Point2D pnt_2 = new Point2D.Double(eg.getDestination().getX(),eg.getDestination().getY());
			edgeList.add(pnt_1);
			edgeList.add(pnt_2);
		}
		return edgeList;
	}
	
	public Boolean createCoordinateGraph(String mapName){
		Boolean success = false;
		try{
			mapModel.saveMapGraph(mapName, tempPntList, tempEdgeList);
		}
		catch(IOException e){
			System.out.println(e.toString());
		}
		
		return success;
	}
	
	/* Added by  neha. For now this method just pushes the data into the listofMaps and urlsofMaps.
	 * Once the model method is available call that method with the same paramaters.
	 * The model method should return a boolean value:
	 * True if mapName and mapImageURL are stored succesfully into the .txt file
	 * False if mapName and mapImageURL are not stored succesfully into the .txt file
	 */
	public Boolean addNewMap(String mapName, String mapImageURL, String mapType){
		System.out.println(mapType);
		listofMaps.add(mapName);
		urlsofMaps.add(mapImageURL);
		return true;
	}
	
	/* Added by  neha. For now this method just deletes the map from listofMaps and urlsofMaps.
	 * Once the model method is available call that method with the same paramaters.
	 * The model method should return a boolean value:
	 * True if mapName and mapImageURL are deleted succesfully from the .txt file
	 * False if mapName and mapImageURL are not deleted succesfully from the .txt file
	 */
	public Boolean deleteMap(String mapName){
		int index = listofMaps.indexOf(mapName);
		listofMaps.remove(index);
		urlsofMaps.remove(index);
		return true;
	}
	
	// Create point on temporal the point graph created in MapEditor
	public Boolean addPoint(Point2D inputPnt){
		Boolean success = false;
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
		// Check if edge already exist in the edge list
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