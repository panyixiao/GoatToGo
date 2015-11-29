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
		
	public MainModel mapModel;
	/**/
	
	private PathSearchController pathSearchController;
	private AdminController userChecker;
	private MapDataController mapDataController = new MapDataController(this);

	public MainController(MainModel mapModel){
		this.mapModel = mapModel;
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
		System.out.println("Task Type: " + pntType);	
		targetPnt = mapModel.validatePoint(mapName, taskPnt.x, taskPnt.y);
		mapModel.setStartEndPathPoint(targetPnt, pntType, mapName);	
		return targetPnt;
	}

	public PathData getPathData(){
		PathData path = new PathData();
		String mapName = "BoyntonHall_1";
		
	 	mapModel.testDij(mapName);
	 	Path calculateResult = mapModel.getPath();
		mapModel.printPath(mapName);
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
		mapNames.add(mapName);		
		path.setArrayOfMapNames(mapNames);
		return path;
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
		System.out.println(mapName);
		System.out.println(mapImageURL);
		System.out.println(mapType);
		mapDataController.addNewMapToList(mapName);
		mapDataController.addNewMapURLToList(mapImageURL);
		return true;
	}
	
	public Boolean deleteMap(String mapName){
		//int index = listofMaps.indexOf(mapName);
		//listofMaps.remove(index);
		//urlsofMaps.remove(index);
		//return true;
		return mapDataController.removeMapFromList(mapName);
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
		/*
		boolean success=false;
		if (this.findNodeInList(nodeID)!=null){
			//this.findNodeInList(nodeID).setDescription();
			//waiting for extension of node class;
			success=true;
			return success;
		}*/
		return mapDataController.setDescriptionOfNode(nodeID);
	}
	
	public boolean setEntranceIDOfNode (int nodeID){
/*		boolean success=false;
		if (this.findNodeInList(nodeID)!=null){
			//this.findNodeInList(nodeID).setEntranceID();
			//waiting for extension of node class;
			success=true;
			return success;
		}*/
		return mapDataController.setEntranceIDOfNode(nodeID);
	}
	
	public boolean setTypeOfNode (int nodeID){
/*		boolean success=false;
		if (this.findNodeInList(nodeID)!=null){
			//this.findNodeInList(nodeID).setType();
			//waiting for extension of node class;
			success=true;
			return success;
		}*/
		return mapDataController.setTypeOfNode(nodeID);
	}
	
	public int getNodeID(Point inputPnt){
		int NodeID = -1;
		//NodeID = mapDataController.getNodeID(inputPnt);
		NodeID = mapDataController.CheckPntExistence(inputPnt);
		return NodeID;
	}
	
	public String getNodeBuildingName(int nodeID){
		return mapDataController.getBuildingNameofNode(nodeID);
	}
	
	public int getNodeFloorNum(int nodeID){
		return mapDataController.getFloorNumofNode(nodeID);
	}
	
	public String getDescriptionOfNode (int nodeID){
	/*	String description=new String();
		if (this.findNodeInList(nodeID)!=null){
			//description=this.findNodeInList(nodeID).getDescription();
			//waiting for extension of node class;
			return description;
		}*/
		return mapDataController.getDescriptionOfNode(nodeID);
	}
	
	public int getEntranceIDOfNode (int nodeID){
/*		int entranceID=-1;
		if (this.findNodeInList(nodeID)!=null){
			//entranceID=this.findNodeInList(nodeID).getEntranceID();
			//waiting for extension of node class;
			return entranceID;
		}*/
		return mapDataController.getEntranceIDOfNode(nodeID);
	}
	
	public String getTypeOfNode (int nodeID){
/*		String nodeType=new String();
		if (this.findNodeInList(nodeID)!=null){
			//nodeType=this.findNodeInList(nodeID).getType();
			//waiting for extension of node class;
			return nodeType;
		}*/
		return mapDataController.getTypeOfNode(nodeID);
	}

	public Point pointMapping(Point inputPnt){		
		/*Point2D searchingResult = new Point2D.Double(0,0);
		
		for (Point2D temPnt : tempPntList){
			double d = mapModel.calculateDistance(inputPnt.getX(), temPnt.getX(), inputPnt.getY(), temPnt.getY());
			if(d <= 15){
				
				System.out.println("Mapping To Point" + temPnt.getX() + "," + temPnt.getY());
				searchingResult = temPnt;
				return searchingResult;
			}
		}		
		System.out.println("Invalid Input!!");*/
		return mapDataController.pointMapping(inputPnt);
	}

	public Point getLastPnt()
	{
		/*Point2D pnt = new Point2D.Double(0,0);
		if(tempPntList.size()!=0){
			pnt = tempPntList.get(tempPntList.size()-1);
			System.out.println("The last Point is: "+pnt.getX()+ "" + pnt.getY());
			return pnt;
		}	
		System.out.println("There is no Pnt in the list!");
		return pnt;		*/
		return mapDataController.getLastPntInPntList();
	}

}
