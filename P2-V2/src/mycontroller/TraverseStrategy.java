package mycontroller;

import java.util.HashMap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class TraverseStrategy implements DrivingStrategy {
	
	private MyAutoController controller;
	
	public TraverseStrategy(MyAutoController controller) {	this.controller = controller;	}
	
	public boolean checkFollowingWall(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
		if(controller.getWallFollowing()) {
			switch(orientation){
			case EAST:
				return controller.checkNorth(currentView);
			case NORTH:
				return controller.checkWest(currentView);
			case SOUTH:
				return controller.checkEast(currentView);
			case WEST:
				return controller.checkSouth(currentView);
			default:
				return false;
			}
		}else {
			switch(orientation){
			case EAST:
				return controller.checkSouth(currentView);
			case NORTH:
				return controller.checkEast(currentView);
			case SOUTH:
				return controller.checkWest(currentView);
			case WEST:
				return controller.checkNorth(currentView);
			default:
				return false;
			}
		}
	}
	
	@Override
	public void driving(HashMap<Coordinate, MapTile> currentView) {
		if(controller.getWallFollowing()) {
			if(controller.getSpeed() < controller.getMaxSpeed()){       // Need speed to turn and progress toward the exit
				controller.applyForwardAcceleration();   // Tough luck if there's a wall in the way
			}
			if (controller.IsFollowingWall()) {
				// If wall no longer on left, turn left
				if(!checkFollowingWall(controller.getOrientation(), currentView)) {
					controller.turnLeft();
				} else {
					// If wall on left and wall straight ahead, turn right
					if(controller.checkWallAhead(controller.getOrientation(), currentView)) {
						controller.turnRight();
					}
				}
			} else {
				// Start wall-following (with wall on left) as soon as we see a wall straight ahead
				if(controller.checkWallAhead(controller.getOrientation(),currentView)) {
					if (controller.checkWallAhead(WorldSpatial.changeDirection
							(controller.getOrientation(), WorldSpatial.RelativeDirection.RIGHT), currentView)) {
						controller.turnLeft();
						controller.switchWallFollowingMode();
					}else {
						controller.turnRight();
					}
					((MyAutoController) controller).setIsFollowingWall(true);
				}
			}
		}else {
			if(controller.getSpeed() < controller.getMaxSpeed()){       // Need speed to turn and progress toward the exit
				controller.applyForwardAcceleration();   // Tough luck if there's a wall in the way
			}
			if (controller.IsFollowingWall()) {
				// If wall no longer on left, turn left
				if(!checkFollowingWall(controller.getOrientation(), currentView)) {
					controller.turnRight();
				} else {
					// If wall on left and wall straight ahead, turn right
					if(controller.checkWallAhead(controller.getOrientation(), currentView)) {
						controller.turnLeft();
					}
				}
			} else {
				// Start wall-following (with wall on left) as soon as we see a wall straight ahead
				if(controller.checkWallAhead(controller.getOrientation(),currentView)) {
					if (controller.checkWallAhead(WorldSpatial.changeDirection
							(controller.getOrientation(), WorldSpatial.RelativeDirection.LEFT), currentView)) {
						controller.turnRight();
						controller.switchWallFollowingMode();
					}else {
						controller.turnLeft();
					}
					((MyAutoController) controller).setIsFollowingWall(true);
				}
			}
		}
		
	}
}
