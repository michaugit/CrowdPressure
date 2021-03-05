package Crowd_Pressure_AGH.model;

import java.util.Optional;

/** The class responsible for information about obstacles and minimum distance */
public class MinimumDistance {
	private Double minimumDistance;
	private Optional<Double> notMovingObstacle;

	public MinimumDistance(Double minimumDistance,Optional<Double> notMovingObstacle) {
		super();
		this.minimumDistance = minimumDistance;
		this.setNotMovingObstacle(notMovingObstacle);
	}

	public Double getMinimumDistance() {
		return minimumDistance;
	}

	public void setMinimumDistance(Double minimumDistance) {
		this.minimumDistance = minimumDistance;
	}

	public Optional<Double> getNotMovingObstacle() {
		return notMovingObstacle;
	}

	public void setNotMovingObstacle(Optional<Double> notMovingObstacle) {
		this.notMovingObstacle = notMovingObstacle;
	}

}
