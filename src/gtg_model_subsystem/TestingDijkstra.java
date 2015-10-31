package gtg_model_subsystem;

import gtg_model_subsystem.Node;
import gtg_model_subsystem.CoordinateGraph;
import gtg_model_subsystem.Edge;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class TestingDijkstra {
	private List<Node> nodes;
	private List<Edge> edges;
	private FileLoader fileLoader;
	public void testDij(){
		
		edges = new ArrayList<Edge>();
		nodes = new ArrayList<Node>();
		fileLoader = new FileLoader();
		try {
			LoadNodes();
			LoadEdges();
			printNodes();
			printEdges();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	void LoadNodes() throws IOException{
		fileLoader.readNodesFile(nodes, MapNodeURLS.TEST_MAP_NODES);
		
	}
	void LoadEdges() throws IOException{
		fileLoader.readEdgesFile(nodes, edges, MapEdgeURLS.TEST_MAP_EDGES);
	}
	
	void printNodes(){
		for(Node node: nodes){
			System.out.print(node.getID() + " ");
			System.out.print(node.getX() + " ");
			System.out.print(node.getY() + " ");
			System.out.println();
		}
		System.out.println("END PRINT NODES");
	}
	void printEdges(){
		for(Edge edge: edges){
			System.out.print(edge.getEdgeID() + " ");
			System.out.print(edge.getSource().getID() + " ");
			System.out.print(edge.getDestination().getID() + " ");
			System.out.print(edge.getEdgeLength() + " ");
			System.out.println();
		}
		System.out.println("END PRINT EDGES");
	}
}
