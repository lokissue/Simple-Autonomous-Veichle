# SWEN30006
Autonomous Vehicles

Car has limited view on the map, so the A* algorithm may not be suitable here.

Use Strategy pattern

1. Car traverse map use wall-following and BFS route selection

2. Record locations in sight while traversing (apply weight)

3. Pick up parcel when spotted and number of required parcels is not met

All strategy now can be created by singleton strategy factory. The BFS model credit to: Xinda Zheng
