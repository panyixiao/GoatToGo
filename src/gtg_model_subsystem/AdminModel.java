/*package gtg_model_subsystem;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdminModel
{
	public static void main(String[] args) {
		
		AdminModel adminModel=new AdminModel();
		/*adminModel.newNode(new Point(200,200));
	  
		adminModel.newEdge(new Point(100,100),new Point(200,200));
		
		List<Point> points=new ArrayList<Point>();
		points.add(new Point(23,20));
		points.add(new Point(56,89));
		adminModel.newPath(points);
		
		
	}
	private List<Node> nodes;
	private List<Edge> edges;
	private Path path;
	private CoordinateGraph graph;
	private List<Admin> admins;
	private FileProcessing fileProcessing;
	private Map testMap;
	
	public AdminModel() 
	{
		edges = new ArrayList<Edge>();
		nodes = new ArrayList<Node>();
		admins = new ArrayList<Admin>();
		fileProcessing = new FileProcessing();
		try{
			loadAdmin();
			printAdmins();
		}
		catch(IOException e){
			System.out.println(e.toString());
		}
		try {
			loadNodes();
			loadEdges();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}//END CATCH loadNodes/Edges
	}
    
	//generate a new node id
	public int countNumber(List list)
	{
		Iterator it=list.iterator();
		int count=1;
		while(it.hasNext())
		{
			it.next();
			count++;
		}
		return count;
	}
	
	//admin create a point
	public void newNode(Point point)
    {
    	Node node=new Node(countNumber(nodes),point.x,point.y);
		nodes.add(node);
    	try {
			saveNodes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	//admin specifies an edge
    public void newEdge(Point source,Point destination)
    {
    	Node sourceNode=new Node(countNumber(nodes),source.x,source.y);
    	nodes.add(sourceNode);
    	Node destinationNode=new Node(countNumber(nodes),destination.x,destination.y);
    	nodes.add(destinationNode);
    	//Don't need to calculate edgeLength. It will be calculated when loading edges 
    	Edge edge=new Edge(countNumber(edges),sourceNode,destinationNode,0);
    	edges.add(edge);
    	try {
			saveNodes();
    		saveEdges();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //admin create a path
    public void newPath(List<Point> points)
    {
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
    	  endNode=new Node(countNumber(nodes),point.x,point.y);
    	  nodes.add(endNode);
    	  if(startNode!=null)
    	  {
    		 edge=new Edge(countNumber(edges),startNode,endNode,0);
    	     edges.add(edge);
    	  }
    	  startNode=endNode;
    	}
    	try {
			saveNodes();
			saveEdges();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
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
    
    public void loadAdmin() throws IOException{
		fileProcessing.readAdmin(admins, "ModelFiles\\adminFile.txt");
	}
	public void loadNodes() throws IOException{
		fileProcessing.readNodesFile(nodes, MapNodeURLS.TEST_MAP_NODES);
	}
	public void loadEdges() throws IOException{
		fileProcessing.readEdgesFile(nodes, edges, MapEdgeURLS.TEST_MAP_EDGES);
	}
	public void printAdmins(){
		for(Admin admin: admins){
			System.out.print(admin.getUsername()+ " " + admin.getPassword());
			System.out.println();
		}
		System.out.println("END OF PRINT ADMIN");
	}
	public void saveNodes() throws IOException{
		fileProcessing.saveNodesFile(nodes, MapNodeURLS.TEST_MAP_NODES);
	}
	public void saveEdges() throws IOException{
		fileProcessing.saveEdgesFile(edges, MapEdgeURLS.TEST_MAP_EDGES);
	}
}
*/
