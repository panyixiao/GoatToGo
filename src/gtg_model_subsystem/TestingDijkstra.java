package gtg_model_subsystem;

import gtg_model_subsystem.Node;
import gtg_model_subsystem.CoordinateGraph;
import gtg_model_subsystem.Edge;
import gtg_model_subsystem.ShortestPath;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class TestingDijkstra {
	private List<Node> nodes;
	private List<Edge> edges;
	private LinkedList<Node> path;
	private CoordinateGraph graph;
	private List<Admin> admins;
	private FileProcessing fileProcessing;
	
	public void testDij(){
		
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}//END CATCH loadNodes/Edges
		
		//testing for saving nodes and edges
		try {
			saveNodes();
			saveEdges();
		}
			catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
		}//END CATCH saveNodes/Edges
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
