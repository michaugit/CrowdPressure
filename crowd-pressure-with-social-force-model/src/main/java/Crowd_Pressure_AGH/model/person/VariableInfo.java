package Crowd_Pressure_AGH.model.person;

import Crowd_Pressure_AGH.DefaultConfig;
import Crowd_Pressure_AGH.calculators.GeometricCalculator;
import Crowd_Pressure_AGH.calculators.figures.Vector;
import Crowd_Pressure_AGH.model.Position;

/** The class containing dynamic variables of person Simulation properties */
public class VariableInfo {
	private double visionCenter;
	private double destinationAngle;
	private Position destinationPoint;
	private Position position;
	private Position nextPosition;
	private double desiredDirection;
	private Vector desiredSpeed;
	private Vector desiredAcceleration;
	private boolean finished;
	private double  crowdPressure;
	private double  maxCrowdPressure;

	public VariableInfo(double visionCenter, Position destinationPoint, Position position) {
		this.visionCenter = visionCenter;
		this.destinationPoint = destinationPoint;
		this.position = position;
		this.nextPosition = position;
		this.destinationAngle = GeometricCalculator.calculateAngle.apply(nextPosition, destinationPoint);
		this.setFinished(GeometricCalculator.isBigger.apply(
				GeometricCalculator.distance.apply(position, destinationPoint), DefaultConfig.MAX_DISTANCE_TO_GOAL));
		this.desiredDirection = visionCenter;
		this.desiredSpeed = new Vector(desiredDirection, 0);
		this.desiredAcceleration = new Vector(Double.NaN, 0.0);
		this.crowdPressure = 1.0;
	}

	public VariableInfo(Position destinationPoint, Position position) {
		this(GeometricCalculator.calculateAngle.apply(position, destinationPoint), destinationPoint, position);
	}

	public void setDestinationAngle(double destinationAngle) {
		this.destinationAngle = destinationAngle;
	}

	public double getVisionCenter() {
		return visionCenter;
	}

	public void setVisionCenter(double visionCenter) {
		this.visionCenter = visionCenter;
	}

	public Position getDestinationPoint() {
		return destinationPoint;
	}

	public void setDestinationPoint(Position destinationPoint) {
		this.destinationPoint = destinationPoint;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public double getDesiredDirection() {
		return desiredDirection;
	}

	public void setDesiredDirection(double desiredDirection) {
		this.desiredDirection = desiredDirection;
	}

	public Vector getDesiredSpeed() {
		return desiredSpeed;
	}

	public void setDesiredSpeed(Vector desiredSpeed) {
		this.desiredSpeed = desiredSpeed;
	}

	public double getDestinationAngle() {
		return destinationAngle;
	}

	public Position getNextPosition() {
		return nextPosition;
	}

	public void setNextPosition(Position nextPosition) {
		this.nextPosition = nextPosition;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Vector getDesiredAcceleration() {
		return desiredAcceleration;
	}

	public void setDesiredAcceleration(Vector desiredAcceleration) {
		this.desiredAcceleration = desiredAcceleration;
	}

	public double getCrowdPressure() {
		return crowdPressure;
	}

	public void setCrowdPressure(double crowdPressure) {
		this.crowdPressure = crowdPressure;
		if(crowdPressure > getMaxCrowdPressure()){
			setMaxCrowdPressure(crowdPressure);
		}
	}

	public double getMaxCrowdPressure(){return maxCrowdPressure;}

	public void setMaxCrowdPressure(double crowdPressure){ this.maxCrowdPressure=crowdPressure;}

}
