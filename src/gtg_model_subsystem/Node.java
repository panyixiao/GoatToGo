package gtg_model_subsystem;

public class Node
	{
		/** the node's id
		 */
		private int id;
	    /** the node's x coordinate in the graph
	     */
		private int x;
		
		/** the node's y coordinate in the graph
		 */
		private int  y;
		
		private int floorNum;
		// floor number, 1 by default
		private int entranceID;
		// entrance ID, 0 by default, means it is not an entrance
		private String buildingName;
		// building name
		private String nodeType;
		// type of node, for example, classroom, staircase or restroom.
		private String nodeDescription;
		// text description of node
		/** the information about the node
		 *such as its name and location
		 */
		//private NodeAttribute attribute;
		
		public Node(int id, int x, int y, int floorNum, int entranceID, String buildingName, String nodeType, String nodeDescription) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.floorNum=floorNum;
			this.entranceID=entranceID;
			this.buildingName=buildingName;
			this.nodeType=nodeType;
			this.nodeDescription=nodeDescription;
		}
		// construction
		
		public Node(int id, int x, int y) {
			this(id, x, y, 1, 0, "null", "null", "null");
		}
		// overload construction, compatible with current code
		
		public int getID(){
			return this.id;
		}
		public int getX(){
			return this.x;
		}
		

		public int getY(){
			return this.y;
		}
		public int getFloorNum(){
			return this.floorNum;
		}
		
		public int getEntranceID(){
			return this.entranceID;
		}
		
		public String getBuildingName(){
			return this.buildingName;
		}
		
		public String getNodeType(){
			return this.nodeType;
		}
		
		public String getNodeDescription(){
			return this.nodeDescription;
		}
		
		
	}
