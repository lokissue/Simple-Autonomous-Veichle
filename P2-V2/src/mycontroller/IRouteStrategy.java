package mycontroller;

import java.util.LinkedList;

import tiles.MapTile;
import utilities.Coordinate;

public interface IRouteStrategy {
	
	public LinkedList<Coordinate> getRoute(MapTile[][] map, 
			LinkedList<Coordinate> location, Coordinate curPosition);
	
	public String getType();
}
