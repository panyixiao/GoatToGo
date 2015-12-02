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
import java.util.Hashtable;
import java.util.Enumeration;



public class MainController{
		
	public MainModel mapModel;
	/**/
	
	private PathSearchController pathSearchController;
	private Hashtable<String, Path> calculationResult;
	private ArrayList<String> resultMapList;
	private Path currentPath;
	private Node startNode;
	private Node endNode;
	
	
	private AdminController userChecker;
	
	private MapDataController mapDataController;

	public MainController(MainModel mapModel){
		this.mapModel = mapModel;
		mapDataController = new MapDataController(this);
	}	
	
	/******************************
	 * 
	 * 		  Map Page interface
	 * 
	 *******************************/
	public ArrayList<String> getMapList(String mapName){
		return mapDataController.getMapList(mapName);
	}
	
	public String getMapURL(String mapName){
		return mapDataController.getMapURL(mapName);
	}

	// This 3 function is called each time when View is changing page
	public Boolean LoadingPntsAndEdges(String mapName){
		return mapDataController.LoadingPntsAndEdges(mapName);
	}
	public ArrayList<Point> getDisplayPnt(){
		return mapDataController.getDisplayPnt();
	}
	public ArrayList<Point> getDisplayEdge(){
		return mapDataController.getDisplayEdge();
	}	
	public ArrayList<Point> getFilteredList(String pointType){
		return mapDataController.getFilteredList(pointType);
	}
	
	/* Not used right now,correspond to getMapList() method. Get the
	 * 
	 * 1 Building List
	 * 2 Floor List
	 * 
	 * From ModelSubsystem*/
	public ArrayList<String> getMapData(String mapName){
		ArrayList<String> mapData= new ArrayList<String>();
		mapData=mapModel.getArrayOfMapNames();
		return mapData;
	}
	
	/******************************
	 * 
	 * 		 Path Searching Control
	 * 
	 *******************************/
	public Point setTaskPnt(Point taskPnt, String pntType, String mapName){
		Point targetPnt = new Point();
		
		//System.out.println("Task Type: " + pntType);
		if(pntType.equals("FROM")){
			startNode = mapModel.validatePoint(mapName, (int)(taskPnt.getX()),(int)(taskPnt.getY()),"");
			targetPnt.x = startNode.getX();
			targetPnt.y = startNode.getY();
		}
		else if(pntType.equals("TO")){
			endNode = mapModel.validatePoint(mapName, (int)(taskPnt.getX()),(int)(taskPnt.getY()),"");
			targetPnt.x = endNode.getX();
			targetPnt.y = endNode.getY();
		}
		
		return targetPnt;
	}
	
	public PathData getDesiredPath(int Index){
		PathData path = new PathData();
		
		String requestedMapName = resultMapList.get(Index);
		currentPath = calculationResult.get(requestedMapName);

		// Set StartPnt
		Point TempStartPnt = new Point();
		Node TempNode =  currentPath.getStartPoint();
		TempStartPnt.x = TempNode.getX();
		TempStartPnt.y = TempNode.getY();		
		path.setStartPoint(TempStartPnt);
		
		// Set EndPnt
		TempNode = currentPath.getEndPoint();
		Point TempEndPnt = new Point();
		TempEndPnt.x = TempNode.getX();
		TempEndPnt.y = TempNode.getY();
		path.setEndPoint(TempEndPnt);		
		
		// Set wayPoint List
		ArrayList<Point> displayWayPnts = convertNodeListIntoPointList(currentPath);
		path.setWayPoints(displayWayPnts);

		// Set mapName List
		path.setArrayOfMapNames(resultMapList);	
		
		// Set URL of current map
		int IndexOfMapURL = mapDataController.getCurrentMapNameList().indexOf(requestedMapName);
		String mapURL = mapDataController.getCurrentMapURLList().get(IndexOfMapURL);		
		path.setMapURL(mapURL);
		
		return path;
	}
	
	private ArrayList<Point> convertNodeListIntoPointList(Path inputPath){
		ArrayList<Point> pntPath = new ArrayList<Point>();
		List<Node> currentNodePath = inputPath.getWayPoints();
		if(!currentNodePath.isEmpty()){
			for(Node nd:currentNodePath){
				Point pnt = new Point();
				pnt.x = nd.getX();
				pnt.y = nd.getY();
				pntPath.add(pnt);			
			}
		}		
		return pntPath;
	}
	
	
	public boolean getPathData(){
		//String mapName = "BoyntonHall_1";
		boolean pathCalculated = false;
		pathCalculated =  mapModel.multiPathCalculate(startNode, endNode);
		if(pathCalculated){			
			calculationResult = mapModel.getMapPaths();
			Enumeration<String> calculationResultMapName = calculationResult.keys();
			while(calculationResultMapName.hasMoreElements()) {
				String mapName = calculationResultMapName.nextElement();
				resultMapList.add(mapName);
			}			
		}		
		return pathCalculated;
	}
	
	
	/******************************
	 * 
	 * 		  Admin Control
	 * 
	 *******************************/
	public Boolean adminQualification(String userName, String passWord){
		Boolean isAdmin = false;
		isAdmin = mapModel.isValidAdmin(userName, passWord);
		if(!isAdmin){
			System.out.println("Sorry You are not Admin!");
			mapModel.printAdmins();
		}
		return isAdmin;
	}
	
	
	/******************************
	 * 
	 * 		  Map Editor
	 * 
	 *******************************/
	public Boolean addNewMap(String mapName, String mapImageURL, String mapType){
		boolean mapSaved = false;
		System.out.println(mapName);
		System.out.println(mapImageURL);
		System.out.println(mapType);
		try{
			if(mapDataController.mapIsInTheOldList(mapName)){
				System.out.println("Map already in the old list");
				return mapSaved;
			}
			// Generate a relative filePath:
			int markPos = mapImageURL.lastIndexOf("images");
			if(markPos>0){
				mapImageURL = mapImageURL.substring(markPos);
			}		
			
			System.out.println(mapName);
			System.out.println(mapImageURL);
			System.out.println(mapType);
			
			if(mapModel.saveNewMap(mapName, mapDataController.changeBackSeparator(mapImageURL), mapType)){
				mapDataController.addNewMapToList(mapName);
				mapDataController.addNewMapURLToList(mapImageURL);
				mapSaved = true;
			}
		}
		catch(IOException e){
			System.out.println(e.toString());
		}
		
		return mapSaved;
	}
	
	public Boolean deleteMap(String mapName){
		boolean mapDeleted = false;
		try{
			if(mapModel.deleteMap(mapName)){
				mapDataController.removeMapFromList(mapName);
				mapDeleted = true;
			}
		}
		catch(IOException e){
			System.out.println(e.toString());	
		}
		return mapDeleted;
	}

	public Boolean createCoordinateGraph(String mapName){
		Boolean success = false;
		try{
			List<Node> nodeToBeSaved =  mapDataController.getNodeList();
			List<Edge> edgeToBeSaved =  mapDataController.getEdgeList();
			
			mapModel.saveMapGraph(mapName, 
								  nodeToBeSaved, 
								  edgeToBeSaved);
		}
		catch(IOException e){
			System.out.println(e.toString());
		}
		return success;
	}

	public Boolean addPoint(Point inputPnt, int floorNum, int entranceID, String buildingName, String pointType, String pointDescription){
		return mapDataController.addPoint(inputPnt, floorNum, entranceID, buildingName, pointType, pointDescription);
	}

	public Boolean createEdge(Point pnt1, Point pnt2){		
		return mapDataController.createEdge(pnt1, pnt2);
	}

	public boolean deletePoint(Point inputPnt){
		return mapDataController.deletePoint(inputPnt);
	}

	public boolean deleteEdge(Point p){
		return mapDataController.deleteEdge(p);
	}

	public void clearAllTempData(){
		mapDataController.clearAllTempData();
	}
	
	public boolean setDescriptionOfNode (int nodeID){
		return mapDataController.setDescriptionOfNode(nodeID);
	}
	
	public boolean setEntranceIDOfNode (int nodeID){
		return mapDataController.setEntranceIDOfNode(nodeID);
	}
	
	public boolean setTypeOfNode (int nodeID){
		return mapDataController.setTypeOfNode(nodeID);
	}
	
	public int getNodeID(Point inputPnt){
		return mapDataController.CheckPntExistence(inputPnt);
	}
	
	public String getNodeBuildingName(int nodeID){
		return mapDataController.getBuildingNameofNode(nodeID);
	}
	
	public int getNodeFloorNum(int nodeID){
		return mapDataController.getFloorNumofNode(nodeID);
	}
	
	public String getDescriptionOfNode (int nodeID){
		return mapDataController.getDescriptionOfNode(nodeID);
	}
	
	public int getEntranceIDOfNode (int nodeID){
		return mapDataController.getEntranceIDOfNode(nodeID);
	}
	
	public String getTypeOfNode (int nodeID){
		return mapDataController.getTypeOfNode(nodeID);
	}

	public Point pointMapping(Point inputPnt){
		return mapDataController.pointMapping(inputPnt);
	}

	public Point getLastPnt(){
		return mapDataController.getLastPntInPntList();
	}

}
