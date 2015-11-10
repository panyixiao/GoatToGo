
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
		fileProcessing.readAdmin(admins, "ModelFiles"+System.getProperty("file.separator")+"adminFile.txt");
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
	//Overrode method to handle controller temporary list for nodes and edges
	public void saveMapGraph(String mapName, List<Node> tempNodeList, List<Edge> tempEdgeList) throws IOException{
		Map saveMap = mapTable.get(mapName);
		fileProcessing.saveNodesFile(saveMap.getGraph().getNodes(), MapNodeURLS.TEST_MAP_NODES);
		fileProcessing.saveEdgesFile(saveMap.getGraph().getEdges(), MapEdgeURLS.TEST_MAP_EDGES);
	}
	public List<Node> generatingNodeList(String mapName, ArrayList<Point2D> inputPointList){
		List<Node> NodeList = new ArrayList<Node>();
		
		if(inputPointList.isEmpty()){
			System.out.println("Point List is Empty, there is nothing to save");
			
			return NodeList;
		}		
		for(int i = 0; i<inputPointList.size(); i++)
		{
			Point2D tempPnt = inputPointList.get(i);
			int X = (int)tempPnt.getX();
			int Y = (int)tempPnt.getY();
			Node tempNode = new Node(mapTable.get(mapName).getGraph().getNodes().size()+i, X, Y);
			NodeList.add(tempNode);			
		}
		
		return NodeList;
	}
	
	
	public List<Edge> generatingEdgeList(ArrayList<Point2D> inputEdgeList, List<Node> tempNodeList){
		List<Edge> EdgeList = new ArrayList<Edge>();
		// It is also better to add odd/even number judgement here in the future
		if(inputEdgeList.isEmpty()){
			System.out.println("Edge List is Empty, there is nothing to save");			
			return EdgeList;			
		}
		for (int i = 0; i<inputEdgeList.size(); i = i+2)
		{
			Point2D pnt_1 = inputEdgeList.get(i);
			Point2D pnt_2 = inputEdgeList.get(i+1);
			//Edge tempEdge = new Edge();
		}
		
		return EdgeList;
	}
	
	
	// Temporarily
	private Node convertPnt2Node(Point2D inputPnt){
		Node outPutNode = new Node(0,0,0);
		return outPutNode;		
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
	//admin create a point
    public boolean newNode(String mapName,Point point)
    {
    	nodes=mapTable.get(mapName).getGraph().getNodes();
	Node node=new Node(nodes.get(nodes.size()-1).getID()+1,point.x,point.y);
	nodes.add(node);
    	try {
		saveMapGraph(mapName);
	     } catch (IOException e) {
	    	 e.printStackTrace();
	    }
    	return true;
    }
    
    //find node Id for the start of the edge
    public int findNodeId(String mapName,Point point)
    {
<<<<<<< HEAD
    	for(Node node: mapTable.get(mapName).getGraph().getNodes())
    	{
			if((node.getX()==point.x)&&(node.getY()==point.y))
			{ 
				return node.getID();   
			}
    	}
		return 0;
=======
	for(Node node: mapTable.get(mapName).getGraph().getNodes())
	if((node.getX()==point.x)&&(node.getY()==point.y))
	{ 
		return node.getID();
	}
	return 0;
>>>>>>> 9f3aadf4402578a8a2f3e279bdcd5e40548918d4
    }
	
	//admin specifies an edge
    public boolean newEdge(String mapName,Point source,Point destination)
    {
    	nodes=mapTable.get(mapName).getGraph().getNodes();
    	edges=mapTable.get(mapName).getGraph().getEdges();
    	Point point=validatePoint(mapName,source.x,source.y);
    	if((point.x==0)&&(point.y==0))
    	  {
    		newNode(mapName,point);
    		point=validatePoint(mapName,source.x,source.y);
    	  }
    	Node sourceNode=new Node(findNodeId(mapName,point),point.x,point.y);	
    	Node destinationNode=new Node(nodes.get(nodes.size()-1).getID()+1,destination.x,destination.y);
    	nodes.add(destinationNode);
    	//Don't need to calculate edgeLength. It will be calculated when loading edges 
    	Edge edge=new Edge(edges.get(edges.size()-1).getEdgeID()+1,sourceNode,destinationNode,0);
    	edges.add(edge);
    	try {
			saveMapGraph(mapName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return true;
    }
    
    //admin create a path
    public boolean newPath(String mapName,List<Point> points)
    {
    	nodes=mapTable.get(mapName).getGraph().getNodes();
    	edges=mapTable.get(mapName).getGraph().getEdges();
    	Iterator iterator=points.iterator();
    	//the start node of an edge
    	Node startNode=null;
    	//the end Node of an edge
    	Node endNode=null;
    	Point point=null;
    	Edge edge=null;
    	while(iterator.hasNext())
    	{
    	  point=(Point)iterator.next();
    	  endNode=new Node(nodes.get(nodes.size()-1).getID()+1,point.x,point.y);
    	  nodes.add(endNode);
    	  if(startNode!=null)
    	  {
    	     edge=new Edge(edges.get(edges.size()-1).getEdgeID()+1,startNode,endNode,0);
    	     edges.add(edge);
    	  }
    	  startNode=endNode;
    	}
    	try {
		saveMapGraph(mapName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return true;
    }
}
