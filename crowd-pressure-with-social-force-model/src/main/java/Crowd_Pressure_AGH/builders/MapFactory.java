package Crowd_Pressure_AGH.builders;

import java.util.ArrayList;
import java.util.List;

import Crowd_Pressure_AGH.model.map.Map;
import Crowd_Pressure_AGH.model.map.PrimitiveWall;
import Crowd_Pressure_AGH.model.map.Wall;
import Crowd_Pressure_AGH.SimulationTypes;
import Crowd_Pressure_AGH.model.Position;

/** The class creating the maps */
public class MapFactory {

	public Map getMap(SimulationTypes sym) {
		if (sym.equals(SimulationTypes.SYM_ONE_WALL_TWO_GROUPS)){
			return getMapOneWallTwoGroups();
		} else if (sym.equals(SimulationTypes.SYM_ROOM_WITHOUT_OBSTACLE)){
			return getRoomWithoutObstacle();
		} else if (sym.equals(SimulationTypes.SYM_ROOM_WITH_VERTICAL_OBSTACLE)){
			return getRoomWithVerticalObstacle();
		} else if (sym.equals(SimulationTypes.SYM_ROOM_WITH_HORIZONTAL_OBSTACLE)) {
			return getRoomWithHorizontalObstacle();
		} else if (sym.equals(SimulationTypes.SYM_ROOM_WITH_CORRIDOR)){
			return getRoomWithCorridor();
		} else if (sym.equals(SimulationTypes.SYM_ROOM_WITH_CORRIDOR_AND_COLUMN)){
			return getRoomWithCorridorAndColumn();
		} else if (sym.equals(SimulationTypes.SYM_THREE_GROUPS)){
			return getEmpty();
		} else if (sym.equals(SimulationTypes.SYM_EMPTY)){
			return getEmpty();
		}

		return getEmpty();
	}

	public Map getMapOneWallTwoGroups() {
		List<Wall> walls = new ArrayList<>();

		walls.add(new PrimitiveWall(new Position(70, 76), new Position(70, 45)));

		walls.add(new PrimitiveWall(new Position(70, 0), new Position(70, 35)));
		Map map = new Map(walls);

		return map;
	}

	public Map getRoomWithoutObstacle(){
		Map map = new Map(getRoom());
		return map;
	}

	public Map getRoomWithVerticalObstacle(){
		Position obstacleTop = new Position(86, 45);
		Position obstacleBottom = new Position(86, 35);

		List<Wall> walls = getRoom();
		walls.add(new PrimitiveWall(obstacleTop, obstacleBottom));

		Map map = new Map(walls);
		return map;
	}

	public Map getRoomWithHorizontalObstacle(){
		Position obstacleLeft = new Position(70, 40);
		Position obstacleRight = new Position(90, 40);

		List<Wall> walls = getRoom();
		walls.add(new PrimitiveWall(obstacleLeft, obstacleRight));

		Map map = new Map(walls);
		return map;
	}

	public Map getRoomWithCorridor(){
		Position topLeft = new Position(79.8, 43.2);
		Position topRight = new Position(105, 43.2);
		Position bottomLeft= new Position(79.8, 36.8);
		Position bottomRight= new Position(105, 36.8);

		List<Wall> walls = getRoom();
		walls.add(new PrimitiveWall(topLeft,topRight));
		walls.add(new PrimitiveWall(bottomLeft,bottomRight));


		Map map = new Map(walls);
		return map;
	}

	public Map getRoomWithCorridorAndColumn(){
		Position topLeft = new Position(79.8, 43.2);
		Position topRight = new Position(105, 43.2);
		Position bottomLeft= new Position(79.8, 36.8);
		Position bottomRight= new Position(105, 36.8);

		Position c_left=new Position(50, 40-0.1);
		Position c_rt=  new Position(65, 44+1);
		Position c_rb=  new Position(65, 36-1);

		Position c_left_2=new Position(50, 40+0.1);
		Position c_rt_2=  new Position(64.8, 44.1+1);
		Position c_rb_2=  new Position(64.8, 35.9-1);

		List<Wall> walls = getRoom();
		walls.add(new PrimitiveWall(topLeft,topRight));
		walls.add(new PrimitiveWall(bottomLeft,bottomRight));

		walls.add(new PrimitiveWall(c_rt_2,c_rb_2));
		walls.add(new PrimitiveWall(c_left,c_rt));
		walls.add(new PrimitiveWall(c_left_2,c_rb));

		Map map = new Map(walls);
		return map;
	}

	public Map getEmpty(){
		return new Map(new ArrayList<Wall>());
	}

	public List<Wall> getRoom(){
		List<Wall> walls = new ArrayList<>();

		Position rt_1 = new Position(80, 70);
		Position rb_1 = new Position(80, 43);

		Position rt_2 = new Position(80, 37);
		Position rb_2 = new Position(80, 10);

		Position lt = new Position(10, 70);
		Position lb = new Position(10, 10);

		walls.add(new PrimitiveWall(lt, lb));
		walls.add(new PrimitiveWall(lt, rt_1));
		walls.add(new PrimitiveWall(lb, rb_2));
		walls.add(new PrimitiveWall(rt_1, rb_1));
		walls.add(new PrimitiveWall(rt_2, rb_2));

		return walls;
	}
}
