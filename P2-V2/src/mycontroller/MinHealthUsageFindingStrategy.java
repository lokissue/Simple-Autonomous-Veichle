package mycontroller;

import tiles.MapTile;
import tiles.TrapTile;
import tiles.MapTile.Type;

public class MinHealthUsageFindingStrategy extends FindingStrategy implements HealthConserve{
	
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
		return CANNOT_REACH;
	}
}
