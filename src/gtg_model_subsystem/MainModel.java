
package gtg_model_subsystem;
import java.awt.Point;
import java.awt.geom.Point2D;


import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Hashtable;

/**
 */
public class MainModel {
	private List<Node> nodes;
	private List<Edge> edges;
	private Path path;
	private CoordinateGraph graph;
	private List<Admin> admins;
	private FileProcessing fileProcessing;
	private Map tempMap;
	private Hashtable<String, Map> mapTable;
	private Hashtable<String, Path> mapPaths;
	public MainModel(){
		admins = new ArrayList<Admin>();
		fileProcessing = new FileProcessing();
		mapTable = new Hashtable<String, Map>();
		path = new Path(null, null, null);
		try {			
			loadMapLists();
			loadAdmin();			
			loadFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
	/**
	 * Load maps from master map text file and store into table of maps
	
	 * @return true if load and store the map table successfully
	 * 		   false if maps does not exist */
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
		//IF the master map list failed to load THEN
		if(masterMapList==null){
			return false;
		} else{
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
	
	
	 * @return true if deletion was successful false otherwise * @throws IOException  */
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
	
	 * @param mapType the type of map that we are saving
	
	
	 * @param mapImgURL String
	 * @return true if map was successfully created false otherwise * @throws IOException  */
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
	
	public boolean loadFiles(){
		boolean mapCreated = false;
		try{
			for(String mapName: mapTable.keySet()){
				mapCreated = createMapGraph(mapName);
			}
		}catch(IOException e){
			System.out.println(e.toString());
		}		
		return mapCreated;		
	}
	
	/**
	 * Method loadFiles.
	 * @param mapName String
	 * @return boolean
	 */
	public boolean loadFiles(String mapName){
		boolean mapCreated = false;
		try{
			mapCreated = createMapGraph(mapName);
		}catch(IOException e){
			System.out.println(e.toString());
		}		
		return mapCreated;		
	}
	/**
	 * Method loadAdmin.
	 * @throws IOException
	 */
	public void loadAdmin() throws IOException{
		fileProcessing.readAdmin(admins);
	}
	
	/**
	 * Method createMapGraph.
	 * @param mapName String
	 * @return boolean
	 * @throws IOException
	 */
	public boolean createMapGraph(String mapName) throws IOException{
		
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		boolean createMapGraphSuccess = true;
		if(!mapTable.containsKey(mapName)){
			createMapGraphSuccess = false;
			System.out.println("Map does not exist in table");
			return createMapGraphSuccess;
		}
		fileProcessing.readGraphInformation(nodes, edges, mapName);
		
		
		graph = new CoordinateGraph(nodes, edges);
		mapTable.get(mapName).setGraph(graph);

		return createMapGraphSuccess;
	}
	
	/**
	 * The saveMapGraph method will save the temporarily stored nodes and edges from the controller into their 
	 * appropriate text files based on mapName. First, it will generate the node list by seeing if there is already
	 * a unique node on maps node list. Secondly, it will generate the edge list based on unique node identifiers in the
	 * node list. Lastly, it will save the newly created nodes and edges to their appropriate map specified text files.
	 * @param mapName the name of the map that the nodes and edges belong to
	 * @param tempPntList the temporary points that need to be converted to nodes and saved.
	 * @param tempEdgeList the temporary list that has unique node IDs to generate edges.
	
	
	 * @return true of write was successful, false otherwise * @throws IOException */
	public boolean saveMapGraph(String mapName, List<Node> tempNodeList, List<Edge> tempEdgeList) throws IOException{
			//Generate new nodes from the temporary point list to be added to graph
			//List<Node> nodeList = generatingNodeList(mapName, tempPntList);	
			//Generate edge list from unique node IDs specified
			//List<Edge> edgeList = generatingEdgeList(mapName,tempEdgeList, nodeList);
			
			//Generate a new coordinate graph to be saved by admin
			graph = new CoordinateGraph(tempNodeList, tempEdgeList);
			//STORE it as the new graph
			mapTable.get(mapName).setGraph(graph);
			//IF the node list is not empty THEN

			try{
				//STORE the new nodes and edges
				fileProcessing.saveGraphInformation(mapTable.get(mapName).getGraph().getNodes(), 
													mapTable.get(mapName).getGraph().getEdges(), 
													mapName);
			}catch(IOException e){
				//Signal input output error to controller based on false
				System.out.println(e.toString());
				return false;
			}
			System.out.println("File saved successfully");	
		
			return true;
		}
	
	/**
	 * Method testDij.
	 * @param mapName String
	 */
	public void testDij(String mapName){
		
		//testing for loading of nodes/edges
		try {
			runJDijkstra(mapName);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}//END CATCH loadNodes/Edges
		
	}
	
	public boolean multiPathCalculate(String[] mapNames){
		boolean multiPathCalcSuccess = true;
		for(String mapName: mapNames){
			singlePathCalculate(mapName);
		}
		
		return multiPathCalcSuccess;
	}
	/**
	 * Method testDij.
	 * @param mapName String
	 */
	public void singlePathCalculate(String mapName){
		
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
	 * @param mapName String
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
		//Store the map with the associated path for user
		mapPaths.put(mapName, path);
	}
	
	/**
	 * isValidAdmin method validates if the user that has choosen to login as admin is an admin.
	 * @param userName the string representation of the login username
	 * @param password the string representation of the login password
	
	 * @return true if user is admin; otherwise false. */
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
	 * Take in x and y values for user chosen node points, then cycle through each node on current coordinate graph node list.
	 * Then keep track of current difference in x and y value. If the current difference is less then the previous difference;
	 * of both x and y points then set the new validated point as the new closest point.
	 * @param x the x value for the given point
	 * @param y the y value for the given point
	
	 * @param mapName String
	 * @return the more accurate validated point */
	public Point validatePoint(String mapName, int x, int y){
		Node validatedNode = null;
		if(mapTable.isEmpty()){
			Point pnt = new Point();
			System.out.println("MapTable is empty, Validation Failed");
			return pnt;
		}
		double currentDiff = 0.0;
		double previousDiff = Double.POSITIVE_INFINITY;
		//printNodes("BH_Basement");
		printNodes(mapName);
		for(Node node: mapTable.get(mapName).getGraph().getNodes()){
			currentDiff = calculateDistance(node.getX(),x,node.getY(),y);
			if(currentDiff < previousDiff){
				previousDiff = currentDiff;
				validatedNode = node;
			}				
		}
		Point correctedPoint = new Point(validatedNode.getX(), validatedNode.getY());
		System.out.println(correctedPoint.getX() + " " + correctedPoint.getY());
		return correctedPoint;
	}
	/**
	 * Method setStartEndPathPoint.
	 * @param point Point
	 * @param pointType String
	 * @param mapName String
	 * @return boolean
	 */
	public boolean setStartEndPathPoint(Point point, String pointType, String mapName){
			boolean isSet = false;
			for(Node node: mapTable.get(mapName).getGraph().getNodes()){
				if((point.x == node.getX()) && (point.y == node.getY()) && (pointType.equals("FROM"))){
					System.out.println("Set Start point");
					path.setStartPoint(node);
					isSet = true;
				}
				else if((point.x == node.getX()) && (point.y == node.getY()) && (pointType.equals("TO"))){
					System.out.println("Set End point");
					path.setEndPoint(node);
					isSet = true;
				}
			}
			return isSet;
	}
	/**
	 * Method printNodes.
	 * @param mapName String
	 */
	public void printNodes(String mapName){
		int count = 0;
		for(Node node: mapTable.get(mapName).getGraph().getNodes()){
			count++;
			System.out.print(node.getID() + " " + node.getX() + " " + node.getY() + " " + node.getFloor());
			System.out.println();
		}
		System.out.println(count);
		System.out.println("END PRINT NODES");
	}
	/**
	 * Method printNodes.
	 * @param nodes List<Node>
	 */
	public void printNodes(List<Node> nodes){
		int count = 0;
		System.out.println("PRINTING NODES");
		for(Node node: nodes){
			count++;
			System.out.print(node.getID() + " " + node.getX() + " " + node.getY());
			System.out.println();
		}
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
	/**
	 * Method printPath.
	 * @param mapName String
	 */
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

	/**
	 * Method getPath.
	 * @return Path
	 */
	public Path getPath(){
		return this.path;
	}
	/**
	 * Method convertWayPointsToPoints.
	 * @return ArrayList<Point>
	 */
	public ArrayList<Point> convertWayPointsToPoints(){
		ArrayList<Point> tempWayPoints = new ArrayList<Point>();
		for(Node node : path.getWayPoints()){
			tempWayPoints.add(new Point(node.getX(), node.getY()));
		}
		return tempWayPoints;
	}
	/**
	 * Method getArrayOfMapNames.
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getArrayOfMapNames(){
		ArrayList<String> tempArrayOfMapNames = new ArrayList<String>();
		Enumeration e = mapTable.keys();
		while(e.hasMoreElements()){
			String mapName = (String) e.nextElement();
			tempArrayOfMapNames.add(mapName);
		}
		return tempArrayOfMapNames;
	}
	/**
	 * Method getNodeList.
	 * @param mapName String
	 * @return List<Node>
	 */
	public List<Node> getNodeList(String mapName){
		List<Node> currentNodeList = mapTable.get(mapName).getGraph().getNodes();	
		return currentNodeList;		
	}
	/**
	 * Method getEdgeList.
	 * @param mapName String
	 * @return List<Edge>
	 */
	public List<Edge> getEdgeList(String mapName){
		List<Edge> currentEdgeList = mapTable.get(mapName).getGraph().getEdges();	
		return currentEdgeList;
	}
	/**
	 * Method calculateDistance.
	 * @param x1 double
	 * @param x2 double
	 * @param y1 double
	 * @param y2 double
	 * @return double
	 */
	public double calculateDistance(double x1, double x2, double y1, double y2){
		return Math.sqrt(Math.pow(x2-x1, 2)+ Math.pow(y2 - y1, 2));
	}
    //find node Id for the start of the edge
    /**
     * Method findNodeId.
     * @param mapName String
     * @param point Point
     * @return int
     */
    public int findNodeId(String mapName,Point point){
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
