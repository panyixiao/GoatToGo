package gtg_model_subsystem;

import gtg_model_subsystem.Node;
import gtg_model_subsystem.Edge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class FileLoader {
	
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
	public void readEdgesFile(List<Node> nodes, List<Edge> edges, String mapNodeURL) throws IOException{
		File file = new File(mapNodeURL);
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		String line;
		String[] lines;
		int nodeId1, nodeId2;
		while((line = buffer.readLine()) != null){
			line = line.trim();
			lines = line.split("[\\s+]");
			
			
			nodeId1 = Integer.parseInt(lines[1]);
			nodeId2 = Integer.parseInt(lines[2]);

			Edge edge = new Edge(Integer.parseInt(lines[0]), 
								 nodes.get(nodeId1 - 1), 
								 nodes.get(nodeId2 - 1),
								 calculateDistance(nodes.get(nodeId1).getX(), nodes.get(nodeId2).getX(), nodes.get(nodeId1).getY(), nodes.get(nodeId2).getY())
								 );
			edges.add(edge);
		}
	}
	private double calculateDistance(double x1, double x2, double y1, double y2)
	{
		return Math.sqrt(Math.pow(x2-x1, 2)+ Math.pow(y2 - y1, 2));
	}
}
