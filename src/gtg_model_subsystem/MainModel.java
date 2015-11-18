
package gtg_model_subsystem;
import java.awt.Point;
import java.awt.geom.Point2D;
import gtg_model_subsystem.Node;
import gtg_model_subsystem.CoordinateGraph;
import gtg_model_subsystem.Edge;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Hashtable;

public class MainModel {
	private List<Node> nodes;
	private List<Edge> edges;
	private Path path;
	private CoordinateGraph graph;
	private List<Admin> admins;
	private FileProcessing fileProcessing;
	private Map tempMap;
	private Hashtable<String, Map> mapTable;
	public MainModel(){
		admins = new ArrayList<Admin>();
		fileProcessing = new FileProcessing();
		mapTable = new Hashtable<String, Map>();
		path = new Path(null, null, null);
		try {			
			loadMapLists();
			loadAdmin();			
			//loadMapListFile();
			loadFiles("BH_Basement");	// Yixiao
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
	/**
	 * Load maps from master map text file and store into table of maps
	 * @return true if load and store the map table successfully
	 * 		   false if maps does not exist
	 */
	public boolean loadMapLists()
	{
		ArrayList<Map> masterMapList=null;
		try {
			//load maps from master text file
			masterMapList=fileProcessing.loadMapList();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(masterMapList==null){
			System.out.println("Map list is null");
			return false;
		} else{
			System.out.println("Map List");
			//access each map and store it into map table
			for(Map map:masterMapList){
				System.out.println(map.getMapName());
				mapTable.put(map.getMapName(), map);
			}
			
			return true;
		}
	}
	/**
	 * deleteMap will delete map from the map table and the master list text file
	 * @param mapName the name of the map the admin wishes to delete
	 * @return true if deletion was successful false otherwise
	 * @throws IOException 
	 */
	public boolean deleteMap(String mapName) throws IOException{
			boolean deleteSuccess = true;
			if(mapTable.get(mapName) == null){
				System.out.println("Map does not exist");
				deleteSuccess = false;
			}
			//IF map exists in map table THEN
			if(mapTable.get(mapName) != null){
				//will this delete from memory??
				//REMOVE map from map table
				mapTable.remove(mapName);
				//REMOVE map from master map list so it will not be re-loaded
				fileProcessing.deleteMapFromMaster(mapName);
				System.out.println("Delete Success");
			}
			return deleteSuccess;
	}
	
	/**
	 * Method to save newly created maps added by admin from admin view to the list of maps.
	 * @param mapName the name of new map to be saved
	 * @param mapNameURL the name of new map URL to be saved
	 * @param mapType the type of map that we are saving
	 * @return true if map was successfully created false otherwise
	 * @throws IOException 
	 */
	public boolean saveNewMap(String mapName, String mapImgURL, String mapType) throws IOException{
				boolean saveNewMap = true;
				//IF map does not exist in map table THEN
				if (mapTable.get(mapName) != null){
					System.out.println("Map Already exists");
					saveNewMap = false;
				}
				//STORE map in masterMapList 
				else{
					fileProcessing.saveMapToMaster(mapName, mapImgURL, mapType);
				}
				return saveNewMap;
	}
	// Yixiao 2015-11-15
	public List<Node> getNodeList(String mapName){
		List<Node> currentNodeList = mapTable.get(mapName).getGraph().getNodes();	
		return currentNodeList;		
	}
	// Yixiao 2015-11-15
	public List<Edge> getEdgeList(String mapName){
		List<Edge> currentEdgeList = mapTable.get(mapName).getGraph().getEdges();	
		return currentEdgeList;
	}
	
	//re-add string mapName future
	public boolean loadFiles(String mapName){
		boolean mapCreated = false;
		try{
			mapCreated = createMapGraph(mapName);
		}catch(IOException e){
			System.out.println(e.toString());
		}		
		return mapCreated;		
	}
	public void loadAdmin() throws IOException{
		fileProcessing.readAdmin(admins);
	}
	
	public boolean createMapGraph(String mapName) throws IOException{
		
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		boolean createMapGraphSuccess = true;
		if(!mapTable.containsKey(mapName)){
			createMapGraphSuccess = false;
			System.out.println("Map does not exist in table");
			return createMapGraphSuccess;
		}
		fileProcessing.readNodesFile(nodes, mapName);
		fileProcessing.readEdgesFile(nodes, edges, mapName);		
		
		graph = new CoordinateGraph(nodes, edges);
		mapTable.get(mapName).setGraph(graph);

		return createMapGraphSuccess;
	}
	
	public void saveMapGraph(String mapName) throws IOException{		
		// Save to existing File
		Map saveMap = mapTable.get(mapName);
		fileProcessing.saveNodesFile(saveMap.getGraph().getNodes(), MapNodeURLS.TEST_MAP_NODES);
		fileProcessing.saveEdgesFile(saveMap.getGraph().getEdges(), MapEdgeURLS.TEST_MAP_EDGES);
	}
	
	//Overrode method to handle controller temporary list for nodes and edges
	public void saveMapGraph(String mapName, ArrayList<Point2D> tempPntList, ArrayList<Point2D> tempEdgeList) throws IOException{
		try{
			// Yixiao 2015-11-16 In case creating a new map
			Boolean newMap = false;
			// Save a new map
			if(mapTable.get(mapName) == null){
				newMap = true;
			}
			
			List<Node> nodeList = generatingNodeList(mapName, tempPntList);	
			if(!nodeList.isEmpty()){
				System.out.println("Node List created!");
				List<Edge> edgeList = generatingEdgeList(mapName,tempEdgeList, nodeList);
				if(!edgeList.isEmpty()){
					System.out.println("Edge List Created!");

					// Adding to the existing nodeList and Edge list;
					for(Node newNode:nodeList){
						nodes.add(newNode);
					}
					for(Edge newEdge:edgeList){
						edges.add(newEdge);
					}					
					// Yixiao 2015-11-17
					String NodeURL = "ModelFiles"+System.getProperty("file.separator")+"NodeFiles"+System.getProperty("file.separator")+mapName+"_Node.txt";
					String EdgeURL = "ModelFiles"+System.getProperty("file.separator")+"NodeFiles"+System.getProperty("file.separator")+mapName+"_Edge.txt";	
					fileProcessing.saveNodesFile(nodes, NodeURL);	
					fileProcessing.saveEdgesFile(edges, EdgeURL);		
					System.out.println("File saved successfully");	
				}	
			}
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
}
	
	private List<Node> generatingNodeList(String mapName, ArrayList<Point2D> inputPointList){
		List<Node> NodeList = new ArrayList<Node>();
		
		if(inputPointList.isEmpty()){
			System.out.println("Point List is Empty, there is nothing to save");			
			return NodeList;
		}
		
		// Yixiao 2015-11-16 In case generating a new map		
		Boolean NewMap = false;
		if(mapTable.get(mapName) == null){
			NewMap = true;
		}
		
		for(int i = 0; i<inputPointList.size(); i++)
		{
			Point2D tempPnt = inputPointList.get(i);
			int X = (int)tempPnt.getX();
			int Y = (int)tempPnt.getY();			
			if(NewMap){
				Node tempNode = new Node(i+1, X, Y);
				NodeList.add(tempNode);				
			}
			else{
				Node tempNode = new Node(mapTable.get(mapName).getGraph().getNodes().size()+i+1, X, Y);
				NodeList.add(tempNode);	
			}
		}	
		return NodeList;
	}
	
	private List<Edge> generatingEdgeList(String mapName, ArrayList<Point2D> inputEdgeList, List<Node> tempNodeList){
		List<Edge> EdgeList = new ArrayList<Edge>();
		// It is also better to add odd/even number judgement here in the future
		if(inputEdgeList.isEmpty()){
			System.out.println("Edge List is Empty, there is nothing to save");			
			return EdgeList;
		}
		
		// Yixiao 2015-11-16 In case generating a new map
		Boolean NewMap = false;
		if(mapTable.get(mapName) == null){
			NewMap = true;
		}

		for (int i = 0; i<inputEdgeList.size(); i+=2)
		{
			System.out.println(i);
			Point2D pnt_1 = inputEdgeList.get(i);
			Point2D pnt_2 = inputEdgeList.get(i+1);
			Node startNode= new Node(0,0,0);
			Node endNode= new Node(0,0,0);
			for(Node n:tempNodeList){
				if(n.getX() == (int)pnt_1.getX() && n.getY() == (int)pnt_1.getY()){
					startNode = n;
					break;
				}
			}
			for(Node n:tempNodeList){
				if(n.getX() == (int)pnt_2.getX() && n.getY() == (int)pnt_2.getY()){
					endNode = n;
					break;
				}
			}
			
			if (startNode.getID()!=0&&endNode.getID()!=0){
				if(NewMap){
					EdgeList.add(new Edge(i+1, startNode, endNode, 1));	
					EdgeList.add(new Edge(i+2, endNode, startNode, 1));
				}
				else{
					EdgeList.add(new Edge(mapTable.get(mapName).getGraph().getEdges().size() + i+1, startNode, endNode, 1));	
					EdgeList.add(new Edge(mapTable.get(mapName).getGraph().getEdges().size() + i+2, endNode, startNode, 1));					
				}
			}
		}
		
		return EdgeList;
	}
	

	
	public void testDij(String mapName){
		
		//testing for loading of nodes/edges
		try {
			runJDijkstra(mapName);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}//END CATCH loadNodes/Edges
		
	}
	/** Temporary java dijkstra algorithim implemented by Joshua until he speaks with Libin about
	 *  fixing his up. First set the current maps graph into the algorithim. Next cycle through the nodes
	 *  and pluck out 
	 * @param start node ID
	 * @param end node ID
	 */
	public void runJDijkstra(String mapName){
		//Create object instance with temporary dijkstra algorithim
		JDijkstra dijkstra = new JDijkstra(mapTable.get(mapName).getGraph());
		
		//Start the execution with the first starting node
		dijkstra.execute(path.getStartPoint());
		
		//Store the wayPoints for the map's graph
		LinkedList<Node> wayPoints = dijkstra.getPath(path.getEndPoint());
		
		//Create the new path with the start point, end point, and way points
		path.setPath(wayPoints);
		
	}
	
	/**
	 * isValidAdmin method validates if the user that has choosen to login as admin is an admin.
	 * @param userName the string representation of the login username
	 * @param password the string representation of the login password
	 * @return true if user is admin; otherwise false.
	 */
	public boolean isValidAdmin(String userName, String password){
		//Assume user is not an admin
		boolean isAdmin = false;
		//FOR EACH admin in admin list
		for(Admin admin: admins){
			//IF current admin user name EQUALS param username AND admin password EQUALS param password
			if((admin.getUsername().equals(userName)) && (admin.getPassword().equals(password)))
				//SET isAdmin as valid
				isAdmin =true;
		}
		return isAdmin;
	}
	/**
	 * Validates the point for the view. By returning a Node for the controller to extract a point.
	 * Intake x and y values for user chosen node points, then cycle through each node on current coordinate graph node list.
	 * Then keep track of current difference in x and y value. If the current difference is less then the previous difference;
	 * of both x and y points then set the new validated point as the new closest point.
	 * @param x the x value for the given point
	 * @param y the y value for the given point
	 * @return the more accurate validated point
	 */
	public Point validatePoint(String mapName, int x, int y){
		Node validatedNode = null;
		if(mapTable.isEmpty()){
			Point pnt = new Point();
			System.out.println("MapTable is empty, Validation Failed");
			return pnt;
		}
		int currentDiff = 0;
		int previousDiff = Integer.MAX_VALUE;
		for(Node node: mapTable.get(mapName).getGraph().getNodes()){
			currentDiff = (int) Math.sqrt(Math.pow(node.getX()-x, 2)+ Math.pow(node.getY() - y, 2));
			if(currentDiff < previousDiff){
				previousDiff = currentDiff;
				validatedNode = node;
			}				
		}
		Point correctedPoint = new Point(validatedNode.getX(), validatedNode.getY());
		return correctedPoint;
	}
	public boolean setStartEndPathPoint(Point point, String pointType, String mapName){
			boolean isSet = false;
			for(Node node: mapTable.get(mapName).getGraph().getNodes()){
				if((point.x == node.getX()) && (point.y == node.getY()) && (pointType == "FROM")){
					System.out.println("Set Start point");
					path.setStartPoint(node);
					isSet = true;
				}
				else if((point.x == node.getX()) && (point.y == node.getY()) && (pointType == "TO")){
					System.out.println("Set End point");
					path.setEndPoint(node);
					isSet = true;
				}
			}
			return isSet;
	}
	public void printNodes(String mapName){
		int count = 0;
		for(Node node: mapTable.get(mapName).getGraph().getNodes()){
			count++;
			System.out.print(node.getID() + " " + node.getX() + " " + node.getY());
			System.out.println();
		}
		System.out.println(count);
		System.out.println("END PRINT NODES");
	}
	public void printEdges(){
		for(Edge edge: edges){
			System.out.print(edge.getEdgeID() + " " + edge.getSource().getID() + " " + edge.getDestination().getID() + " " + edge.getEdgeLength());
			System.out.println();
		}
		System.out.println("END PRINT EDGES");
	}
	public void printAdmins(){
		for(Admin admin: admins){
			System.out.print(admin.getUsername()+ " " + admin.getPassword());
			System.out.println();
		}
		System.out.println("END OF PRINT ADMIN");
	}
	public void printPath(String mapName){
		for(Node node: path.getWayPoints()){
			System.out.println(node.getID());
		}
		System.out.println("END PATH");
	}
	public void printMaps(){
		for(String value: mapTable.keySet()){
			System.out.println(value);
		}
	}

	public Path getPath(){
		return this.path;
	}
	public ArrayList<Point> convertWayPointsToPoints(){
		ArrayList<Point> tempWayPoints = new ArrayList<Point>();
		for(Node node : path.getWayPoints()){
			tempWayPoints.add(new Point(node.getX(), node.getY()));
		}
		return tempWayPoints;
	}
	public ArrayList<String> getArrayOfMapNames(){
		ArrayList<String> tempArrayOfMapNames = new ArrayList<String>();
		Enumeration e = mapTable.keys();
		while(e.hasMoreElements()){
			String mapName = (String) e.nextElement();
			tempArrayOfMapNames.add(mapName);
		}
		return tempArrayOfMapNames;
	}
	
    
    //find node Id for the start of the edge
    public int findNodeId(String mapName,Point point)
    {
    	for(Node node: mapTable.get(mapName).getGraph().getNodes())
    	{
			if((node.getX()==point.x)&&(node.getY()==point.y))
			{ 
				return node.getID();   
			}
    	}
		return 0;
    }
}
