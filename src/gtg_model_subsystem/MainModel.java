
package gtg_model_subsystem;
import java.awt.Point;
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
			loadAdmin();
			loadFiles();	// Yixiao
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//re-add string mapName future
	public void loadFiles(){
		try{
			createMapGraph("BH_Basement");
		}catch(IOException e){
			System.out.println(e.toString());
		}
	}
	public void loadAdmin() throws IOException{
		fileProcessing.readAdmin(admins, "ModelFiles\\adminFile.txt");
	}
	public void createMapGraph(String mapName) throws IOException{
		System.out.println("creating the Map/Graph");
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		fileProcessing.readNodesFile(nodes, MapNodeURLS.TEST_MAP_NODES);
		fileProcessing.readEdgesFile(nodes, edges, MapEdgeURLS.TEST_MAP_EDGES);
		
		graph = new CoordinateGraph(nodes, edges);
		tempMap = new Map(mapName, graph, null);
		mapTable.put(mapName, tempMap);
	}
	public void saveMapGraph(String mapName) throws IOException{
		Map saveMap = mapTable.get(mapName);
		fileProcessing.saveNodesFile(saveMap.getGraph().getNodes(), MapNodeURLS.TEST_MAP_NODES);
		fileProcessing.saveEdgesFile(saveMap.getGraph().getEdges(), MapEdgeURLS.TEST_MAP_EDGES);
	}
	public void testDij(String mapName){
		
		//testing for loading of nodes/edges
		try {
			runJDijkstra(mapName);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}//END CATCH loadNodes/Edges
		
		//testing for saving nodes and edges
		//try {
			//saveNodes();
			//saveEdges();
		//}
		//catch (IOException e) {
				// TODO Auto-generated catch block
				//System.out.println(e.toString());
		//}//END CATCH saveNodes/Edges
		
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
		
		//Values to hold the start point and end point for the path
/*		Node startPoint = null;
		Node endPoint = null;
	
		//Search though the node elements
		for(Node node: mapTable.get(mapName).getGraph().getNodes()){
			//IF current node ID equals start integer value THEN
			if(node.getID() == start)
				//SET start node as current node
				startPoint = node;
			//ELSE IF current node ID equals end integer value THEN
			else if(node.getID() == end)
				//SET end node as current node
				endPoint = node;
		}*/
		//Start the execution with the first starting node
		dijkstra.execute(path.getStartPoint());
		
		//Store the wayPoints for the map's graph
		LinkedList<Node> wayPoints = dijkstra.getPath(path.getEndPoint());
		
		//Create the new path with the start point, end point, and way points
		path.setPath(wayPoints);;
		
		//Store it into the map
		//mapTable.get(mapName).setPath(path);
	}
	/*public void runDijkstra(int start, int end){
		int nodeId1;
		int nodeId2;
		int edgeLength;
		int count = 0;
		Edge e;
		Dijkstra d;
		int matrix[][]=new int[28][28];
		for(int i=0;i<=27;i++)
		  for(int j=0;j<=27;j++)
			matrix[i][j]=10000;  
		
		Iterator<Edge> it=edges.iterator();
		while(it.hasNext())
		{
		     e=(Edge)it.next();
		     nodeId1=e.getSource().getID();
		     nodeId2=e.getDestination().getID();
		     edgeLength=(int)e.getEdgeLength();
		     //System.out.println(nodeId1+" "+nodeId2+" "+edgeLength);
		     count++;
		     //System.out.println(count);
		     matrix[nodeId1][nodeId2]=edgeLength;
		}
		
//		for(int i=0;i<=27;i++)
//		{
//		  for(int j=0;j<=27;j++)
//			System.out.print(matrix[i][j]+" ");
//		  System.out.println();
//		}
		d=new Dijkstra(matrix,start,end);
		d.find();
		d.printShortestDistance();
		d.printPath();
	}*/
	
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
			if((admin.getUsername() == userName) && (admin.getPassword() == password))
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
					//mapTable.get(mapName).getPath().setStartPoint(node);
					System.out.println("Set Start point");
					path.setStartPoint(node);
					isSet = true;
				}
				else if((point.x == node.getX()) && (point.y == node.getY()) && (pointType == "TO")){
					//mapTable.get(mapName).getPath().setEndPoint(node);
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
}
