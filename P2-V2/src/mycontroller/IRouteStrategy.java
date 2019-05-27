package mycontroller;

import java.util.LinkedList;

import tiles.MapTile;
import utilities.Coordinate;

public interface IRouteStrategy {
	
	/**
	 * @param map  the world
	 * @param location  the possible targets
	 * @param curPosition  the current position
	 * @return  the best route to follow
	 */
	public LinkedList<Coordinate> getRoute(MapTile[][] map, 
			LinkedList<Coordinate> location, Coordinate curPosition);
	
}
