package mycontroller;

import controller.CarController;
import swen30006.driving.Simulation;
import swen30006.driving.Simulation.StrategyMode;
import world.Car;

import java.util.HashMap;
import java.util.LinkedList;


import tiles.MapTile;
import tiles.TrapTile;
import tiles.MapTile.Type;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

public class MyAutoController extends CarController{		
	private MapTile[][] map;
	private LinkedList<Coordinate> parcelLocation;
	private LinkedList<Coordinate> destLocation;
	private LinkedList<Coordinate> route;
	private IRouteStrategy strategy;


	public MyAutoController(Car car) {
		super(car);
		map = new MapTile[mapWidth() + 1][mapHeight() + 1];
		parcelLocation = new LinkedList<Coordinate>();
		route = new LinkedList<Coordinate>(); 
		destLocation = new LinkedList<Coordinate>();
		
//		browsingStrategy = new BrowsingStrategy();
//		findingStrategy = new FindingStrategy();
		
	}

	@Override
	public void update() {
		// update the map we cached
		recordMap();
		
		// get current position
		Coordinate curPosition = new Coordinate(getPosition());
		
		// select targets, find parcel or find exit
		LinkedList<Coordinate> targets = 
				numParcelsFound() < numParcels() ? parcelLocation : destLocation; 
		
		// find the route to target
		strategy = RouteStrategyFactory.getInstance().getStrategy("finding");
		route = strategy.getRoute(map, targets, curPosition);
		
		
		// if no route is finded, need to browse the map to get more info
		if(route == null || route.isEmpty()) {
			strategy = RouteStrategyFactory.getInstance().getStrategy("browsing");
			route = strategy.getRoute(map, null, curPosition);
		}
		
		// follow the selected route
		followRoute();
	}
	
 	private void followRoute() {
 		System.out.println(route);
 		applyForwardAcceleration();
 		if(route == null)
 			return;
 		if(route.size() < 1)
 			return;
 		
		Coordinate next = route.get(1);
		Coordinate cur = new Coordinate(getPosition());
		if(next.x == cur.x) {
			if(next.y + 1 == cur.y) {
				moveSouth();
			}
			else if (next.y - 1 == cur.y){
				moveNorth();
			}
		}
		else if(next.y == cur.y) {
			if(next.x + 1 == cur.x) {
				moveWest();
			}
			else if (next.x - 1 == cur.x){
				moveEast();
			}
		}
	}

	private void moveSouth() {
		Direction d = getOrientation();
		if(d == Direction.SOUTH) {
			applyForwardAcceleration();
		}
		else if(d == Direction.NORTH) {
			applyReverseAcceleration();
		}
		else if(d == Direction.EAST) {
			turnRight();
		}
		else if(d == Direction.WEST){
			turnLeft();
		}
		else {
			throw new RuntimeException("No Direction");
		}
	}

	private void moveNorth() {
		Direction d = getOrientation();
		if(d == Direction.NORTH) {
			applyForwardAcceleration();
		}
		else if(d == Direction.SOUTH) {
			applyReverseAcceleration();
		}
		else if(d == Direction.EAST) {
			turnLeft();
		}
		else if(d == Direction.WEST){
			turnRight();
		}
		else {
			throw new RuntimeException("No Direction");
		}
	}

	private void moveEast() {
		Direction d = getOrientation();
		if(d == Direction.EAST) {
			applyForwardAcceleration();
		}
		else if(d == Direction.WEST) {
			applyReverseAcceleration();
		}
		else if(d == Direction.NORTH) {
			turnRight();
		}
		else if(d == Direction.SOUTH){
			turnLeft();
		}
		else {
			throw new RuntimeException("No Direction");
		}
	}

	private void moveWest() {
		Direction d = getOrientation();
		if(d == Direction.WEST) {
			applyForwardAcceleration();
		}
		else if(d == Direction.EAST) {
			applyReverseAcceleration();
		}
		else if(d == Direction.NORTH) {
			turnLeft();
		}
		else if(d == Direction.SOUTH){
			turnRight();
		}
		else {
			throw new RuntimeException("No Direction");
		}
	}

	private void recordMap() {
		HashMap<Coordinate, MapTile> currentView = getView();
		Coordinate currentPosition = new Coordinate(getPosition());
		parcelLocation.remove(currentPosition);
		for(Coordinate c : currentView.keySet()) {
			MapTile mt = currentView.get(c);
			try {
				map[c.x][c.y] = mt;
			}catch (Exception e) {
			}

			if(mt.isType(Type.TRAP)) {
				TrapTile trapTile = (TrapTile) mt;
				if(trapTile.getTrap().equals("parcel")) {
					if(!parcelLocation.contains(c))
						parcelLocation.addFirst(c);
				}
			}
			
			if(mt.isType(Type.FINISH)) {
				if(!destLocation.contains(c))
					destLocation.addFirst(c);
			}
		}
	}


}

