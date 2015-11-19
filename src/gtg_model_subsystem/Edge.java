package gtg_model_subsystem;

import gtg_model_subsystem.Node;

public class Edge
{
		/**One endpoint of the edge
		*/
		private Node source;
		
		/**The other endpoint of the edge
		*/
		private Node destination;
		
		/**the ID of the edge
		*/
		private int edgeID;
		
		/**the length of the edge
		*/
		private double edgeLength;
		
		
		public Edge(int edgeID, Node source, Node destination, double edgeLength){
			this.source = source;
			this.destination = destination;
			this.edgeID = edgeID;
			this.edgeLength = edgeLength;
		}
		
		public Edge(int edgeID, Node source, Node destination){
			this(edgeID, source, destination, Math.sqrt(Math.pow(source.getX()-destination.getX(), 2)+Math.pow(source.getY()-destination.getY(), 2)));
		}
		
		/**return source of the edge
		*/
		public Node getSource(){
			return this.source;
		}
		
		/**return destination of the edge
		*/
		public Node getDestination() {
			return this.destination;
		}
		
		/**return id of the edge
		*/
		public int getEdgeID() {
			return this.edgeID;
		}
		
		/**return length of the edge
		*/
		public double getEdgeLength() {
			return this.edgeLength;
		}
}

