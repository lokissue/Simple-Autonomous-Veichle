package mycontroller;

import java.util.LinkedList;

import tiles.MapTile;
import utilities.Coordinate;

public abstract class BFSRoutingStrategy {
	
	public static final int[][] directions = new int[][] {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
	public static final int CANNOT_REACH = -1;
	//
	/**
	 * 
	 * @param map  the world
	 * @param location  the possible targets
	 * @param curPosition  the current position
	 * @return  the best route to follow
	 */
	public abstract LinkedList<Coordinate> getRoute(MapTile[][] map, 
			LinkedList<Coordinate> location, Coordinate curPosition);
	
	
	/**
	 * using bfs to find the best route
	 * 
	 * @param map   the world
	 * @param start   the starting point
	 * @param target   the ending point
	 * @param path    the best path
	 * @return    the estimated cost for the following the best path
	 */
	protected int bfs(MapTile[][] map, Coordinate start, 
			Coordinate target, LinkedList<Coordinate> path) {
		// initialization for bfs
		LinkedList<Coordinate> queue = new LinkedList<Coordinate>();
		int[][] costMap = new int[map.length][map[0].length];
		Coordinate[][] from = new Coordinate[map.length][map[0].length];
		costMap[start.x][start.y] = 1;
		queue.offer(start);
		
		// bfs with memory to record the min cost
		while(!queue.isEmpty()) {
			Coordinate cur = queue.poll();
			int curCost = costMap[cur.x][cur.y];
			for(int[] d : directions) {
				int x = cur.x + d[0];
				int y = cur.y + d[1];
				if(x < 0 || y < 0 || 
						x >= costMap.length || y >= costMap[0].length) {
					continue;
				}
				MapTile mt = map[x][y];
				int cost = getTraverseCost(mt);
				if(cost == CANNOT_REACH) {
					continue;
				}
				if(costMap[x][y] == 0 || cost + curCost < costMap[x][y]) {
					from[x][y] = cur;
					costMap[x][y] = cost + curCost;
					queue.offer(new Coordinate(x, y));
				}
			}
		}
		
		// reconstruct the path
		if(from[target.x][target.y] != null) {
			path.add(target);
			Coordinate temp = from[target.x][target.y];
			while(temp != null) {
				path.addFirst(temp);
				temp = from[temp.x][temp.y];
			}
			return costMap[target.x][target.y];
		}
		
		// if no path is found
		return Integer.MAX_VALUE;
	}
	
	public abstract int getTraverseCost(MapTile mt);
}
