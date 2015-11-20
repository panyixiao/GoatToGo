package gtg_model_subsystem;

/**
 */
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
		
		/** the information about the node
		 *such as its name and location
		 * @param id int
		 * @param x int
		 * @param y int
		 */
		//private NodeAttribute attribute;
		
		public Node(int id, int x, int y){
			this.id = id;
			this.x = x;
			this.y = y;
		}
		
		/**
		 * Method getID.
		 * @return int
		 */
		public int getID(){
			return this.id;
		}
		/**
		 * Method getX.
		 * @return int
		 */
		public int getX(){
			return this.x;
		}
		

		/**
		 * Method getY.
		 * @return int
		 */
		public int getY(){
			return this.y;
		}
	}
