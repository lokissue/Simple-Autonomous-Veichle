package mycontroller;
import java.util.HashMap;
import mycontroller.MyAutoController;
import tiles.MapTile;
import utilities.Coordinate;

public interface DrivingStrategy {
	public void driving(HashMap<Coordinate, MapTile> currentView);

}
