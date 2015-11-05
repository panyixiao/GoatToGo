package gtg_model_subsystem;

import gtg_model_subsystem.Node;
import gtg_model_subsystem.Edge;
import gtg_model_subsystem.Admin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class FileProcessing {
	
	public void readNodesFile(List<Node> nodes, String mapNodeURL) throws IOException{
		File file = new File(mapNodeURL);
		BufferedReader buffer = new BufferedReader(new FileReader(file));
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
	}
	public void readEdgesFile(List<Node> nodes, List<Edge> edges, String mapEdgeURL) throws IOException{
		File file = new File(mapEdgeURL);
		BufferedReader buffer = new BufferedReader(new FileReader(file));
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
	}
	public void readAdmin(List<Admin> admins, String adminURL) throws IOException{
		File file = new File(adminURL);
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		String line;
		String[] lines;
		int nodeId1, nodeId2;
		while((line = buffer.readLine()) != null){
				line = line.trim();
				lines = line.split("[\\s+]");
				
				Admin admin = new Admin(lines[0], lines[1]);
				admins.add(admin);
		}
	}
	public void saveNodesFile(List<Node> nodes, String mapNodeURL) throws IOException{
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
	public void saveEdgesFile(List<Edge> edges, String mapEdgeURL) throws IOException{
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
	private double calculateDistance(double x1, double x2, double y1, double y2)
	{
		return Math.sqrt(Math.pow(x2-x1, 2)+ Math.pow(y2 - y1, 2));
	}
	
}
