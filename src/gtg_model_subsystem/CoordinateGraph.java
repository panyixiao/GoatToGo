package gtg_model_subsystem;

import gtg_model_subsystem.Node;
import gtg_model_subsystem.Edge;
import gtg_model_subsystem.Path;

import java.util.List;

public class CoordinateGraph
{
	/**All the nodes stored in a particular graph. 
	*/
	private List<Node> nodes;
	
	/** All of the edges stored in a particular graph.
	*/
	private List<Edge> edges;

	public CoordinateGraph(List<Node> nodes, List<Edge> edges){
		this.nodes = nodes;
		this.edges = edges;
	}
	/**add a new Node to the graph
	 *@pre Node is not NULL
	 *@post return true
	 *@post a Node is added into the nodes
	 */
	//public boolean addNode(Node node){
	//	
	//}
	
	/** add a new edge to the graph
	*@pre Edge is not NULL
	*@post return true
	*@post a Edge is added into edges
	*/
	//public boolean addEdge(Edge edge){
	//	
	//}
	public List<Node> getNodes(){
		return nodes;
	}
	public List<Edge> getEdges(){
		return edges;
	}
	
}
