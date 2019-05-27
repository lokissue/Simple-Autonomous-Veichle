package mycontroller;

public class RouteStrategyFactory {
	private static RouteStrategyFactory instance = null;
	private IRouteStrategy strategy = null; 
	
	public IRouteStrategy getStrategy(String type, String mode) {
		if(type.equals("browsing")) {
			strategy = new BrowsingStrategy(mode);
		}else if(type.equals("finding")) {
			strategy = new FindingStrategy(mode);
		}
		return strategy;
	}
	
	public static synchronized RouteStrategyFactory getInstance() {
		if(instance == null) {
			instance = new RouteStrategyFactory();
		}
		return instance;
	}
}
