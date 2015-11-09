package gtg_model_subsystem;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdminModel
{
	public static void main(String[] args) {
		
		AdminModel adminModel=new AdminModel();
		/*adminModel.newNode(200,200);
		
		Node node1=new Node(10,635,369);
		Node node2=new Node(11,786,509);
		adminModel.newEdge(node1,node2);
		
		List<Point> points=new ArrayList<Point>();
		points.add(new Point(23,20));
		points.add(new Point(56,89));
		adminModel.newPath(points);
		*/
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
	public void newNode(int x,int y)
    {
    	Node node=new Node(countNumber(nodes),x,y);
		nodes.add(node);
    	try {
			saveNodes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	//admin specifies an edge
    public void newEdge(Node source,Node destination)
    {
    	Edge edge=new Edge(countNumber(edges),source,destination,0);
    	edges.add(edge);
    	try {
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
    	Node node=null;
    	Point p=null;
    	while(iterator.hasNext())
    	{
    	  p=(Point)iterator.next();
    	  node=new Node(countNumber(nodes),p.x,p.y);
    	  nodes.add(node);
    	  if(startNode!=null)
    	  {
    		  newEdge(startNode,node);
    	  }
    	  startNode=node;
    	}
    	try {
			saveNodes();
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
