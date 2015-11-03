package gtg_model_subsystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

//Dijkstra
public class Dijkstra {
	private static final int maxDis = Integer.MAX_VALUE;
	
	//the adjacent matrix for the graph
	int[][] matrix;
	
	//start point
	int startIndex;
	
	//save the distance from start point to any other point
	HashMap<Integer, Integer> distanceMap = new HashMap<Integer, Integer>();
	
	//points whose shortest paths have been found
	Set<Integer> findedSet = new HashSet<Integer>();
	
	//save the shortest path using the predecessor 
	int[] result;

	public Dijkstra(int[][] matrix, int start) {
		this.matrix = matrix;
		this.startIndex = start;
		this.result=new int[matrix.length];
	}

	public void find() {
		
		//initialize distanceMap by using start point
		for (int i = 0; i < matrix.length; i++) {
		  distanceMap.put(i, matrix[startIndex][i]);
		}
        
		//
		int predecessor=startIndex;
		while (findedSet.size() != matrix.length) {
			int currentMinIndex = currentMinIndex();
			
			//use this node to update all the distances
			for (int i = 0; i < matrix.length; i++) {
			  if (!findedSet.contains(i) && matrix[currentMinIndex][i] != maxDis
				&& matrix[currentMinIndex][i] + distanceMap.get(currentMinIndex) < distanceMap.get(i))
				{
				  distanceMap.put(i, matrix[currentMinIndex][i] + distanceMap.get(currentMinIndex));
				}
			}
			
			//put it into findedset and save the path
			result[currentMinIndex]=predecessor;
			predecessor=currentMinIndex;
			findedSet.add(currentMinIndex);
		}
	}

	//print shortest distance from source to any other points
	public void printDistance(){
		Iterator<Entry<Integer, Integer>> it = distanceMap.entrySet().iterator();
		int min = Integer.MIN_VALUE;
		int minIndex = -1;
		while (it.hasNext()) {
			Entry<Integer, Integer> entry = it.next();
			System.out.println(startIndex+" to "+entry.getKey()+" shortest distance:"+entry.getValue());
		}
		
		//print the path from start to the last point
		System.out.print("From point 0 to point "+(matrix.length-1)+",the path is:");
		
		int temp=matrix.length-1;
		System.out.print(temp+" ");
		while(result[temp]!=startIndex)
		{
			System.out.print(result[temp]+" ");
			temp=result[temp];
		}
		System.out.println(result[temp]);
	}
	
	// return the point which has minimum distance(not in findedSet)
	private int currentMinIndex() {
		Iterator<Entry<Integer, Integer>> it = distanceMap.entrySet().iterator();
		int min = Integer.MAX_VALUE;
		int minIndex = -1;
		while (it.hasNext()) {
			Entry<Integer, Integer> entry = it.next();
			if (!findedSet.contains(entry.getKey()) && entry.getValue() < min) {
				min = entry.getValue();
				minIndex = entry.getKey();
			}
		}
		return minIndex;
	}
    
	public static void main(String[] args) {
		int[][] inputMatrix = new int[][] {
				{0,5,2,maxDis},{5,0,3,2},{2,3,0,1},{maxDis,2,1,0}
		};
		Dijkstra path = new Dijkstra(inputMatrix, 0);
		path.find();
		path.printDistance();

	}
}
