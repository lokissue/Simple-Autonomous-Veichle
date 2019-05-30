# SWEN30006
Autonomous Vehicles

UML: https://www.draw.io/#G1h0PnPs-NrTwVMpSroCiwphK6Q-Eab32r

Car has limited view on the map, so the A* algorithm may not be suitable here.

Use Strategy pattern

1. Car traverse map use wall-following and BFS route selection

2. Record locations in sight while traversing (weight applied)

3. Pick up parcel when spotted and number of required parcels is not met (parcel has high level of priority)

All strategy now can be created by singleton strategy factory. 

Simple strategy pattern reference: https://dzone.com/articles/design-patterns-strategy

Strategy pattern with factory: https://dzone.com/articles/java-the-strategy-pattern


The idea of the autocontroller is base on Breadth First Search. The car (robot) will initially strat with a null Linked List, and go to search the map with following the walls, it will record the points which are within visual range into the linked List, it will upload the Linked List every steps. And when it find a parcel, it will decide how to get the parcel with the "weight". The best path will depend on bfs.

For the health and fuel conserves, I set a weight (or call it cost) for the lava and researchable. Which means, the controller will decided to go across the lava or find another way to get the parcel with the lavaCost. For example, I set the lavaCost as 50 for the health conserves, when the car find a parcel, it will spend 50 more steps to find another way (or go via another found way) rather than go across the lava, so that it can follow the 'health-first' rule even it need to spend more fuel. Of course, if the parcel arround has already searched and the lava is the only way to get the parcel, then it will go across the lava to make sure we finish the job. On the other hand, I set the lavaCost as 2 for the fuel conserves, which means it will go across lava rather than spend more steps, so that to use less fuel.

In the BFSRouting.java, we have a method call BFSRouting, which can be easily changed the cost of both converses, so that programmer can be easily optimized the path of the controller for any particular situation. Moreover the mothod can be added more modes or more relational cost to improve the controller or make further functions.

When the car get enough parcels, it will directly go to the end-point, if it have searched the path to the end-point, otherwise just continue to search for the path to end-point.
