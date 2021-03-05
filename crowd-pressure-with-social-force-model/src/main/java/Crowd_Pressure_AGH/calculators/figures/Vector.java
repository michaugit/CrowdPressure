package Crowd_Pressure_AGH.calculators.figures;

/** The class representing physical vector */
public class Vector {
	private double angle;
	private double value;

	public Vector(double angle, double value) {
		super();
		this.angle = angle;
		this.value = value;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
