package gtg_control_subsystem;

import java.util.List;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import gtg_model_subsystem.Node;
import gtg_model_subsystem.Edge;

public class MapDataController {
	
	private MainController mainController;
	
	private ArrayList<String> listOfMaps = new ArrayList<String>();
	private ArrayList<String> urlsOfMaps = new ArrayList<String>();
	private ArrayList<Node> nodeList = new ArrayList<Node>();
	private ArrayList<Edge> edgeList = new ArrayList<Edge>();
	private ArrayList<Point2D> tempPntList  = new ArrayList<Point2D>();
	private ArrayList<Point2D> tempEdgeList = new ArrayList<Point2D>();
	
	// Constructor
	public MapDataController(MainController controlInterface){
		//mapModelHandle = mapModel;	
		mainController = controlInterface;
		mapListInitial();
	}
	
	/* *******************************
	 * 
	 * 			Map Manipulation
	 * 
	 * ********************************/
	private void mapListInitial(){
/*		listOfMaps.add("BoyntonHall_Basement");
		listOfMaps.add("BoyntonHall_FirstFloor");
		listOfMaps.add("BoyntonHall_SecondFloor");
		listOfMaps.add("BoyntonHall_ThirdFloor");*/
		
		listOfMaps.add("BH_Basement");
		listOfMaps.add("BH_FirstFloor");
		listOfMaps.add("BH_SecondFloor");
		listOfMaps.add("BH_ThirdFloor");
		
		// map urls
		String BH_BASEMENT = "images"+System.getProperty("file.separator")+"BH_Basement.png";
		String BH_FIRST_FLOOR = "images"+System.getProperty("file.separator")+"BH_FirstFloor.png";
		String BH_SECOND_FLOOR = "images"+System.getProperty("file.separator")+"BH_SecondFloor.png";
		String BH_THIRD_FLOOR = "images"+System.getProperty("file.separator")+"BH_ThirdFloor.png";
		
		urlsOfMaps.add(BH_BASEMENT);
		urlsOfMaps.add(BH_FIRST_FLOOR);
		urlsOfMaps.add(BH_SECOND_FLOOR);
		urlsOfMaps.add(BH_THIRD_FLOOR);
	}
	
	/* Added by  neha. For now this method just pushes the data into the listofMaps and urlsofMaps.
	 * Once the model method is available call that method with the same paramaters.
	 * The model method should return a boolean value:
	 * True if mapName and mapImageURL are stored succesfully into the .txt file
	 * False if mapName and mapImageURL are not stored succesfully into the .txt file
	 */
	public void addNewMapToList(String mapName){
		listOfMaps.add(mapName);
	}
	
	public void addNewMapURLToList(String mapURL){
		urlsOfMaps.add(mapURL);
	}

	/* Added by  neha. For now this method just deletes the map from listofMaps and urlsofMaps.
	 * Once the model method is available call that method with the same paramaters.
	 * The model method should return a boolean value:
	 * True if mapName and mapImageURL are deleted succesfully from the .txt file
	 * False if mapName and mapImageURL are not deleted succesfully from the .txt file
	 */
	// Remove the map from MapList and MapURLList at the same time;
	public Boolean removeMapFromList(String mapName){
		Boolean mapRemoved = false;
		int index = listOfMaps.indexOf(mapName);
		if(index>0){
			listOfMaps.remove(index);
			urlsOfMaps.remove(index);
			mapRemoved = true;
		}
		else{
			System.out.print("Can't find map in the MapList");
		}
		return mapRemoved;
	}
	
	/* For integration the method return me the hardcoded arraylist.
	 * Once a method from model is available the method should return me an arrayList of mapnames.
	 * The input parameter mapName will help us to use the same method in the map page
	 * i.e if mapName is admin means the list should conatins all the map names
	 * if mapName is campus then the list will contain all building names
	 * if mapName is building then the list will contain all the floor names of the building and the campus map also.
	 * You will have to implement the switch case.
	 */
	public ArrayList<String> getMapList(String mapName){
		return listOfMaps;
	}
	
	public String getMapURL(String mapName){
		String mapurl = "";
		int index = listOfMaps.indexOf(mapName);
		mapurl = urlsOfMaps.get(index);
		return mapurl;
	}
	
	/* *******************************
	 * 
	 * 			Nodes and Edges
	 * 
	 * ********************************/
	public Boolean LoadingPntsAndEdges(String mapName){
		if(mainController.mapModel.loadFiles(mapName)){
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
	
	private void LoadInNodeList(String mapName){
		// Clear the temporary nodeList before add new point into it;
		this.nodeList.clear();
		ArrayList<Node> tempNode = (ArrayList<Node>)mainController.mapModel.getNodeList(mapName);		
		for(Node nd:tempNode){
			this.nodeList.add(nd);
		}
		//return copySuccess;		
	}
	
	private void LoadInEdgeList(String mapName){
		// Clear the temporary edgeList before add new point into it;
		this.edgeList.clear();
		ArrayList<Edge> tempEdge = (ArrayList<Edge>)mainController.mapModel.getEdgeList(mapName);		
		for(Edge eg:tempEdge){
			this.edgeList.add(eg);
		}
	}

	private void transferNodeToPnt2D(List<Node> targetList){
		tempPntList.clear();
		for(Node nd:targetList){
			Point2D pnt = new Point2D.Double(nd.getX(),nd.getY());
			tempPntList.add(pnt);			
		}
	}
	
	private void transferEdgeToPnt2D(List<Edge> targetList){
		tempEdgeList.clear();
		for(Edge eg:targetList){
			Point2D pnt_1 = new Point2D.Double(eg.getSource().getX(),eg.getSource().getY());
			Point2D pnt_2 = new Point2D.Double(eg.getDestination().getX(),eg.getDestination().getY());
			tempEdgeList.add(pnt_1);
			tempEdgeList.add(pnt_2);
		}
		
	}

	public Boolean addPoint(Point2D inputPnt, int floorNum, int entranceID, String buildingName, String pointType, String pointDescription){
		Boolean success = false;
		if(CheckPntExistence(inputPnt)==0){
			
			int coord_X = (int)inputPnt.getX();
			int coord_Y = (int)inputPnt.getY();						
			for(Node nd:nodeList){
				if(Math.abs(nd.getX() - coord_X)<5){
					coord_X = nd.getX();					
				}
				if(Math.abs(nd.getY() - coord_Y)<5){
					coord_Y = nd.getY();					
				}
			}
			
			nodeList.add(new Node(this.getMaxNodeID()+1, coord_X, coord_Y, entranceID, buildingName, floorNum, pointType));
			
			transferNodeToPnt2D(nodeList);
			success = true;
		}
		return success;
	}
	
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
	
	public Point2D pointMapping(Point2D inputPnt){		
		Point2D searchingResult = new Point2D.Double(0,0);
		
		for (Point2D temPnt : tempPntList){
			double d = Math.sqrt(Math.pow(inputPnt.getX()-temPnt.getX(), 2)+ Math.pow(inputPnt.getY() - temPnt.getY(), 2));
			if(d <= 15){
				System.out.println("Mapping To Point" + temPnt.getX() + "," + temPnt.getY());
				searchingResult = temPnt;
				return searchingResult;
			}
		}		
		System.out.println("Invalid Input!!");
		return searchingResult;
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
	
	private int CheckPntExistence(Point2D pnt){
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

	//See if selected point is a part of an existing edge
	private int checkIfPointIsInEdge(Point2D p){
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

	public ArrayList<Point2D> getDisplayPnt(){		
		return tempPntList;
	}
	
	public ArrayList<Point2D> getDisplayEdge(){
		return tempEdgeList;
	}
	
	public ArrayList<Node> getNodeList(){
		return nodeList;
	}
	
	public ArrayList<Edge> getEdgeList(){
		return edgeList;
	}	
	
	private int getMaxNodeID(){
		int maxNodeID=0;
		for (Node n:nodeList) {
			if (n.getID()>maxNodeID){
				maxNodeID=n.getID();
			}
		}
		return maxNodeID;
	}
	
	private int getMaxEdgeID() {
		int maxEdgeID=0;
		for (Edge e:edgeList) {
			if (e.getEdgeID()>maxEdgeID){
				maxEdgeID=e.getEdgeID();
			}
		}
		return maxEdgeID;
	}	

	public Point2D getLastPntInPntList()
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
	
	// Get Node properties
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
	
	// Set nodes properties
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
	
	// Clear Data
	public void clearAllTempData(){
		tempPntList.clear();
		tempEdgeList.clear();
		nodeList.clear();
		edgeList.clear();
	}
}
