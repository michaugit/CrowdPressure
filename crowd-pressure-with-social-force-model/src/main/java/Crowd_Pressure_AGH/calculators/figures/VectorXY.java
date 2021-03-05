package Crowd_Pressure_AGH.calculators.figures;

import Crowd_Pressure_AGH.model.Position;

/** The class representing X Y vector */
public class VectorXY {
	private double x;
	private double y;

	public VectorXY(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public VectorXY(Position position) {
		this(position.getX(),position.getY());
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
