package mycontroller;

import java.util.LinkedList;

import tiles.MapTile;
import tiles.TrapTile;
import tiles.MapTile.Type;
import utilities.Coordinate;

public abstract class BFSRouting implements IRouteStrategy{
	
	public static final int REACHABLE_COST = 1; 
//	public static final int LAVA_COST = 40; 
	
	public static final int[][] DIRECTIONS = new int[][] {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
	public static final int UNREACHABLE = -1;
	
	private int LAVA_COST;
	
	public BFSRouting(String mode) {
		if(mode.equals("health")) {
			this.LAVA_COST = 50;
		}else {
			this.LAVA_COST = 2;
		}
	}
	
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
			for(int[] d : DIRECTIONS) {
				int x = cur.x + d[0];
				int y = cur.y + d[1];
				if(x < 0 || y < 0 || 
						x >= costMap.length || y >= costMap[0].length) {
					continue;
				}
				MapTile mt = map[x][y];
				int cost = getTraverseCost(mt);
				if(cost == UNREACHABLE) {
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
	
	public int getTraverseCost(MapTile mt) {
		if(mt == null)
			return REACHABLE_COST;
		if(mt.getType() == Type.START || mt.getType() == Type.ROAD || mt.getType() == Type.FINISH)
			return REACHABLE_COST;
		if(mt.getType() == Type.TRAP) {
			TrapTile trapTile = (TrapTile) mt;
			if(trapTile.getTrap().equals("lava")) {
				return LAVA_COST;
			} else {
				return REACHABLE_COST;
			}
		}
		return UNREACHABLE;
	}
}
