package gtg_model_subsystem;

import java.awt.Image;

public class Map {
		 /**The background image is loaded into Map
		 */
		 private Image img;
		
		/**The graph contains the nodes in a map which
		*will be used to calculate the path
		*/
		private CoordinateGraph graph;
		
		String mapName;
		
		public Map(String mapName, CoordinateGraph graph, Image img){
			this.img = null;
			this.graph = graph;
		    this.mapName = mapName;
		}
		/**Load the image into the map 
		*according to the image's path
		*@pre image exists
		*@post img!=NULL
		*/
		//public void drawMap(String filePath){}
		
		public void setGraph(CoordinateGraph graph){
			this.graph = graph;
		}
		/**return img
		*/
		public Image getImg(){
			return this.img;
		}

		 /**
		 * return the graph
		 */
		public CoordinateGraph getGraph() {
			return this.graph;
		}
		public String getMapName(){
			return this.mapName;
		}
}
