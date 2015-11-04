package gtg_model_subsystem;

import gtg_model_subsystem.Node;
import gtg_model_subsystem.CoordinateGraph;
import gtg_model_subsystem.Edge;
import gtg_model_subsystem.Dijkstra;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class TestingDijkstra {
	private List<Node> nodes;
	private List<Edge> edges;
	private LinkedList<Node> path;
	private CoordinateGraph graph;
	private List<Admin> admins;
	private FileProcessing fileProcessing;
	
	public void testDij(int start,int end){
		
		edges = new ArrayList<Edge>();
		nodes = new ArrayList<Node>();
		admins = new ArrayList<Admin>();
		fileProcessing = new FileProcessing();
		//testing for loading admins
		try{
			loadAdmin();
			printAdmins();
		}
		catch(IOException e){
			System.out.println(e.toString());
		}
		//testing for loading of nodes/edges
		try {
			loadNodes();
			loadEdges();
			graph = new CoordinateGraph(nodes, edges);
			
			printNodes();
			printEdges();
			
			int nodeId1;
			int nodeId2;
			int edgeLength;
			Edge e;
			Dijkstra d;
			int matrix[][]=new int[10][10];
			for(int i=0;i<=9;i++)
			  for(int j=0;j<=9;j++)
				matrix[i][j]=10000;  
			
			Iterator<Edge> it=edges.iterator();
			while(it.hasNext())
			{
			     e=(Edge)it.next();
			     nodeId1=e.getSource().getID();
			     nodeId2=e.getDestination().getID();
			     edgeLength=(int)e.getEdgeLength();
			     System.out.println(nodeId1+" "+nodeId2+" "+edgeLength);
			     matrix[nodeId1][nodeId2]=edgeLength;
			}
			
			for(int i=0;i<=9;i++)
			{
			  for(int j=0;j<=9;j++)
				System.out.print(matrix[i][j]+" ");
			  System.out.println();
			}
			d=new Dijkstra(matrix,start,end);
			d.find();
			d.printDistance();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}//END CATCH loadNodes/Edges
		
		//testing for saving nodes and edges
		/*try {
			saveNodes();
			saveEdges();
		}
			catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
		}//END CATCH saveNodes/Edges
		*/
	}
	void loadAdmin() throws IOException{
		fileProcessing.readAdmin(admins, "ModelFiles\\adminFile.txt");
	}
	void loadNodes() throws IOException{
		fileProcessing.readNodesFile(nodes, MapNodeURLS.TEST_MAP_NODES);
		
	}
	void loadEdges() throws IOException{
		fileProcessing.readEdgesFile(nodes, edges, MapEdgeURLS.TEST_MAP_EDGES);
	}
	void saveNodes() throws IOException{
		fileProcessing.saveNodesFile(nodes, MapNodeURLS.TEST_MAP_NODES);
	}
	void saveEdges() throws IOException{
		fileProcessing.saveEdgesFile(edges, MapEdgeURLS.TEST_MAP_EDGES);
	}
	void printNodes(){
		for(Node node: nodes){
			System.out.print(node.getID() + " " + node.getX() + " " + node.getY());
			System.out.println();
		}
		System.out.println("END PRINT NODES");
	}
	void printEdges(){
		for(Edge edge: edges){
			System.out.print(edge.getEdgeID() + " " + edge.getSource().getID() + " " + edge.getDestination().getID() + " " + edge.getEdgeLength());
			System.out.println();
		}
		System.out.println("END PRINT EDGES");
	}
	void printAdmins(){
		for(Admin admin: admins){
			System.out.print(admin.getUsername()+ " " + admin.getPassword());
			System.out.println();
		}
		System.out.println("END OF PRINT ADMIN");
	}
}
