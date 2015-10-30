import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class ShortestPath {
	private static final int minDis = 0;
	private static final int maxDis = Integer.MAX_VALUE;
	
	
	//the ajdacent matrix for th graph
	int[][] matrix;
	
	//start point
	int startIndex;
	
	//save the distance from start point to any other point
	HashMap<Integer, Integer> distanceMap = new HashMap<Integer, Integer>();
	
	//points whose shortest paths have been found
	Set<Integer> findedSet = new HashSet<Integer>();
	
	//only record the path only for the last point because we can set it to be our end point 
	ArrayList result;

	public ShortestPath(int[][] matrix, int start) {
		this.matrix = matrix;
		this.startIndex = start;
	}

	public void find() {
		//initialize distanceMap by using start point
		result=new ArrayList();
		for (int i = 0; i < matrix.length; i++) {
		  distanceMap.put(i, matrix[startIndex][i]);
		}

		while (findedSet.size() != matrix.length) {
			int currentMinIndex = currentMinIndex();
			
			//use this node to update all the distances
			for (int i = 0; i < matrix.length; i++) {
				if (!findedSet.contains(i) && matrix[currentMinIndex][i] != maxDis
						&& matrix[currentMinIndex][i] + distanceMap.get(currentMinIndex) < distanceMap.get(i))
					{
					  distanceMap.put(i, matrix[currentMinIndex][i] + distanceMap.get(currentMinIndex));
					  if(i==matrix.length-1)
						result.add(currentMinIndex); 
					}
			}
			
			//put it into findedset
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
		
		//print the path from point 0 to the last point
		System.out.print("From point 0 to point "+(matrix.length-1)+",the path is:");
		System.out.print("0 ");
		Iterator it2=result.iterator();
		while(it2.hasNext())
		{
			System.out.print(it2.next().toString()+" ");
		}
		System.out.println(matrix.length-1);
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
		int[][] inputMatrix = new int[][] { { minDis, 2, maxDis, 1, maxDis, maxDis, maxDis }, { maxDis, minDis, maxDis, 3, 10, maxDis, maxDis },
				{ 4, maxDis, minDis, maxDis, maxDis, 5, maxDis }, { maxDis, maxDis, 2, minDis, 2, 8, 4 },
				{ maxDis, maxDis, maxDis, maxDis, minDis, maxDis, 6 }, { maxDis, maxDis, maxDis, maxDis, maxDis, minDis, maxDis },
				{ maxDis, maxDis, maxDis, maxDis, maxDis, 1, minDis } };
		ShortestPath path = new ShortestPath(inputMatrix, 0);
		path.find();
		path.printDistance();

	}
}
