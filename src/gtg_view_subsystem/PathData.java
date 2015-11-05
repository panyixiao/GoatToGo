package gtg_view_subsystem;

import java.awt.Point;
import java.util.ArrayList;

public class PathData {
	private Point startPoint;
	private Point endPoint;
	private ArrayList<Point> coordinateList = new ArrayList<Point>();
	private ArrayList<String> mapNames = new ArrayList<String>();
	
	public PathData(){
		
	}
	
	public void setStartPoint(Point startPoint){
		this.startPoint = startPoint;
	}

	public Point getStartPoint(){
		return this.startPoint;
	}
	
	public void setEndPoint(Point endPoint){
		this.endPoint = endPoint;
	}

	public Point getEndPoint(){
		return this.endPoint;
	}
	
	public void setArrayOfPoints(ArrayList<Point> coordinateList){
		this.coordinateList = coordinateList;
	}
	
	public ArrayList<Point> getArrayOfPoints(){
		return this.coordinateList;
	}
	
	public void setArrayOfMapNames(ArrayList<String> mapNames){
		this.mapNames = mapNames;
	}
	
	public ArrayList<String> getArrayOfMapNames(){
		return this.mapNames;
	}
}
