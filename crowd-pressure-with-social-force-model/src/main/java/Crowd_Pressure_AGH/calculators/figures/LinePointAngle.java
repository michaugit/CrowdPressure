package Crowd_Pressure_AGH.calculators.figures;

import Crowd_Pressure_AGH.model.Position;

/** The class providing angle based on a line */
public class LinePointAngle {
	private Position point;
	private double angle;

	public LinePointAngle(Position point, double angle) {
		super();
		this.point = point;
		this.angle = angle;
	}

	public Position getPoint() {
		return point;
	}

	public void setPoint(Position point) {
		this.point = point;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
}
