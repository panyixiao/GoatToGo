package gtg_model_subsystem;
import java.util.List;
public class Path
{
		/**store the start point;
		*/
		private Node startPoint;
		
		/**store the end point;
		*/
		private Node endPoint;
		
		/**store the way points
		*/
		private List<Node> wayPoints;
		
		public Path(Node startPoint, Node endPoint, List<Node> wayPoints){
			this.startPoint = startPoint;
			this.endPoint = endPoint;
			this.wayPoints = wayPoints;
		}
		/**calculate and return a multiple layer path from campus to floor
		*or return a multiple layer path on campus
		*@pre start Node and end Node are not null
		*@post the path will be stored in the graph
		*/
		//public MultipleLayerPath calculatePathCampus(Node start,Node end){}
		
		/**calculate and return a multiple layer path from one floor to another floor
		*@pre start Node and end Node are not null
		*@post the path will be store in the graph
		*/
		//public MultipleLayerPath calculatePathBuilding(Node start,Node end){}
		
		/**calculate and return a signle layer path from room to room in a floor
		*@pre start Node and end Node are not null
		*@post the path will be store in the graph
		*/
		//public SingleLayerPath calculatePathFloor(Node start,Node end){}
		
		public void setStartPoint(Node startPoint){
			this.startPoint = startPoint;
		}
		public void setEndPoint(Node endPoint){
			this.endPoint = endPoint;
		}
		public void setPath(List<Node> wayPoints){
			this.wayPoints = wayPoints;
		}
		/**return start point
		*/
		public Node getStartPoint(){
			return this.startPoint;
		}
		
		/**return end point
		*/
		public Node getEndPoint(){
			return this.endPoint;
		}
	    
		/**return a List of Nodes
		*/
		public List<Node> getWayPoints(){
			return this.wayPoints;
		}
	}
