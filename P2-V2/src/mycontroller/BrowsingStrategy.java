package mycontroller;

import java.util.LinkedList;

import tiles.MapTile;
import utilities.Coordinate;

public class BrowsingStrategy extends BFSRouting{
	
	public BrowsingStrategy(String mode) {
		super(mode);
	}

	@Override
	public LinkedList<Coordinate> getRoute(MapTile[][] map, LinkedList<Coordinate> location, Coordinate curPosition) {
		LinkedList<Coordinate> path = new LinkedList<Coordinate>();
		for(int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if(map[i][j] == null) {
					bfs(map, curPosition, new Coordinate(i, j), path);
				}
				if(path.size() > 0)
					return path;
			}
		}
		return path;
	}
}
