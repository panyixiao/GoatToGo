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
	
	//end point
	int endIndex;
	
	//save the distance from start point to any other point
	HashMap<Integer, Integer> distanceMap = new HashMap<Integer, Integer>();
	
	//points whose shortest paths have been found
	Set<Integer> findedSet = new HashSet<Integer>();
	
	//save the shortest path using the predecessor 
	int[] result;
	
	int[] resultPath;

	public Dijkstra(int[][] matrix, int start,int end) {
		this.matrix = matrix;
		this.startIndex = start;
		this.endIndex=end;
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

	//print shortest distance from source to the destination
	public void printShortestDistance(){
		Iterator<Entry<Integer, Integer>> it = distanceMap.entrySet().iterator();
		int min = Integer.MIN_VALUE;
		int minIndex = -1;
		while (it.hasNext()) {
			Entry<Integer, Integer> entry = it.next();
			if(entry.getKey()==endIndex)
			{
			  System.out.println(startIndex+" to "+entry.getKey()+" shortest distance:"+entry.getValue());
			}
		}
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
	public void printPath(){
		//print the path from start to the last point
		System.out.print("From point "+startIndex+" to point "+endIndex+",the path is:");
		
		int temp=endIndex;
		System.out.print(temp+" ");
		while(result[temp]!=startIndex)
		{
			System.out.print(result[temp]+" ");
			temp=result[temp];
		}
		System.out.println(result[temp]);
		System.out.println(result.length);
	}
	public int[] getResultPath(){
		return this.result;
	}
}
