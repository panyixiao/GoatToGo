package gtg_model_subsystem;


/**
 */
public class Map {

		
		/**The graph contains the nodes in a map which
		*will be used to calculate the path
		*/
		private CoordinateGraph graph;
		private String mapName;
		private String mapType;
		private String mapImgURL;
		
		/**
		 * Constructor for Map.
		 * @param mapName String
		 * @param graph CoordinateGraph
		 * @param mapImgURL String
		 * @param mapType String
		 */
		public Map(String mapName, CoordinateGraph graph, String mapImgURL,String mapType){
			this.mapImgURL = mapImgURL;
			this.graph = graph;
		    this.mapName = mapName;
		    this.mapType = mapType;
		}

		
		/**
		 * Method setGraph.
		 * @param graph CoordinateGraph
		 */
		public void setGraph(CoordinateGraph graph){
			this.graph = graph;
		}
		

		 /**
		 * return the graph
		 * @return CoordinateGraph
		  */
		public CoordinateGraph getGraph() {
			return this.graph;
		}
		/**
		 * Method getMapName.
		 * @return String
		 */
		public String getMapName(){
			return this.mapName;
		}


		/**
		 * Method getMapImgURL.
		 * @return String
		 */
		public String getMapImgURL() {
			return mapImgURL;
		}


		/**
		 * Method setMapImgURL.
		 * @param mapImgURL String
		 */
		public void setMapImgURL(String mapImgURL) {
			this.mapImgURL = mapImgURL;
		}


		/**
		 * Method getMapType.
		 * @return String
		 */
		public String getMapType() {
			return mapType;
		}


		/**
		 * Method setMapType.
		 * @param mapType String
		 */
		public void setMapType(String mapType) {
			this.mapType = mapType;
		}
}
