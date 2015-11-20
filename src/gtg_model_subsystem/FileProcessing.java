package gtg_model_subsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
/**
 */
public class FileProcessing {
	
	/**
	 * The method loadMapList load maps from master map list stored in masterMapList.txt
	
	
	 * @return master map list * @throws IOException */
	public ArrayList<Map> loadMapList() throws IOException
	{
		//Get the master map list text file reference
		String masterMapListURL = "ModelFiles"+System.getProperty("file.separator")+ "masterMapList.txt";
		//reading each map into line
		String line;
		//array of stored types for each map
		String[] lines;
		File file = null;
		BufferedReader buffer = null;
		Map tempMap = null;
		//the maps from the master map list text file reference
		ArrayList<Map> tempMapList = null;
		
		try{
			file = new File(masterMapListURL);
			buffer = new BufferedReader(new FileReader(file));
			tempMapList = new ArrayList<Map>();
			
			while((line=buffer.readLine())!=null){
				line.trim();
				lines = line.split("[\\s+]");
				//create a Map using stored types(name,graph,img url,type) for each map
				tempMap=new Map(lines[0], null, lines[1],lines[2]);
				//add the created map into the map list
				tempMapList.add(tempMap);
			}
			buffer.close();
		}
		catch(IOException e){
			System.out.println(e.toString());
		}
		return tempMapList;
	}
	/**
	 * deleteMapFromMaster will take in a map name and delete the given line that is associated with the
	 * map name given.
	 * @param mapName the name of the map needs to be deleted from masterMapList.txt
	
	
	 * @return true if delete succeeded false otherwise * @throws IOException  */
	public boolean deleteMapFromMaster(String mapName) throws IOException{
			boolean deleteSuccess = true;
			//String references to master list and a temporary written master list file
			String masterMapListURL = "ModelFiles"+System.getProperty("file.separator")+ "masterMapList.txt";
			String masterMapListTemp = "ModelFiles"+System.getProperty("file.separator")+ "masterMapList_temp.txt";
			
			//variables for manipulation
			File originalFile = null;
			File tempFile = null;
			BufferedReader reader = null;
			BufferedWriter writer = null;
			//Current line being read from file
			String line;
			//Array of map values from master list text file
			String lines[];
			try{
				originalFile = new File(masterMapListURL);
				tempFile = new File(masterMapListTemp);
				reader = new BufferedReader(new FileReader(originalFile));
				writer = new BufferedWriter(new FileWriter(tempFile));
				//WHILE a line exists in master list text file
				while((line = reader.readLine()) != null){
						//remove the end line
						line.trim();
						//split values and store into lines array
						lines = line.split("[\\s+]");
						//IF the line map name does not equal null and the line map name does not equal map name
						if(lines[0] != null && !lines[0].equals(mapName)){
								//THEN re-write line to temporary file
								System.out.println("line is not search deletion re-write");
								writer.write(line  + System.getProperty("line.separator"));
						}
				}
			}catch(IOException e){
				//CATCH failure and set the delete success to false
				System.out.println(e.toString());
				deleteSuccess = false;
			} finally{
				writer.close();
				reader.close();
				//DELETE original master list file
				originalFile.delete();
				//RE-NAME new temp list to master list
				deleteSuccess = tempFile.renameTo(originalFile);
			}
			return deleteSuccess;
	}
	/**
	 * saveMapToMaster method saves a newly added map from admin view to master map list.
	 * @param mapName the name of map to be saved.
	
	 * @param mapType the type of map that we are saving (IE possible campus map).
	
	
	 * @param mapImgURL String
	 * @return true if write was successful false if write failed. * @throws IOException */
	public boolean saveMapToMaster(String mapName, String mapImgURL, String mapType) throws IOException{
			boolean writeSuccess = true;
			try{
				//Get the master map list text file reference
				String masterMapListURL = "ModelFiles"+System.getProperty("file.separator")+ "masterMapList.txt";
				System.out.println(masterMapListURL);
				//Create a file writer that will write to file, append value set to true to append to end of document
				FileWriter fstream = new FileWriter(masterMapListURL, true);
				//Create a buffered writer for the file stream index
			    BufferedWriter out = new BufferedWriter(fstream);
			    //Write newly created map to end of master map list
			    out.write(System.getProperty("line.separator") + mapName + " " + mapImgURL + " " + mapType);
			    out.close();
			}
			catch(IOException e){
				//Print out error if either map file does not exist
				System.out.println(e.toString());
				//return false for failure
				writeSuccess = false;
			}
			return writeSuccess;
	}
	
	
	/**
	 * Method readNodesFile.
	 * @param nodes List<Node>
	 * @param mapName String
	 * @return boolean
	 * @throws IOException
	 */
	public boolean readNodesFile(List<Node> nodes, String mapName) throws IOException{
		String mapNodeURL = "ModelFiles"+System.getProperty("file.separator")+"NodeFiles"+System.getProperty("file.separator")+mapName+"_Node.txt";
		System.out.println(mapNodeURL);
		boolean readSuccess = true;
		File file = new File(mapNodeURL);
		BufferedReader buffer = null;
		//CHECK to see if there is a new file that needs to be created
		//true of created
		//false if file exists
		if(createFile(file)){
			//return true because we created a new file
			return true;
		}else{
			try{
				System.out.println("Reading instead");
				buffer = new BufferedReader(new FileReader(file));
				String line;
				String[] lines;
				while((line = buffer.readLine()) != null){
					line = line.trim();
					lines = line.split("[\\s+]");
					
					Node node = new Node(Integer.parseInt(lines[0]), 
										 Integer.parseInt(lines[1]), 
										 Integer.parseInt(lines[2])
										 );
					nodes.add(node);
				}
			}catch(Exception e){
				System.out.println(e.toString());
				readSuccess = false;
			}finally{
				buffer.close();
			}
			return readSuccess;
		}
	}
	/**
	 * Method readEdgesFile.
	 * @param nodes List<Node>
	 * @param edges List<Edge>
	 * @param mapName String
	 * @return boolean
	 * @throws IOException
	 */
	public boolean readEdgesFile(List<Node> nodes, List<Edge> edges, String mapName) throws IOException{
		String mapEdgeURL = "ModelFiles"+System.getProperty("file.separator")+"EdgeFiles"+System.getProperty("file.separator")+mapName+"_Edge.txt";		
		File file = new File(mapEdgeURL);
		BufferedReader buffer = null; 
		boolean readSuccess = true;
		//CHECK to see if there is a new file that needs to be created
		//true of created
		//false if file exists
		if(createFile(file)){
			//return true because we created a new file
			return true;
		}	
		else{
			try{
				buffer = new BufferedReader(new FileReader(file));
				String line;
				String[] lines;
				int nodeId1, nodeId2;
				while((line = buffer.readLine()) != null){
					line = line.trim();
					lines = line.split("[\\s+]");
					
					//Subtract one for arraylist index value
					nodeId1 = Integer.parseInt(lines[1]) - 1;
					nodeId2 = Integer.parseInt(lines[2]) - 1;
		
					Edge edge = new Edge(Integer.parseInt(lines[0]), 
										 nodes.get(nodeId1), 
										 nodes.get(nodeId2),
										 calculateDistance(nodes.get(nodeId1).getX(), nodes.get(nodeId2).getX(), nodes.get(nodeId1).getY(), nodes.get(nodeId2).getY())
										 );
					
					edges.add(edge);
				}
			}catch(Exception e){
				System.out.println(e.toString());
				readSuccess = false;
			}finally{
				buffer.close();
			}
			return readSuccess;
		}
	}
	/**
	 * Method readAdmin.
	 * @param admins List<Admin>
	 * @throws IOException
	 */
	public void readAdmin(List<Admin> admins) throws IOException{
		String adminURL = "ModelFiles"+System.getProperty("file.separator")+"adminFile.txt";
		File file = new File(adminURL);
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		String line;
		String[] lines;
		while((line = buffer.readLine()) != null){
				line = line.trim();
				lines = line.split("[\\s+]");
				
				Admin admin = new Admin(lines[0], lines[1]);
				admins.add(admin);
		}
		buffer.close();
	}
	/**
	 * Method saveNodesFile.
	 * @param nodes List<Node>
	 * @param mapName String
	 * @throws IOException
	 */
	public void saveNodesFile(List<Node> nodes, String mapName) throws IOException{
		String mapNodeURL = "ModelFiles"+System.getProperty("file.separator")+"NodeFiles"+System.getProperty("file.separator")+mapName+"_Node.txt";
		try{
		    FileWriter fstream = new FileWriter(mapNodeURL);
		    BufferedWriter out = new BufferedWriter(fstream);
		    for(Node node: nodes){
		    	out.write(node.getID() + " " + node.getX()+ " " + node.getY());
		    	out.newLine();
		    }
		    System.out.println("File Node Write Success!");
		    out.close();
		}
		catch(Exception E){
			System.out.println(E.toString());
		}
	}
	/**
	 * Method saveEdgesFile.
	 * @param edges List<Edge>
	 * @param mapName String
	 * @throws IOException
	 */
	public void saveEdgesFile(List<Edge> edges, String mapName) throws IOException{
		String mapEdgeURL = "ModelFiles"+System.getProperty("file.separator")+"EdgeFiles"+System.getProperty("file.separator")+mapName+"_Edge.txt";
		try{
		    FileWriter fstream = new FileWriter(mapEdgeURL);
		    BufferedWriter out = new BufferedWriter(fstream);
		    for(Edge edge: edges){
		    	out.write(edge.getEdgeID() + " " + edge.getSource().getID() + " " + edge.getDestination().getID());
		    	out.newLine();
		    }
		    System.out.println("File Edge Write Success!");
		    out.close();
		}
		catch(Exception E){
			System.out.println(E.toString());
		}
	}
	
	/**
	 * Method createFile.
	 * @param file File
	 * @return boolean
	 */
	private boolean createFile(File file){
		boolean createFile = true;
		//IF file does not exist create new file
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				System.out.println(e.toString());
				
			}
		}
		else{
			return false;
		}
		return createFile;
	}
	/**
	 * Method calculateDistance.
	 * @param x1 double
	 * @param x2 double
	 * @param y1 double
	 * @param y2 double
	 * @return double
	 */
	private double calculateDistance(double x1, double x2, double y1, double y2)
	{
		return Math.sqrt(Math.pow(x2-x1, 2)+ Math.pow(y2 - y1, 2));
	}
}
