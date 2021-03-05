package Crowd_Pressure_AGH.model.map;

import java.util.List;

/** The class representing a map to which walls are being added */
public class Map {
	private List<Wall> walls;

	public Map(List<Wall> walls) {
		super();
		this.walls = walls;
	}

	public List<Wall> getWalls() {
		return walls;
	}

	public void setWalls(List<Wall> walls) {
		this.walls = walls;
	}
}
