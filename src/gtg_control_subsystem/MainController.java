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


/**
 */
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
		
	private ArrayList<Point2D> tempPntList  = new ArrayList<Point2D>();
	private ArrayList<Point2D> tempEdgeList = new ArrayList<Point2D>();
	private ArrayList<Node> nodeList = new ArrayList<Node>();
	private ArrayList<Edge> edgeList = new ArrayList<Edge>();

	
	/**/
	/**
	 * Constructor for MainController.
	 * @param mapModel MainModel
	 */
	public MainController(MainModel mapModel){
		this.mapModel = mapModel;
		MapListIntial();
	}
	
	// temporarily initializer, will be moved to Model-subsystem in the future
	private void MapListIntial(){
		listofMaps.add("BH_Basement");
		listofMaps.add("BH_FirstFloor");
		listofMaps.add("BH_SecondFloor");
		listofMaps.add("BH_ThirdFloor");
		
		// map urls
		String BH_BASEMENT = "images"+System.getProperty("file.separator")+"BH_Basement.png";
		String BH_FIRST_FLOOR = "images"+System.getProperty("file.separator")+"BH_FirstFloor.png";
		String BH_SECOND_FLOOR = "images"+System.getProperty("file.separator")+"BH_SecondFloor.png";
		String BH_THIRD_FLOOR = "images"+System.getProperty("file.separator")+"BH_ThirdFloor.png";
		
		urlsofMaps.add(BH_BASEMENT);
		urlsofMaps.add(BH_FIRST_FLOOR);
		urlsofMaps.add(BH_SECOND_FLOOR);
		urlsofMaps.add(BH_THIRD_FLOOR);		
	}
	
	/**
	 * Method getMapData.
	 * @param mapName String
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMapData(String mapName){
		ArrayList<String> mapData= new ArrayList<String>();
		mapData=mapModel.getArrayOfMapNames();
		return mapData;
	}
	
	/* Added by neha
	 * This method should fetch the map url from the model and return the url to the view subsystem.
	 */
	/**
	 * Method getMapURL.
	 * @param mapName String
	 * @return String
	 */
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
	/**
	 * Method getMapList.
	 * @param mapName String
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMapList(String mapName){
		return listofMaps;		
	}

	/**
	 * Method setTaskPnt.
	 * @param taskPnt Point
	 * @param pntType String
	 * @param mapName String
	 * @return Point
	 */
	public Point setTaskPnt(Point taskPnt, String pntType, String mapName){
		//TargetPntInfo targetPnt = new TargetPntInfo();
		Point targetPnt = new Point();
		System.out.println("Task Type:" + pntType);	
		targetPnt = mapModel.validatePoint(mapName, taskPnt.x, taskPnt.y);
		mapModel.setStartEndPathPoint(targetPnt, pntType, mapName);	
		return targetPnt;
	}

	/**
	 * Method getPathData.
	 * @return PathData
	 */
	public PathData getPathData(){
		PathData path = new PathData();
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
	
	/**
	 * Method adminQualification.
	 * @param userName String
	 * @param passWord String
	 * @return Boolean
	 */
	public Boolean adminQualification(String userName, String passWord){
		Boolean isAdmin = false;
		isAdmin = mapModel.isValidAdmin(userName, passWord);

		return isAdmin;
	}
	
	// This function is called each time when View is changing page
	/**
	 * Method LoadingPntsAndEdges.
	 * @param mapName String
	 * @return Boolean
	 */
	public Boolean LoadingPntsAndEdges(String mapName){
		if(mapModel.loadFiles(mapName)){
			LoadInNodeList(mapName);
			LoadInEdgeList(mapName);			 
			transferNodeToPnt2D(this.nodeList);
			transferEdgeToPnt2D(this.edgeList);

			return true;
		}
		else{
			System.out.println("Loading File failed");
			return false;
		}
	}
	
	/**
	 * Method LoadInNodeList.
	 * @param mapName String
	 */
	private void LoadInNodeList(String mapName){
		// Clear the temporary nodeList before add new point into it;
		this.nodeList.clear();
		ArrayList<Node> tempNode = (ArrayList<Node>)mapModel.getNodeList(mapName);		
		for(Node nd:tempNode){
			this.nodeList.add(nd);
		}
		//return copySuccess;		
	}
	
	/**
	 * Method LoadInEdgeList.
	 * @param mapName String
	 */
	private void LoadInEdgeList(String mapName){
		// Clear the temporary edgeList before add new point into it;
		this.edgeList.clear();
		ArrayList<Edge> tempEdge = (ArrayList<Edge>)mapModel.getEdgeList(mapName);		
		for(Edge eg:tempEdge){
			this.edgeList.add(eg);
		}
	}
	
	/**
	 * Method transferNodeToPnt2D.
	 * @param targetList List<Node>
	 * @return ArrayList<Point2D>
	 */
	private void transferNodeToPnt2D(List<Node> targetList){
		tempPntList.clear();
		for(Node nd:targetList){
			Point2D pnt = new Point2D.Double(nd.getX(),nd.getY());
			tempPntList.add(pnt);			
		}
	}
	
	/**
	 * Method transferEdgeToPnt2D.
	 * @param targetList List<Edge>
	 * @return ArrayList<Point2D>
	 */
	private void transferEdgeToPnt2D(List<Edge> targetList){
		tempEdgeList.clear();
		for(Edge eg:targetList){
			Point2D pnt_1 = new Point2D.Double(eg.getSource().getX(),eg.getSource().getY());
			Point2D pnt_2 = new Point2D.Double(eg.getDestination().getX(),eg.getDestination().getY());
			tempEdgeList.add(pnt_1);
			tempEdgeList.add(pnt_2);
		}
		
	}
	
	/**
	 * Method createCoordinateGraph.
	 * @param mapName String
	 * @return Boolean
	 */
	public Boolean createCoordinateGraph(String mapName){
		Boolean success = false;
		try{
			mapModel.saveMapGraph(mapName, nodeList, edgeList);
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
	/**
	 * Method addNewMap.
	 * @param mapName String
	 * @param mapImageURL String
	 * @param mapType String
	 * @return Boolean
	 */
	public Boolean addNewMap(String mapName, String mapImageURL, String mapType){
		System.out.println(mapName);
		System.out.println(mapImageURL);
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
	/**
	 * Method deleteMap.
	 * @param mapName String
	 * @return Boolean
	 */
	public Boolean deleteMap(String mapName){
		int index = listofMaps.indexOf(mapName);
		listofMaps.remove(index);
		urlsofMaps.remove(index);
		return true;
	}	
	
	/**
	 * Method getMaxNodeID.
	 * @return int
	 */
	public int getMaxNodeID() {
		int maxNodeID=0;
		for (Node n:nodeList) {
			if (n.getID()>maxNodeID){
				maxNodeID=n.getID();
			}
		}
		return maxNodeID;
	}
	
	/**
	 * Method getMaxEdgeID.
	 * @return int
	 */
	public int getMaxEdgeID() {
		int maxEdgeID=0;
		for (Edge e:edgeList) {
			if (e.getEdgeID()>maxEdgeID){
				maxEdgeID=e.getEdgeID();
			}
		}
		return maxEdgeID;
	}
	
	/**
	 * Method addPoint.
	 * @param inputPnt Point2D
	 * @param floorNum int
	 * @param entranceID int
	 * @param buildingName String
	 * @param pointType String
	 * @param pointDescription String
	 * @return Boolean
	 */
	public Boolean addPoint(Point2D inputPnt, String floor, int entranceID, String buildingName, String pointType, String pointDescription){
		Boolean success = false;
		if(CheckPntExistence(inputPnt)==0){
			//nodeList.add(new Node(this.getMaxNodeID()+1, (int)inputPnt.getX(), (int)inputPnt.getY(), floorNum, entranceID, buildingName, pointType, pointDescription));
			//Changed to have floor as a String will be less confusing in the long run -J
			nodeList.add(new Node(this.getMaxNodeID()+1, (int)inputPnt.getX(), (int)inputPnt.getY(), entranceID, buildingName, floor, pointType));	
			//nodeList.add(new Node(this.getMaxNodeID()+1, (int)inputPnt.getX(), (int)inputPnt.getY()));
			transferNodeToPnt2D(nodeList);
			success = true;
		}
		return success;
	}
	//DO WE NEED BELOW METHOD? SEEMS LIKE NO?
	/**
	 * Method addPoint.
	 * @param inputPnt Point2D
	 * @return Boolean
	 */
	/**public Boolean addPoint(Point2D inputPnt){
		Boolean success = false;
		if(CheckPntExistence(inputPnt)==0){
			//nodeList.add(new Node(this.getMaxNodeID()+1, (int)inputPnt.getX(), (int)inputPnt.getY(), 1, 0, "null", "null", "null"));	
			nodeList.add(new Node(this.getMaxNodeID()+1, (int)inputPnt.getX(), (int)inputPnt.getY()));
			transferNodeToPnt2D(nodeList);
			success = true;
		}
		return success;
	}*/

	/**
	 * Method createEdge.
	 * @param pnt1 Point2D
	 * @param pnt2 Point2D
	 * @return Boolean
	 */
	public Boolean createEdge(Point2D pnt1, Point2D pnt2){
		Boolean success = false;
		// Check Edge Redundancy
		int PointID_1 = CheckPntExistence(pnt1);
		int PointID_2 = CheckPntExistence(pnt2);
		// Check if edge already exist in the edge list
		if (PointID_1>0&&PointID_2>0&&PointID_1!=PointID_2) {
			for (Edge e:edgeList){
				if (((e.getSource().getID()==PointID_1)&&(e.getDestination().getID()==PointID_2))||((e.getSource().getID()==PointID_2)&&(e.getDestination().getID()==PointID_1))) {
					System.out.println("Edge exists!");
					return success;
				}
			}
			Node start=findNodeInList(PointID_1);
			Node end=findNodeInList(PointID_2);
			if ((start==null)||(end==null)) {
				return success;
			}
			
			edgeList.add(new Edge(this.getMaxEdgeID()+1, start, end ,Math.sqrt(Math.pow(start.getX()-end.getX(), 2)+Math.pow(start.getY()-end.getY(), 2))));
			transferEdgeToPnt2D(edgeList);
			
			success=true;
			return success;
		} 
		
		return success;
	}
	
	public void clearAllTempData(){
		tempPntList.clear();
		tempEdgeList.clear();
		nodeList.clear();
		edgeList.clear();
	}
	
	/**
	 * Method deletePoint.
	 * @param inputPnt Point2D
	 * @return boolean
	 */
	public boolean deletePoint(Point2D inputPnt){
		Boolean pointDeleted = false;
		Node nodeFound=null;

		if(nodeList.isEmpty()){
			System.out.println("Node List is empty, nothing to delete.");
			return pointDeleted;
		}
		
		int pntID = this.CheckPntExistence(inputPnt);
		if(pntID == 0){
			System.out.println("Can't find point in the List, Will not delete any points.");
			return pointDeleted;
		}		
		
		nodeFound=findNodeInList(pntID);
		for (int edgeSeq=edgeList.size()-1; edgeSeq>=0; edgeSeq--){
			if ((edgeList.get(edgeSeq).getSource().getID()==pntID)||(edgeList.get(edgeSeq).getDestination().getID()==pntID)){
				edgeList.remove(edgeSeq);
				}
		}
		nodeList.remove(nodeFound);
		transferNodeToPnt2D(nodeList);
		transferEdgeToPnt2D(edgeList);
		pointDeleted=true;
		return pointDeleted;
	}

	/**
	 * Method deleteEdge.
	 * @param p Point2D
	 * @return boolean
	 */
	public boolean deleteEdge(Point2D p){
		int edgeID = checkIfPointIsInEdge(p);
		
		if(edgeID>0){			
			System.out.println("Edge "+ edgeID+ " will be deleted");
			for (int edgeSeq=edgeList.size()-1; edgeSeq>=0; edgeSeq--){
				if(edgeList.get(edgeSeq).getEdgeID() == edgeID){
					edgeList.remove(edgeSeq);	
					System.out.println("An edge is deleted!");
					transferEdgeToPnt2D(edgeList);
					return true;		
				}
			}
		}
		return false;
	}
	

	
 	private void testPrintNodeEdgeList(){
		System.out.println("There are currently total "+this.nodeList.size()+" Nodes in Controller's memory");
		for(Node n:this.nodeList){
			System.out.println("Node"+n.getID()+":"+n.getX()+" "+n.getY());
		}		
		
		System.out.println("There are currently total "+this.edgeList.size()+" Edges in Controller's memory");
		for(Edge e:this.edgeList){
			System.out.println("Edge "+e.getEdgeID()+":"+e.getSource().getID()+" -> "+e.getDestination().getID());
		}
	}
	
	//See if selected point is a part of an existing edge
	// If the distance between the selected point 
	/**
	 * Method checkIfPointIsInEdge.
	 * @param p Point2D
	 * @return int
	 */
	public int checkIfPointIsInEdge(Point2D p){
		double ab, ap, pb;
		int r = 0;
		for(Edge e: edgeList){
			
			ab=e.getEdgeLength();			
			ap=Math.sqrt(Math.pow(e.getSource().getX()-p.getX(), 2)+Math.pow(e.getSource().getY()-p.getY(), 2));
			pb=Math.sqrt(Math.pow(e.getDestination().getX()-p.getX(), 2)+Math.pow(e.getDestination().getY()-p.getY(), 2));
			
			if(Math.abs(ab-(ap+pb))<=5){
				r=e.getEdgeID();				
				System.out.println("Point " + p + " belongs to Edge "+r);
				return r;
			}
		}
		System.out.println("This Point doesn't belongs to any Edge");
		return r;
	}

	/**
	 * Method CheckPntExistence.
	 * @param pnt Point2D
	 * @return int
	 */
	public int CheckPntExistence(Point2D pnt){
		int pntID = 0;
		int toleranceRadius = 15;	// 15 pixels
		for (Node tempN: nodeList){
			double d = Math.sqrt(Math.pow(pnt.getX() - tempN.getX(), 2) + 
							     Math.pow(pnt.getY() - tempN.getY(), 2));
			System.out.println("the distance is : "+d);
			if(d <= toleranceRadius){
				pntID = tempN.getID();
				System.out.println("Point "+pntID+" is Found in the nodeList!");
				return pntID;
			}
		}
		return pntID;
	}	
	
	private Node findNodeInList (int nodeID) {
		Node node=null;
		for (Node n:nodeList) {
			if (n.getID()==nodeID) {
				node=n;
				return node;
			}
		}
		return node;
	}
	
	public boolean setDescriptionOfNode (int nodeID){
		boolean success=false;
		if (this.findNodeInList(nodeID)!=null){
			//this.findNodeInList(nodeID).setDescription();
			//waiting for extension of node class;
			success=true;
			return success;
		}
		return success;
	}
	
	public boolean setEntranceIDOfNode (int nodeID){
		boolean success=false;
		if (this.findNodeInList(nodeID)!=null){
			//this.findNodeInList(nodeID).setEntranceID();
			//waiting for extension of node class;
			success=true;
			return success;
		}
		return success;
	}
	
	public boolean setTypeOfNode (int nodeID){
		boolean success=false;
		if (this.findNodeInList(nodeID)!=null){
			//this.findNodeInList(nodeID).setType();
			//waiting for extension of node class;
			success=true;
			return success;
		}
		return success;
	}
	
	public String getDescriptionOfNode (int nodeID){
		String description=new String();
		if (this.findNodeInList(nodeID)!=null){
			//description=this.findNodeInList(nodeID).getDescription();
			//waiting for extension of node class;
			return description;
		}
		return description;
	}
	
	public int getEntranceIDOfNode (int nodeID){
		int entranceID=-1;
		if (this.findNodeInList(nodeID)!=null){
			//entranceID=this.findNodeInList(nodeID).getEntranceID();
			//waiting for extension of node class;
			return entranceID;
		}
		return entranceID;
	}
	
	public String getTypeOfNode (int nodeID){
		String nodeType=new String();
		if (this.findNodeInList(nodeID)!=null){
			//nodeType=this.findNodeInList(nodeID).getType();
			//waiting for extension of node class;
			return nodeType;
		}
		return nodeType;
	}
	
	/**
	 * Method pointMapping.
	 * @param inputPnt Point2D
	 * @return Point2D
	 */
	public Point2D pointMapping(Point2D inputPnt){		
		Point2D searchingResult = new Point2D.Double(0,0);
		
		for (Point2D temPnt : tempPntList){
			double d = mapModel.calculateDistance(inputPnt.getX(), temPnt.getX(), inputPnt.getY(), temPnt.getY());
			if(d <= 15){
				
				System.out.println("Mapping To Point" + temPnt.getX() + "," + temPnt.getY());
				searchingResult = temPnt;
				return searchingResult;
			}
		}		
		System.out.println("Invalid Input!!");
		return searchingResult;
	}
	
	/**
	 * Method getLastPnt.
	 * @return Point2D
	 */
	public Point2D getLastPnt()
	{
		Point2D pnt = new Point2D.Double(0,0);
		if(tempPntList.size()!=0){
			pnt = tempPntList.get(tempPntList.size()-1);
			System.out.println("The last Point is: "+pnt.getX()+ "" + pnt.getY());
			return pnt;
		}	
		System.out.println("There is no Pnt in the list!");
		return pnt;		
	}
	
	/**
	 * Method getDisplayPnt.
	 * @return ArrayList<Point2D>
	 */
	public ArrayList<Point2D> getDisplayPnt(){		
		return tempPntList;
	}
	
	/**
	 * Method getDisplayEdge.
	 * @return ArrayList<Point2D>
	 */
	public ArrayList<Point2D> getDisplayEdge(){
		return tempEdgeList;
	}
}
