package gtg_model_subsystem;

import java.awt.Image;

public class Map {

		
		/**The graph contains the nodes in a map which
		*will be used to calculate the path
		*/
		private CoordinateGraph graph;
		private String mapName;
		private String mapType;
		private String mapImgURL;
		
		public Map(String mapName, CoordinateGraph graph, String mapImgURL,String mapType){
			this.mapImgURL = mapImgURL;
			this.graph = graph;
		    this.mapName = mapName;
		    this.mapType=mapType;
		}

		
		public void setGraph(CoordinateGraph graph){
			this.graph = graph;
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
