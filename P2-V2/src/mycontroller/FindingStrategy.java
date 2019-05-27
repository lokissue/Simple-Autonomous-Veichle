package mycontroller;

import java.util.LinkedList;

import tiles.MapTile;
import utilities.Coordinate;

public abstract class FindingStrategy extends BFSRoutingStrategy{

	@Override
	public LinkedList<Coordinate> getRoute(MapTile[][] map, 
			LinkedList<Coordinate> location, Coordinate curPosition){
		LinkedList<Coordinate> minPath = new LinkedList<Coordinate>();
		int minCost = Integer.MAX_VALUE;
		for(Coordinate targetCoordinate : location) {
			Coordinate currentCoordinate = curPosition;
			LinkedList<Coordinate> path = new LinkedList<>();
			int cost = bfs(map, currentCoordinate, targetCoordinate, path);
			if(cost > 0 && cost < minCost && path.size() > 0) {
				minPath = path;
				minCost = cost;
			}
		}
		return minPath;
	}
}
