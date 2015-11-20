package gtg_model_subsystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 */
public class JDijkstra {
	  private final List<Node> nodes;
	  private final List<Edge> edges;
	  private Set<Node> settledNodes;
	  private Set<Node> unSettledNodes;
	  private Map<Node, Node> predecessors;
	  private Map<Node, Integer> distance;

	  /**
	   * Constructor for JDijkstra.
	   * @param graph CoordinateGraph
	   */
	  public JDijkstra(CoordinateGraph graph) {
	    // create a copy of the array so that we can operate on this array
	    this.nodes = new ArrayList<Node>(graph.getNodes());
	    this.edges = new ArrayList<Edge>(graph.getEdges());
	  }

	  /**
	   * Method execute.
	   * @param source Node
	   */
	  public void execute(Node source) {
	    settledNodes = new HashSet<Node>();
	    unSettledNodes = new HashSet<Node>();
	    distance = new HashMap<Node, Integer>();
	    predecessors = new HashMap<Node, Node>();
	    distance.put(source, 0);
	    unSettledNodes.add(source);
	    while (unSettledNodes.size() > 0) {
	      Node node = getMinimum(unSettledNodes);
	      settledNodes.add(node);
	      unSettledNodes.remove(node);
	      findMinimalDistances(node);
	    }
	  }

	  /**
	   * Method findMinimalDistances.
	   * @param node Node
	   */
	  private void findMinimalDistances(Node node) {
	    List<Node> adjacentNodes = getNeighbors(node);
	    for (Node target : adjacentNodes) {
	      if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
	        distance.put(target, getShortestDistance(node) + getDistance(node, target));
	        predecessors.put(target, node);
	        unSettledNodes.add(target);
	      }
	    }

	  }

	  /**
	   * Method getDistance.
	   * @param node Node
	   * @param target Node
	   * @return int
	   */
	  private int getDistance(Node node, Node target) {
	    for (Edge edge : edges) {
	      if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
	        return (int)edge.getEdgeLength();
	      }
	    }
	    throw new RuntimeException("Should not happen");
	  }

	  /**
	   * Method getNeighbors.
	   * @param node Node
	   * @return List<Node>
	   */
	  private List<Node> getNeighbors(Node node) {
	    List<Node> neighbors = new ArrayList<Node>();
	   
	    for (Edge edge : edges) {
	      if (edge.getSource().equals(node)&& !isSettled(edge.getDestination()))
	        neighbors.add(edge.getDestination());
	    }
	    
	    return neighbors;
	  }

	  /**
	   * Method getMinimum.
	   * @param Nodees Set<Node>
	   * @return Node
	   */
	  private Node getMinimum(Set<Node> Nodees) {
	    Node minimum = null;
	    for (Node Node : Nodees) {
	      if (minimum == null) {
	        minimum = Node;
	      } 
	      else {
	        if (getShortestDistance(Node) < getShortestDistance(minimum)) {
	          minimum = Node;
	        }
	      }
	    }
	    return minimum;
	  }

	  /**
	   * Method isSettled.
	   * @param Node Node
	   * @return boolean
	   */
	  private boolean isSettled(Node Node) {
	    return settledNodes.contains(Node);
	  }

	  /**
	   * Method getShortestDistance.
	   * @param destination Node
	   * @return int
	   */
	  private int getShortestDistance(Node destination) {
	    Integer d = distance.get(destination);
	    if (d == null) {
	      return Integer.MAX_VALUE;
	    } else {
	      return d;
	    }
	  }

	  /*
	   * This method returns the path from the source to the selected target and
	   * NULL if no path exists
	   */
	  /**
	   * Method getPath.
	   * @param target Node
	   * @return LinkedList<Node>
	   */
	  public LinkedList<Node> getPath(Node target) {
	    LinkedList<Node> path = new LinkedList<Node>();
	    Node step = target;
	    // check if a path exists
	    if (predecessors.get(step) == null) {
	      return null;
	    }
	    path.add(step);
	    while (predecessors.get(step) != null) {
	      step = predecessors.get(step);
	      path.add(step);
	    }
	    // Put it into the correct order
	    Collections.reverse(path);
	    return path;
	  }
}
