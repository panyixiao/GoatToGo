package gtg_view_subsystem;

import java.awt.Point;
import java.util.ArrayList;

public class PathData {
	private Point startPoint;
	private Point endPoint;
	private ArrayList<Point> wayPoints = new ArrayList<Point>();
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
	
	public void setWayPoints(ArrayList<Point> wayPoints){
		this.wayPoints = wayPoints;
	}
	
	public ArrayList<Point> getWayPoints(){
		return this.wayPoints;
	}
	
	public void setArrayOfMapNames(ArrayList<String> mapNames){
		this.mapNames = mapNames;
	}
	
	public ArrayList<String> getArrayOfMapNames(){
		return this.mapNames;
	}
}
